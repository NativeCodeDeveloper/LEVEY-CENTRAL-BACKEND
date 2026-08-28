# Pruebas HTTP - Tipos de Usuarios

Aplicación ejecutándose en `http://localhost:8080`.

Todas las respuestas se muestran usando `jq`. Si el registro con ID `1` no existe, reemplaza ese valor por un ID válido.

Define el JWT de Clerk antes de ejecutar las solicitudes:

```bash
TOKEN_CLERK='eyJhbGciOiJSUzI1NiIsImNhdCI6ImNsX0I3ZDRQRDExMUFBQSIsImtpZCI6Imluc18zSU41VGo0RTFWY2RMb2p3RGVlcHQwNjZteFEiLCJvaWF0IjoxNzg3ODU4ODk2LCJ0eXAiOiJKV1QifQ.eyJhenAiOiJodHRwOi8vbG9jYWxob3N0OjMwMDAiLCJleHAiOjE3ODc4NTg5NTYsImZ2YSI6WzkyMywtMV0sImlhdCI6MTc4Nzg1ODg5NiwiaXNzIjoiaHR0cHM6Ly9pbmZpbml0ZS1jb2QtMTM2My5jbGVyay5hY2NvdW50cy5kZXYiLCJuYmYiOjE3ODc4NTg4ODYsIm8iOnsiaWQiOiJvcmdfM0lRQ0plMHhIWTA5aUVGQjREbnpTMWZQbkhTIiwicm9sIjoiYWRtaW4iLCJzbGciOiJjbGluaWNhLXNhbi12aWNpZW50ZS0xNzg3Njg3MzM0NTQ4MDEwMzI1In0sInNpZCI6InNlc3NfM0lUem5GdTJQTUtGRlkwbzk4T3Rna3gxWGpOIiwic3RzIjoiYWN0aXZlIiwic3ViIjoidXNlcl8zSU41dmJRdWVVb1Z4azZ4V2kweEt3ZW14NlYiLCJ2IjoyfQ.hjoPxCMYft2u6qWyaacAcdxKGx-t0MDisWNiQXY1mVVHGKCmctFt4MgpSmpmJYzDNuabv3c-a4oPvSZuNUYyC5tDqvxdV2UWWucvosYTWEvaqNW9kriuTWRC1Vn7_DPYGFIm2Jx-NKV4Axj2MeKK6TdkS68Y3yY0SApI5gpi8gozUAjn6Qvi-JsiTaDrHSoFMBW8PYJ9CGvXs3Ja6GQXfmk9QcyfBvipYy2XGEBnuvF5JI2mrBkghLIg8LOJGOS8nHnhfzJ5rCymndQ9jcj3LS069DLqjp44n46v2mjCHbyPTdCGGHdIQH9xLJ7vm2Wt56qysYbt_03X8yeqw06LKA'

Las solicitudes usan este valor mediante el encabezado `Authorization: Bearer`. No uses la clave secreta, la publishable key ni un identificador de usuario como Bearer token.

## Observaciones del controlador

Antes de ejecutar las pruebas, revisa estas dos situaciones en `TipoUsuarioController`:

- La búsqueda por nombre está documentada como `GET /tipos-usuarios/buscar?nombreTipo={nombreTipo}`, pero el parámetro de consulta no debe formar parte del valor de `@GetMapping`. El `curl` de esta documentación usa la ruta prevista: `/tipos-usuarios/buscar?nombreTipo=Administrador`.
- Activar y desactivar tienen actualmente el mismo `@PatchMapping`: `/tipos-usuarios/{idTipoUsuarios}/activar`. La prueba de desactivación usa la ruta prevista por el comentario: `/tipos-usuarios/{idTipoUsuarios}/desactivar`. Mientras ambas anotaciones sean iguales, Spring puede rechazar el arranque por un mapeo ambiguo.

## Crear tipo de usuario

### Éxito esperado: HTTP 201

```bash
curl -sS -X POST "http://localhost:8080/tipos-usuarios" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" \
  -d '{
    "nombreTipo": "Administrador",
    "descripcion": "Tipo de usuario con permisos administrativos",
    "usuarioCreacionId": 1
  }' | jq .
```

### Fracaso esperado: HTTP 400 por información incompleta

```bash
curl -i -X POST "http://localhost:8080/tipos-usuarios" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" \
  -d '{
    "nombreTipo": ""
  }'
```

## Listar tipos de usuarios

### Éxito esperado: HTTP 200

```bash
curl -i -X GET "http://localhost:8080/tipos-usuarios" \
  -H "Authorization: Bearer ${TOKEN_CLERK}"
```

Si no existen registros, el controlador devuelve HTTP 404.

## Listar tipos de usuarios activos

### Éxito esperado: HTTP 200

```bash
curl -sS -X GET "http://localhost:8080/tipos-usuarios/activos" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

Si no existen tipos de usuarios activos, el controlador devuelve HTTP 404.

## Buscar tipo de usuario por ID

### Éxito esperado: HTTP 200

```bash
curl -sS -X GET "http://localhost:8080/tipos-usuarios/1" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

### Fracaso esperado: HTTP 404 por ID inexistente

```bash
curl -sS -X GET "http://localhost:8080/tipos-usuarios/999999" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

### Fracaso esperado: HTTP 400 por ID no numérico

```bash
curl -sS -X GET "http://localhost:8080/tipos-usuarios/no-numerico" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

## Buscar tipo de usuario por nombre

### Éxito esperado: HTTP 200

```bash
curl -sS -G "http://localhost:8080/tipos-usuarios/buscar" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" \
  --data-urlencode "nombreTipo=Administrador" | jq .
```

### Fracaso esperado: HTTP 404 por nombre inexistente

```bash
curl -sS -G "http://localhost:8080/tipos-usuarios/buscar" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" \
  --data-urlencode "nombreTipo=TipoInexistente999999" | jq .
```

## Actualizar tipo de usuario

### Éxito esperado: HTTP 200

```bash
curl -sS -X PUT "http://localhost:8080/tipos-usuarios" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" \
  -d '{
    "idTipoUsuarios": 1,
    "nombreTipo": "Administrador actualizado",
    "descripcion": "Descripción actualizada del tipo de usuario",
    "usuarioModificacionId": 1
  }' | jq .
```

### Fracaso esperado: HTTP 400 por falta de ID

```bash
curl -sS -X PUT "http://localhost:8080/tipos-usuarios" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" \
  -d '{
    "nombreTipo": "Tipo sin ID",
    "descripcion": "No debería actualizarse"
  }' | jq .
```

### Fracaso esperado: HTTP 400 por tipo de usuario inexistente

```bash
curl -sS -X PUT "http://localhost:8080/tipos-usuarios" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" \
  -d '{
    "idTipoUsuarios": 999999,
    "nombreTipo": "Tipo inexistente",
    "descripcion": "No debería actualizarse"
  }' | jq .
```

## Activar tipo de usuario

### Éxito esperado: HTTP 200

```bash
curl -sS -X PATCH "http://localhost:8080/tipos-usuarios/1/activar" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

### Fracaso esperado: HTTP 400 por tipo de usuario inexistente

```bash
curl -sS -X PATCH "http://localhost:8080/tipos-usuarios/999999/activar" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

## Desactivar tipo de usuario

### Éxito esperado: HTTP 200

```bash
curl -sS -X PATCH "http://localhost:8080/tipos-usuarios/1/desactivar" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

### Fracaso esperado: HTTP 400 por tipo de usuario inexistente

```bash
curl -sS -X PATCH "http://localhost:8080/tipos-usuarios/999999/desactivar" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

## Resumen de rutas

| Método | Ruta | Resultado esperado |
|---|---|---|
| POST | `/tipos-usuarios` | Crear un tipo de usuario |
| GET | `/tipos-usuarios` | Listar todos los tipos |
| GET | `/tipos-usuarios/activos` | Listar tipos activos |
| GET | `/tipos-usuarios/{idTipoUsuarios}` | Buscar por ID |
| GET | `/tipos-usuarios/buscar?nombreTipo={nombreTipo}` | Buscar por nombre |
| PUT | `/tipos-usuarios` | Actualizar un tipo |
| PATCH | `/tipos-usuarios/{idTipoUsuarios}/activar` | Activar un tipo |
| PATCH | `/tipos-usuarios/{idTipoUsuarios}/desactivar` | Desactivar un tipo |

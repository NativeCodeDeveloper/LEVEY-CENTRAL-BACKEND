# Pruebas `curl` — PermisoAccion

Documentación de solicitudes HTTP para `PermisoAccionController`.

## Configuración

- URL base: `http://localhost:8080`
- Autorización temporal: `xxxx-ejemplo-bearer`
- Encabezado utilizado en todas las solicitudes:

```text
Authorization: Bearer xxxx-ejemplo-bearer
```

> Reemplaza `xxxx-ejemplo-bearer` por el token Bearer real cuando esté disponible.

## Crear permiso y acción

Ruta del controlador: `POST /permiso/crearPermisoAccion`

### Caso exitoso

```bash
curl -sS -X POST "http://localhost:8080/permiso/crearPermisoAccion" \
  -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsImNhdCI6ImNsX0I3ZDRQRDExMUFBQSIsImtpZCI6Imluc18zSU41VGo0RTFWY2RMb2p3RGVlcHQwNjZteFEiLCJvaWF0IjoxNzg4MTk3MjE4LCJ0eXAiOiJKV1QifQ.eyJhenAiOiJodHRwOi8vbG9jYWxob3N0OjMwMDEiLCJleHAiOjE3ODgxOTcyNzgsImZ2YSI6WzY1NjEsLTFdLCJpYXQiOjE3ODgxOTcyMTgsImlzcyI6Imh0dHBzOi8vaW5maW5pdGUtY29kLTEzNjMuY2xlcmsuYWNjb3VudHMuZGV2IiwibmJmIjoxNzg4MTk3MjA4LCJvIjp7ImlkIjoib3JnXzNJUUNKZTB4SFkwOWlFRkI0RG56UzFmUG5IUyIsInJvbCI6ImFkbWluIiwic2xnIjoiY2xpbmljYS1zYW4tdmljaWVudGUtMTc4NzY4NzMzNDU0ODAxMDMyNSJ9LCJzaWQiOiJzZXNzXzNJVHpuRnUyUE1LRkZZMG85OE90Z2t4MVhqTiIsInN0cyI6ImFjdGl2ZSIsInN1YiI6InVzZXJfM0lONXZiUXVlVW9WeGs2eFdpMHhLd2VteDZWIiwidiI6Mn0.Wv4sFvN6rktSm1ifYd3-R8WIAgyypxzitWZlxM-KGLNq-yed35uLlPFz5jNO_LCpehdOG-Cvyr5_OxZWXqfvKax6gNr2qpWHXmag7C0yCTXf7o5sFwfxkxmKJv7qDVjxpxy0NSUL7gZ0LjQbTCPBC63tK9G_zCdQJLlprJESRVZ6xxySKjP27V-aGSC_WGFoEbDL6w_UQKOZ7N1ksMYWhW7NXzNTTIF01-INltTBQbexbjC5QaMfuuiJ0dbSLJVa9BP9Fgb1hVWiMl7qbPUN2cpmYvDFCs_zNUjBTO6HbpUZ0JDX79tOVTaJfgEPWeC0qpPuYJaSG-xQdyL-0gQ2iQ" \
  -H "Content-Type: application/json" \
  -d '{
    "codigoPermiso": "USUARIO_CREAR",
    "nombrePermiso": "Crear usuario",
    "modulo": "usuarios",
    "accion": "crear",
    "descripcion": "Permite crear usuarios",
    "usuarioCreacionId": "usuario-prueba"
  }' | jq
```

### Caso de fracaso: cuerpo vacío

```bash
curl -sS -X POST "http://localhost:8080/permiso/crearPermisoAccion" \
  -H "Authorization: Bearer xxxx-ejemplo-bearer" \
  -H "Content-Type: application/json" \
  -d '{}' | jq
```

## Listar todos los permisos y acciones

Ruta del controlador: `GET /permiso/listarPermisosAcciones`

```bash
curl -sS -X GET "http://localhost:8080/permiso/listarPermisosAcciones" \
  -H "Authorization: Bearer xxxx-ejemplo-bearer" | jq
```

## Listar permisos y acciones activos

Ruta del controlador: `GET /permiso/listarPermisosAccionesActivos`

```bash
curl -sS -X GET "http://localhost:8080/permiso/listarPermisosAccionesActivos" \
  -H "Authorization: Bearer xxxx-ejemplo-bearer" | jq
```

## Buscar por ID

Ruta del controlador: `GET /permiso/buscarPermisoAccionPorId/{idPermisoAccion}`

### Caso exitoso

```bash
curl -sS -X GET "http://localhost:8080/permiso/buscarPermisoAccionPorId/1" \
  -H "Authorization: Bearer xxxx-ejemplo-bearer" | jq
```

### Caso de fracaso: ID inexistente

```bash
curl -sS -X GET "http://localhost:8080/permiso/buscarPermisoAccionPorId/999999" \
  -H "Authorization: Bearer xxxx-ejemplo-bearer" | jq
```

## Buscar por código

Ruta del controlador: `GET /permiso/buscarPermisoAccionPorCodigo/{codigoPermiso}`

```bash
curl -sS -X GET "http://localhost:8080/permiso/buscarPermisoAccionPorCodigo/USUARIO_CREAR" \
  -H "Authorization: Bearer xxxx-ejemplo-bearer" | jq
```

## Buscar por módulo

Ruta del controlador: `GET /permiso/buscarPermisosPorModulo/{modulo}`

```bash
curl -sS -X GET "http://localhost:8080/permiso/buscarPermisosPorModulo/usuarios" \
  -H "Authorization: Bearer xxxx-ejemplo-bearer" | jq
```

## Buscar por acción

Ruta del controlador: `GET /permiso/buscarPermisosPorAccion/{accion}`

```bash
curl -sS -X GET "http://localhost:8080/permiso/buscarPermisosPorAccion/crear" \
  -H "Authorization: Bearer xxxx-ejemplo-bearer" | jq
```

## Actualizar permiso y acción

Ruta del controlador: `PUT /permiso/actualizarPermisoAccion`

```bash
curl -sS -X PUT "http://localhost:8080/permiso/actualizarPermisoAccion" \
  -H "Authorization: Bearer xxxx-ejemplo-bearer" \
  -H "Content-Type: application/json" \
  -d '{
    "idPermisoAccion": 1,
    "codigoPermiso": "USUARIO_CREAR",
    "nombrePermiso": "Crear usuario actualizado",
    "modulo": "usuarios",
    "accion": "crear",
    "descripcion": "Descripción actualizada",
    "usuarioModificacionId": "usuario-prueba"
  }' | jq
```

## Activar permiso y acción

Ruta del controlador: `PATCH /permiso/activarPermisoAccion/{idPermisoAccion}`

```bash
curl -sS -X PATCH "http://localhost:8080/permiso/activarPermisoAccion/1" \
  -H "Authorization: Bearer xxxx-ejemplo-bearer" | jq
```

## Desactivar permiso y acción

Ruta del controlador: `PATCH /permiso/desactivarPermisoAccion/{idPermisoAccion}`

```bash
curl -sS -X PATCH "http://localhost:8080/permiso/desactivarPermisoAccion/1" \
  -H "Authorization: Bearer xxxx-ejemplo-bearer" | jq
```

## Casos generales de autorización

Para probar autorización, modifica únicamente el token:

```bash
curl -sS -X GET "http://localhost:8080/permiso/listarPermisosAcciones" \
  -H "Authorization: Bearer token-invalido" | jq
```

También puedes probar la ausencia del encabezado:

```bash
curl -sS -X GET "http://localhost:8080/permiso/listarPermisosAcciones" | jq
```

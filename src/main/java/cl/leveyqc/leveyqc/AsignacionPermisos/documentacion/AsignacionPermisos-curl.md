# Pruebas `curl` — Asignación de permisos

Documentación de las rutas disponibles en `AsignacionPermisosController`.

## Configuración y autorización Bearer

- URL base: `http://localhost:8080`
- Todas las rutas de este controlador requieren autenticación JWT.
- Instala `jq` si aún no está disponible: `brew install jq`.

Define un token JWT válido de Clerk antes de ejecutar las pruebas:

```bash
TOKEN_CLERK='eyJhbGciOiJSUzI1NiIsImNhdCI6ImNsX0I3ZDRQRDExMUFBQSIsImtpZCI6Imluc18zSU41VGo0RTFWY2RMb2p3RGVlcHQwNjZteFEiLCJvaWF0IjoxNzg4Mjc5MDU4LCJ0eXAiOiJKV1QifQ.eyJhenAiOiJodHRwOi8vbG9jYWxob3N0OjMwMDEiLCJleHAiOjE3ODgyNzkxMTgsImZ2YSI6WzEwODksLTFdLCJpYXQiOjE3ODgyNzkwNTgsImlzcyI6Imh0dHBzOi8vaW5maW5pdGUtY29kLTEzNjMuY2xlcmsuYWNjb3VudHMuZGV2IiwibmJmIjoxNzg4Mjc5MDQ4LCJvIjp7ImlkIjoib3JnXzNJUUNKZTB4SFkwOWlFRkI0RG56UzFmUG5IUyIsInJvbCI6ImFkbWluIiwic2xnIjoiY2xpbmljYS1zYW4tdmljaWVudGUtMTc4NzY4NzMzNDU0ODAxMDMyNSJ9LCJzaWQiOiJzZXNzXzNJaFBFTzRDcTdURGg3ZVA0M1BZUEF4cVdsRCIsInN0cyI6ImFjdGl2ZSIsInN1YiI6InVzZXJfM0lONXZiUXVlVW9WeGs2eFdpMHhLd2VteDZWIiwidiI6Mn0.Z_5UKCENIu5oCvI3aZ8y6gJqfMZDHPoES92VnkHVpJuJxKElnIkGKrv-P01Em5ordvQEckdm0CuQJf_YkJKstzzmV-Eh-q04XHWYIGOCjvFrxTdcyrlu81WIUe-cHcC0dzyZ52JLCux7XSyIkLpVt-vK6sJ39rNvlZ4s0d2CX2ytKcqw2iHo0OPt-oTwzakE4_hZYIzGKlYU0z9Wj9lMrm-5l3iZc4iwOc3EYQ-Hyc2MqaKhGYT0m2t_hRuNfOr0xdtUiq4d21KQw_R7kz8FJPj2oU_xFkCozVwbu8EWZvq0r5IRYjH4Tu7_wFpTL33WD7FBhbLD6JVIlZ2VgQV8Aw'
```

Cada solicitud incluye el encabezado `Authorization: Bearer ${TOKEN_CLERK}` y termina con `| jq .` para mostrar la respuesta JSON formateada. No uses como token la clave secreta, la publishable key ni el ID de usuario.

Los valores `1` usados como IDs deben existir en la base de datos. Reemplázalos cuando sea necesario.

## Crear asignación de permiso

Ruta: `POST /asignacion-permisos/crear`

Para crear una asignación, el servicio exige `idPermisoAccion`, `idTipoUsuarios` y `usuarioCreacionId`. La aplicación asigna automáticamente `activo: 1` y `fechaCreacion` al persistirla.

### Éxito esperado: HTTP 200

```bash
curl -sS -X POST "http://localhost:8080/asignacion-permisos/crear" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" \
  -H "Content-Type: application/json" \
  -d '{
    "idPermisoAccion": 1,
    "idTipoUsuarios": 1,
    "usuarioCreacionId": "user_123456789"
  }' | jq .
```

### Fracaso esperado: HTTP 500 por campos obligatorios ausentes

```bash
curl -sS -X POST "http://localhost:8080/asignacion-permisos/crear" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" \
  -H "Content-Type: application/json" \
  -d '{}' | jq .
```

### Fracaso esperado: HTTP 500 al enviar `idAsignacion`

El ID es generado por la base de datos, por lo que el servicio rechaza recibirlo durante la creación.

```bash
curl -sS -X POST "http://localhost:8080/asignacion-permisos/crear" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" \
  -H "Content-Type: application/json" \
  -d '{
    "idAsignacion": 1,
    "idPermisoAccion": 1,
    "idTipoUsuarios": 1,
    "usuarioCreacionId": "user_123456789"
  }' | jq .
```

### Fracaso esperado: HTTP 500 al enviar `activo`

El servicio define este campo automáticamente en el ciclo de persistencia; por ello tampoco debe incluirse en el cuerpo de creación.

```bash
curl -sS -X POST "http://localhost:8080/asignacion-permisos/crear" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" \
  -H "Content-Type: application/json" \
  -d '{
    "idPermisoAccion": 1,
    "idTipoUsuarios": 1,
    "activo": 1,
    "usuarioCreacionId": "user_123456789"
  }' | jq .
```

## Buscar asignación por ID

Ruta: `GET /asignacion-permisos/buscar/{idAsignacion}`

### Éxito esperado: HTTP 200

```bash
curl -sS -X GET "http://localhost:8080/asignacion-permisos/buscar/1" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

### Fracaso esperado: HTTP 404 por ID inexistente

```bash
curl -sS -X GET "http://localhost:8080/asignacion-permisos/buscar/999999" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

### Fracaso esperado: HTTP 400 por ID no numérico

```bash
curl -sS -X GET "http://localhost:8080/asignacion-permisos/buscar/no-numerico" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

## Listar asignaciones por tipo de usuario

Ruta: `GET /asignacion-permisos/buscar-tipo-usuario/{idTipoUsuario}`

### Éxito esperado: HTTP 200 con asignaciones

```bash
curl -sS -X GET "http://localhost:8080/asignacion-permisos/buscar-tipo-usuario/1" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

### Sin resultados: HTTP 200 con `success: false`

El controlador no responde `404` en este caso: devuelve una lista vacía dentro de `data`.

```bash
curl -sS -X GET "http://localhost:8080/asignacion-permisos/buscar-tipo-usuario/999999" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

### Fracaso esperado: HTTP 400 por ID no numérico

```bash
curl -sS -X GET "http://localhost:8080/asignacion-permisos/buscar-tipo-usuario/no-numerico" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

## Eliminar lógicamente una asignación

Ruta: `PATCH /asignacion-permisos/eliminar/{idAsignacion}`

Esta operación no borra el registro: actualiza su campo `activo` a `0`.

### Éxito esperado: HTTP 200 con `success: true`

```bash
curl -sS -X PATCH "http://localhost:8080/asignacion-permisos/eliminar/1" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

### Fracaso esperado: HTTP 200 con `success: false` por ID inexistente

El controlador mantiene HTTP 200 aun si no encuentra el registro; verifica el campo `success` de la respuesta.

```bash
curl -sS -X PATCH "http://localhost:8080/asignacion-permisos/eliminar/999999" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

### Fracaso esperado: HTTP 400 por ID no numérico

```bash
curl -sS -X PATCH "http://localhost:8080/asignacion-permisos/eliminar/no-numerico" \
  -H "Authorization: Bearer ${TOKEN_CLERK}" | jq .
```

## Pruebas de autorización

### Token inválido: HTTP 401 esperado

```bash
curl -sS -X GET "http://localhost:8080/asignacion-permisos/buscar/1" \
  -H "Authorization: Bearer token-invalido" | jq .
```

### Encabezado Bearer ausente: HTTP 401 esperado

```bash
curl -sS -X GET "http://localhost:8080/asignacion-permisos/buscar/1" | jq .
```

## Resumen de rutas

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/asignacion-permisos/crear` | Crea una asignación de permiso para un tipo de usuario. |
| `GET` | `/asignacion-permisos/buscar/{idAsignacion}` | Busca una asignación por su ID. |
| `GET` | `/asignacion-permisos/buscar-tipo-usuario/{idTipoUsuario}` | Lista asignaciones asociadas a un tipo de usuario. |
| `PATCH` | `/asignacion-permisos/eliminar/{idAsignacion}` | Realiza eliminación lógica, dejando `activo` en `0`. |

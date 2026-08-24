# Pruebas HTTP - Usuarios Levey

Aplicación ejecutándose en `http://localhost:8080`.

Los ejemplos exitosos usan el usuario con ID `1`. Si ese registro no existe en tu base de datos, reemplaza el número directamente en la URL o en el JSON.

## Crear usuario

### Éxito esperado: HTTP 201

```bash
curl -sS -X POST "http://localhost:8080/usuarios-levey/insertar" \
  -H "Content-Type: application/json" \
  -d '{
    "clerkUserId": "user_prueba_001",
    "nombre": "Nicolas",
    "apellido": "Prueba",
    "rut": "12345678-9",
    "email": "usuario.prueba@email.com",
    "profesion": "Tecnologo Medico",
    "username": "usuario_prueba_001",
    "telefono": "912345678",
    "idLaboratorioClinico": 1,
    "idTipoUsuarios": 1,
    "usuarioCreacionId": 1
  }' | jq .
```

### Fracaso esperado: HTTP 400

```bash
curl -sS -X POST "http://localhost:8080/usuarios-levey/insertar" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Usuario incompleto",
    "email": "incompleto@email.com"
  }' | jq .
```

## Listar usuarios

### Éxito esperado: HTTP 200

```bash
curl -sS -X GET "http://localhost:8080/usuarios-levey" | jq .
```

Si no existen registros, el mismo endpoint devuelve HTTP 404.

## Listar usuarios activos

### Éxito esperado: HTTP 200

```bash
curl -sS -X GET "http://localhost:8080/usuarios-levey/activos" | jq .
```

Si no existen usuarios activos, el mismo endpoint devuelve HTTP 404.

## Buscar usuario por ID

### Éxito esperado: HTTP 200

```bash
curl -sS -X GET "http://localhost:8080/usuarios-levey/1" | jq .
```

### Fracaso esperado: HTTP 404

```bash
curl -sS -X GET "http://localhost:8080/usuarios-levey/999999" | jq .
```

## Buscar usuario por Clerk User ID

### Éxito esperado: HTTP 200

```bash
curl -sS -X GET "http://localhost:8080/usuarios-levey/clerk/user_prueba_001" | jq .
```

### Fracaso esperado: HTTP 404

```bash
curl -sS -X GET "http://localhost:8080/usuarios-levey/clerk/user_inexistente" | jq .
```

## Buscar usuario por email

### Éxito esperado: HTTP 200

```bash
curl -sS -X GET "http://localhost:8080/usuarios-levey/email/usuario.prueba%40email.com" | jq .
```

### Fracaso esperado: HTTP 404

```bash
curl -sS -X GET "http://localhost:8080/usuarios-levey/email/inexistente%40email.com" | jq .
```

## Actualizar usuario

### Éxito esperado: HTTP 200

```bash
curl -sS -X PUT "http://localhost:8080/usuarios-levey/actualizar" \
  -H "Content-Type: application/json" \
  -d '{
    "idUsuarioLevey": 1,
    "nombre": "Nicolas actualizado",
    "apellido": "Prueba actualizada",
    "rut": "12345678-9",
    "email": "usuario.actualizado@email.com",
    "profesion": "Tecnologo Medico",
    "username": "usuario_actualizado_001",
    "telefono": "987654321",
    "idLaboratorioClinico": 1,
    "idTipoUsuarios": 1,
    "usuarioModificacionId": 1
  }' | jq .
```

### Fracaso esperado: HTTP 400 por falta de ID

```bash
curl -sS -X PUT "http://localhost:8080/usuarios-levey/actualizar" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Usuario sin ID",
    "usuarioModificacionId": 1
  }' | jq .
```

### Fracaso esperado: HTTP 400 por usuario inexistente

```bash
curl -sS -X PUT "http://localhost:8080/usuarios-levey/actualizar" \
  -H "Content-Type: application/json" \
  -d '{
    "idUsuarioLevey": 999999,
    "nombre": "Usuario inexistente",
    "usuarioModificacionId": 1
  }' | jq .
```

## Registrar último acceso

### Éxito esperado: HTTP 200

```bash
curl -sS -X PATCH "http://localhost:8080/usuarios-levey/1/ultimo-acceso" | jq .
```

### Fracaso esperado: HTTP 400

```bash
curl -sS -X PATCH "http://localhost:8080/usuarios-levey/999999/ultimo-acceso" | jq .
```

> Esta ruta puede devolver HTTP 500 porque el controlador usa `{idUsuarioLevey}` en la ruta, pero el parámetro Java se llama `usuarioQueAccedioId`.

## Activar usuario

### Éxito esperado: HTTP 200

```bash
curl -sS -X PATCH "http://localhost:8080/usuarios-levey/1/activar" | jq .
```

### Fracaso esperado: HTTP 400

```bash
curl -sS -X PATCH "http://localhost:8080/usuarios-levey/999999/activar" | jq .
```

## Bloquear usuario

### Éxito esperado: HTTP 200

```bash
curl -sS -X PATCH "http://localhost:8080/usuarios-levey/1/bloquear" | jq .
```

### Fracaso esperado: HTTP 400

```bash
curl -sS -X PATCH "http://localhost:8080/usuarios-levey/999999/bloquear" | jq .
```

## Desactivar usuario

### Éxito esperado: HTTP 200

```bash
curl -sS -X PATCH "http://localhost:8080/usuarios-levey/1/desactivar" | jq .
```

### Fracaso esperado: HTTP 400

```bash
curl -sS -X PATCH "http://localhost:8080/usuarios-levey/999999/desactivar" | jq .
```

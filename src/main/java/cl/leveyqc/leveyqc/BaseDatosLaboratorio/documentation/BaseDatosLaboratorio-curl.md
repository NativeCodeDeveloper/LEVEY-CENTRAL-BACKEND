# Pruebas HTTP de `BaseDatosLaboratorio`

> Base URL utilizada: `http://localhost:8080`
>
> Ejecutar los casos normales en el orden indicado. Los IDs `1` y `2` deben existir para que las pruebas dependientes puedan continuar.

## Casos normales

### 1. Crear una base de datos de laboratorio

```bash
curl -sS -X POST "http://localhost:8080/bases-datos-laboratorio" \
  -H "Content-Type: application/json" \
  -d '{
    "idLaboratorioClinico": 1,
    "nombreBaseDatos": "Base QC Principal",
    "motorBaseDatos": "PostgreSQL",
    "hostReferencia": "localhost",
    "puertoReferencia": 5432,
    "secretoConexionKey": "secret-qc-principal",
    "estadoConexion": "desconectada",
    "activo": 1,
    "usuarioCreacionId": 1
  }' | jq
```

### 2. Listar todas las bases de datos

```bash
curl -sS -X GET "http://localhost:8080/bases-datos-laboratorio" | jq
```

### 3. Listar bases de datos activas

```bash
curl -sS -X GET "http://localhost:8080/bases-datos-laboratorio/activas" | jq
```

### 4. Listar bases de datos disponibles

```bash
curl -sS -X GET "http://localhost:8080/bases-datos-laboratorio/disponibles" | jq
```

### 5. Buscar una base de datos por ID

```bash
curl -sS -X GET "http://localhost:8080/bases-datos-laboratorio/1" | jq
```

### 6. Buscar bases de datos por laboratorio clínico

```bash
curl -sS -X GET "http://localhost:8080/bases-datos-laboratorio/laboratorio/1" | jq
```

### 7. Actualizar una base de datos completa

```bash
curl -sS -X PUT "http://localhost:8080/bases-datos-laboratorio/laboratorio" \
  -H "Content-Type: application/json" \
  -d '{
    "idBaseDatosLaboratorio": 1,
    "idLaboratorioClinico": 1,
    "nombreBaseDatos": "Base QC Actualizada",
    "motorBaseDatos": "PostgreSQL",
    "hostReferencia": "127.0.0.1",
    "puertoReferencia": 5432,
    "secretoConexionKey": "secret-qc-actualizado",
    "estadoConexion": "desconectada",
    "activo": 1,
    "usuarioModificacionId": 1
  }' | jq
```

### 8. Asignar una base de datos a un laboratorio

```bash
curl -sS -X PATCH "http://localhost:8080/bases-datos-laboratorio/1/asignar/2" | jq
```

### 9. Marcar conexión como conectada

```bash
curl -sS -X PATCH "http://localhost:8080/bases-datos-laboratorio/1/conectar" | jq
```

### 10. Marcar conexión como desconectada

```bash
curl -sS -X PATCH "http://localhost:8080/bases-datos-laboratorio/1/desconectar" | jq
```

### 11. Activar una base de datos

```bash
curl -sS -X PATCH "http://localhost:8080/bases-datos-laboratorio/1/activar" | jq
```

### 12. Desactivar una base de datos

```bash
curl -sS -X PATCH "http://localhost:8080/bases-datos-laboratorio/1/desactivar" | jq
```

## Casos raros o esperados como error

### 13. Crear enviando un ID generado

Debe responder `400 Bad Request` según la validación actual del service.

```bash
curl -sS -X POST "http://localhost:8080/bases-datos-laboratorio" \
  -H "Content-Type: application/json" \
  -d '{
    "idBaseDatosLaboratorio": 99,
    "idLaboratorioClinico": 1,
    "nombreBaseDatos": "Base inválida",
    "motorBaseDatos": "PostgreSQL",
    "hostReferencia": "localhost",
    "puertoReferencia": 5432,
    "secretoConexionKey": "secret-invalido",
    "estadoConexion": "desconectada",
    "usuarioCreacionId": 1
  }' | jq
```

### 14. Crear enviando campos obligatorios nulos

Debe responder `400 Bad Request`.

```bash
curl -sS -X POST "http://localhost:8080/bases-datos-laboratorio" \
  -H "Content-Type: application/json" \
  -d '{
    "idLaboratorioClinico": null,
    "nombreBaseDatos": null,
    "motorBaseDatos": "PostgreSQL",
    "hostReferencia": "localhost",
    "puertoReferencia": 5432,
    "secretoConexionKey": "secret-error",
    "estadoConexion": "desconectada",
    "usuarioCreacionId": 1
  }' | jq
```

### 15. Actualizar enviando un cuerpo vacío

Debe responder `400 Bad Request`.

```bash
curl -sS -X PUT "http://localhost:8080/bases-datos-laboratorio/laboratorio" \
  -H "Content-Type: application/json" \
  -d '{}' | jq
```

### 16. Buscar un ID inexistente

Debe responder `404 Not Found` si no existe el ID `999999`.

```bash
curl -sS -X GET "http://localhost:8080/bases-datos-laboratorio/999999" | jq
```

### 17. Buscar por un laboratorio inexistente

Debe responder `404 Not Found` si no existen bases asociadas al laboratorio `999999`.

```bash
curl -sS -X GET "http://localhost:8080/bases-datos-laboratorio/laboratorio/999999" | jq
```

### 18. Asignar usando una base inexistente

Debe responder `400 Bad Request` según el controlador actual.

```bash
curl -sS -X PATCH "http://localhost:8080/bases-datos-laboratorio/999999/asignar/1" | jq
```

### 19. Conectar una base inexistente

Debe responder `400 Bad Request` según el controlador actual.

```bash
curl -sS -X PATCH "http://localhost:8080/bases-datos-laboratorio/999999/conectar" | jq
```

### 20. Desconectar una base inexistente

Debe responder `400 Bad Request` según el controlador actual.

```bash
curl -sS -X PATCH "http://localhost:8080/bases-datos-laboratorio/999999/desconectar" | jq
```

### 21. Activar una base inexistente

Debe responder `400 Bad Request` según el controlador actual.

```bash
curl -sS -X PATCH "http://localhost:8080/bases-datos-laboratorio/999999/activar" | jq
```

### 22. Desactivar una base inexistente

Debe responder `400 Bad Request` según el controlador actual.

```bash
curl -sS -X PATCH "http://localhost:8080/bases-datos-laboratorio/999999/desactivar" | jq
```

### 23. Usar un ID con formato inválido

Spring debería responder `400 Bad Request` porque no puede convertir `abc` a `Long`.

```bash
curl -sS -X GET "http://localhost:8080/bases-datos-laboratorio/abc" | jq
```

### 24. Enviar JSON malformado

Spring debería responder `400 Bad Request`.

```bash
curl -sS -X POST "http://localhost:8080/bases-datos-laboratorio" \
  -H "Content-Type: application/json" \
  -d '{"nombreBaseDatos":"JSON incompleto"' | jq
```

## Observaciones

- Los endpoints que devuelven `null` desde el service responden `400` según el controlador actual, aunque algunos casos podrían representar `404 Not Found`.
- El endpoint de búsqueda por ID retorna una lista porque el service actual usa `findByIdBaseDatosLaboratorio(...)`.
- Si la aplicación utiliza otro puerto o un prefijo global, reemplazar `http://localhost:8080` en las solicitudes.

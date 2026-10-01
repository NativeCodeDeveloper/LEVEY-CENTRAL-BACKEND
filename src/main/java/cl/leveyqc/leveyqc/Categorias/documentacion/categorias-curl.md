# Pruebas HTTP de Categorías

> Reemplaza `http://localhost:8080` por el host y puerto reales si son diferentes.
> Después de `Bearer ` debes pegar el token JWT correspondiente.

## Variables de referencia

```bash
BASE_URL="http://localhost:8080"
TOKEN=""
ID_CATEGORIA="1"
```

## Crear categoría

### Caso exitoso

```bash
curl -i -X POST "$BASE_URL/categorias" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombreCategoria": "Hematología",
    "activo": 1
  }' | jq
```

### Caso fallido: nombre vacío

```bash
curl -i -X POST "$BASE_URL/categorias" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombreCategoria": "   ",
    "activo": 1
  }' | jq
```

### Caso fallido: nombre nulo

```bash
curl -i -X POST "$BASE_URL/categorias" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombreCategoria": null,
    "activo": 1
  }' | jq
```

## Listar todas las categorías

### Caso exitoso

```bash
curl -i -X GET "$BASE_URL/categorias" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Caso esperado cuando no existen categorías

```bash
curl -i -X GET "$BASE_URL/categorias" \
  -H "Authorization: Bearer $TOKEN" | jq
```

## Listar categorías activas

### Caso exitoso

```bash
curl -i -X GET "$BASE_URL/categorias/activas" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Caso esperado cuando no existen categorías activas

```bash
curl -i -X GET "$BASE_URL/categorias/activas" \
  -H "Authorization: Bearer $TOKEN" | jq
```

## Buscar categoría por identificador

### Caso exitoso

```bash
curl -i -X GET "$BASE_URL/categorias/$ID_CATEGORIA" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Caso fallido: categoría inexistente

```bash
curl -i -X GET "$BASE_URL/categorias/999999999" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Caso fallido: identificador con formato inválido

```bash
curl -i -X GET "$BASE_URL/categorias/abc" \
  -H "Authorization: Bearer $TOKEN" | jq
```

## Actualizar categoría

### Caso exitoso

```bash
curl -i -X PUT "$BASE_URL/categorias/actualizar" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d "{
    \"idCategoria\": $ID_CATEGORIA,
    \"nombreCategoria\": \"Hematología actualizada\"
  }" | jq
```

### Caso fallido: identificador nulo

```bash
curl -i -X PUT "$BASE_URL/categorias/actualizar" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "idCategoria": null,
    "nombreCategoria": "Categoría inválida"
  }' | jq
```

### Caso fallido: nombre vacío

```bash
curl -i -X PUT "$BASE_URL/categorias/actualizar" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d "{
    \"idCategoria\": $ID_CATEGORIA,
    \"nombreCategoria\": \"   \"
  }" | jq
```

### Caso fallido: categoría inexistente

```bash
curl -i -X PUT "$BASE_URL/categorias/actualizar" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "idCategoria": 999999999,
    "nombreCategoria": "Categoría inexistente"
  }' | jq
```

## Desactivar categoría

### Caso exitoso

```bash
curl -i -X PUT "$BASE_URL/categorias/desactivar/$ID_CATEGORIA" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Caso fallido: categoría inexistente

```bash
curl -i -X PUT "$BASE_URL/categorias/desactivar/999999999" \
  -H "Authorization: Bearer $TOKEN" | jq
```

## Activar categoría

### Caso exitoso

```bash
curl -i -X PUT "$BASE_URL/categorias/activar/$ID_CATEGORIA" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Caso fallido: categoría inexistente

```bash
curl -i -X PUT "$BASE_URL/categorias/activar/999999999" \
  -H "Authorization: Bearer $TOKEN" | jq
```

## Prueba de autenticación

### Caso fallido: token ausente

```bash
curl -i -X GET "$BASE_URL/categorias" | jq
```

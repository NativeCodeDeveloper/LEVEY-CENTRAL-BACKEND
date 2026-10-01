# Pruebas HTTP de Unidades de Medida

> Reemplaza `http://localhost:8080` por el host y puerto reales si son diferentes.
> Después de `Bearer ` debes pegar el token JWT correspondiente.

## Variables de referencia

```bash
BASE_URL="http://localhost:8080"
TOKEN=""
ID_UNIDAD="1"
```

## Crear unidad de medida

### Caso exitoso

```bash
curl -sS -X POST "$BASE_URL/unidadDeMedida" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "unidadDeMedida": "mg/dL"
  }' | jq
```

### Caso fallido: cuerpo nulo

```bash
curl -sS -X POST "$BASE_URL/unidadDeMedida" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d 'null' | jq
```

### Caso fallido: unidad nula

```bash
curl -sS -X POST "$BASE_URL/unidadDeMedida" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "unidadDeMedida": null
  }' | jq
```

### Caso fallido: unidad vacía

```bash
curl -sS -X POST "$BASE_URL/unidadDeMedida" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "unidadDeMedida": "   "
  }' | jq
```

## Listar todas las unidades de medida

### Caso exitoso

```bash
curl -sS -X GET "$BASE_URL/unidadDeMedida" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Caso esperado cuando no existen unidades

```bash
curl -sS -X GET "$BASE_URL/unidadDeMedida" \
  -H "Authorization: Bearer $TOKEN" | jq
```

## Listar unidades activas

### Caso exitoso

```bash
curl -sS -X GET "$BASE_URL/unidadesdemedida/activas" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Caso esperado cuando no existen unidades activas

```bash
curl -sS -X GET "$BASE_URL/unidadesdemedida/activas" \
  -H "Authorization: Bearer $TOKEN" | jq
```

## Buscar unidad por identificador

### Caso exitoso

```bash
curl -sS -X GET "$BASE_URL/unidadesDeMedida/$ID_UNIDAD" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Caso fallido: unidad inexistente

```bash
curl -sS -X GET "$BASE_URL/unidadesDeMedida/999999999" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Caso fallido: identificador con formato inválido

```bash
curl -sS -X GET "$BASE_URL/unidadesDeMedida/abc" \
  -H "Authorization: Bearer $TOKEN" | jq
```

## Actualizar unidad de medida

### Caso exitoso

```bash
curl -sS -X PUT "$BASE_URL/unidadesdemedida/actualizar" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d "{
    \"idUnidadesDeMedida\": $ID_UNIDAD,
    \"unidadDeMedida\": \"mg/L\"
  }" | jq
```

### Caso fallido: identificador nulo

```bash
curl -sS -X PUT "$BASE_URL/unidadesdemedida/actualizar" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "idUnidadesDeMedida": null,
    "unidadDeMedida": "mg/L"
  }' | jq
```

### Caso fallido: unidad vacía

```bash
curl -sS -X PUT "$BASE_URL/unidadesdemedida/actualizar" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d "{
    \"idUnidadesDeMedida\": $ID_UNIDAD,
    \"unidadDeMedida\": \"   \"
  }" | jq
```

### Caso fallido: unidad inexistente

```bash
curl -sS -X PUT "$BASE_URL/unidadesdemedida/actualizar" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "idUnidadesDeMedida": 999999999,
    "unidadDeMedida": "mg/L"
  }' | jq
```

## Desactivar unidad de medida

### Caso exitoso

```bash
curl -sS -X PATCH "$BASE_URL/unidadesdemedida/desactivar/$ID_UNIDAD" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Caso fallido: unidad inexistente

```bash
curl -sS -X PATCH "$BASE_URL/unidadesdemedida/desactivar/999999999" \
  -H "Authorization: Bearer $TOKEN" | jq
```

## Activar unidad de medida

### Caso exitoso

```bash
curl -sS -X PATCH "$BASE_URL/unidadesdemedida/activar/$ID_UNIDAD" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Caso fallido: unidad inexistente

```bash
curl -sS -X PATCH "$BASE_URL/unidadesdemedida/activar/999999999" \
  -H "Authorization: Bearer $TOKEN" | jq
```

## Buscar unidades por similitud

### Caso exitoso

```bash
curl -sS -G "$BASE_URL/unidadesdemedida/similitud/mg" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Caso esperado sin coincidencias

```bash
curl -sS -G "$BASE_URL/unidadesdemedida/similitud/xyz_sin_coincidencias" \
  -H "Authorization: Bearer $TOKEN" | jq
```

## Prueba de autenticación

### Caso fallido: token ausente

```bash
curl -sS -X GET "$BASE_URL/unidadDeMedida" | jq
```

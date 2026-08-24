# Laboratorio Clínico: comandos curl

## 1. Crear laboratorio - caso exitoso

```bash
curl --request POST "http://localhost:8080/laboratorios-clinicos/insertar" \
  --header "Content-Type: application/json" \
  --data-raw '{
    "clerkOrganizationId": "org_prueba_001",
    "nombreLaboratorioClinico": "Laboratorio Clínico de Prueba",
    "rutInstitucion": "76.123.456-7",
    "representanteLegal": "Nicolás Pérez",
    "emailContacto": "contacto@laboratorioprueba.cl",
    "telefonoContacto": "+56912345678",
    "direccion": "Avenida de Prueba 123",
    "comuna": "Santiago",
    "ciudad": "Santiago",
    "region": "Región Metropolitana",
    "pais": "Chile",
    "activo": 1,
    "usuarioCreacionId": 1
  }' | jq
```

## 2. Crear laboratorio - caso fallido por campos obligatorios ausentes

```bash
curl --request POST "http://localhost:8080/laboratorios-clinicos/insertar" \
  --header "Content-Type: application/json" \
  --data-raw '{
    "nombreLaboratorioClinico": "Laboratorio incompleto"
  }' | jq
```

## 3. Listar laboratorios - caso exitoso

```bash
curl --request GET "http://localhost:8080/laboratorios-clinicos" | jq
```

## 4. Obtener laboratorio por ID - caso exitoso

```bash
curl --request GET "http://localhost:8080/laboratorios-clinicos/1" | jq
```

## 5. Obtener laboratorio por ID - caso fallido

```bash
curl --request GET "http://localhost:8080/laboratorios-clinicos/999999" | jq
```

## 6. Actualizar laboratorio - caso exitoso

```bash
curl --request PUT "http://localhost:8080/laboratorios-clinicos/actualizar" \
  --header "Content-Type: application/json" \
  --data-raw '{
    "idLaboratorioClinico": 1,
    "clerkOrganizationId": "org_prueba_001",
    "nombreLaboratorioClinico": "Laboratorio Clínico Actualizado",
    "rutInstitucion": "76.123.456-7",
    "representanteLegal": "Nicolás Pérez",
    "emailContacto": "actualizado@laboratorioprueba.cl",
    "telefonoContacto": "+56987654321",
    "direccion": "Avenida Actualizada 456",
    "comuna": "Providencia",
    "ciudad": "Santiago",
    "region": "Región Metropolitana",
    "pais": "Chile",
    "activo": 1,
    "usuarioCreacionId": 1,
    "usuarioModificacionId": 2323232
  }' | jq
```

## 7. Actualizar laboratorio - caso fallido por ID inexistente

```bash
curl --request PUT "http://localhost:8080/laboratorios-clinicos/actualizar" \
  --header "Content-Type: application/json" \
  --data-raw '{
    "idLaboratorioClinico": 999999,
    "clerkOrganizationId": "org_inexistente_999",
    "nombreLaboratorioClinico": "Laboratorio inexistente",
    "rutInstitucion": "99.999.999-9",
    "representanteLegal": "Representante de Prueba",
    "emailContacto": "inexistente@laboratorioprueba.cl",
    "telefonoContacto": "+56911111111",
    "direccion": "Dirección inexistente 999",
    "comuna": "Santiago",
    "ciudad": "Santiago",
    "region": "Región Metropolitana",
    "pais": "Chile",
    "activo": 1,
    "usuarioCreacionId": 1
  }' | jq
```

## 8. Activar laboratorio - caso exitoso

```bash
curl --request PATCH "http://localhost:8080/laboratorios-clinicos/1/activar" | jq
```

## 9. Activar laboratorio - caso fallido

```bash
curl --request PATCH "http://localhost:8080/laboratorios-clinicos/999999/activar" | jq
```

## 10. Buscar por organización - caso exitoso

```bash
curl --request GET "http://localhost:8080/laboratorios-clinicos/organizacion/org_prueba_001" | jq
```

## 11. Buscar por organización - caso fallido

```bash
curl --request GET "http://localhost:8080/laboratorios-clinicos/organizacion/org_inexistente_999" | jq
```

## 12. Verificar RUT - caso exitoso

```bash
curl --request GET "http://localhost:8080/laboratorios-clinicos/verificar-rut/76.123.456-7" | jq
```

## 13. Verificar RUT - caso fallido

```bash
curl --request GET "http://localhost:8080/laboratorios-clinicos/verificar-rut/99.999.999-9" | jq
```

# Documentación de API con Swagger/OpenAPI

## Descripción

Este proyecto incluye documentación completa de la API utilizando Swagger/OpenAPI 3.0. La documentación está implementada siguiendo los estándares de OpenAPI y proporciona una interfaz interactiva para probar todos los endpoints.

## Configuración

### Dependencias

El proyecto ya incluye las dependencias necesarias en el `pom.xml`:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.6.0</version>
</dependency>
```

### Configuración de Propiedades

En `application.properties`:

# Configuraciones APIDOC
springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.path=/doc/swagger-ui.html

## Acceso a la Documentación

Una vez que la aplicación esté ejecutándose, puedes acceder a la documentación de Swagger en:

- **URL de Swagger UI**: `http://localhost:8080/doc/swagger-ui.html`
- **URL de OpenAPI JSON**: `http://localhost:8080/v3/api-docs`
- **URL de OpenAPI YAML**: `http://localhost:8080/v3/api-docs.yaml`

## Estructura de la Documentación

### Configuración Global

La configuración global de OpenAPI se encuentra en `OpenApiConfig.java` e incluye:

- Información del proyecto (título, descripción, versión)
- Información de contacto
- Licencia
- Servidores configurados (desarrollo y producción)

### Controladores Documentados

Todos los controladores incluyen anotaciones completas de Swagger:

#### 1. **Autenticación** (`AuthController`)
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/registro` - Registrar nuevo usuario

#### 2. **Usuarios** (`UsuarioController`)
- `GET /api/usuarios` - Obtener todos los usuarios
- `GET /api/usuarios/{id}` - Obtener usuario por ID
- `GET /api/usuarios/email/{email}` - Obtener usuario por email
- `POST /api/usuarios` - Crear nuevo usuario
- `PUT /api/usuarios/{id}` - Actualizar usuario
- `DELETE /api/usuarios/{id}` - Eliminar usuario

#### 3. **Inventario** (`InventarioController`)
- `GET /api/inventario/sucursal` - Obtener inventario de sucursal
- `POST /api/inventario` - Crear producto en inventario
- `PUT /api/inventario/{inventarioId}` - Actualizar inventario
- `DELETE /api/inventario/{inventarioId}` - Eliminar producto del inventario

#### 4. **Categorías** (`CategoriaController`)
- `GET /api/categorias` - Obtener todas las categorías
- `GET /api/categorias/{id}` - Obtener categoría por ID
- `POST /api/categorias` - Crear nueva categoría
- `PUT /api/categorias/{id}` - Actualizar categoría
- `DELETE /api/categorias/{id}` - Eliminar categoría

#### 5. **Sucursales** (`SucursalController`)
- `GET /api/sucursales` - Obtener todas las sucursales
- `GET /api/sucursales/{id}` - Obtener sucursal por ID
- `POST /api/sucursales` - Crear nueva sucursal
- `PUT /api/sucursales/{id}` - Actualizar sucursal
- `DELETE /api/sucursales/{id}` - Eliminar sucursal

#### 6. **Pagos** (`PagoController`)
- `GET /api/pagos` - Obtener todos los pagos
- `GET /api/pagos/{id}` - Obtener pago por ID
- `POST /api/pagos` - Crear nuevo pago
- `PUT /api/pagos/{id}` - Actualizar pago
- `DELETE /api/pagos/{id}` - Eliminar pago

#### 7. **Logística** (`LogisticaController`)
- `GET /api/logistica/envios` - Obtener todos los envíos
- `GET /api/logistica/envios/{id}` - Obtener envío por ID
- `GET /api/logistica/envios/pedido/{pedidoId}` - Obtener envío por pedido
- `GET /api/logistica/envios/estado/{estado}` - Obtener envíos por estado
- `POST /api/logistica/envios` - Crear nuevo envío
- `PUT /api/logistica/envios/{id}` - Actualizar envío
- `PUT /api/logistica/envios/{id}/estado` - Actualizar estado de envío
- `DELETE /api/logistica/envios/{id}` - Eliminar envío

#### 8. **Reportes** (`ReporteController`)
- `GET /api/reportes` - Obtener todos los reportes
- `GET /api/reportes/{id}` - Obtener reporte por ID
- `GET /api/reportes/tipo/{tipo}` - Obtener reportes por tipo
- `GET /api/reportes/usuario/{usuarioId}` - Obtener reportes por usuario
- `POST /api/reportes/generar` - Generar reporte personalizado
- `POST /api/reportes/estadisticas/ventas` - Generar estadísticas de ventas
- `POST /api/reportes/estadisticas/inventario` - Generar estadísticas de inventario
- `POST /api/reportes/estadisticas/usuarios` - Generar estadísticas de usuarios
- `DELETE /api/reportes/{id}` - Eliminar reporte

#### 9. **Notificaciones** (`NotificacionController`)
- `GET /api/notificaciones` - Obtener todas las notificaciones
- `GET /api/notificaciones/{id}` - Obtener notificación por ID
- `GET /api/notificaciones/usuario/{usuarioId}` - Obtener notificaciones por usuario
- `GET /api/notificaciones/no-leidas/{usuarioId}` - Obtener notificaciones no leídas
- `POST /api/notificaciones` - Crear nueva notificación
- `PUT /api/notificaciones/{id}` - Actualizar notificación
- `PUT /api/notificaciones/{id}/marcar-leida` - Marcar notificación como leída
- `DELETE /api/notificaciones/{id}` - Eliminar notificación

#### 10. **Auditoría** (`AuditoriaController`)
- `GET /api/auditoria` - Obtener todos los registros de auditoría
- `GET /api/auditoria/{id}` - Obtener registro de auditoría por ID
- `GET /api/auditoria/usuario/{usuarioId}` - Obtener registros por usuario
- `GET /api/auditoria/tipo/{tipoAccion}` - Obtener registros por tipo de acción
- `GET /api/auditoria/fecha` - Obtener registros por rango de fechas
- `GET /api/auditoria/entidad/{entidad}` - Obtener registros por entidad
- `POST /api/auditoria` - Crear nuevo registro de auditoría
- `DELETE /api/auditoria/{id}` - Eliminar registro de auditoría

## Anotaciones Utilizadas

### Anotaciones de Clase
- `@Tag` - Define el nombre y descripción del grupo de endpoints
- `@RestController` - Marca la clase como controlador REST

### Anotaciones de Método
- `@Operation` - Define el resumen y descripción del endpoint
- `@ApiResponses` - Define las respuestas posibles del endpoint
- `@ApiResponse` - Define una respuesta específica con código y descripción
- `@Parameter` - Documenta los parámetros del endpoint

### Anotaciones de Contenido
- `@Content` - Define el contenido de la respuesta
- `@Schema` - Define el esquema de la respuesta

## Códigos de Respuesta Documentados

Todos los endpoints incluyen documentación de los siguientes códigos de respuesta:

- **200** - Operación exitosa
- **400** - Datos de entrada inválidos
- **401** - No autorizado (para endpoints de autenticación)
- **404** - Recurso no encontrado
- **500** - Error interno del servidor

## Uso de Swagger UI

1. **Acceder a la interfaz**: Navega a `http://localhost:8080/doc/swagger-ui.html`
2. **Explorar endpoints**: Los endpoints están organizados por tags (categorías)
3. **Probar endpoints**: Haz clic en "Try it out" para probar cualquier endpoint
4. **Ver esquemas**: Los modelos de datos están documentados automáticamente
5. **Descargar especificación**: Puedes descargar la especificación OpenAPI en formato JSON o YAML

## Personalización

### Modificar Configuración Global


### Agregar Nuevos Endpoints

Para documentar nuevos endpoints, agrega las anotaciones correspondientes:

@Operation(
    summary = "Resumen del endpoint",
    description = "Descripción detallada del endpoint"
)
@ApiResponses(value = {
    @ApiResponse(
        responseCode = "200", 
        description = "Operación exitosa",
        content = @Content(schema = @Schema(implementation = TuClase.class))
    ),
    @ApiResponse(
        responseCode = "400", 
        description = "Datos inválidos"
    )
})
public ResponseEntity<TuClase> tuMetodo(
    @Parameter(description = "Descripción del parámetro") @PathVariable Long id) {
    // Implementación
}

## Notas Importantes

- La documentación se genera automáticamente al compilar el proyecto
- Los cambios en las anotaciones requieren reiniciar la aplicación
- La interfaz de Swagger UI es responsiva y funciona en dispositivos móviles
- Todos los endpoints están protegidos por Spring Security, por lo que algunos pueden requerir autenticación 
# 🚀 API REST Perfulandia con HATEOAS

## 📋 Descripción

API REST completa para el sistema de gestión de Perfulandia con soporte HATEOAS (Hypermedia as the Engine of Application State) que permite navegación dinámica entre recursos.

## 🔗 Enlaces HATEOAS

### Estructura de Respuesta HATEOAS

Todas las respuestas incluyen enlaces HATEOAS en formato HAL (Hypertext Application Language):

```json
{
  "id": 1,
  "nombre": "Juan Pérez",
  "email": "juan@perfulandia.com",
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/usuarios/1"
    },
    "usuarios": {
      "href": "http://localhost:8080/api/usuarios"
    },
    "usuarioByEmail": {
      "href": "http://localhost:8080/api/usuarios/email/juan@perfulandia.com"
    }
  }
}
```

## 🎯 Endpoints Principales

### 👥 Gestión de Usuarios
- `GET /api/usuarios` - Obtener todos los usuarios
- `GET /api/usuarios/{id}` - Obtener usuario por ID
- `GET /api/usuarios/email/{email}` - Obtener usuario por email
- `POST /api/usuarios` - Crear nuevo usuario
- `PUT /api/usuarios/{id}` - Actualizar usuario
- `DELETE /api/usuarios/{id}` - Eliminar usuario

### 💰 Gestión de Pagos
- `GET /api/pagos` - Obtener todos los pagos
- `GET /api/pagos/{id}` - Obtener pago por ID
- `POST /api/pagos` - Crear nuevo pago
- `PUT /api/pagos/{id}` - Actualizar pago
- `DELETE /api/pagos/{id}` - Eliminar pago

### 📦 Gestión de Inventario
- `GET /api/inventario/sucursal` - Obtener inventario de sucursal
- `POST /api/inventario` - Crear producto en inventario
- `PUT /api/inventario/{id}` - Actualizar inventario
- `DELETE /api/inventario/{id}` - Eliminar del inventario

### 🔔 Notificaciones
- `GET /api/notificaciones` - Obtener todas las notificaciones
- `GET /api/notificaciones/{id}` - Obtener notificación por ID
- `GET /api/notificaciones/usuario/{usuarioId}` - Notificaciones por usuario
- `GET /api/notificaciones/no-leidas/{usuarioId}` - Notificaciones no leídas
- `POST /api/notificaciones` - Crear notificación
- `PUT /api/notificaciones/{id}/marcar-leida` - Marcar como leída

### 🚚 Logística y Envíos
- `GET /api/logistica/envios` - Obtener todos los envíos
- `GET /api/logistica/envios/{id}` - Obtener envío por ID
- `GET /api/logistica/envios/pedido/{pedidoId}` - Envío por pedido
- `GET /api/logistica/envios/estado/{estado}` - Envíos por estado
- `POST /api/logistica/envios` - Crear envío
- `PUT /api/logistica/envios/{id}/estado` - Actualizar estado de envío

### 📊 Auditoría y Monitoreo
- `GET /api/auditoria` - Obtener todas las auditorías
- `GET /api/auditoria/{id}` - Obtener auditoría por ID
- `GET /api/auditoria/usuario/{usuarioId}` - Auditorías por usuario
- `GET /api/auditoria/tipo/{tipoAccion}` - Auditorías por tipo
- `GET /api/auditoria/fecha?fechaInicio=X&fechaFin=Y` - Auditorías por fecha
- `GET /api/auditoria/entidad/{entidad}` - Auditorías por entidad

### 📈 Reportes y Estadísticas
- `GET /api/reportes` - Obtener todos los reportes
- `GET /api/reportes/{id}` - Obtener reporte por ID
- `GET /api/reportes/tipo/{tipo}` - Reportes por tipo
- `GET /api/reportes/usuario/{usuarioId}` - Reportes por usuario
- `POST /api/reportes/generar` - Generar reporte personalizado
- `POST /api/reportes/estadisticas/ventas` - Estadísticas de ventas
- `POST /api/reportes/estadisticas/inventario` - Estadísticas de inventario
- `POST /api/reportes/estadisticas/usuarios` - Estadísticas de usuarios

### 🏪 Gestión de Categorías
- `GET /api/categorias` - Obtener todas las categorías
- `GET /api/categorias/{id}` - Obtener categoría por ID
- `POST /api/categorias` - Crear categoría
- `PUT /api/categorias/{id}` - Actualizar categoría
- `DELETE /api/categorias/{id}` - Eliminar categoría

### 🏢 Gestión de Sucursales
- `GET /api/sucursales` - Obtener todas las sucursales
- `GET /api/sucursales/{id}` - Obtener sucursal por ID
- `POST /api/sucursales` - Crear sucursal
- `PUT /api/sucursales/{id}` - Actualizar sucursal
- `DELETE /api/sucursales/{id}` - Eliminar sucursal

## 🔐 Autenticación

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@perfulandia.com",
  "password": "password123"
}
```

### Registro
```http
POST /api/auth/registro
Content-Type: application/json

{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@perfulandia.com",
  "password": "password123"
}
```

## 🧪 Pruebas Unitarias

### Estructura de Pruebas
- **Controladores**: 4 pruebas por controlador
- **Servicios**: 4 pruebas por servicio
- **Assemblers**: 4 pruebas por assembler

### Ejecutar Pruebas
```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar pruebas específicas
mvn test -Dtest=UsuarioControllerTest

# Ejecutar con cobertura
mvn test jacoco:report
```

## 📚 Documentación Interactiva

### Swagger UI
- **URL**: `http://localhost:8080/doc/swagger-ui.html`
- **Descripción**: Documentación interactiva con ejemplos de uso

### OpenAPI Specification
- **URL**: `http://localhost:8080/v3/api-docs`
- **Formato**: JSON

## 🛠️ Configuración

### Variables de Entorno
```bash
# Base de datos
DB_URL=jdbc:mysql://localhost:3306/perfulandia
DB_USERNAME=root
DB_PASSWORD=password

# Base de datos de pruebas
DB_URL_TEST=jdbc:mysql://localhost:3306/perfulandia_test

# JWT
JWT_SECRET=tu_clave_secreta_muy_segura
JWT_EXPIRATION=86400000

# Puerto
PORT=8080
```

### Perfiles de Configuración
- **Desarrollo**: `application-dev.properties`
- **Pruebas**: `application-test.properties`
- **Producción**: `application.properties`

## 🚀 Ejecución

### Desarrollo
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

### Pruebas
```bash
mvn spring-boot:run -Dspring.profiles.active=test
```

### Producción
```bash
mvn spring-boot:run
```

## 📊 Ejemplos de Uso HATEOAS

### Navegación entre Recursos
1. **Obtener usuario**: `GET /api/usuarios/1`
2. **Seguir enlace a notificaciones**: Usar `_links.notificacionesUsuario.href`
3. **Seguir enlace a auditoría**: Usar `_links.auditoriasUsuario.href`

### Respuesta Completa con HATEOAS
```json
{
  "id": 1,
  "nombre": "Juan Pérez",
  "email": "juan@perfulandia.com",
  "activo": true,
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/usuarios/1"
    },
    "usuarios": {
      "href": "http://localhost:8080/api/usuarios"
    },
    "usuarioByEmail": {
      "href": "http://localhost:8080/api/usuarios/email/juan@perfulandia.com"
    },
    "notificacionesUsuario": {
      "href": "http://localhost:8080/api/notificaciones/usuario/1"
    },
    "auditoriasUsuario": {
      "href": "http://localhost:8080/api/auditoria/usuario/1"
    }
  }
}
```

## 🔧 Tecnologías Utilizadas

- **Spring Boot 3.2.3**
- **Spring HATEOAS**
- **Spring Security + JWT**
- **Spring Data JPA**
- **MySQL / H2**
- **JUnit 5 + Mockito**
- **Swagger/OpenAPI 3**
- **Lombok**

## 📝 Licencia

MIT License - Ver archivo LICENSE para más detalles. 
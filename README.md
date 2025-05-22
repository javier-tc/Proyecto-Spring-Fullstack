# Perfulandia - Sistema de Gestión de Perfumería

## Descripción
Sistema de gestión integral para una perfumería, desarrollado con Spring Boot y React. El sistema maneja usuarios, productos, ventas, inventario y más.

## Tecnologías Utilizadas
- Backend:
  - Java 17
  - Spring Boot 3.x
  - Spring Security con JWT
  - MySQL 8.x
  - Maven
  - JPA/Hibernate

## Estructura del Proyecto

### Modelos de Datos

#### Usuario y Autenticación
- **Usuario**
  - id (Long)
  - email (String, único)
  - password (String)
  - nombre (String)
  - apellido (String)
  - roles (Set<Rol>)
  - activo (boolean)

- **Rol**
  - id (Long)
  - nombre (TipoRol enum)
  - permisos (Set<Permiso>)
  - descripcion (String)

- **Permiso**
  - id (Long)
  - nombre (String, único)
  - descripcion (String)
  - tipo (TipoPermiso enum)

#### Catálogo y Productos
- **Producto**
  - id (Long)
  - nombre (String)
  - codigo (String)
  - descripcion (String)
  - precio (BigDecimal)
  - stock (Integer)
  - stockMinimo (Integer)
  - categoria (Categoria)
  - sucursal (Sucursal)
  - activo (boolean)

- **Categoria**
  - id (Long)
  - nombre (String, único)
  - descripcion (String)
  - activo (boolean)

#### Gestión de Sucursales
- **Sucursal**
  - id (Long)
  - nombre (String)
  - direccion (String)
  - telefono (String)
  - email (String)
  - activa (boolean)

- **Inventario**
  - id (Long)
  - sucursal (Sucursal)
  - producto (Producto)
  - cantidad (Integer)
  - stockMinimo (Integer)
  - stockMaximo (Integer)
  - ubicacionEstante (String)

#### Ventas y Pedidos
- **Pedido**
  - id (Long)
  - numeroPedido (String)
  - cliente (Usuario)
  - fechaCreacion (LocalDateTime)
  - fechaActualizacion (LocalDateTime)
  - estado (EstadoPedido enum)
  - detalles (List<DetallePedido>)
  - total (BigDecimal)
  - sucursal (Sucursal)
  - observaciones (String)

- **DetallePedido**
  - id (Long)
  - pedido (Pedido)
  - producto (Producto)
  - cantidad (Integer)
  - precioUnitario (BigDecimal)
  - subtotal (BigDecimal)

- **Factura**
  - id (Long)
  - numeroFactura (String, único)
  - pedido (Pedido)
  - cliente (Usuario)
  - fechaEmision (LocalDateTime)
  - subtotal (double)
  - impuestos (double)
  - descuentos (double)
  - total (double)
  - estado (EstadoFactura enum)
  - detalles (List<DetalleFactura>)
  - rutaArchivoPDF (String)
  - rutaArchivoXML (String)

#### Carrito de Compras
- **CarritoCompra**
  - id (Long)
  - usuario (Usuario)
  - items (List<ItemCarrito>)
  - fechaCreacion (LocalDateTime)
  - fechaActualizacion (LocalDateTime)
  - codigoPromocional (String)
  - subtotal (BigDecimal)
  - descuento (BigDecimal)
  - total (BigDecimal)

- **ItemCarrito**
  - id (Long)
  - carrito (CarritoCompra)
  - producto (Producto)
  - cantidad (Integer)
  - precioUnitario (BigDecimal)
  - subtotal (BigDecimal)

#### Promociones y Pagos
- **CodigoPromocional**
  - id (Long)
  - codigo (String, único)
  - descripcion (String)
  - tipoDescuento (TipoDescuento enum)
  - valorDescuento (double)
  - montoMinimoCompra (double)
  - fechaInicio (LocalDateTime)
  - fechaFin (LocalDateTime)
  - activo (boolean)
  - usosMaximos (int)
  - usosActuales (int)
  - creadoPor (Usuario)
  - fechaCreacion (LocalDateTime)

- **Pago**
  - id (Long)
  - pedido (Pedido)
  - numeroTransaccion (String)
  - monto (BigDecimal)
  - metodoPago (MetodoPago enum)
  - estado (EstadoPago enum)
  - fechaPago (LocalDateTime)
  - detallesTransaccion (String)

#### Sistema de Notificaciones
- **Notificacion**
  - id (Long)
  - usuario (Usuario)
  - tipo (TipoNotificacion enum)
  - mensaje (String)
  - leido (boolean)
  - fechaCreacion (LocalDateTime)
  - fechaLectura (LocalDateTime)

#### Reportes
- **Reporte**
  - id (Long)
  - tipo (TipoReporte enum)
  - generadoPor (Usuario)
  - fechaGeneracion (LocalDateTime)
  - rutaArchivo (String)
  - formato (String)
  - parametros (String)

### Enums
- **TipoRol**: CLIENTE, EMPLEADO_VENTAS, EMPLEADO_LOGISTICA, GERENTE_SUCURSAL, ADMINISTRADOR
- **TipoPermiso**: LECTURA, ESCRITURA, ELIMINACION, ADMINISTRACION
- **EstadoPedido**: PENDIENTE, CONFIRMADO, EN_PREPARACION, ENVIADO, ENTREGADO, CANCELADO
- **EstadoFactura**: EMITIDA, ANULADA, PAGADA, VENCIDA
- **TipoDescuento**: PORCENTAJE, MONTO_FIJO
- **MetodoPago**: TARJETA_CREDITO, TARJETA_DEBITO, PAYPAL, WEBPAY, TRANSFERENCIA
- **EstadoPago**: PENDIENTE, COMPLETADO, RECHAZADO, REEMBOLSADO
- **TipoNotificacion**: PUSH, SMS, EMAIL, SISTEMA
- **TipoReporte**: VENTAS_DIARIAS, VENTAS_MENSUALES, INVENTARIO, PEDIDOS_PENDIENTES, PRODUCTOS_MAS_VENDIDOS, CLIENTES_FRECUENTES, RENDIMIENTO_SUCURSAL, LOGISTICA_ENVIOS

## Configuración y Ejecución

### Requisitos Previos
- Java 17 o superior
- Maven
- MySQL 8.x
- Node.js y npm (para el frontend)

### Configuración de la Base de Datos
El proyecto utiliza MySQL como base de datos principal:

```properties
# Configuración MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/perfulandia?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuración JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true
```

### Ejecución del Proyecto

1. Clonar el repositorio:
```bash
git clone [url-del-repositorio]
cd fullstack
```

2. Configurar la base de datos:
- Crear una base de datos MySQL llamada `perfulandia`
- Configurar las credenciales en `.env.local`

3. Compilar y ejecutar el backend:
```bash
# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run
```

4. Acceder a la aplicación:
- API: http://localhost:8080

### Credenciales por Defecto
- Usuario administrador:
  - Email: admin@perfulandia.com
  - Password: (configurado en data.sql) / admin123

## Seguridad
- Autenticación basada en JWT
- Roles y permisos implementados
- Endpoints protegidos según rol
- CORS configurado para desarrollo

## Desarrollo
El proyecto está en desarrollo activo. Las características implementadas incluyen:
- ✅ Sistema de autenticación y autorización
- ✅ Gestión de usuarios y roles
- ✅ API RESTful
- ✅ Base de datos MySQL
- ✅ Gestión de productos
- ⏳ Sistema de ventas
- ✅ Gestión de inventario

## Estado de Implementación de Requerimientos

### Requerimientos Funcionales

#### 1. Gestión de Usuarios y Autenticación ✅
- ✅ Registro de usuarios
- ✅ Inicio de sesión con JWT
- ✅ Gestión de roles y permisos
- ✅ Actualización de información personal
- ⏳ Recuperación de contraseña
- ⏳ Validación de email

#### 2. Catálogo de Productos 🚧
- ✅ Modelo de datos definido
- ✅ CRUD de productos
- ✅ Gestión de categorías

#### 3. Procesamiento de Pedidos y Pagos ⏳
- ✅ Modelo de datos definido
- ⏳ Creación de pedidos
- ⏳ Procesamiento de pagos
- ⏳ Integración con pasarelas de pago
- ⏳ Gestión de facturas
- ⏳ Sistema de devoluciones

#### 4. Administración de Inventario 🚧
- ✅ Modelo de datos definido
- ✅ Control de stock
- ⏳ Alertas de stock mínimo
- ⏳ Gestión de sucursales
- ⏳ Transferencias entre sucursales

#### 5. Generación de Reportes ⏳
- ✅ Modelo de datos definido
- ⏳ Reportes de ventas
- ⏳ Reportes de inventario
- ⏳ Reportes de rendimiento
- ⏳ Exportación a diferentes formatos
- ⏳ Dashboard de métricas

#### 6. Logística ⏳
- ✅ Modelo de datos definido
- ⏳ Seguimiento de envíos
- ⏳ Planificación de rutas
- ⏳ Gestión de entregas
- ⏳ Integración con transportistas
- ⏳ Notificaciones de estado

#### 7. Estadísticas y Monitoreo ⏳
- ⏳ Dashboard de métricas
- ⏳ Análisis de ventas
- ⏳ Monitoreo de rendimiento
- ⏳ Alertas del sistema
- ⏳ KPIs de negocio

### Requerimientos No Funcionales

#### 1. Escalabilidad y Disponibilidad 🚧
- ✅ Arquitectura en capas
- ✅ Base de datos MySQL
- ⏳ Caché distribuido
- ⏳ Balanceo de carga
- ⏳ Alta disponibilidad

#### 2. Resistencia a Fallos 🚧
- ✅ Manejo de excepciones
- ✅ Transacciones
- ⏳ Circuit breakers
- ⏳ Reintentos automáticos
- ⏳ Recuperación de fallos

#### 3. Integración con Pagos ⏳
- ⏳ Integración con pasarelas
- ⏳ Procesamiento seguro
- ⏳ Múltiples métodos de pago
- ⏳ Gestión de reembolsos
- ⏳ Facturación electrónica

#### 4. Seguridad e Integridad 🚧
- ✅ Autenticación JWT
- ✅ Roles y permisos
- ✅ Encriptación de datos sensibles
- ⏳ Auditoría de acciones

### Historias de Usuario por Rol

#### Administrador 🚧
- ✅ Gestión básica de usuarios
- ✅ Asignación de roles
- ⏳ Monitoreo del sistema
- ⏳ Copias de seguridad
- ⏳ Gestión de permisos avanzada

#### Gerente de Sucursal ⏳
- ✅ Gestión de inventario
- ⏳ Reportes de ventas
- ⏳ Configuración de sucursal
- ⏳ Autorización de pedidos
- ⏳ Gestión de personal

#### Empleado de Ventas ⏳
- ⏳ Procesamiento de ventas
- ⏳ Gestión de devoluciones
- ✅ Verificación de inventario
- ⏳ Emisión de facturas
- ⏳ Atención al cliente

#### Empleado de Logística ⏳
- ⏳ Gestión de envíos
- ⏳ Planificación de rutas
- ⏳ Actualización de estados
- ⏳ Gestión de proveedores
- ⏳ Recepción de mercancías

#### Cliente 🚧
- ✅ Registro e inicio de sesión
- ✅ Actualización de perfil
- ⏳ Carrito de compras
- ⏳ Historial de pedidos
- ⏳ Evaluaciones de productos
- ⏳ Códigos promocionales
- ⏳ Atención al cliente

### Leyenda
- ✅ Implementado
- 🚧 En desarrollo/parcialmente implementado
- ⏳ Pendiente

## Contribución
1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia
Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para más detalles.
# Sistema de Turnero Médico

## Descripción
Este proyecto es una aplicación Java con interfaz gráfica (Swing) que implementa un sistema de gestión de turnos médicos. Actualmente, la funcionalidad principal implementada es el ABM (Alta, Baja, Modificación) de médicos.

## Funcionalidad Implementada

### Entidad Médico
La entidad que está completamente funcional es **Médico**. Esta permite realizar todas las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los datos de los médicos.

### Acceso a la Funcionalidad
Para acceder a la gestión de médicos:
1. Ejecutar la aplicación
2. Navegar al menú **Administración**
3. Seleccionar **Médicos**

Desde esta sección podrás:
- Agregar nuevos médicos
- Consultar médicos existentes
- Modificar información de médicos
- Eliminar médicos del sistema

## Configuración de Base de Datos

### Configuración de Conexión
Los parámetros de conexión a la base de datos se encuentran en la clase `DatabaseConfig.java`:
```
src/main/java/edu/up/controllers/infrastructure/DatabaseConfig.java
```

**Configuración actual:**
- Host: `localhost`
- Puerto: `3306`
- Base de datos: `turnero_medico`
- Usuario: `root`
- Contraseña: `123456`

### Modificar Configuración
Para cambiar los parámetros de conexión, edita las constantes en `DatabaseConfig.java`:
```java
private static final String DB_HOST = "localhost";
private static final String DB_PORT = "3306";
private static final String DB_NAME = "turnero_medico";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "123456";
```

### Inicialización de Base de Datos
El script para crear la estructura de la base de datos se encuentra en:
```
database_setup.sql
```

**Para inicializar la base de datos:**
1. Asegúrate de tener MySQL instalado y funcionando
2. Crea la base de datos `turnero_medico`
3. Ejecuta el script `database_setup.sql` en tu cliente MySQL

**Comando MySQL:**
```sql
CREATE DATABASE turnero_medico;
USE turnero_medico;
SOURCE database_setup.sql;
```

## Estructura del Proyecto

### Arquitectura
El proyecto sigue una arquitectura en capas:
- **UI (Interfaz de Usuario)**: Paquete `ui` con formularios Swing
- **Controladores**: Paquete `controllers` con lógica de negocio
- **Modelos**: Paquete `models` con entidades y repositorios
- **Infraestructura**: Configuración de base de datos y conexiones

### Tecnologías Utilizadas
- **Java**: Lenguaje de programación principal
- **Swing**: Framework para la interfaz gráfica
- **MySQL**: Base de datos relacional
- **JDBC**: Conectividad con base de datos
- **Maven**: Gestión de dependencias y construcción

## Ejecutar el Proyecto

### Prerrequisitos
1. Java JDK 21
2. MySQL Server
3. Maven

### Pasos para Ejecutar
1. **Configurar base de datos:**
   - Instalar MySQL
   - Crear base de datos `turnero_medico`
   - Ejecutar `database_setup.sql`

2. **Configurar conexión:**
   - Verificar/modificar parámetros en `DatabaseConfig.java`

3. **Compilar y ejecutar:**
   ```bash
   mvn clean compile
   mvn exec:java "-Dexec.mainClass=edu.up.App"
   ```

## Notas para el Profesor

- ✅ **Entidad Médico**: Completamente funcional con ABM
- ✅ **Base de datos**: Configuración centralizada en `DatabaseConfig.java`
- ✅ **Script SQL**: Disponible en `database_setup.sql`
- ✅ **Interfaz gráfica**: Acceso vía menú Administración → Médicos

La aplicación está lista para demostrar la funcionalidad de gestión de médicos. Todas las operaciones CRUD están implementadas y funcionando correctamente.

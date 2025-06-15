# Sistema de Turnero Médico

## Descripción
Sistema de gestión de turnos médicos desarrollado en Java 21 con interfaz gráfica Swing. Implementa una arquitectura MVC robusta con separación de capas, siguiendo principios SOLID y mejores prácticas de desarrollo.

## Arquitectura del Sistema

### Estructura de Capas

#### **Capa de Presentación (UI)**
- **Ubicación**: `src/main/java/edu/up/ui/`
- **Responsabilidad**: Interfaz gráfica y experiencia de usuario
- **Componentes**:
  - `MainFrame.java` - Ventana principal con navegación
  - `views/` - Vistas específicas por funcionalidad
  - `forms/` - Formularios de entrada de datos
  - `sections/` - Componentes reutilizables de UI

#### **Capa de Control (Controllers)**
- **Ubicación**: `src/main/java/edu/up/controllers/`
- **Responsabilidad**: Lógica de negocio y coordinación
- **Componentes**:
  - `MedicController.java` - Controlador de médicos
  - `service/` - Capa de servicios de negocio
  - `dao/` - Capa de acceso a datos
  - `infrastructure/` - Configuración y conexiones
  - `exceptions/` - Excepciones personalizadas

#### **Capa de Modelo (Models)**
- **Ubicación**: `src/main/java/edu/up/models/`
- **Responsabilidad**: Entidades y estructura de datos
- **Componentes**:
  - `entities/` - Entidades del dominio

#### **Utilidades**
- **Ubicación**: `src/main/java/edu/up/utils/`
- **Responsabilidad**: Funcionalidades transversales
- **Componentes**:
  - `Logger.java` - Sistema de logging

## Tecnologías y Dependencias
- **Java 21** - Lenguaje de programación
- **Swing** - Framework de interfaz gráfica
- **MySQL 8.0** - Base de datos relacional
- **JDBC** - Conectividad con base de datos
- **Maven** - Gestión de dependencias y construcción

## Configuración del Sistema

### Configuración de Base de Datos

Los parámetros de conexión están externalizados en:
```
src/main/resources/application.properties
```

## Patrones de Diseño Implementados

### 1. **MVC (Model-View-Controller)**
- Separación clara de responsabilidades
- Bajo acoplamiento entre capas
- Alta cohesión dentro de cada capa

### 2. **DAO (Data Access Object)**
- Abstracción del acceso a datos
- Interfaces para flexibilidad
- Implementaciones específicas por tecnología

### 3. **Service Layer**
- Lógica de negocio centralizada
- Transacciones y validaciones
- Reutilización de componentes

### 4. **Dependency Injection**
- Inyección manual de dependencias
- Inversión de control
- Facilita mantenimiento

### 5. **Singleton**
- `DatabaseConfig` - Configuración única
- `MySQLConnectionManager` - Gestión de conexiones
- `Logger` - Sistema de logging

## Ejecución del Proyecto

### Prerrequisitos
1. **Java JDK 21**
2. **MySQL Server**
3. **Maven**


#### Compilar y Ejecutar
```bash
# Limpiar y compilar
mvn clean compile

# Ejecutar aplicación
mvn exec:java
```

### Docker (Opcional)
```bash
# Iniciar MySQL con Docker
docker-compose up -d

# La aplicación se conectará automáticamente
mvn exec:java
```

## Funcionalidades Implementadas

### Gestión de Médicos (CRUD Completo)
- **Crear**: Agregar nuevos médicos al sistema
- **Leer**: Consultar médicos por ID, código o nombre
- **Actualizar**: Modificar información existente
- **Eliminar**: Remover médicos del sistema

### Interfaz de Usuario
- Navegación por menús
- Formularios de entrada
- Listados con selección
- Mensajes de confirmación y error

## Acceso a Funcionalidades

### Navegación Principal
1. **Ejecutar aplicación** → `mvn exec:java`
2. **Menú Administración** → Clic en "Administración"
3. **Gestión Médicos** → Clic en "Médicos"

### Operaciones Disponibles
- **Agregar Médico**: Botón "Nuevo" → Completar formulario
- **Editar Médico**: Seleccionar de lista → Botón "Editar"
- **Eliminar Médico**: Seleccionar de lista → Botón "Eliminar"
- **Buscar Médico**: Campo de búsqueda → Enter

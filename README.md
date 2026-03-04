# Foro Hub - API REST 

## Descripción
Esta es una API RESTful desarrollada como parte del desafío Foro Hub. Permite la gestión de tópicos de un foro, incluyendo funcionalidades completas de CRUD (Crear, Leer, Actualizar, Eliminar) y un sistema de autenticación seguro para proteger las rutas.

## Funcionalidades
* **Autenticación de Usuarios:** Sistema de login seguro que devuelve un Token JWT.
* **Gestión de Tópicos:** Rutas protegidas para crear, listar, detallar, actualizar y eliminar información en la base de datos.
* **Manejo de Errores:** Intercepción global de excepciones para devolver respuestas JSON estructuradas (códigos 400 y 404).
* **Seguridad de Credenciales:** Uso de variables de entorno para proteger datos sensibles y evitar su exposición en el repositorio.

## Tecnologías Utilizadas
* Java
* Spring Boot (Web, Data JPA, Security)
* PostgreSQL
* Flyway (Migraciones de base de datos)
* Auth0 java-jwt (Generación y validación de tokens)
* Maven

---

##  Configuración y Ejecución Local

Para ejecutar este proyecto en tu máquina local, sigue estos pasos:

### 1. Requisitos previos
* Tener instalado Java (JDK 17 o superior).
* Tener instalado PostgreSQL.
* IDE de tu preferencia (IntelliJ IDEA, Eclipse, etc.) o Maven instalado en tu terminal.

### 2. Configurar la Base de Datos
Crea una base de datos vacía en PostgreSQL llamada `dbforohub`. Las tablas se crearán automáticamente al iniciar la aplicación gracias a las migraciones de Flyway.

### 3. Variables de Entorno (¡Importante!)
Por motivos de seguridad, las credenciales de la base de datos y la firma del token no están en el código fuente. Antes de ejecutar la aplicación, debes configurar las siguientes **variables de entorno** en tu IDE o sistema operativo:

* `DB_USER`: Tu usuario de PostgreSQL (generalmente `postgres`).
* `DB_PASSWORD`: Tu contraseña de PostgreSQL.
* `JWT_SECRET`: Una palabra secreta o código para firmar los tokens (puedes usar cualquier valor para pruebas locales, ej. `123456`).

*(Si usas IntelliJ IDEA, puedes configurarlas yendo a `Edit Configurations` -> `Environment variables` y añadiendo estas tres claves con tus datos locales).*

### 4. Ejecutar la Aplicación
Una vez configuradas las variables y la base de datos, ejecuta la clase principal `ForohubApplication.java` para iniciar el servidor local en `http://localhost:8080`.

---

## Autor
* Carlos Barra

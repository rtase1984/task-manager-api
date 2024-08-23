# Task Manager API

## Descripción

Este proyecto es una API para la gestión de tareas que permite a los usuarios crear, actualizar, eliminar y consultar tareas de manera eficiente. La API está diseñada siguiendo los principios de arquitectura limpia y utilizando tecnologías modernas como Spring Boot, PostgreSQL y Docker. Este proyecto fue desarrollado como parte de una prueba técnica para un puesto de desarrollador Java.

## Características

- **Gestión de tareas:** Crear, leer, actualizar y eliminar tareas.
- **API RESTful:** Interfaz de usuario amigable que sigue las mejores prácticas de REST.
- **Persistencia de datos:** Uso de PostgreSQL para el almacenamiento persistente de tareas.
- **Contenerización:** El proyecto está preparado para ejecutarse en un entorno Docker, lo que facilita su despliegue.
- **Arquitectura limpia:** La arquitectura del proyecto sigue el enfoque de la arquitectura hexagonal para una alta mantenibilidad y escalabilidad.

## Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **PostgreSQL 15**
- **Docker**
- **Docker Compose**
- **Maven**

## Estructura del proyecto

El proyecto sigue la estructura estándar de un proyecto Spring Boot con una arquitectura hexagonal:

```bash
src/
|-- main/
|   |-- java/
|   |   |-- com.example.taskmanager/   # Código fuente principal
|   |       |-- application/           # Lógica de aplicación (casos de uso)
|   |       |-- domain/                # Entidades de dominio y lógica de negocio
|   |       |-- infrastructure/        # Adaptadores y configuración
|   |       |-- TaskManagerApplication.java   # Clase principal
|   |
|   |-- resources/
|       |-- application.yml            # Configuraciones de la aplicación
|-- test/                               # Pruebas unitarias e integración
=======


## Configuración y ejecución

### Requisitos previos

** Docker
** Docker Compose
** JDK 17

### Configuración de la base de datos

La base de datos PostgreSQL se configura automáticamente utilizando Docker Compose. Los detalles de configuración se encuentran en el archivo docker-compose.yml.

### Iniciar el proyecto

1. Clona el repositorio:

´´´bash 
git clone https://github.com/rtase1984/task-manager-api.git
cd task-manager-api 
´´´


2. Construye y levanta los contenedores con Docker Compose:

´´´bash docker-compose up --build ´´´


La aplicación estará disponible en http://localhost:8080.
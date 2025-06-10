# ToDo App - Full Stack Project

Aplicacion para el manejo de Tareas, desarrollado con una arquitectura hexagonal usando **Spring Boot**, **Angular**, **MySQL** y **Docker**.

## 📦 Estructura del Proyecto

```
fullstack-HotelReservation/
├── backend/         # Api para el manejo de tareas CRUD
├── database/
│   └── sql/         # Scripts y stored procedures
├── frontend/        # Interfaz de usuario (Angular)
├── docker-compose.yml
```

## 🚀 Tecnologías Usadas

- Spring Boot 3 (Java 21)
- Angular
- MySQL 8
- Docker + Docker Compose

## 🐳 Cómo levantar el proyecto

1. Clona el repositorio:

```bash
git clone https://github.com/jborbor/fullstack-ToDoApp.git
cd fullstack-ToDoApp
```

2. Levanta los contenedores:

```bash
docker compose up --build
```

Esto levantará:

- La base de datos MySQL
- Ejecutara automaticamente los scripts de creacion de BD, stored procedures, etc:
- Los microservicios backend
- El frontend Angular (vía NGINX)

## 📦 Documentacion de la api con OpenApi

http://localhost:8080/api/v1/swagger-ui.html

## 📬 Contacto

Proyecto desarrollado por [Jonathan Borbor].

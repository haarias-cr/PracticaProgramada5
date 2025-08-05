README — Práctica Programada 5: Fideflix (Cliente-Servidor + MySQL + GUI)

📦 Estructura del Proyecto

src/
│
├── cliente/
│   ├── ClienteSocket.java
│   ├── Main.java
│   ├── VentanaCrearUsuario.java
│   ├── VentanaDocumentales.java
│   ├── VentanaInicioSesion.java
│   ├── VentanaMenuPrincipal.java
│   ├── VentanaPeliculas.java
│   ├── VentanaSeries.java
│
├── modelo/
│   ├── Audiovisual.java
│   ├── Documental.java
│   ├── Pelicula.java
│   ├── Serie.java
│   ├── Usuario.java
│   ├── UsuarioNoEncontradoException.java
│
├── servidor/
│   ├── ConexionBD.java
│   ├── DocumentalDAO.java
│   ├── GestorUsuarios.java
│   ├── PeliculaDAO.java
│   ├── PruebaConexion.java
│   ├── SerieDAO.java
│   ├── ServidorBD.java
│   ├── ServidorUsuarios.java
│
├── util/
│   └── PasswordUtil.java
│
├── usuarios.dat
├── README.txt


⚙️ Requisitos y Dependencias

+ JDK 17 o superior (probado en Temurin 21)
+ MySQL Server instalado y corriendo (en Linux o Windows, según tu ambiente)
+ MySQL Connector/J (mysql-connector-j-9.4.0.jar) agregado al classpath/proyecto
+ IntelliJ IDEA, NetBeans, Eclipse o cualquier IDE Java con soporte Swing

📝 Estructura de la Base de Datos (Tablas SQL)

Tabla: peliculas

CREATE TABLE peliculas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    genero VARCHAR(50),
    duracion INT,
    anio_estreno INT,
    calificacion DECIMAL(2,1),
    director VARCHAR(100)
);

Tabla: series

CREATE TABLE series (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    genero VARCHAR(50),
    duracion INT,
    anio_estreno INT,
    calificacion DECIMAL(2,1),
    temporadas INT,
    episodios INT,
    creador VARCHAR(100)
);

Tabla: documentales

CREATE TABLE documentales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    genero VARCHAR(50),
    duracion INT,
    anio_estreno INT,
    calificacion DECIMAL(2,1),
    tema VARCHAR(100),
    director VARCHAR(100)
);

mysql> DESCRIBE series;
+--------------+--------------+------+-----+---------+----------------+
| Field        | Type         | Null | Key | Default | Extra          |
+--------------+--------------+------+-----+---------+----------------+
| id           | int          | NO   | PRI | NULL    | auto_increment |
| titulo       | varchar(100) | NO   |     | NULL    |                |
| genero       | varchar(50)  | YES  |     | NULL    |                |
| duracion     | int          | YES  |     | NULL    |                |
| anio_estreno | int          | YES  |     | NULL    |                |
| calificacion | decimal(2,1) | YES  |     | NULL    |                |
| temporadas   | int          | YES  |     | NULL    |                |
| episodios    | int          | YES  |     | NULL    |                |
| creador      | varchar(100) | YES  |     | NULL    |                |
+--------------+--------------+------+-----+---------+----------------+

mysql> DESCRIBE peliculas;
+--------------+--------------+------+-----+---------+----------------+
| Field        | Type         | Null | Key | Default | Extra          |
+--------------+--------------+------+-----+---------+----------------+
| id           | int          | NO   | PRI | NULL    | auto_increment |
| titulo       | varchar(100) | NO   |     | NULL    |                |
| genero       | varchar(50)  | YES  |     | NULL    |                |
| duracion     | int          | YES  |     | NULL    |                |
| anio_estreno | int          | YES  |     | NULL    |                |
| calificacion | decimal(2,1) | YES  |     | NULL    |                |
| director     | varchar(100) | YES  |     | NULL    |                |
+--------------+--------------+------+-----+---------+----------------+

mysql> DESCRIBE documentales;
+--------------+--------------+------+-----+---------+----------------+
| Field        | Type         | Null | Key | Default | Extra          |
+--------------+--------------+------+-----+---------+----------------+
| id           | int          | NO   | PRI | NULL    | auto_increment |
| titulo       | varchar(100) | NO   |     | NULL    |                |
| genero       | varchar(50)  | YES  |     | NULL    |                |
| duracion     | int          | YES  |     | NULL    |                |
| anio_estreno | int          | YES  |     | NULL    |                |
| calificacion | decimal(2,1) | YES  |     | NULL    |                |
| tema         | varchar(100) | YES  |     | NULL    |                |
| director     | varchar(100) | YES  |     | NULL    |                |
+--------------+--------------+------+-----+---------+----------------+

🖥️ Configuración MySQL y Usuario

1 - Crear Base de Datos:

CREATE DATABASE fideflix;
USE fideflix;

2 - Crear usuario para Java (recomendado):

CREATE USER 'javauser'@'%' IDENTIFIED BY 'fidelitas';
GRANT ALL PRIVILEGES ON fideflix.* TO 'javauser'@'%';
FLUSH PRIVILEGES;

3 - Importante: Asegúrate de que MySQL escuche en la red y que el firewall permita conexiones (puerto 3306).

🚀 Ejecución y Pruebas

1. Arranca los servidores en este orden:

    ServidorUsuarios.java - (Gestión de usuarios y autenticación, puerto 5000)
    ServidorBD.java - (Gestión CRUD de películas, series y documentales, puerto 6000)

    Ambos se ejecutan como aplicaciones Java estándar (desde tu IDE o consola).

2. Ejecuta el Cliente

    Corre el Main.java en el paquete cliente.
    Se abrirá la ventana de Inicio de Sesión.

3. Flujo Básico

    Crear usuario:
    Desde el login, puedes crear un usuario nuevo (¡no acepta duplicados!).

    Iniciar sesión:
    Ingresa con el usuario registrado.

    Gestión de datos:
    El menú principal te permite gestionar películas, series y documentales con operaciones CRUD (agregar, editar, eliminar).

    Validaciones:
    El sistema impide insertar datos vacíos, negativos, duplicados y muestra mensajes claros en caso de error.

🔑 Notas y Buenas Prácticas

    Contraseñas: El sistema ahora cifra las contraseñas al guardarlas en usuarios.dat (ver PasswordUtil.java).
    Feedback: El usuario recibe mensajes en caso de error de conexión, datos duplicados, validaciones, etc.


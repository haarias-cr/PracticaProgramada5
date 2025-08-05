# Práctica Programada 5: Fideflix (Cliente-Servidor + MySQL + GUI)

---

## 📂 Estructura del Proyecto

```bash
src/
 ├─ cliente/
 │   ├─ ClienteSocket.java
 │   ├─ Main.java
 │   ├─ VentanaCrearUsuario.java
 │   ├─ VentanaDocumentales.java
 │   ├─ VentanaInicioSesion.java
 │   ├─ VentanaMenuPrincipal.java
 │   ├─ VentanaPeliculas.java
 │   ├─ VentanaSeries.java
 ├─ modelo/
 │   ├─ Audiovisual.java
 │   ├─ Documental.java
 │   ├─ Pelicula.java
 │   ├─ Serie.java
 │   ├─ Usuario.java
 │   ├─ UsuarioNoEncontradoException.java
 ├─ servidor/
 │   ├─ ConexionBD.java
 │   ├─ DocumentalDAO.java
 │   ├─ GestorUsuarios.java
 │   ├─ PeliculaDAO.java
 │   ├─ PruebaConexion.java
 │   ├─ SerieDAO.java
 │   ├─ ServidorBD.java
 │   ├─ ServidorUsuarios.java
 ├─ util/
 │   ├─ PasswordUtil.java
 ├─ usuarios.dat
 └─ README.txt
```

---

## 🛠️ Requisitos y Dependencias

- **JDK 17 o superior** (probado en Temurin 21)
- **MySQL Server** instalado y corriendo (en Linux o Windows, según tu ambiente)
- **MySQL Connector/J** ([mysql-connector-j-9.4.0.jar](https://dev.mysql.com/downloads/connector/j/?os=26)) agregado al classpath/proyecto
- **IntelliJ IDEA**, **NetBeans**, **Eclipse** o cualquier IDE Java con soporte Swing

---

## 🗄️ Estructura de la Base de Datos (Tablas SQL)

### Tabla: `Peliculas`

<pre>CREATE TABLE peliculas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(100) NOT NULL,
  genero VARCHAR(50),
  duracion INT,
  anio_estreno INT,
  calificacion DECIMAL(2,1),
  director VARCHAR(100)
);</pre>


### Tabla: `Series`

<pre>CREATE TABLE series (
  id INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(100) NOT NULL,
  genero VARCHAR(50),
  duracion INT,
  anio_estreno INT,
  calificacion DECIMAL(2,1),
  temporadas INT,
  episodios INT,
  creador VARCHAR(100)
);</pre>


### Tabla: `Documentales`

<pre>CREATE TABLE documentales (
  id INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(100) NOT NULL,
  genero VARCHAR(50),
  duracion INT,
  anio_estreno INT,
  calificacion DECIMAL(2,1),
  tema VARCHAR(100),
  director VARCHAR(100)
);</pre>

## Ejemplo de DESCRIBE en MySQL

`Series`

| Field         | Type         | Null | Key | Default | Extra           |
| ------------- | ------------ | ---- | --- | ------- | --------------- |
| id            | int          | NO   | PRI | NULL    | auto\_increment |
| titulo        | varchar(100) | NO   |     | NULL    |                 |
| genero        | varchar(50)  | YES  |     | NULL    |                 |
| duracion      | int          | YES  |     | NULL    |                 |
| anio\_estreno | int          | YES  |     | NULL    |                 |
| calificacion  | decimal(2,1) | YES  |     | NULL    |                 |
| temporadas    | int          | YES  |     | NULL    |                 |
| episodios     | int          | YES  |     | NULL    |                 |
| creador       | varchar(100) | YES  |     | NULL    |                 |

`Peliculas`

| Field         | Type         | Null | Key | Default | Extra           |
| ------------- | ------------ | ---- | --- | ------- | --------------- |
| id            | int          | NO   | PRI | NULL    | auto\_increment |
| titulo        | varchar(100) | NO   |     | NULL    |                 |
| genero        | varchar(50)  | YES  |     | NULL    |                 |
| duracion      | int          | YES  |     | NULL    |                 |
| anio\_estreno | int          | YES  |     | NULL    |                 |
| calificacion  | decimal(2,1) | YES  |     | NULL    |                 |
| director      | varchar(100) | YES  |     | NULL    |                 |

`Documentales`

| Field         | Type         | Null | Key | Default | Extra           |
| ------------- | ------------ | ---- | --- | ------- | --------------- |
| id            | int          | NO   | PRI | NULL    | auto\_increment |
| titulo        | varchar(100) | NO   |     | NULL    |                 |
| genero        | varchar(50)  | YES  |     | NULL    |                 |
| duracion      | int          | YES  |     | NULL    |                 |
| anio\_estreno | int          | YES  |     | NULL    |                 |
| calificacion  | decimal(2,1) | YES  |     | NULL    |                 |
| tema          | varchar(100) | YES  |     | NULL    |                 |
| director      | varchar(100) | YES  |     | NULL    |                 |


## 🚀 Instrucciones para probar el Proyecto
1. Clona o descarga el repositorio en tu máquina local.

2. Crea la base de datos y ejecuta los scripts de las tablas SQL anteriores en tu instancia de MySQL.

3. Agrega el conector JDBC (mysql-connector-j-9.4.0.jar) al classpath/proyecto.

4. Compila todo el proyecto en tu IDE favorito.

## Inicia ambos servidores:

1. Ejecuta ServidorUsuarios.java (gestión de usuarios)

2. Ejecuta ServidorBD.java (CRUD de películas, series y documentales)

3. Ejecuta el cliente con Main.java:

4. Puedes crear usuarios nuevos

5. Iniciar sesión y gestionar el catálogo audiovisual con la interfaz gráfica (Swing)

6. ¡Listo! Puedes insertar, listar, editar y eliminar registros en todas las entidades desde el GUI.

## ✨ Características adicionales
1. Validación de duplicados: No permite usuarios o audiovisuales con nombre/año repetidos.

2. Contraseñas cifradas: Usa SHA-256 (PasswordUtil.java).

3. Validaciones de entrada: Evita valores vacíos o inválidos en formularios.

4. Separación Cliente-Servidor: Todo CRUD audiovisual va a través de sockets, no por acceso directo.

🧑‍💻 Autor
Harvi Arias
GitHub: haarias-cr

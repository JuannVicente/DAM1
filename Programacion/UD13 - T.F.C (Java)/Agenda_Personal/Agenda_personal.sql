CREATE DATABASE agenda_personal;

use agenda_personal;

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(40) NOT NULL,
    apellido VARCHAR(40) NOT NULL
);

CREATE TABLE users (
    id INT PRIMARY KEY,
    nombre_usuario VARCHAR(40) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    FOREIGN KEY(id) REFERENCES usuarios (id_usuario)
);

CREATE TABLE eventos (
    id_evento INT AUTO_INCREMENT PRIMARY KEY,
    nombre_evento VARCHAR(40) NOT NULL,
    descripcion_evento VARCHAR(255),
    fecha_evento DATETIME NOT NULL,
    fecha_creacion_de_evento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	estado ENUM('Completado', 'Pendiente', 'No_asistido') NOT NULL,
    creador_de_evento INT,
    FOREIGN KEY (creador_de_evento) REFERENCES usuarios(id_usuario)
);

CREATE TABLE tareas (
    id_tarea INT AUTO_INCREMENT PRIMARY KEY,
    nombre_tarea VARCHAR(40) NOT NULL,
    descripcion_tarea VARCHAR(255),
    fecha_tarea DATETIME NOT NULL,
    fecha_creacion_de_tarea TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	estado ENUM('Completado', 'Pendiente', 'No_completado') NOT NULL,
	
    creador_de_tarea INT,
    FOREIGN KEY (creador_de_tarea) REFERENCES usuarios(id_usuario)
);

CREATE TABLE notas(
id_nota INT PRIMARY KEY AUTO_INCREMENT,
nota VARCHAR(255) NOT NULL,
fecha_creacion_de_nota TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
creador_de_la_nota INT,
FOREIGN KEY (creador_de_la_nota) REFERENCES usuarios(id_usuario)
);


DELIMITER //

CREATE PROCEDURE CambiarEstadoEvento(
    IN idEvento INT,
    IN nuevoEstado ENUM('Completado', 'Pendiente', 'No_asistido')
)
BEGIN
    UPDATE eventos SET estado = nuevoEstado WHERE id_evento = idEvento;
END //

DELIMITER ;



DELIMITER //

CREATE PROCEDURE CambiarEstadoTarea(
    IN idTarea INT,
    IN nuevoEstado ENUM('Completado', 'Pendiente', 'No_completado')
)
BEGIN
    UPDATE tareas SET estado = nuevoEstado WHERE id_tarea = idTarea;
END //

DELIMITER ;

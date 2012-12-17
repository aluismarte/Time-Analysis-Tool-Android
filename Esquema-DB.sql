CREATE TABLE operacion
(
id_operacion INTEGER PRIMARY KEY AUTOINCREMENT,
nombre VARCHAR(150)
);

CREATE TABLE operacionDescripcion
(
id_descripcion INTEGER PRIMARY KEY AUTOINCREMENT,
id_operacion INTEGER,
nombre VARCHAR(150),
FOREIGN KEY (id_operacion) REFERENCES operacion(id_operacion)
);

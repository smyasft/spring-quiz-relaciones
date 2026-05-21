-- Asociaciones
INSERT INTO asociaciones (nombre, pais, presidente) VALUES
    ('Federacion Colombiana de Futbol', 'Colombia', 'Ramon Jesurun'),
    ('Dimayor',                         'Colombia', 'Fernando Jaramillo'),
    ('Conmebol',                        'Argentina','Alejandro Dominguez');

-- Entrenadores
INSERT INTO entrenadores (nombre, apellido, edad, nacionalidad) VALUES
    ('Alberto',  'Gamero',   62, 'Colombiano'),
    ('Julio',    'Comesana', 70, 'Uruguayo'),
    ('Paulo',    'Autuori',  67, 'Brasileno');

-- Clubes
INSERT INTO clubes (nombre, ciudad, anio_fundacion, entrenador_id, asociacion_id) VALUES
    ('Millonarios FC',    'Bogota',        1946, 1, 1),
    ('Atletico Nacional', 'Medellin',      1947, 2, 1),
    ('Junior FC',         'Barranquilla',  1924, 3, 1);

-- Jugadores
INSERT INTO jugadores (nombre, apellido, numero, posicion, id_club) VALUES
    ('David',    'Mackalister', 10, 'Mediocampista', 1),
    ('Stiven',   'Vega',         8, 'Mediocampista', 1),
    ('Jhon',     'Duque',        4, 'Defensa',       1),
    ('Jefferson','Duque',        9, 'Delantero',     2),
    ('Andres',   'Andrade',      6, 'Mediocampista', 2),
    ('Carlos',   'Bacca',        7, 'Delantero',     3);

-- Competiciones
INSERT INTO competiciones (nombre, monto_premio, fecha_inicio, fecha_fin) VALUES
    ('Liga BetPlay Dimayor', 500000000,  '2025-01-20', '2025-06-15'),
    ('Copa Colombia',        200000000,  '2025-02-01', '2025-07-30'),
    ('Copa Libertadores',    1000000000, '2025-02-12', '2025-11-30');

-- ManyToMany
INSERT INTO clubes_competiciones (club_id, competicion_id) VALUES
    (1, 1),(1, 2),(1, 3),
    (2, 1),(2, 2),(2, 3),
    (3, 1),(3, 2);

package com.clubnautico.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initData() {
        jdbcTemplate.execute("INSERT INTO personas (dni, nombre, apellidos, edad, num_telefono, fecha_nacimiento)\r\n"
        		+ "VALUES ('29087618L', 'Antonio', 'Rodriguez Muñoz', 24, '665098123', '2000-09-01'),\r\n"
        		+ "       ('90876512A', 'Juan', 'Cid Blanco', 22, '765092439', '2002-04-12'),\r\n"
        		+ "       ('11223344A', 'Gonzalo', 'Mendoza Serrano', 25, '601789134', '1999-01-30'),\r\n"
        		+ "       ('12345678A', 'Beatriz', 'Sanchez Cabeza', 26, '677091222', '1998-02-20'),\r\n"
        		+ "       ('98765432Z', 'Manuel', 'Jimenez Lopez', 23, '633803571', '2001-01-05'),\r\n"
        		+ "       ('09152473O', 'Lucia', 'Perez Ruiz', 23, '710986351', '2001-01-05'),\r\n"
        		+ "       ('01653419N', 'Marta', 'Luque Pereira', 21, '776109761', '2003-01-05');");
        
        jdbcTemplate.execute("INSERT INTO patrones (id)\r\n"
        		+ "SELECT id FROM personas WHERE dni IN ('29087618L', '90876512A', '11223344A');");
        
        jdbcTemplate.execute("INSERT INTO socios (id, numero_socio)\r\n"
        		+ "VALUES ((SELECT id FROM personas WHERE dni = '12345678A'), 111111111),\r\n"
        		+ "       ((SELECT id FROM personas WHERE dni = '98765432Z'), 222222222),\r\n"
        		+ "       ((SELECT id FROM personas WHERE dni = '09152473O'), 333333333);");
        
        jdbcTemplate.execute("INSERT INTO barcos (matricula, nombre, amarre, cuota)\r\n"
        		+ "VALUES ('333ABd09o', 'A290', 10, 1000.0),\r\n"
        		+ "       ('109AAJPp1', 'B100', 2, 215.10),\r\n"
        		+ "       ('000AAAopf', 'B150', 7, 515.50),\r\n"
        		+ "       ('762qwK3L9', 'Q350', 5, 415.30);");
        
        jdbcTemplate.execute("INSERT INTO barco_patron (barco_id, patron_id)\r\n"
        		+ "VALUES ((SELECT id FROM barcos WHERE matricula = '333ABd09o'), 1),\r\n"
        		+ "       ((SELECT id FROM barcos WHERE matricula = '109AAJPp1'), 1);");
        
        jdbcTemplate.execute("INSERT INTO barco_socio (barco_id, socio_id)\r\n"
        		+ "VALUES ((SELECT id FROM barcos WHERE matricula = '000AAAopf'), 4),\r\n"
        		+ "       ((SELECT id FROM barcos WHERE matricula = '762qwK3L9'), 5);");
        
        jdbcTemplate.execute("INSERT INTO salidas (fecha_salida, destino, barco_id, patron_id)\r\n"
        		
        		+ "VALUES ('2022-03-01 10:00:00', 'Malaga', (SELECT id FROM barcos WHERE matricula = '333ABd09o'), "
        		+ "(SELECT id FROM patrones WHERE id = 1)),\r\n"
        		+ "       ('2023-07-05 11:00:00', 'Galicia', (SELECT id FROM barcos WHERE matricula = '109AAJPp1'), "
        		+ "(SELECT id FROM patrones WHERE id = 1)),\r\n"
        		+ "       ('2024-02-03 12:00:00', 'Cadiz', (SELECT id FROM barcos WHERE matricula = '000AAAopf'), "
        		+ "(SELECT id FROM patrones WHERE id = 1)),\r\n"
        		+ "       ('2022-08-07 13:00:00', 'Valencia', (SELECT id FROM barcos WHERE matricula = '333ABd09o'), "
        		+ "(SELECT id FROM patrones WHERE id = 3));");
    }
}

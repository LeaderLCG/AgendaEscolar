package com.example.laion.logintestv2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    Connection con=null;

    public Connection conectar() throws Exception{
        Class.forName("com.mysql.jdbc.Driver").newInstance();

        con = DriverManager.getConnection("jdbc:mysql://107.180.12.177:3306/agendaescolar2", "proyectoagenda2", "proyecto");

        return con;

    }
}
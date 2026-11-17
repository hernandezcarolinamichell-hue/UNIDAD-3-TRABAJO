package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class Conexion {
    /**
 *
 * @author carol
 */

    private final String BD = "tienda308";
    private final String USER = "root";
    private final String PASSWORD = "1234567890";
    private final String URL = "jdbc:mysql://localhost:3306/" + BD;

    private Connection con;

    public Connection getConexion() {
        try {
            this.con = DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);
        } catch (SQLException e) {
            System.err.println(e);
        }

        return con;
    }
}
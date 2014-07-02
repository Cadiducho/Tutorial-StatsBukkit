package com.cadiducho.dev.Tutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import org.bukkit.plugin.Plugin;


public class MySQL {
    private final String user;
    private final String database;
    private final String password;
    private final String port;
    private final String hostname;
    public Tutorial plugin;
    
    private Connection connection;

    public MySQL(Plugin plugin, String hostname, String port, String database, String username, String password) {
        this.plugin = (Tutorial) plugin;
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = username;
        this.password = password;
        this.connection = null;
    }


    public Connection openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.user, this.password);
            plugin.getLogger().log(Level.INFO, "Conectado a MySQL");
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not connect to MySQL server! because: {0}", e.getMessage());
        } catch (ClassNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "JDBC Driver not found!");
        }
        return connection;
    }


    public boolean checkConnection() {
        return connection != null;
    }


    public Connection getConnection() {
        return connection;
    }


    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                plugin.getLogger().log(Level.SEVERE, "Error closing the MySQL Connection!");
                plugin.getLogger().log(Level.WARNING, e.toString());
            }
        }
    }

    public ResultSet querySQL(String query) {
        Connection c = null;

        if (checkConnection()) {
            c = getConnection();
        } else {
            c = openConnection();
        }

        Statement s = null;

        try {
            s = c.createStatement();
        } catch (SQLException e1) {
            plugin.getLogger().log(Level.SEVERE, e1.toString());
        }

        ResultSet ret = null;

        try {
            ret = s.executeQuery(query);
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, e.toString());
        }

        closeConnection();

        return ret;
    }

    public void updateSQL(String update) {

        Connection c = null;

        if (checkConnection()) {
            c = getConnection();
        } else {
            c = openConnection();
        }

        Statement s = null;

        try {
            s = c.createStatement();
            s.executeUpdate(update);
        } catch (SQLException e1) {
            plugin.getLogger().log(Level.SEVERE, e1.toString());
        }

        closeConnection();

    }
    public static void crearTabla(MySQL mysql, Connection connection, Plugin plugin){
        Statement statement;
        try{
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS StatsBukkit("
                    + "Player VARCHAR(40) NOT NULL,"
                    + "Asesinatos INT(8) NOT NULL,"
                    + "Muertes INT(8) NOT NULL,"
                    + "VecesConectado INT(8) NOT NULL) DEFAULT CHARSET=utf8 ;");
            plugin.getLogger().info("Se ha generado la tabla de las stats");
        } catch(SQLException e) {
            plugin.getLogger().severe("No se ha podido generar la tabla!");
            plugin.getLogger().log(Level.SEVERE, "Causa:{0}", e.getMessage());
        }
    }
}

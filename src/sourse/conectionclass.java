/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sourse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author TOJOZ
 */
public class conectionclass {

    public Connection conexion;
    public static String host;
    public static String puerto;
    public static String dbName;
    public static String username;
    public static String userpass;

    public Connection dbconection() {
//        final String auxhost = host;
//        final String auxpuerto = puerto;
//        final String auxdbName = dbName;
//        final String auxusername = username;
//        final String auxuserpass = userpass;
        try {
            final String Controlador = "org.postgresql.Driver";
            Class.forName(Controlador);
//creamos la ruta de la base de datos
            final String ruta = "jdbc:postgresql://" + host + ":" + puerto + "/" + dbName;
//Establecemos la conexión a la base de datos con el nombre de usuario y la contraseña
            conexion = DriverManager.getConnection(ruta, username, userpass);
        } catch (SQLException ex) {
            Logger.getLogger(conectionclass.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos.\nError:" + ex + "\nVerifique sus datos..", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(conectionclass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}

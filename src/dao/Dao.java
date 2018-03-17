/*
 * Dao.java
 *
 * Created on 10 de enero de 2012, 09:48 AM
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import dao.beans.BaseObject;

/**
 *
 * @author sfernandez
 *
 *  Esta interfaz provee las firmas necesarias para trabajar con el acceso
 *  a datos. Util para trabajar con JDBC.
 */
public interface Dao {

    /**
     * Retorna la conexión actual. Para usar transacciones.
     * @return Connection
     */
    public Connection getCurrentConnection();

    /**
     * @param String
     * @return ResultSet
     * Devuelve un ResultSet en base a una consulta SQL
     */
    public ResultSet getDBData(String sql) throws SQLException;

    /**
     * @param String
     * @param List
     * @return ResultSet
     * Devuelve un ResultSet en base a una consulta SQL con parámetros.
     * La lista de parámetros (params) debe tener los parámetros en orden
     * según la consulta SQL
     */
    public ResultSet getDBData(String sql, List params) throws SQLException;

    /**
     * @param String
     * @return boolean
     * Ejecuta un consulta SQL
     */
    public boolean executeQuery(String sql) throws SQLException;

    /**
     * @param String
     * @param List
     * @return boolean
     * Ejecuta una consulta SQL con parámetros.
     * La lista de parámetros (params) debe tener los parámetros en orden
     * según la consulta SQL
     */
    public boolean executeQuery(String sql, List params) throws SQLException;

    /**
     * Método útil para ejecutar un INSERT en base a un objeto que herede de 
     * BaseObject.
     * El String sql se arma en base a los annotations del Bean.
     * Por el momento éste método es util para tablas con claves simples
     *
     * @param BaseObject
     * @return boolean
     * @throws SQLException
     *
     * TODO: 
     *  * Hacer un tratamiento especial cuando la propiedad a la que se 
     *  accede sea otro objecto (Objetos anidados)
     *  * Hacer un tratamiento especial para clases con clave compuesta
     */
    public boolean insert(BaseObject obj) throws SQLException;

    /**
     * Método útil para ejecutar un DELETE en base a un objeto que herede de 
     * BaseObject.
     * El String sql se arma en base a los annotations del Bean.
     *
     * @param BaseObject
     * @throws SQLException
     */
    public void delete(BaseObject obj) throws SQLException;

    /**
     * Método útil para ejecutar un UPDATE en base a un objeto que herede de 
     * BaseObject.
     * El String sql se arma en base a los annotations del Bean.
     *
     * @param BaseObject
     * @throws SQLException
     */
    public void update(BaseObject obj) throws SQLException;

    /**
     * Cierra la conexion utilizada.
     * Debe ser invocada desde el manager.
     */
    public void closeResources();
}

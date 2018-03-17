/*
 * BaseDao.java
 *
 * Created on 10 de enero de 2012, 09:48 AM
 */
package dao.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


import dao.*;
import dao.annotations.*;
import dao.beans.*;
import beans.BeanUtil;
import sourse.conectionclass;
import sourse.utilities;

/**
 *
 * @author sfernandez
 */
// TODO: Manejar transacciones
public class BaseDao implements Dao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;
    private BeanUtil beanUtil = new BeanUtil();
     utilities Utilities = new utilities();

    /** Creates a new instance of BaseDao */
    public BaseDao() {
    }

    /**
     * Retorna la conexión actual. Para usar transacciones.
     * @return Connection
     */
    public Connection getCurrentConnection() {
        return this.conn;
    }

//    public BaseObject getRegistro(Class clazz, Object id) {
//        StringBuilder sql = new StringBuilder("select * from ");
//        
//        BaseObject obj = clazz.getClass().newInstance();
//        String tableName = ((TableInfo) obj.getClass().getAnnotation(TableInfo.class)).name();
//        String tableKey = null;
//        
//        Field field = obj.getClass().getDeclaredField("id");
//        field.setAccessible(true);
//        params.add(field.get(obj));
//
//        Annotation colAnn = field.getAnnotation(ColumnInfo.class);
//        ColumnInfo colInfo = (ColumnInfo) colAnn;
//
//        tableKey = colInfo.name();
//        
//        sql.append(tableName);
//        sql.append(" where ");
//        sql.append(tableKey);
//        sql.append(" = ? ");
//        
//        List params = new ArrayList();
//        Map<String, Object> registro = this.executeQuery(sql.toString(), params)
//        
//        params.add(id);
//        
//        (new BeanUtil()).populateObject()
//        
//        return obj;
//    }
    /**
     * @param String
     * @return ResultSet
     * Devuelve un ResultSet en base a una consulta SQL
     */
    public ResultSet getDBData(String sql) throws SQLException {
        try {
            rs = getConnection().createStatement().executeQuery(sql);
        } catch (SQLException sqle) {
            closeResources();
            throw sqle;
        } /*finally {
        closeResources();
        }*/

        return rs;
    }

    /**
     * @param String
     * @param List
     * Devuelve un ResultSet en base a una consulta SQL con parámetros.
     * La lista de parámetros (params) debe tener los parámetros en orden
     * según la consulta SQL
     */
    public ResultSet getDBData(String sql, List params) throws SQLException {
        try {
            ps = getConnection().prepareStatement(sql);
            setParams(ps, params);
            rs = ps.executeQuery();
        } catch (SQLException sqle) {
            closeResources();
            throw sqle;
        } /*finally {
        closeResources();
        }*/

        return rs;
    }

    /**
     * @param String
     * @return boolean
     * Ejecuta un consulta SQL
     */
    public boolean executeQuery(String sql) throws SQLException {
        boolean status = false;

        try {
            status = getConnection().createStatement().execute(sql);
        } catch (SQLException sqle) {
            closeResources();
            throw sqle;
        } /*finally {
        closeResources();
        }*/

        return status;
    }

    /**
     * @param String
     * @param List
     * @return boolean
     * Ejecuta una consulta SQL con parámetros.
     * La lista de parámetros (params) debe tener los parámetros en orden
     * según la consulta SQL
     */
    public boolean executeQuery(String sql, List params) throws SQLException {
        boolean status = false;

        try {
            ps = getConnection().prepareStatement(sql);
            setParams(ps, params);
            status = ps.execute();
        } catch (SQLException sqle) {
            closeResources();
            throw sqle;
        } finally {
            closeResources();
        }

        return status;
    }

    /**
     * @param String
     * @param List
     * @param Map
     * @return boolean
     * Ejecuta una consulta SQL con parámetros.
     * La lista de parámetros (params) debe tener los parámetros en orden
     * según la consulta SQL
     * Además obtiene el id generado para la tabla y lo carga en el objecto
     */
    private boolean executeQuery(String sql, List params, Map<String, Object> objParams) throws SQLException {
        boolean status = false;

        try {
            ps = getConnection().prepareStatement(sql);
            setParams(ps, params);
            rs = ps.executeQuery();

            // Si retorno un valor, está todo correcto
            if (rs.next()) {
                BaseObject obj = (BaseObject) objParams.get("baseObject");
                String fieldName = (String) objParams.get("fieldName");

                Field idField = obj.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(obj, rs.getObject(fieldName));

                objParams.remove("baseObject");
                objParams.put("baseObject", obj);

                // Sino, algo salio mal. Si es un query que no retorna nada,
                // utilizar el método executeQuery(String, List)
            } else {
                status = false;
            }

            status = true;
        } catch (SQLException sqle) {
            closeResources();
            throw sqle;
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            status = false;
        } catch (SecurityException ex) {
            ex.printStackTrace();
            status = false;
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }

        return status;
    }

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
    public boolean insert(BaseObject obj) throws SQLException {
        List listParams = new ArrayList();

        String tableKey = "";
        String tableName = ((TableInfo) obj.getClass().getAnnotation(TableInfo.class)).name();

        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");

        // Por cada propiedad del objeto
        for (Field field : obj.getClass().getDeclaredFields()) {
            Annotation colAnn = field.getAnnotation(ColumnInfo.class);
            ColumnInfo colInfo = (ColumnInfo) colAnn;

            if ( colInfo != null ) {
                // Si es el ID de la tabla
                if ("id".equals(field.getName())) {
                    tableKey = colInfo.name();
                }

                if (colInfo.insert()) {
                    try {
                        this.prepareInsertFieldString(sql, obj, field, colInfo, listParams);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NoSuchFieldException ex) {
                        Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        sql = new StringBuilder(sql.substring(0, (sql.length() - 1)));
        sql.append(") VALUES (");

        for (int i = 0; i < listParams.size(); i++) {
            sql.append("?,");
        }

        sql = new StringBuilder(sql.substring(0, (sql.length() - 1)));
        sql.append(") RETURNING " + tableName + "." + tableKey);

        // Preparo los parámetros para poblar el objeto
        Map<String, Object> objParams = new HashMap<String, Object>();
        objParams.put("fieldName", tableKey);
        objParams.put("baseObject", obj);

        // Ejecuto el query
        boolean status = executeQuery(sql.toString(), listParams, objParams);

        // Obtengo el objeto con el id obtenido del query
        obj = (BaseObject) objParams.get("baseObject");

        return status;
    }

    /**
     * Método útil para ejecutar un DELETE en base a un objeto que herede de 
     * BaseObject.
     * El String sql se arma en base a los annotations del Bean.
     *
     * @param BaseObject
     * @throws SQLException
     */
    public void delete(BaseObject obj) throws SQLException {
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        String tableKey = null;
        String tableName = ((TableInfo) obj.getClass().getAnnotation(TableInfo.class)).name();

        try {
            Field field = obj.getClass().getDeclaredField("id");
            field.setAccessible(true);
            params.add(field.get(obj));

            Annotation colAnn = field.getAnnotation(ColumnInfo.class);
            ColumnInfo colInfo = (ColumnInfo) colAnn;

            tableKey = colInfo.name();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (tableKey != null) {
            // Armo el String SQL
            sql.append("DELETE FROM " + tableName + " WHERE " + tableKey + " = ? ");
        } else {
            System.err.println("No se definió una propiedad ID para la clase");
        }

        //Ejecuto el query
        executeQuery(sql.toString(), params);
    }

    /**
     * Método útil para ejecutar un UPDATE en base a un objeto que herede de 
     * BaseObject.
     * El String sql se arma en base a los annotations del Bean.
     * Es importante que se obtenga el registro de la base de datos 
     *
     * @param BaseObject
     * @throws SQLException
     */
    public void update(BaseObject obj) throws SQLException {
        List listParams = new ArrayList();

        String tableKey = "";
        String tableName = ((TableInfo) obj.getClass().getAnnotation(TableInfo.class)).name();

        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");

        // Crear el SQL Update en base a cada campo de la clase
        for (Field field : obj.getClass().getDeclaredFields()) {
            Annotation colAnn = field.getAnnotation(ColumnInfo.class);
            ColumnInfo colInfo = (ColumnInfo) colAnn;

            if (colInfo != null) {
                
                // Si es el ID de la tabla, no necesitamos incluir en el update
                if ("id".equals(field.getName())) {

                    tableKey = colInfo.name();
                } else {

                    if (colInfo.update()) {
                        try {
                            this.prepareUpdateFieldString(sql, obj, field, colInfo, listParams);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NoSuchFieldException ex) {
                            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }

        sql = new StringBuilder(sql.substring(0, (sql.length() - 1)));
        sql.append(" WHERE " + tableKey + " = ? ");

        try {
            listParams.add(beanUtil.getBaseObjectKeyValue(obj));
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Ejecuto el query
        this.executeQuery(sql.toString(), listParams);
    }

    /**
     * @param StringBuilder
     * @param BaseObject
     * @param Field
     * @param ColumnInfo
     * @param List
     * Prepara la lista de columnas en una consulta SQL INSERT y 
     * sus valores en base a la definición de la clase.
     */
    private void prepareInsertFieldString(StringBuilder sql, BaseObject obj, Field field, ColumnInfo colInfo, List listParams)
            throws IllegalAccessException, NoSuchFieldException {

        Object tmpObj = null;
        try {
            field.setAccessible(true);
            tmpObj = field.get(obj);
        } catch (Exception e) {
        }

        if (tmpObj instanceof BaseObject) {
            BaseObject baseObj = (BaseObject) tmpObj; //field.get(obj);
            Field baseObjField = baseObj.getClass().getDeclaredField("id");

            prepareInsertFieldString(sql, baseObj, baseObjField, colInfo, listParams);
        } else {
            // Preparo el String sql
            sql.append(colInfo.name() + ",");

            // Preparo los parámetros
            if ((colInfo.upperCase()) && (!Utilities.isEmpty(field.get(obj))) && (field.get(obj) instanceof String)) {
                listParams.add(field.get(obj).toString().toUpperCase());
            } else {
                listParams.add(field.get(obj));
            }
        }
    }

    /**
     * @param StringBuilder
     * @param BaseObject
     * @param Field
     * @param ColumnInfo
     * @param List
     * Prepara la lista de columnas en una consulta SQL UPDATE y 
     * sus valores en base a la definición de la clase.
     */
    private void prepareUpdateFieldString(StringBuilder sql, BaseObject obj, Field field, ColumnInfo colInfo, List listParams)
            throws IllegalAccessException, NoSuchFieldException {

        Object tmpObj = null;
        try {
            field.setAccessible(true);
            tmpObj = field.get(obj);
        } catch (Exception e) {
        }

        if (tmpObj instanceof BaseObject) {
            BaseObject baseObj = (BaseObject) tmpObj; //field.get(obj);
            Field baseObjField = baseObj.getClass().getDeclaredField("id");

            prepareUpdateFieldString(sql, baseObj, baseObjField, colInfo, listParams);
        } else {
            // Preparo el String sql
            sql.append(colInfo.name() + " = ?,");

            // Preparo los parámetros
            listParams.add(field.get(obj));
        }
    }

    private Connection getConnection() {
        try {
             conectionclass ConectionClass = new conectionclass();
            if (conn == null) {
//                GetConnection.initDatasource((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext());
//                conn = GetConnection.getPooledConnection((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext());
//          Connection conn = ConectionClass.dbconection();
            } else if (conn.isClosed()) {
////                GetConnection.initDatasource((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext());
////                conn = GetConnection.getPooledConnection((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext());
////        Connection conn = ConectionClass.dbconection();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conn;
    }

    /**
     * Cierra la conexion utilizada.
     * Debe ser invocada desde el manager.
     */
    public void closeResources() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println(">> Ocurrió un error cerrando los recursos");
            e.printStackTrace();
        }
    }

    /**
     * Helper para armar el preparedStatement correctamente
     * Los valores deben ser agregados en el orden en que se cargan los parametros
     * @param ps
     * @param list
     * @throws SQLException 
     */
    public void setParams(PreparedStatement ps, List list) throws SQLException {
        int count = 1;

        Iterator it = list.listIterator();

        while (it.hasNext()) {
            Object obj = it.next();

            /*if (obj instanceof String) {
            String strObj = (String) obj;
            if (strObj.equals(""))
            ps.setNull(count, 0);
            else
            ps.setString(count, strObj);
            } else if (obj instanceof Integer) {
            Integer intObj = (Integer) obj;
            if (intObj != null)
            ps.setInt(count, intObj);
            else
            ps.setNull(count, 0);
            } else*/ if (obj instanceof java.util.Date) {
                java.util.Date utilDateObj = (java.util.Date) obj;
                Date dateObj = new Date(utilDateObj.getTime());

                if (dateObj != null) {
                    ps.setDate(count, dateObj);
                } else {
                    ps.setNull(count, 0);
                }
            } /*else if (obj instanceof Long) {
            Long longObj = (Long) obj;
            if (longObj != null)
            ps.setLong(count, longObj);
            else
            ps.setNull(count, 0);
            } else if (obj instanceof Double) {
            Double dblObj = (Double) obj;
            if (dblObj != null)
            ps.setDouble(count, dblObj);
            else
            ps.setNull(count, 0);
            } else if (obj instanceof BigDecimal) {
            BigDecimal bdObj = (BigDecimal) obj;
            if (bdObj != null)
            ps.setBigDecimal(count, bdObj);
            else
            ps.setNull(count, 0);
            } else if (obj instanceof Short) {
            Short shortObj = (Short) obj;
            if (shortObj != null)
            ps.setShort(count, shortObj);
            else
            ps.setNull(count, 0);
            } else if (obj instanceof BigInteger) {
            BigDecimal biObj = (BigDecimal) obj;
            if (biObj != null)
            ps.setO(count, biObj);
            else
            ps.setNull(count, 0);
            }*/ else if (obj == null) {
                ps.setNull(count, 0);
            } else {
                ps.setObject(count, obj);
            }

            count++;
        }
    }
}

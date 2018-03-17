/*
 * BaseManager.java
 *
 * Created on 10 de enero de 2012, 11:49 AM
 */
package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import beans.BeanUtil;
//import beans.QueryBean;
import dao.*;
import dao.beans.*;
import dao.impl.BaseDao;

/**
 *
 * @author sfernandez
 */
public class BaseManager {

    protected Dao dao;

    private ResultSet rs;

    private Map<String, Object> record = null;
    private List<Map<String, Object>> result = null;
    private BeanUtil beanUtil = new BeanUtil();

    /**
     * Creates a new instance of BaseManager
     */
    public BaseManager() {
        this.dao = new BaseDao();
    }

    /**
     * Obtiene todos los registros de una tabla en base a la definición del bean
     * correspondiente.
     *
     * @param Class
     * @param Object
     */
//    public BaseObject getRegistro(Class<? extends BaseObject> clazz, Object id) throws SQLException {
//        BaseObject bObject = null;
//        List param = new ArrayList();
//        
//        try {
//            param.add(id);
//
//            Map<String, Object> registro = beanUtil.prepareData(this.getDBDataAsRs(QueryBean.getSelectSQLStringById(clazz), param)).iterator().next();
//            
//            bObject = clazz.newInstance();
//            beanUtil.populateObject(bObject, registro);
//        } catch (IllegalAccessException ex) {
//            ex.printStackTrace();
//        } catch (InstantiationException ex) {
//            ex.printStackTrace();
//        } catch (NoSuchFieldException ex) {
//            ex.printStackTrace();
//        }
//        
//        return bObject;
//    }
    /**
     * Obtiene todos los registros de una tabla mapeada a un Bean, los carga en
     * un List
     * @autor:
     * @param clazz clase
     * @param sql
     * @return List<Class<? extends BaseObject>>
     */
    public List getRegistros(Class<? extends BaseObject> clazz, String sql) {
        BaseObject bObject = null;
        List listaRegistro = new ArrayList();
        //comentario de prueba
        try {
            List<Map<String, Object>> registros = beanUtil.prepareData(this.getDBDataAsRs(sql));

            for (Map<String, Object> registro : registros) {
                bObject = clazz.newInstance();

                beanUtil.populateObject(bObject, registro);

                listaRegistro.add(bObject);
            }

        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaRegistro;
    }

    /**
     * Método útil para ejecutar un INSERT en base a un objeto que herede de
     * BaseObject. El String sql se arma en base a los annotations del Bean. Por
     * el momento éste método es util para tablas con claves simples
     *
     * @param BaseObject
     * @return boolean
     * @throws SQLException
     */
    public boolean insert(BaseObject obj) throws SQLException {
        return dao.insert(obj);
    }

    /**
     * Método útil para ejecutar un DELETE en base a un objeto que herede de
     * BaseObject. El String sql se arma en base a los annotations del Bean.
     *
     * @param BaseObject
     * @return boolean
     * @throws SQLException
     */
    public void delete(BaseObject obj) throws SQLException {
        dao.delete(obj);
    }

    /**
     * Método útil para ejecutar un UPDATE en base a un objeto que herede de
     * BaseObject. El String sql se arma en base a los annotations del Bean. Es
     * importante que se obtenga el registro de la base de datos
     *
     * @param BaseObject
     * @throws SQLException
     */
    public void update(BaseObject obj) throws SQLException {
        dao.update(obj);
    }

    /**
     * @param String
     * @return List Devuelte un List de Map<String, Object>. La llave
     * corresponde al nombre de la columna en la base de datos, y el valor es el
     * valor correspondiente para esa columna para ese registro
     */
    protected List<Map<String, Object>> getDBData(String sql) throws SQLException {
        result = new ArrayList<Map<String, Object>>();
        record = null;
        rs = null;

        result = beanUtil.prepareData(dao.getDBData(sql));

        return result;
    }

    protected ResultSet getDBDataAsRs(String sql) throws SQLException {
        return dao.getDBData(sql);
    }

    /**
     * @param String
     * @param List
     * @return List Devuelte un List de Map<String, Object> con parámetros. La
     * lista de parámetros (params) debe tener los parámetros en orden según la
     * consulta SQL. La llave corresponde al nombre de la columna en la base de
     * datos, y el valor es el valor correspondiente para esa columna para ese
     * registro.
     */
    protected List<Map<String, Object>> getDBData(String sql, List params) throws SQLException {
        result = new ArrayList<Map<String, Object>>();
        record = null;
        rs = null;

        result = beanUtil.prepareData(dao.getDBData(sql, params));

        return result;
    }

    protected ResultSet getDBDataAsRs(String sql, List params) throws SQLException {
        return dao.getDBData(sql, params);
    }

    /**
     * @param String
     * @return boolean Ejecuta un consulta SQL
     */
    protected boolean executeQuery(String sql) throws SQLException {
        return dao.executeQuery(sql);
    }

    /**
     * @param String
     * @param List
     * @return boolean Ejecuta una consulta SQL con parámetros. La lista de
     * parámetros (params) debe tener los parámetros en orden según la consulta
     * SQL
     */
    protected boolean executeQuery(String sql, List params) throws SQLException {
        return dao.executeQuery(sql, params);
    }

    private void closeResources() {
        try {
            if (rs != null) {
                rs.close();
            }

            dao.closeResources();
        } catch (SQLException ex) {
            System.err.println(">> Ocurrió un error cerrando los recursos");
            ex.printStackTrace();
        }
    }
}

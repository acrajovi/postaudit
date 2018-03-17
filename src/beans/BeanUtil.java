/*
 * BeanUtil.java
 *
 * Created on 23 de enero de 2012, 09:59 AM
 */
package beans;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dao.beans.*;
import dao.annotations.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author sfernandez
 */
public class BeanUtil {

    /** Default Constructor of BeanUtil */
    public BeanUtil() {
    }

    /** 
     * Carga los datos de un objeto en base a los datos obtenidos en el Map.
     * Utiliza los annotations de la clase para obtener los KEYs del Map.
     * @param BaseObject
     * @param Map<String, BaseObject>
     */
    public void populateObject(BaseObject obj, Map<String, Object> registro) {
        // Obtenemos los campos de la tabla
        for (Field field : obj.getClass().getDeclaredFields()) {
            // Hacemos accesible el campo
            field.setAccessible(true);

            // Obtenemos el annotation ColumnInfo
            Annotation colAnn = field.getAnnotation(ColumnInfo.class);
            ColumnInfo colInfo = (ColumnInfo) colAnn;
            try {

                if (colAnn != null) {
                    
                    // Verificamos el tipo de datos del objeto
                    if (field.get(obj) instanceof BaseObject) {
                        field.set(obj, convertIntoBaseObject(field.getType(), registro.get(colInfo.name())));
                    } else {
                        // Obtenemos el valor del registro y lo cargamos en el objeto
                        field.set(obj, registro.get(colInfo.name()));
                    }
                }
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                Logger.getLogger(BeanUtil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(BeanUtil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(BeanUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Método que prepara el List de Maps. Asigna como clave
     * el nombre de la columna de la base de datos y el valor de la
     * columna para cada registro.
     * @param ResultSet
     * @return List<Map<String, Object>>
     */
    public List<Map<String, Object>> prepareData(ResultSet rs) throws SQLException {
        Map<String, Object> record;
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        ResultSetMetaData rsm = rs.getMetaData();

        while (rs.next()) {
            record = new HashMap<String, Object>();

            for (int i = 1; i <= rsm.getColumnCount(); i++) {
                record.put(rsm.getColumnName(i), rs.getObject(i));
            }

            result.add(record);
        }

        return result;
    }

    /**
     * Método privado para setear el id de las propiedades del objeto que 
     * son referencia a otros objetos (BaseObject)
     * @param Class
     * @param Object
     * @return BaseObject
     */
    private BaseObject convertIntoBaseObject(Class clazz, Object idValue) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        BaseObject obj = (BaseObject) clazz.newInstance();

        // Obtenemos el field correspondiente al id
        Field idField = obj.getClass().getDeclaredField("id");
        idField.setAccessible(true);

        // Seteamos el ID 
        // TODO: Verificar que ocurre cuando el valor es nulo
        idField.set(obj, idValue);

        return obj;
    }

    public Object getBaseObjectKeyValue(BaseObject obj) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField("id");
        field.setAccessible(true);

        return field.get(obj);
    }
}

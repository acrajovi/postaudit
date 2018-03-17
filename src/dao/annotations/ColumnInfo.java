/*
 * TableInfo.java
 *
 * Created on 13 de enero de 2012, 12:19 PM
 */

package dao.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author sfernandez
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnInfo {

    public String name();
    public boolean insert() default true;
    public boolean update() default true;
    public boolean delete() default true;
    public boolean upperCase() default false;
}

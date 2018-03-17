/*
 * BaseObject.java
 *
 * Created on 13 de enero de 2012, 11:39 AM
 */

package dao.beans;

/**
 *
 * @author sfernandez
 */
public abstract class BaseObject {
    
    public abstract String toString();
    public abstract boolean equals(Object o);
    public abstract int hashCode();
}

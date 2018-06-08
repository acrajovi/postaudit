package procesos;

import java.sql.Connection;
import java.sql.SQLException;

public class AuditoriaBD {

    private final Connection conn;

    public AuditoriaBD(Connection conn) throws ClassNotFoundException, SQLException {

        this.conn = conn;
    }

    public String make() throws SQLException, ClassNotFoundException {
        return this.make(false);
    }

    public String make(boolean newTables) throws SQLException, ClassNotFoundException {

        String auxStr = "";
        try {
            Auditoria audit = new Auditoria(this.conn);

            conn.setAutoCommit(false);

            if (!newTables) {

                // 1) Se crea el esquema de auditoria
                conn.createStatement().execute("CREATE SCHEMA auditoria AUTHORIZATION postgres; COMMENT ON SCHEMA auditoria IS 'Esquema para manejar relaciones y funciones relacionadas a auditoria';");
                conn.commit();
            }

            // 2) Agregamos el campo usuario a las tablas que falten
            System.out.println("Agregando campos usuario...");
            System.out.println(audit.addUserField());
            conn.createStatement().execute(audit.addUserField());
            conn.commit();

            // 3) Creamos las tablas de auditoria
            String tabless = audit.createAuditTables();
            System.out.println("Creando tablas...");
            System.out.println(tabless);
            conn.createStatement().execute(tabless);

            // La ejecución de funciones falla, por eso el commit aca.
            conn.commit();

            // 4) Creamos las funciones
            auxStr = audit.createAuditFunctionsAndTriggers(conn);
            System.out.println("Creando triggers...");
            System.out.println(auxStr);

            conn.createStatement().execute(auxStr);

            conn.commit();
        } catch (SQLException sqle) {
            conn.rollback();
            throw sqle;
        }
        /*finally {
            try {
                conn.close();
            }
            catch( Exception ex1 ) {
                ex1.printStackTrace();
            }
        }*/

        return auxStr;
    }

    public String updateTables() throws SQLException, ClassNotFoundException {

        String auxStr = "";
        try {

            Auditoria audit = new Auditoria(this.conn);

            conn.setAutoCommit(false);

            // 1) Comparar cada campo de las tablas con las tablas de auditoria
            // 2) Si se encuentra que no posee un campo,
            // generar una sentencia alter table add column en la tabla de auditoria
            String alterTables = audit.createAlterTables();
            System.out.println("Creando Alter Tables...");
            System.out.println(alterTables);
            conn.createStatement().execute(alterTables);

            conn.commit();

            // 3) Generar nuevamente las funciones
            auxStr = audit.createAuditFunctionsAndTriggers(conn);
            System.out.println("Creando triggers...");
            System.out.println(auxStr);

        } catch (SQLException sqle) {
            conn.rollback();
            throw sqle;
        }
        /*finally {
            try {
                conn.close();
            }
            catch( Exception ex1 ) {
                ex1.printStackTrace();
            }
        }*/

        // Devuelve las funciones para cargarlas en un texarea y ejecutar desde ahi
        return auxStr;
    }
}

package procesos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Sql;

public class Auditoria {

    private final Connection conn;
    private ResultSet rs;

    public Auditoria(Connection conn) throws ClassNotFoundException, SQLException {
        this.conn = conn;
    }

    /**
     * Devuelve un String con la sentencia SQL generada de agregado de campo
     * a las tablas, para ser ejecutadas.
     * @return
     * @throws SQLException
     */
    public String addUserField() throws SQLException {
        String sql = Sql.getAllTablesWithoutUserField();
        String query = "";

        try {
            rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                query += "ALTER TABLE " + rs.getString("table_name") + " ADD COLUMN usuariosys character varying(50);\n";
            }

            return query;
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    /**
     * Ejecuta las sentencias de agregado del campo a las tablas
     * @throws SQLException
     */
    public void addUserFieldd() throws SQLException {
        String sql = Sql.getAllTablesWithoutUserField();
        String query = "";

        try {
            rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                query = "ALTER TABLE " + rs.getString("table_name") + " ADD COLUMN usuariosys character varying(50);\n";

                conn.createStatement().execute(query);
            }

        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    /**
     * Devuelve un String para la generación de las tablas de auditoria
     * para ser ejecutado manualmente
     * @return
     * @throws SQLException
     */
    public String createAuditTables() throws SQLException {
        String sql = Sql.getTablesDefinition();
        String query = "", table = "", head = "", body = "", foot = "";

        boolean finished = false;

        try {

            rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {

                if (!table.equals(rs.getString("table_name"))) {
                    table = rs.getString("table_name");
                    finished = true;

                    if ("usuarios_sucursales".equals(table)) {
                        finished = true;
                    }

                    if (!"".equals(head) && finished) {
                        query += head + body + foot;

                        finished = false;

                        head = body = foot = "";
                    }

                    head += "CREATE TABLE auditoria." + table + " (\n";

                    foot += "\taudit_usuariosys character varying(50),\n" +
                            "\taudit_usuariodb character varying(100) NOT NULL DEFAULT \"current_user\"(),\n" +
                            "\taudit_fecha timestamp without time zone NOT NULL DEFAULT now(),\n" +
                            "\taudit_usuarioip character(15),\n" +
                            "\taudit_accion character(1) NOT NULL\n" +
                            ");\n\n";
                }

                if (!"usuariosys".equals(rs.getString("column_name"))) {
                    body += "\t" + rs.getString("column_name") + "_new " + rs.getString("data_type") + ",\n" +
                            "\t" + rs.getString("column_name") + "_old " + rs.getString("data_type") + ",\n";
                }

                if (!"".equals(head) && rs.isLast()) {
                    query += head + body + foot;

                    finished = false;

                    head = body = foot = "";
                }
            }

            return query;
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    /**
     * Llama a una función de generación de funciones y triggers de auditoria
     * para ser ejecutado manualmente
     * @param auxConn
     * @return
     * @throws SQLException
     */
    public String createAuditFunctionsAndTriggers(Connection auxConn) throws SQLException {
        return this.createFunction(auxConn);
        //this.createFunctionNew();
    }

    /**
     * Devuelve un String con la generación de las funciones y triggers para cada tabla
     * para ser ejecutado manualmente
     * @param auxConn
     * @return
     * @throws SQLException
     */
    private String createFunction(Connection auxConn) throws SQLException {
        String sql = Sql.getAuditTables();
        String query = "", table = "", insert = "", update = "", delete = "", head = "", foot = "", deleteHead = "",
                deleteFoot = "", insertHead = "", insertFoot = "", updateHead = "", updateFoot = "", insert_v = "",
                delete_v = "", update_v = "";

        boolean finished = false;

        try {

            if (auxConn != null) {
                rs = auxConn.createStatement().executeQuery(sql);
            } else {
                rs = conn.createStatement().executeQuery(sql);
            }

            while (rs.next()) {

                if (!table.equals(rs.getString("table_name"))) {
                    table = rs.getString("table_name");
                    finished = true;

                    if (!"".equals(head) && finished) {
                        query += head + deleteHead + delete + "\t\t\taudit_usuarioip,\n\t\t\taudit_accion\n\t\t) values (\n" + delete_v + deleteFoot + updateHead + update + "\t\t\taudit_usuarioip,\n\t\t\taudit_accion\n\t\t) values (\n" + update_v + updateFoot + insertHead + insert + "\t\t\taudit_usuarioip,\n\t\t\taudit_accion\n\t\t) values (\n" + insert_v + insertFoot + foot;

                        finished = false;

                        head = deleteHead = delete = delete_v = deleteFoot = insertHead = insert = insert_v =
                                insertFoot = updateHead = update = update_v = updateFoot = foot = "";
                    }

                    head += "CREATE OR REPLACE FUNCTION auditoria.audit_" + table + "() RETURNS trigger AS $BODY$ begin\n";

                    deleteHead += "\tif TG_OP = 'DELETE'  then \n";
                    deleteHead += "\t\tinsert into auditoria." + table + "( \n";

                    deleteFoot += "\t\t\t(select ipacceso from usuarios where usuario = old.usuariosys),\n";
                    deleteFoot += "\t\t\tsubstr(TG_OP,1,1)\n\t\t);\n";

                    updateHead += "\telsif TG_OP = 'UPDATE' then \n";
                    updateHead += "\t\tinsert into auditoria." + table + "( \n";

                    updateFoot += "\t\t\t(select ipacceso from usuarios where usuario = new.usuariosys),\n";
                    updateFoot += "\t\t\tsubstr(TG_OP,1,1)\n\t\t);\n";

                    insertHead = "\telse \n";
                    insertHead += "\t\tinsert into auditoria." + table + "( \n";

                    insertFoot += "\t\t\t(select ipacceso from usuarios where usuario = new.usuariosys),\n";
                    insertFoot += "\t\t\tsubstr(TG_OP,1,1)\n\t\t);\n";
                    insertFoot += "end if;\n";

                    foot += "return new;\nend;\n$BODY$\nLANGUAGE 'plpgsql' VOLATILE\nCOST 100;\n\n";
                    foot += "DROP TRIGGER IF EXISTS public_audit_" + table + " ON " + table + ";";
                    foot += "CREATE TRIGGER public_audit_" + table + "\nAFTER INSERT OR UPDATE OR DELETE\nON " + table + "\nFOR EACH ROW\nEXECUTE PROCEDURE auditoria.audit_" + table + "();\n\n\n";
                }

                if ("usuariosys".equals(rs.getString("column_name"))) {
                    delete += "\t\t\taudit_usuariosys,\n";
                    update += "\t\t\taudit_usuariosys,\n";
                    insert += "\t\t\taudit_usuariosys,\n";
                } else {
                    delete += "\t\t\t" + rs.getString("column_name") + "_old,\n";

                    update += "\t\t\t" + rs.getString("column_name") + "_new,\n";
                    update += "\t\t\t" + rs.getString("column_name") + "_old,\n";

                    insert += "\t\t\t" + rs.getString("column_name") + "_new,\n";
                }

                delete_v += "\t\t\told." + rs.getString("column_name") + ",\n";

                update_v += "\t\t\tnew." + rs.getString("column_name") + ",\n";
                if (!"usuariosys".equals(rs.getString("column_name"))) {
                    update_v += "\t\t\told." + rs.getString("column_name") + ",\n";
                }

                insert_v += "\t\t\tnew." + rs.getString("column_name") + ",\n";

                if (!"".equals(head) && rs.isLast()) {
                    query += head + deleteHead + delete + "\t\t\taudit_usuarioip,\n\t\t\taudit_accion\n\t\t) values (\n" + delete_v + deleteFoot + updateHead + update + "\t\t\taudit_usuarioip,\n\t\t\taudit_accion\n\t\t) values (\n" + update_v + updateFoot + insertHead + insert + "\t\t\taudit_usuarioip,\n\t\t\taudit_accion\n\t\t) values (\n" + insert_v + insertFoot + foot;
                    finished = false;
                    head = deleteHead = delete = delete_v = deleteFoot = insertHead = insert = insert_v =
                            insertFoot = updateHead = update = update_v = updateFoot = foot = "";
                }
            }

            return query;
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    public String createAlterTables() throws SQLException {

        String sql = Sql.getAuditTables();
        String query = "", tableName = "", columnName = "", dataType = "";

        ResultSet rsAux;

        try {

            // Obtener todas las tablas con sus respectivas columnas
            rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {

                tableName = rs.getString("table_name");
                columnName = rs.getString("column_name");
                dataType = rs.getString("data_type");

                if (!columnName.equals("usuariosys")) {

                    // Comparar columna por columna contra las tablas de auditoria
                    rsAux = conn.createStatement().executeQuery(Sql.getAuditColumnsByTableName(tableName, columnName));

                    // Verificamos que la cantidad de columnas obtenidas de la tabla de auditoria sean 2
                    // Si es 0, quiere decir que no existen las columnas en las tablas de auditoria
                    if (rsAux.next()) {

                        Long cantidad = rsAux.getLong("cantidad");

                        if (!cantidad.equals(new Long(2))) {

                            // Generar sentencia ALTER TABLE
                            query += "ALTER TABLE auditoria." + tableName + " ADD COLUMN " + columnName + "_new " + dataType + ";\n ";
                            query += "ALTER TABLE auditoria." + tableName + " ADD COLUMN " + columnName + "_old " + dataType + ";\n ";
                        }
                    }
                }
            }

        } catch (SQLException sqle) {
            throw sqle;
        }

        return query;
    }
}

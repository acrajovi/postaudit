package beans;

public final class Sql {

    /**
     * Consulta utilizada por lo metodos Auditoria.addUserField() y Auditoria.addUserFieldd()
     * @return
     */
    public static String getAllTablesWithoutUserField() {
        String sql = "select	distinct isc.table_name " +
                "from 	information_schema.columns isc " +
                "join	pg_tables pgt on (isc.table_name = pgt.tablename) " +
                "where	isc.table_schema = 'public' " +
                "and	isc.table_name not in ( " +
                "	select	distinct table_name " +
                "	from	information_schema.columns isc " +
                "	where	isc.column_name = 'usuariosys' " +
                "	and	isc.table_schema = 'public' " +
                ") " +
                "order by isc.table_name";

        return sql;
    }

    /**
     * Consulta utilizada por el metodo Auditoria.createFunction()
     * @return
     */
    public static String getAuditTables() {
        String sql = "select	isc.table_name, isc.column_name, " +
                "	case 	when ( isc.data_type = 'numeric' ) then " +
                "		case when isc.numeric_precision is null then isc.data_type else isc.data_type || '(' || isc.numeric_precision || ', ' || isc.numeric_scale || ')' end " +
                "	when ( isc.data_type = 'character varying' or isc.data_type = 'character' ) then " +
                "		case when isc.character_maximum_length is null then isc.data_type else isc.data_type || '(' || isc.character_maximum_length || ')' end " +
                "       when ( isc.data_type = 'ARRAY' and isc.table_name = 'solicitudes' and isc.column_name = 'autorizado_subtipo' ) then 'character varying[]' " +
                "	else isc.data_type end as data_type " +
                "from 	information_schema.columns isc " +
                "join	pg_tables pgt on (isc.table_name = pgt.tablename) " +
                "where	pgt.schemaname = 'public' " +
                "and	isc.table_schema not like 'auditoria%' " +
                "and	isc.table_name in ( " +
                "	select	tablename " +
                "	from	pg_tables " +
                "	where	schemaname = 'auditoria' " +
                ") and  isc.data_type != 'ARRAY' " +
                "order by isc.table_name, isc.ordinal_position";

        return sql;
    }

    /**
     * Consulta utilizada por el metodo Auditoria.createAuditTables()
     * @return
     */
    public static String getTablesDefinition() {
        String sql = "select	isc.table_name, " +
                "	isc.column_name, " +
                "	case 	when ( isc.data_type = 'numeric' ) then " +
                "		case when isc.numeric_precision is null then isc.data_type else isc.data_type || '(' || isc.numeric_precision || ', ' || isc.numeric_scale || ')' end " +
                "	when ( isc.data_type = 'character varying' or isc.data_type = 'character' ) then " +
                "		case when isc.character_maximum_length is null then isc.data_type else isc.data_type || '(' || isc.character_maximum_length || ')' end " +
                "	else isc.data_type end as data_type " +
                "from 	information_schema.columns isc " +
                "join	pg_tables pgt on (isc.table_name = pgt.tablename) " +
                "where	pgt.schemaname = 'public' " +
//                "and	isc.table_schema not like 'auditoria%' " +
//                "and	isc.table_name not in ( " +
//                "	select	tablename " +
//                "	from	pg_tables " +
//                "	where	schemaname = 'auditoria' " +
//                "   union  " +
//                "   select  table_name " +
//                "   from    matviews.tablas_no_auditar " +
//                ") " +
                "and    isc.data_type != 'ARRAY' " +
                "order by isc.table_name, isc.ordinal_position";

        return sql;
    }

    /**
     * Consulta utilizada por el método Auditoria.createAlterTables()
     * @param tableName
     * @param columnName
     * @return
     */
    public static String getAuditColumnsByTableName(String tableName, String columnName) {
        String sql = "select  count(*) as cantidad " +
                "from    information_schema.columns " +
                "where   table_schema = 'auditoria' " +
                "and     table_name = '" + tableName + "' " +
                "and     (column_name = '" + columnName + "_new' or column_name = '" + columnName + "_old');";

        return sql;
    }
}

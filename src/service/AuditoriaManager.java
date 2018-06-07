/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import sourse.utilities;
import beans.*;
/**
 *
 * @author sfernandez
 */

public class AuditoriaManager extends BaseManager {
  utilities Utilities = new utilities();
    public List<Map<String, Object>> getAuditTables() throws SQLException {
        return this.getDBData(Sql.getAuditTables());
    }

//    public List<Map<String, Object>> getAuditTableColumns(String tableName) throws SQLException {
//        List params = new ArrayList();
//        params.add(tableName);
//        String sql = Sql.getAuditTableColumns();
//        return this.getDBData(sql, params);
//    }
//
//    public List<Map<String, Object>> getSystemUsers() throws SQLException {
//        String sql = QueryBean.getSysUsers();
//        return this.getDBData(sql);
//    }

    public List<Map<String, Object>> getDataByParams(String table, String user, String action, Date date, String ip) throws SQLException {
        String sql = "select * from auditoria." + table + " where (true) ";
        
        if ( !Utilities.isEmpty(user) ) sql += " and audit_usuariosys = '" + user + "' ";
        if ( !Utilities.isEmpty(action) ) sql += " and audit_accion = '" + action + "' ";
        if ( !Utilities.isEmpty(date) ) sql += " and date(audit_fecha) = '" + date.toString() + "' ";
        if (  !Utilities.isEmpty(ip) ) sql += " and audit_usuarioip = '" + ip + "' ";

        sql += " order by audit_fecha";
        return this.getDBData(sql);
    }

    public String saveAndGetHashExtractos(Connection conn, String identificador, String tipo, String usuario) throws SQLException {
        String hash = "";
        String sql = "insert into impr_extractos (identificador, fecha_hora, tipo, usuariosys) values (?, current_timestamp, ?, ?) ";
        sql += "RETURNING upper( to_char(impr_extractos.fecha_hora, 'YYYYMMDDHH24MISSUS') || '-' || md5(impr_extractos.identificador || impr_extractos.fecha_hora || impr_extractos.tipo || impr_extractos.usuariosys)) as md5";
        
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, identificador);
        ps.setString(2, tipo);
        ps.setString(3, usuario);

        ResultSet rs = ps.executeQuery();

        if ( rs.next() ) {
            hash = rs.getString("md5");
        }

        return hash;
    }
}

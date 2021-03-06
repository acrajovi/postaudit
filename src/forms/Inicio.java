/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sourse.conectionclass;
import sourse.utilities;

/**
 *
 * @author TOJOZ
 */
public class Inicio extends javax.swing.JFrame {

    /**
     * Creates new form Inicio2
     */
    public Inicio() {
        initComponents();
        campo_host.requestFocus();
    }
    utilities Utilities = new utilities();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        campo_host = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        campo_dbName = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel5 = new javax.swing.JLabel();
        campo_puerto = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnConectar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        campo_Usuario = new javax.swing.JTextField();
        campo_pass = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("PostAudit");
        setResizable(false);

        jLabel2.setText("Host:");

        campo_host.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campo_host.setText("localhost");
        campo_host.setToolTipText("");
        campo_host.setName(""); // NOI18N
        campo_host.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campo_hostKeyPressed(evt);
            }
        });

        jLabel3.setText("Nombre de la Base de Datos:");

        campo_dbName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campo_dbName.setText("biost");
        campo_dbName.setToolTipText("");
        campo_dbName.setName(""); // NOI18N
        campo_dbName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campo_dbNameKeyPressed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logoTr.png"))); // NOI18N
        jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextPane1.setBackground(new java.awt.Color(0, 51, 102));
        jTextPane1.setForeground(new java.awt.Color(0, 51, 102));
        jTextPane1.setText("Conéctese a su Base de Datos para crear y/o actualizar las tablas de auditoría de su Base de Datos Postgres.\n\nRequisitos:\n# JRE 8.\n# BASE DE DATOS POSTGRESQL\n# acceder como SUPER USUARIO\n\n# La tabla \"usuarios\" en la base de datos debe tener si o si los campos:\n\t\"ipacceso\" y \"usuario\"\n\n\n\n\n\n\n\n\nSistema desarrollado por \n\tBIO Soluciones Tecnológicas\n\tjacosta@bio.com.py\n\t+595986355113\n\twww.bio.com.py");
        jTextPane1.setAutoscrolls(false);
        jTextPane1.setCaretColor(new java.awt.Color(0, 51, 102));
        jTextPane1.setEnabled(false);
        jScrollPane1.setViewportView(jTextPane1);

        jLabel5.setText("Puerto:");

        campo_puerto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campo_puerto.setText("5432");
        campo_puerto.setToolTipText("");
        campo_puerto.setName(""); // NOI18N
        campo_puerto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                campo_puertoFocusLost(evt);
            }
        });
        campo_puerto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campo_puertoKeyPressed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnConectar.setText("Conectar");
        btnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnConectar)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnConectar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setToolTipText("");
        jPanel2.setName(""); // NOI18N

        jLabel1.setText("Usuario:");

        jLabel4.setText("Contraseña:");

        campo_Usuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campo_Usuario.setText("postgres");
        campo_Usuario.setToolTipText("");
        campo_Usuario.setName(""); // NOI18N
        campo_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campo_UsuarioKeyPressed(evt);
            }
        });

        campo_pass.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campo_pass.setText("123456");
        campo_pass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campo_passKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(campo_Usuario)
                    .addComponent(campo_pass, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(campo_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(campo_pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Datos de Autentucación:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Datos de Acceso:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(campo_dbName, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campo_puerto, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campo_host, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(127, 127, 127)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(109, 109, 109)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campo_host, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(campo_puerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campo_dbName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel2.getAccessibleContext().setAccessibleName("");

        getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void campo_hostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campo_hostKeyPressed
//Si presiono Enter hacemos focus en el siguiente campo
        if (Utilities.ifEnter(evt)) {
            campo_puerto.requestFocus();
        }
    }//GEN-LAST:event_campo_hostKeyPressed

    private void campo_puertoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campo_puertoKeyPressed
//Verifica si se presiono enter
        if (evt.getKeyCode() == 10) {
            campo_dbName.requestFocus();
        }
    }//GEN-LAST:event_campo_puertoKeyPressed

    private void campo_dbNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campo_dbNameKeyPressed
//Si presiono Enter hacemos focus en el siguiente campo        
        if (Utilities.ifEnter(evt)) {
            campo_Usuario.requestFocus();
        }
    }//GEN-LAST:event_campo_dbNameKeyPressed

    private void campo_passKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campo_passKeyPressed
//Si presiono Enter hacemos doClick sobre el botón Conectar
        if (Utilities.ifEnter(evt)) {
            btnConectar.doClick();
        }
    }//GEN-LAST:event_campo_passKeyPressed

    private void campo_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campo_UsuarioKeyPressed
//Si presiono Enter hacemos focus en el siguiente campo
        if (Utilities.ifEnter(evt)) {
            campo_pass.requestFocus();
        }
    }//GEN-LAST:event_campo_UsuarioKeyPressed

    private void campo_puertoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campo_puertoFocusLost
        //si no es enter controla que se tipee solo números
        if (!Utilities.gettDataType(campo_puerto.getText()).equals("NUMERIC")) {
            JOptionPane.showMessageDialog(rootPane, "Solo puede ingresar Números en esta área.", "ERROR", JOptionPane.ERROR_MESSAGE);
            campo_puerto.setText(null);
            campo_puerto.requestFocus();
        }
    }//GEN-LAST:event_campo_puertoFocusLost

    private void btnConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConectarActionPerformed
        //Verificamos que ingrese todos los campos
        if (Utilities.isEmpty(campo_host.getText())) {
            JOptionPane.showMessageDialog(rootPane, "Complete el campo Host.", "ERROR", JOptionPane.ERROR_MESSAGE);
            campo_host.requestFocus();
            return;
        }
        if (Utilities.isEmpty(campo_puerto.getText())) {
            JOptionPane.showMessageDialog(rootPane, "Complete el campo Puerto.", "ERROR", JOptionPane.ERROR_MESSAGE);
            campo_puerto.requestFocus();
            return;
        }
        if (Utilities.isEmpty(campo_dbName.getText())) {
            JOptionPane.showMessageDialog(rootPane, "Complete el campo Nombre de la Base de Datos.", "ERROR", JOptionPane.ERROR_MESSAGE);
            campo_dbName.requestFocus();
            return;
        }
        if (Utilities.isEmpty(campo_Usuario.getText())) {
            JOptionPane.showMessageDialog(rootPane, "Complete el campo Usuario.", "ERROR", JOptionPane.ERROR_MESSAGE);
            campo_Usuario.requestFocus();
            return;
        }
        if (Utilities.isEmpty(campo_pass.getText())) {
            JOptionPane.showMessageDialog(rootPane, "Complete el campo Contraseña.", "ERROR", JOptionPane.ERROR_MESSAGE);
            campo_pass.requestFocus();
            return;
        }

        //cargamos los valores necesarios para la conexión en la clase conectionclass
        conectionclass ConectionClass = new conectionclass();
        ConectionClass.setHost(campo_host.getText());
        ConectionClass.setPuerto(campo_puerto.getText());
        ConectionClass.setDbName(campo_dbName.getText());
        ConectionClass.setUsername(campo_Usuario.getText());
        ConectionClass.setUserpass(campo_pass.getText());

        try {
            //prueba de conexión
            Connection conn = ConectionClass.dbconection();
            Statement st = conn.createStatement();
            JOptionPane.showMessageDialog(null, "Conexión establecida correctamente..", "CONEXIÓN ESTABLECIDA", JOptionPane.INFORMATION_MESSAGE);
            new home().setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Conexion fail\nError:" + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnConectarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            Desktop.getDesktop().browse(new URI("http://www.bio.com.py"));
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConectar;
    private javax.swing.JTextField campo_Usuario;
    private javax.swing.JTextField campo_dbName;
    private javax.swing.JTextField campo_host;
    private javax.swing.JPasswordField campo_pass;
    private javax.swing.JTextField campo_puerto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}

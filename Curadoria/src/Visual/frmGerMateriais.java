package Visual;
import java.sql.*;
import bd.*;
import DAO.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class frmGerMateriais extends javax.swing.JInternalFrame {
    
    Connection conecta;
    PreparedStatement pst;
    ResultSet rs;
    
    int sincIds = -1;

    public frmGerMateriais() throws ClassNotFoundException {
        initComponents();
        this.setLocation(200, 100);
        conecta = ConectaBD.conectar();
        listarMateriais();
    }
    
    
    public void listarMateriais(){
        String sql = "select material.idArea, area.nome, idMaterial, tipo, material.nome, link "
                   + "from material join area "
                   + "on material.idArea = area.idArea "
                   + "order by material.idArea Asc";
        try{
            pst = conecta.prepareStatement(sql);
            rs = pst.executeQuery();
            tblMateriais.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    public void cadastrarMaterial() throws ClassNotFoundException{
        String sql = "Insert into material(tipo, nome, link, idArea) values(?,?,?,?)";
        try{
            pst = conecta.prepareStatement(sql);
            pst.setString(1, txtTipo.getText());
            pst.setString(2, txtNome.getText());
            pst.setString(3, txtLink.getText());
            pst.setInt(4, Integer.parseInt(txtIdArea.getText()));
            
            pst.execute();
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!", "Cadastrado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
            listarMateriais();
            
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    
    public void mostraItens() throws SQLException{
        int seleciona = tblMateriais.getSelectedRow();
        txtIdArea.setText(tblMateriais.getModel().getValueAt(seleciona, 0).toString());
        txtIdMaterial.setText(tblMateriais.getModel().getValueAt(seleciona, 2).toString());
        txtTipo.setText(tblMateriais.getModel().getValueAt(seleciona, 3).toString());
        txtNome.setText(tblMateriais.getModel().getValueAt(seleciona, 4).toString());
        txtLink.setText(tblMateriais.getModel().getValueAt(seleciona, 5).toString());
        txtNomeArea.setText(tblMateriais.getModel().getValueAt(seleciona, 1).toString());
    }
    
    public void limpaCampos(){
        txtIdMaterial.setText("");
        txtTipo.setText("");
        txtNome.setText("");
        txtLink.setText("");
    }
    
    public void editarMaterial(){
        String sql = "Update material set tipo = ?, nome = ?, link = ? where idMaterial = ?";
        
        try{
        pst = conecta.prepareStatement(sql);
        pst.setString(1, txtTipo.getText());
        pst.setString(2, txtNome.getText());
        pst.setString(3, txtLink.getText());
        pst.setInt(4, Integer.parseInt(txtIdMaterial.getText()));
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Area editada com sucesso!");
        listarMateriais();
            
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    public void deletarMaterial(){
        String sql = "Delete from material where idMaterial = ?";
                
        try{
        pst = conecta.prepareStatement(sql);
        pst.setInt(1, Integer.parseInt(txtIdMaterial.getText()));
        pst.execute();
        JOptionPane.showMessageDialog(null, "Area excluída com sucesso!");
        listarMateriais();
            
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMateriais = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtIdMaterial = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtIdArea = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtTipo = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtNome = new javax.swing.JTextPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtLink = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtNomeArea = new javax.swing.JTextPane();

        setClosable(true);
        setIconifiable(true);
        setTitle("Gerenciador de Materiais");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/accept.png"))); // NOI18N
        jButton1.setText("Cadastrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/user_edit.png"))); // NOI18N
        jButton2.setText("Editar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/user_delete.png"))); // NOI18N
        jButton3.setText("Deletar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/cancel.png"))); // NOI18N
        jButton4.setText("Limpar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        tblMateriais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Título 5", "Título 6"
            }
        ));
        tblMateriais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMateriaisMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMateriais);

        jLabel2.setText("IdArea:");

        jLabel3.setText("IdMaterial:");

        jLabel4.setText("Tipo:");

        jLabel5.setText("Nome:");

        jLabel6.setText("Link:");

        txtIdMaterial.setEnabled(false);
        jScrollPane2.setViewportView(txtIdMaterial);

        txtIdArea.setEnabled(false);
        jScrollPane3.setViewportView(txtIdArea);

        jScrollPane4.setViewportView(txtTipo);

        jScrollPane5.setViewportView(txtNome);

        jScrollPane6.setViewportView(txtLink);

        jLabel1.setText("Área:");

        txtNomeArea.setEnabled(false);
        jScrollPane7.setViewportView(txtNomeArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel2))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(74, 74, 74)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(73, 73, 73)
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jScrollPane7))
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane6)))
                            .addComponent(jScrollPane1))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jButton1)
                        .addGap(100, 100, 100)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(50, 50, 50))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2, jButton3, jButton4});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            cadastrarMaterial();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmGerMateriais.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        editarMaterial();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        deletarMaterial();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        limpaCampos();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tblMateriaisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMateriaisMouseClicked
        try {
            mostraItens();
        } catch (SQLException ex) {
            Logger.getLogger(frmGerMateriais.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tblMateriaisMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTable tblMateriais;
    private javax.swing.JTextPane txtIdArea;
    private javax.swing.JTextPane txtIdMaterial;
    private javax.swing.JTextPane txtLink;
    private javax.swing.JTextPane txtNome;
    private javax.swing.JTextPane txtNomeArea;
    private javax.swing.JTextPane txtTipo;
    // End of variables declaration//GEN-END:variables

    private void pesquisaMateriais() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

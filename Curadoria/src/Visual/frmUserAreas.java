package Visual;
import java.sql.*;
import bd.*;
import DAO.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class frmUserAreas extends javax.swing.JInternalFrame {
    
    public int idLoginUsuario = -1;
    boolean click;

    Connection conecta;
    PreparedStatement pst;
    ResultSet rs;
    
    public frmUserAreas() throws ClassNotFoundException {
        initComponents();
        this.setLocation(200, 100);
        conecta = ConectaBD.conectar();
        listarAreasSeguidas();
        listarAreasNaoSeguidas();
        
    }
    
    public frmUserAreas(int idLoginUsuario) throws ClassNotFoundException {
        this.idLoginUsuario = idLoginUsuario;
        initComponents();
        this.setLocation(200, 100);
        conecta = ConectaBD.conectar();
        listarAreasSeguidas();
        listarAreasNaoSeguidas();
        
    }
    
    public void listarAreasSeguidas(){
        String sql = "Select area.idArea, area.nome, area.seguidores, area.numMateriais "
                   + "from area JOIN segue "
                   + "on area.idArea = segue.idArea "
                   + "where segue.idUsuario = ? "
                   + "order by area.idArea";
        try{
            pst = conecta.prepareStatement(sql);
            pst.setInt(1, idLoginUsuario);
            rs = pst.executeQuery();
            tblSim.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    public void listarAreasNaoSeguidas(){
        String sql = "Select area.idArea, area.nome, area.seguidores, area.numMateriais "
                     + "from area "
                     + "except "
                     + "Select area.idArea, area.nome, area.seguidores, area.numMateriais "
                     + "from area JOIN segue "
                     + "on area.idArea = segue.idArea "
                     + "where segue.idUsuario = ? ";
        try{
            pst = conecta.prepareStatement(sql);
            pst.setInt(1, idLoginUsuario);
            rs = pst.executeQuery();
            tblNao.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    public void mostraItens(){
        if(click){
            int seleciona = tblSim.getSelectedRow();
            txtId.setText(tblSim.getModel().getValueAt(seleciona, 0).toString());
            txtNome.setText(tblSim.getModel().getValueAt(seleciona, 1).toString());
        }else{
            int seleciona = tblNao.getSelectedRow();
            txtId.setText(tblNao.getModel().getValueAt(seleciona, 0).toString());
            txtNome.setText(tblNao.getModel().getValueAt(seleciona, 1).toString());
        }
        
    }
    
    public void seguirArea(){
        String sql = "Insert into segue(idUsuario, idArea) values (?,?)";
                
        try{
        pst = conecta.prepareStatement(sql);
        pst.setInt(1, idLoginUsuario);
        pst.setInt(2, Integer.parseInt(txtId.getText()));
        pst.execute();
        JOptionPane.showMessageDialog(null, "Você acaba de seguir uma nova área!");
        listarAreasSeguidas();
        listarAreasNaoSeguidas();
            
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    public void deixarArea(){
        String sql = "Delete from segue where idUsuario = ? and idArea = ?";
                
        try{
        pst = conecta.prepareStatement(sql);
         pst.setInt(1, idLoginUsuario);
        pst.setInt(2, Integer.parseInt(txtId.getText()));
        pst.execute();
        JOptionPane.showMessageDialog(null, "Você deixou de seguir uma área.");
        listarAreasSeguidas();
        listarAreasNaoSeguidas();
            
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblNao = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSim = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtId = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtNome = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);

        tblNao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblNao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNaoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNao);

        tblSim.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblSim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSimMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSim);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Novas Áreas");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Áreas Seguidas");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("ID:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Nome:");

        txtId.setEnabled(false);
        jScrollPane3.setViewportView(txtId);

        jScrollPane4.setViewportView(txtNome);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/accept.png"))); // NOI18N
        jButton1.setText("Seguir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/cancel.png"))); // NOI18N
        jButton2.setText("Deixar de Seguir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(jButton1)
                                .addGap(80, 80, 80)
                                .addComponent(jButton2)))
                        .addGap(0, 82, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(33, 33, 33)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        seguirArea();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        deixarArea();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tblNaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNaoMouseClicked
        click = false;
        mostraItens();
    }//GEN-LAST:event_tblNaoMouseClicked

    private void tblSimMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSimMouseClicked
        click = true;
        mostraItens();
    }//GEN-LAST:event_tblSimMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblNao;
    private javax.swing.JTable tblSim;
    private javax.swing.JTextPane txtId;
    private javax.swing.JTextPane txtNome;
    // End of variables declaration//GEN-END:variables
}

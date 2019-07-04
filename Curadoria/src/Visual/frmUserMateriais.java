package Visual;
import java.sql.*;
import bd.*;
import DAO.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class frmUserMateriais extends javax.swing.JInternalFrame {

    public int idLoginUsuario = -1;
    
    Connection conecta;
    PreparedStatement pst;
    ResultSet rs;
    
    public frmUserMateriais() throws ClassNotFoundException {
        initComponents();
        this.setLocation(200, 100);
        conecta = ConectaBD.conectar();
        listarMateriais();
    }
    
    public frmUserMateriais(int idLoginUsuario) throws ClassNotFoundException {
        this.idLoginUsuario = idLoginUsuario;
        initComponents();
        this.setLocation(200, 100);
        conecta = ConectaBD.conectar();
        listarMateriais();
    }
    
    public void listarMateriais(){
        String sql = "Select material.idArea, area.nome, idMaterial, tipo, material.nome, link "
                   + "from area JOIN segue "
                   + "on area.idArea = segue.idArea "
                   + "join material on area.idArea = material.idArea "
                   + "where segue.idUsuario = ? "
                   + "order by area.idArea Asc";
        try{
            pst = conecta.prepareStatement(sql);
            pst.setInt(1, idLoginUsuario);
            rs = pst.executeQuery();
            tblMateriais.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMateriais = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setTitle("Materiais");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setText("Materiais de Áreas Seguidas");

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
        jScrollPane1.setViewportView(tblMateriais);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMateriais;
    // End of variables declaration//GEN-END:variables
}

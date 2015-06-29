
package sistem;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableColumn;
import sistem.database.DatabaseConnection;
import sistem.validator.IpCharValidator;
import sistem.validator.PortCharValidator;

/**
 *
 * @author moehandi
 */
public final class FrameSetDb extends javax.swing.JFrame {

    Vector vRuleList = new Vector();
    JTable jTableRules = new JTable();
    TableModelRules tableModelRules = new TableModelRules(jTableRules,vRuleList);
    TableColumn column = null;
    MainFrame frame;
    private String query ="";
    private Statement st;
    private DatabaseConnection dbConn;
    private ResultSet rs;
    
    public FrameSetDb() {
        initComponents();
        
        fieldSourceIp.setDocument(new IpCharValidator(18, true));
        fieldSourcePort.setDocument(new PortCharValidator(6, true));
        fieldDestIp.setDocument(new IpCharValidator(18, true));
        fieldDestPort.setDocument(new PortCharValidator(6, true));
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
                       (screenSize.height - frameSize.height) / 3);
        
        jScrollPaneTable.setViewportView(jTableRules);
        jTableRules.setModel(tableModelRules);
        
    for (int i = 0; i < 7; i++) {
        column = jTableRules.getColumnModel().getColumn(i);
        switch (i) {
          case Rule.FIELDID_ID:
            column.setPreferredWidth(10);
            break;
          case Rule.FIELDID_PROTOCOL:
            column.setPreferredWidth(30);
            break;
          case Rule.FIELDID_SRCIP:
            column.setPreferredWidth(100);
            break;
          case Rule.FIELDID_SRCPORT:
            column.setPreferredWidth(50);
            break;
          case Rule.FIELDID_DSTIP:
            column.setPreferredWidth(100);
            break;
          case Rule.FIELDID_DSTPORT:
            column.setPreferredWidth(50);
            break;
          case Rule.FIELDID_ACTION:
            column.setPreferredWidth(30);
            break;          
        }
    }
    
      loadDbOnOpen();
    
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cboProtocol = new javax.swing.JComboBox();
        fieldSourceIp = new javax.swing.JTextField();
        fieldSourcePort = new javax.swing.JTextField();
        fieldDestIp = new javax.swing.JTextField();
        fieldDestPort = new javax.swing.JTextField();
        cboAction = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        panelTable = new javax.swing.JPanel();
        jScrollPaneTable = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        buttonInsert = new javax.swing.JButton();
        buttonReset = new javax.swing.JButton();
        buttonExit = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Rule Insertion");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        cboProtocol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "tcp", "udp" }));

        cboAction.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "accept", "deny" }));

        jLabel1.setText("Protocol");

        jLabel2.setText("Source IP");

        jLabel3.setText("Source Port");

        jLabel4.setText("Destination IP");

        jLabel5.setText("Destination Port");

        jLabel6.setText("Action");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboProtocol, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fieldSourceIp, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fieldSourcePort, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fieldDestIp, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel4)))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fieldDestPort)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboAction, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboProtocol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldSourceIp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldSourcePort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldDestIp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldDestPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        panelTable.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout panelTableLayout = new javax.swing.GroupLayout(panelTable);
        panelTable.setLayout(panelTableLayout);
        panelTableLayout.setHorizontalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE))
        );
        panelTableLayout.setVerticalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 315, Short.MAX_VALUE)
            .addGroup(panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        buttonInsert.setText("Insert");
        buttonInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonInsertActionPerformed(evt);
            }
        });

        buttonReset.setText("Reset");
        buttonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonResetActionPerformed(evt);
            }
        });

        buttonExit.setText("Exit");
        buttonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(buttonInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonInsert)
                    .addComponent(buttonReset)
                    .addComponent(buttonExit))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInsertActionPerformed
       if ((fieldSourceIp.getText().trim().length() == 0)||(fieldSourcePort.getText().trim().length() == 0)
               ||(fieldDestIp.getText().trim().length() == 0)||(fieldDestPort.getText().trim().length() == 0)) {
            JOptionPane.showMessageDialog(this, "Field not complete"+"\nPlease fill Rule field completely");
        }
        else{
            try {
//            query = "set @count:=0";
//            PreparedStatement presttmt2 = dbConn.koneksi.prepareStatement(query);
//            presttmt2.executeUpdate(query);

//            query = "SELECT count(orde) from rule AS nilai";
//            PreparedStatement presttmt3 = dbConn.koneksi.prepareStatement(query);
//            presttmt3.executeQuery(query);
            
            query = "INSERT INTO rule (protocol, source_ip, source_port, dest_ip, dest_port, action) values ('"+cboProtocol.getSelectedItem().toString()+"', '"+fieldSourceIp.getText()
                    +"','"+fieldSourcePort.getText()+"','"+fieldDestIp.getText()
                    +"','"+fieldDestPort.getText()+"','"+cboAction.getSelectedItem().toString()+ "')";
            PreparedStatement presttmt = dbConn.connection.prepareStatement(query);
            presttmt.executeUpdate(query);
                      
            
            vRuleList.clear();
            loadRulesDatabase();
            jTableRules.clearSelection();
            jTableRules.revalidate();
            jTableRules.repaint();
            jTableRules.removeFocusListener(null);//hilangkan selected file
            
//            JOptionPane.showMessageDialog(null, "Rule data Updated");

        }catch (SQLException se) {
            JOptionPane.showMessageDialog(null,
                        "Error inserting new Rule :" + se,
                        "Error", JOptionPane.ERROR_MESSAGE);
            buttonResetActionPerformed(evt);
        }
 }
    }//GEN-LAST:event_buttonInsertActionPerformed

    private void buttonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonResetActionPerformed
       cboProtocol.setSelectedIndex(-1);
       fieldSourceIp.setText("");
       fieldSourcePort.setText("");
       fieldDestIp.setText("");
       fieldDestPort.setText("");
       cboAction.setSelectedIndex(-1);
    }//GEN-LAST:event_buttonResetActionPerformed

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed

        dispose();
    }//GEN-LAST:event_buttonExitActionPerformed

     public void loadRulesDatabase(){
      dbConn = new DatabaseConnection();
      dbConn.createConnection();
      //operasi tampilkan dbConn
      tableModelRules = new TableModelRules();
      try{
          query = "SELECT orde, protocol, source_ip, source_port, dest_ip, dest_port, action FROM rule ORDER by orde ASC;";
          st = (Statement)dbConn.connection.createStatement();
          rs = st.executeQuery(query);
          String linedb;
          while(rs.next()){
             String[] ruleData = {
                                     rs.getString(1).toString(),
                                     rs.getString(2).toString(),
                                     rs.getString(3).toString(),
                                     rs.getString(4).toString(),
                                     rs.getString(5).toString(),
                                     rs.getString(6).toString(),
                                     rs.getString(7).toString()//,
                                    // rs.getString(8).toString()
             };
             System.out.println(""+ruleData[0]+", "+ruleData[1]+", "+ruleData[2]+", "+ruleData[3]+", "+ruleData[4]+", "+ruleData[5]+", "+ruleData[6]+ "");
             //proses stream array ke string
             linedb = (String)""+ruleData[1]+", "+ruleData[2]+", "+ruleData[3]+", "+ruleData[4]+", "+ruleData[5]+", "+ruleData[6]+ "";//
             //System.out.println(linedb);
             Rule ruleDb = new Rule(linedb);
             vRuleList.add(ruleDb);
          }
      }catch (SQLException se){
          se.printStackTrace();
      }
      
      
  }
     
     public void dumpSQL(String dbName, String userName, String password, String location){
        try{
        //DriverConnection conn = new DriverConnection();
        
        int processComplete;// this variable for verify the process

       // String[] executeCmd = new String[]{"mysql", databaseName, "-u" + userName, "-p" + password, "-e", "source D:/backup.sql" };
         String[] executeCmd = new String[]{"mysql", dbName, "-u " + userName, "-p" + password, "-e", "source $PATH" + location };
        //save command dalam array
          Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);// execute command

            processComplete = runtimeProcess.waitFor();// mendapatkan hasil variable

            if(processComplete==1){// jika nilai return = 1 maka proses berhasil

                JOptionPane.showMessageDialog(null, "Dump failed");
            }
            else if(processComplete==0){{// jika nilai return = 0 maka proses berhasil

                JOptionPane.showMessageDialog(null, "Dump Finished succesful");
                }
            }
      }catch(Exception ex){

        JOptionPane.showMessageDialog(null, ex);

      }
    }
    
    public void BackupSQL(){
        try{

            int processComplete; // untuk verifikasi apakah process lain complete
            // memanggil dan mengeksekusi mysqldump di terminal
            Process runtimeProcess = Runtime.getRuntime().exec("mysql -u root -p terminal iptables_db -r >> $HOME/backup.sql");

            processComplete = runtimeProcess.waitFor();//store kondisi dalam variable

            if(processComplete==1){//jika bernilai satu maka gagal

                JOptionPane.showMessageDialog(null, "Backup Failed");//tampilkan pesan
            }
            else if(processComplete==0){//if values equal 0 process failed

                JOptionPane.showMessageDialog(null,"\n Backup telah Berhasil dibuat..\n Cek direktori D: untuk melihat file bernama backup.sql");
                //display message
            }

        }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);//exeception handling
        }
    }
    
    public void dropRuleList(){
        try {
            query = "delete from rule";
            PreparedStatement presttmt = dbConn.connection.prepareStatement(query);
            presttmt.executeUpdate(query);
                       
            JOptionPane.showMessageDialog(null, "Rule list in Database Table Deleted.");

        }catch (SQLException se) {
            JOptionPane.showMessageDialog(null,
                        "Terjadi kesalahan hapus data! error :" + se,
                        "Kesalahan", JOptionPane.ERROR_MESSAGE);
            //dispose();
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton buttonExit;
    private javax.swing.JButton buttonInsert;
    private javax.swing.JButton buttonReset;
    private javax.swing.JComboBox cboAction;
    private javax.swing.JComboBox cboProtocol;
    private javax.swing.JTextField fieldDestIp;
    private javax.swing.JTextField fieldDestPort;
    private javax.swing.JTextField fieldSourceIp;
    private javax.swing.JTextField fieldSourcePort;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPaneTable;
    private javax.swing.JPanel panelTable;
    // End of variables declaration//GEN-END:variables

    private void loadDbOnOpen() {
        Rule.counter = 0;
        vRuleList.clear();
        loadRulesDatabase();
        jTableRules.revalidate();
        jTableRules.repaint();
    }
    
    public void display() throws InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
       
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch(ClassNotFoundException /*| InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException*/ e) {
        e.printStackTrace();
        }
      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrameSetDb dialog = new FrameSetDb();
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
}

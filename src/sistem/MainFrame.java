package sistem;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import sistem.database.DatabaseConnection;
import sistem.database.DatabaseOperation;

public class MainFrame extends javax.swing.JFrame {
    
    private String query ="";
    private Statement st;
    private DatabaseConnection dbConn;
    private ResultSet rs;
    	
    Vector vRuleList = new Vector();
    TranslationTree translationTree = new TranslationTree(vRuleList, this);
    RuleTree ruleTree = new RuleTree("1:2:3:4:5:6", vRuleList, this);//("order", vRuleList = vector,
	
    boolean logDetail = false;
    static final boolean LOG_BASIC=false, LOG_DETAILED=true ;//digunakan pada checkbox detail
    File file = null;

    JMenu menuFile = new JMenu("File");
    JMenu menuSetting = new JMenu("Setting");
    JMenu menuHelp = new JMenu("Help");
    JMenuBar menuBar = new JMenuBar();
    JMenuItem itemLoadDb = new JMenuItem("Load Rule from DB");
    JMenuItem itemLoadFile = new JMenuItem("Load Rule from File");
    JMenuItem itemResetRule = new JMenuItem("Reset Rule");
    JMenuItem itemExit = new JMenuItem("Exit ");
    JMenuItem itemInsertRuleMan = new JMenuItem("New Rule List (Manual)");
    JMenuItem itemAddRule = new JMenuItem("Add Rule List");
    JMenuItem itemRestoreDB = new JMenuItem("Restore Rule Database (Dump SQL)");
    JMenuItem itemBackupDB = new JMenuItem("Backup Rule Database");
    JMenuItem itemDropDB = new JMenuItem("Delete Rule Database ");
    JMenuItem itemOptions = new JMenuItem("Options");
//    JMenuItem itemRestoreDB = new JMenuItem("Restore Rule Database");

    JMenuItem itemManual = new JMenuItem("Manual");
    JMenuItem itemAbout = new JMenuItem("About");
    JToolBar toolBar = new JToolBar();
    JToolBar toolBarRest = new JToolBar();
    
    JButton bBarLoad = new JButton();
    JButton bBarDiscover = new JButton();
    JButton bBarReset = new JButton();
    JButton bBarUpdateRule = new JButton();
    JButton bBarDeleteRule = new JButton();
    JButton bBarExecute = new JButton();
    JButton bBarOptions = new JButton();
    JSeparator separator = new JSeparator();
    
    JButton buttonSave = new JButton("Save");
    JButton buttonDelete = new JButton("Delete");
    //JButton buttonReset = new JButton("Reset");
    JLabel labelOrder = new JLabel("Order");
    JTextField fieldId = new JTextField();
    JLabel labelProtocol = new JLabel("Protocol");
    JComboBox cboProtocol = new JComboBox();
    JLabel labelSourceIp = new JLabel("Source IP");
    JTextField fieldSourceIp = new JTextField();
    JLabel labelSourcePort = new JLabel("Source Port");
    JTextField fieldSourcePort = new JTextField();
    JLabel labelDestIp = new JLabel("Destination IP");
    JTextField fieldDestIp = new JTextField();
    JLabel labelDestPort = new JLabel("Destination Port");
    JTextField fieldDestPort = new JTextField();
    JLabel labelAction = new JLabel("Action");
    JLabel labelNull = new JLabel("");
    JComboBox cboAction = new JComboBox();
    JLabel labelDeleteRange = new JLabel("Delete Range");
    JTextField fieldStart = new JTextField();
    JTextField fieldEnd = new JTextField();
    JButton bDeleteMass = new JButton("OK");

    JButton buttonLoadDb = new JButton("Load DB");
    JButton buttonLoadFile = new JButton("Load File");
    JButton buttonDiscover = new JButton("Discover");
    JButton buttonRefresh = new JButton("Refresh");
    JButton buttonReset = new JButton("Reset");
    JButton buttonShowTrees = new JButton("Tree");
    JButton buttonTranslate = new JButton("Translate");
    JButton buttonExit = new JButton("Exit");
    JButton buttonAbout = new JButton("About");
    JTextField fieldInterfaceS = new JTextField();
    JTextField fieldInterfaceD = new JTextField();
    JCheckBox jCheckBoxLog = new JCheckBox();
    JDialog jDialogAbout = new JDialog(this);
    JDialog jDialogTranslate = new JDialog(this);

  /*komponen main*/
    JPanel panelMain;
    JPanel panelContent;//panel utama komponen
    GridBagLayout gridBagLayout = new GridBagLayout();//layout panel utama
    
    JPanel panelField = new JPanel();
    GridLayout gridLayoutField = new GridLayout();
    TitledBorder titledBorderField;
    
    JPanel panelTable = new JPanel();
    TitledBorder titledBorderTable;
    JScrollPane jScrollPaneTable = new JScrollPane();
    BorderLayout borderLayoutTable = new BorderLayout();
    JTable jTableRules = new JTable();
    TableModelRules tableModelRules = new TableModelRules(jTableRules, vRuleList);//ruleList = vector
    
    JPanel panelTree = new JPanel();
    JScrollPane jScrollPaneTree = new JScrollPane();
    JTree jTreeRules = new JTree(new DefaultMutableTreeNode());
    GridBagLayout gridBagLayoutTree = new GridBagLayout();
    TitledBorder titledBorderTree;

    JPanel panelMessage = new JPanel();
    TitledBorder titledBorderMessages;
    GridBagLayout gridBagLayoutMessage = new GridBagLayout();
    JScrollPane jScrollPaneMessage = new JScrollPane();
    JTextArea jTextAreaMessage = new JTextArea();

    JPanel panelInfo = new JPanel();
    TitledBorder titledBorderInfo;
    GridBagLayout gridBagLayoutInfo = new GridBagLayout();
    JScrollPane jScrollPaneInfo = new JScrollPane();
    JTextArea jTextAreaActivity = new JTextArea();
    
    JScrollPane jScrollPaneTranslate = new JScrollPane();
    JEditorPane jEditorPaneTranslate = new JEditorPane();
    BorderLayout borderLayoutTranslate = new BorderLayout();
   
  @SuppressWarnings("CallToThreadDumpStack")
  public MainFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
    	initComponent();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

private void initComponent() throws Exception  {
    //setIconImage(Toolkit.getDefaultToolkit().createImage(MainFrame2.class.getResource("[Your Icon]")));
    this.setTitle("Iptables Rule Optimization");
    this.setSize(new Dimension(1300,720));
    panelContent = (JPanel) this.getContentPane();
    panelContent.setLayout(gridBagLayout);
    
    menuBar.add(menuFile);
    menuBar.add(menuSetting);
    menuBar.add(menuHelp);
    setJMenuBar(menuBar);
   
    menuFile.add(itemLoadDb);
    itemLoadDb.setToolTipText("Load Rule from existing Rule in DB");
    menuFile.add(itemLoadFile);
    itemLoadFile.setToolTipText("Load Rule from file (txt)");
//    menuFile.add(itemAddRule);
//    itemAddRule.setToolTipText("Add Rule List from sql File");
    menuFile.add(itemResetRule);
    itemResetRule.setToolTipText("Reset All Rule in Table");
    menuFile.add(itemExit);
    menuFile.setMnemonic(KeyEvent.VK_F);
    menuSetting.setMnemonic(KeyEvent.VK_T);
    menuSetting.add(itemInsertRuleMan);
    itemInsertRuleMan.setToolTipText("Insert Rule to DB Manually");
    menuSetting.add(itemRestoreDB);
    itemRestoreDB.setToolTipText("Insert/Restore Full Database");
    menuSetting.add(itemBackupDB);
    itemBackupDB.setToolTipText("Backup Full Database as sql file");
//    menuSetting.add(itemRestoreDB);
    menuSetting.add(itemDropDB);
    itemDropDB.setToolTipText("Delete/Drop Full Database");
    menuSetting.add(itemOptions) ;
    itemOptions.setToolTipText("Setting up configuration of linux and MySQL");
    
    menuHelp.add(itemManual);
    menuHelp.add(itemAbout);
    
    toolBar.setLayout(new GridLayout(1,7));
    bBarLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistem/resource/Load.png")));
    bBarLoad.setToolTipText("Load Rule from Database");
    bBarDiscover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistem/resource/Discover.png")));
    bBarDiscover.setToolTipText("Discover Anomaly");
    
    bBarReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistem/resource/Reset.png")));
    bBarReset.setToolTipText("Reset Rule");
    bBarUpdateRule.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistem/resource/UpdateRule.png")));
    bBarUpdateRule.setToolTipText("Update Selected Rule");
    bBarDeleteRule.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistem/resource/stop.png")));
    bBarDeleteRule.setToolTipText("Delete selected Rule");
    bBarExecute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistem/resource/Execute.png")));
    bBarExecute.setToolTipText("Execute Rule List to Iptables");
    bBarOptions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistem/resource/settings.png"))); 
    bBarOptions.setToolTipText("Show Options");
    
    toolBar.add(bBarLoad);
    toolBar.add(bBarReset);
    toolBar.add(bBarDiscover);
    toolBar.add(bBarUpdateRule);
    toolBar.add(bBarDeleteRule);
    toolBar.add(bBarExecute);  
    toolBar.add(bBarOptions);
            
    titledBorderTable = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 154, 134)),"");
    titledBorderField = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 154, 134)),"");
    titledBorderTree = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 154, 134)),"Rule Tree");
    titledBorderMessages = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 154, 134)),"Messages");
    titledBorderInfo = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 154, 134)),"Information");
    titledBorderInfo = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 154, 134)),"Information");
    
    jScrollPaneTable.setBorder(BorderFactory.createEmptyBorder());
    jScrollPaneTree.setBorder(BorderFactory.createEmptyBorder());
    jScrollPaneMessage.setBorder(BorderFactory.createEmptyBorder());
    jScrollPaneInfo.setBorder(BorderFactory.createEmptyBorder());
    
    jCheckBoxLog.setText("Details");      
    
    jTreeRules.setRootVisible(false);
    jTreeRules.setShowsRootHandles(true);
    jTreeRules.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
      public void mousePressed(MouseEvent e) {
        jTreeRules_mousePressed(e);
      }
    });
    
    panelTree.setBorder(titledBorderTree);
    panelTree.setLayout(gridBagLayoutTree);
    jScrollPaneTree.setViewportView(jTreeRules);
        
    panelInfo.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(134, 154, 134)), "Activity", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(51, 51, 51)));
    panelInfo.setLayout(gridBagLayoutInfo);
    jScrollPaneInfo.setViewportView(jTextAreaActivity);
    jTextAreaActivity.setBorder(null);
    jTextAreaActivity.setEditable(false);
        
    panelMessage.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(134, 154, 134)), "Algorithm Log", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(51, 51, 51)));
    panelMessage.setLayout(gridBagLayoutMessage);
    jScrollPaneMessage.setViewportView(jTextAreaMessage);
    jTextAreaMessage.setBorder(null);
    jTextAreaMessage.setEditable(false);
   
    // PROPERTI ISI PANEL FIELD
    panelField.setLayout(gridLayoutField);
//    panelField.setBorder(titledBorderField);
    gridLayoutField.setColumns(1);
    gridLayoutField.setRows(19);
    gridLayoutField.setVgap(8);//jumlah komponen vertikal grid
    
    panelField.add(labelOrder, null);
    panelField.add(fieldId, null);
    
    fieldId.setEditable(false);
    fieldId.setFont(new java.awt.Font("Ubuntu", 0, 13));
    
    panelField.add(labelProtocol,null);
    panelField.add(cboProtocol,null);
    cboProtocol.addItem("tcp");
    cboProtocol.addItem("udp");
//    cboProtocol.addItem("icmp");
    panelField.add(labelSourceIp,null);
    panelField.add(fieldSourceIp,null);
    panelField.add(labelSourcePort, null);
    panelField.add(fieldSourcePort, null);
    panelField.add(labelDestIp, null);
    panelField.add(fieldDestIp, null);
    panelField.add(labelDestPort, null);
    panelField.add(fieldDestPort, null);
    panelField.add(labelAction, null);
    panelField.add(cboAction, null);
    cboAction.addItem("accept");
    cboAction.addItem("drop");
    panelField.add(labelNull, null); 
    panelField.add(labelDeleteRange);
    panelField.add(fieldStart);
    panelField.add(fieldEnd);
    panelField.add(bDeleteMass);
          
    panelTree.add(jScrollPaneTree,  new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 100, -111)); 
    panelMessage.add(jScrollPaneMessage, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 264, 191));
    panelMessage.add(jCheckBoxLog,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 10, 0));
    panelInfo.add(jScrollPaneInfo, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 168, -111));
    
    //properti letak panel pada panelContent
    //inset(top, left, bottom, right
    //panelContent.add(jPanelField, new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, WIDTH, null, WIDTH, WIDTH))
    
    panelContent.add(toolBar,    new GridBagConstraints(0, 0, 2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 2, 0, 2), 50, 5));
    panelContent.add(toolBarRest,new GridBagConstraints(0, 0, 4, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 10, 10));
    panelContent.add(separator,  new GridBagConstraints(0, 1, 4, 2, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 100, 1));
    panelContent.add(panelField, new GridBagConstraints(0, 2, 1, 3, 1.5, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(10, 8, 8, 0), 10, 1));
    panelContent.add(panelTable, new GridBagConstraints(1, 2, 2, 1, 2.0, 2.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(13, 8, 0, 0), 180, 120));
    //panelContent.add(panelInfo, new GridBagConstraints(1, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 8, 0, 0), 196, 150));
    panelContent.add(panelMessage,new GridBagConstraints(1, 4, 1, 1, 1.0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 8, 8, 0), 100, 0));
    panelContent.add(panelInfo, new GridBagConstraints  (2, 4, 1, 1, 2.0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 8, 8, 0), 100, 0));
    panelContent.add(panelTree, new GridBagConstraints(3, 2, 1, 4, 0.5, 0.5,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(6, 3, 8, 5), 85, 1));
   
    panelTable.setBorder(titledBorderTable);
    panelTable.setLayout(borderLayoutTable);
    panelTable.add(jScrollPaneTable, BorderLayout.CENTER);
    jScrollPaneTable.setViewportView(jTableRules);
    
    jTableRules.setModel(tableModelRules);//tableModel object reference TableModelRules
    TableColumn column = null;
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

    jTableRules.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
        public void mouseClicked(MouseEvent e){
            //jTableRules_mouseClicked(e);
             if(e.getClickCount() == 1){
                displayClickTable();
            }
        }
    });
        
    itemLoadDb.addActionListener(new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            actionLoadDB();
        }
    });
    
    itemLoadFile.addActionListener(new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("Loading Rules from File...");
             actionLoadFile();
             loadRulesFile(file);
            jTableRules.revalidate();
            jTableRules.repaint();

            buttonReset.setEnabled(true);
            buttonDiscover.setEnabled(true);
            buttonLoadFile.setEnabled(false);
            buttonLoadDb.setEnabled(false);
        }
    });
    itemResetRule.addActionListener(new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            actionReset();
        }
    });
    
    itemExit.addActionListener(new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    });
    
    itemInsertRuleMan.addActionListener(new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            FrameRulesInsert ruleInsert = new FrameRulesInsert();
            ruleInsert.show();
        }
    });
    
    itemAddRule.addActionListener(new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            actionDBAddRule();
        }
    });
    
    itemBackupDB.addActionListener(new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            actionDBFullBackup();
        }
    });
    
    itemRestoreDB.addActionListener(new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            actionDBFullRestore();
        }
    });
    
    itemDropDB.addActionListener(new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            actionDBFullDelete();
        }
    });
    
    itemOptions.addActionListener(new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            FrameOptions frameOption = new FrameOptions();
            frameOption.show();
        }
    });
   
    itemManual.addActionListener(new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            FrameHelp helpFrame = new FrameHelp();
            helpFrame.show();
        }
    });
    
    itemAbout.addActionListener(new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            FrameAbout frameAbout = new FrameAbout();
            frameAbout.show();
        }
    });

  // METHOD ACTION BUTTONS -----------------------------------------------------
    bBarLoad.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                long startTime = System.currentTimeMillis();
                actionLoadDB();
                long endTime = System.currentTimeMillis();

                jTextAreaActivity.append("\n(total time: " + (endTime - startTime) + " ms)");
                
                
            }
        });
        
//    buttonLoadFile.addActionListener(new java.awt.event.ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//              buttonLoadFile_actionPerformed(e);
//            }
//    });
    
    bBarDiscover.addActionListener(new java.awt.event.ActionListener(){
        @Override
            public void actionPerformed(ActionEvent e){
               actionDiscovery(); 
            }
    });
    
    bBarReset.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            actionReset();
        }
    });
    
    bBarOptions.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        FrameOptions frameOptions = new FrameOptions();
            frameOptions.show();
        }
    });
    
    bBarUpdateRule.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            actionUpdate();
        }
    });
    bBarDeleteRule.addActionListener(new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            actionDeleteRule();
        }
    });
    
    bBarExecute.addActionListener(new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                actionExecute();
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
    bDeleteMass.addActionListener(new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            actionDeleteMassRule();
        }
    });
    
    buttonRefresh.addActionListener(new java.awt.event.ActionListener() {
            @Override
              public void actionPerformed(ActionEvent e) {
                actionRefresh();
              }
            });

    
    buttonShowTrees.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              buttonShowTrees_actionPerformed(e);
            }
          });

    buttonTranslate.addActionListener(new java.awt.event.ActionListener(){
        @Override
    	public void actionPerformed(ActionEvent e){
    		buttonTranslate_actionPerformed(e);
    	}
    });
    buttonExit.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    
    jCheckBoxLog.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
              public void stateChanged(ChangeEvent e) {
                jCheckBoxLog_stateChanged(e);
              }
            });
    
  }
       
 private void jTableRules_mouseClicked(MouseEvent e){
    if(e.getClickCount() == 1){
        displayClickTable();
    }
 }
 
 private void actionDeleteRule(){
     if (fieldId.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "No Rule Selected");
        }
     else{   
     try {
        try{
           int option = JOptionPane.showConfirmDialog(this, "Are you sure to delete this Rule?",
                        "Alert",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if(option==JOptionPane.YES_OPTION){
                    
               query = "delete from rule where orde ='" + fieldId.getText() + "'";
               PreparedStatement presttmt = dbConn.connection.prepareStatement(query);
               presttmt.executeUpdate(query);

               query = "set @count:=0";
               PreparedStatement presttmt2 = dbConn.connection.prepareStatement(query);
               presttmt2.executeUpdate(query);

               query = "update rule set orde=@count:=@count+1 order by orde";
               PreparedStatement presttmt3 = dbConn.connection.prepareStatement(query);
               presttmt3.executeUpdate(query);
            
               Rule.counter = 0;
                vRuleList.clear();
                jTableRules.clearSelection();//hilangkan selected file
                loadRulesDatabase();
                jTableRules.revalidate();
                jTableRules.repaint();
               //JOptionPane.showMessageDialog(this,"Rule has been deleted","Informtion",JOptionPane.INFORMATION_MESSAGE);
             }else{
                Rule.counter = 0;
                vRuleList.clear();
                jTableRules.clearSelection();//hilangkan selected file
                loadRulesDatabase();
                jTableRules.revalidate();
                jTableRules.repaint();
               // JOptionPane.showMessageDialog(this,"Delete was canceled","Informasi",JOptionPane.INFORMATION_MESSAGE);
              }
            }catch(Error e){

            }
            
            actionDiscovery();
            actionReleaseField();
            
        }catch (SQLException se) {
            JOptionPane.showMessageDialog(null,
                        "Problem Deleting Rule! error :" + se,
                        "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
     }
 }
 
 private void actionDeleteMassRule(){
//     int start = Integer.valueOf(fieldStart.getText());
//     int end = Integer.valueOf(fieldEnd.getText());
     if ((fieldStart.getText().isEmpty() ) && (fieldEnd.getText().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Rule range is empty");
        }
     else if(Integer.valueOf(fieldStart.getText()) >= Integer.valueOf(fieldEnd.getText())){
         JOptionPane.showMessageDialog(this, "field min must smaller than field max");
     }
     else{   
     try {
        try{
           int option = JOptionPane.showConfirmDialog(this, "Are you sure to delete this Rule?",
                        "Alert",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if(option==JOptionPane.YES_OPTION){
                    
               query = "delete from rule where orde >='" + fieldStart.getText() + "' AND orde <= '"+fieldEnd.getText() +"'";
               PreparedStatement presttmt = dbConn.connection.prepareStatement(query);
               presttmt.executeUpdate(query);

               query = "set @count:=0";
               PreparedStatement presttmt2 = dbConn.connection.prepareStatement(query);
               presttmt2.executeUpdate(query);

               query = "update rule set orde=@count:=@count+1 order by orde";
               PreparedStatement presttmt3 = dbConn.connection.prepareStatement(query);
               presttmt3.executeUpdate(query);
            
               Rule.counter = 0;
                vRuleList.clear();
                jTableRules.clearSelection();//hilangkan selected file
                loadRulesDatabase();
                jTableRules.revalidate();
                jTableRules.repaint();
               //JOptionPane.showMessageDialog(this,"Rule has been deleted","Informtion",JOptionPane.INFORMATION_MESSAGE);
             }else{
                Rule.counter = 0;
                vRuleList.clear();
                jTableRules.clearSelection();//hilangkan selected file
                loadRulesDatabase();
                jTableRules.revalidate();
                jTableRules.repaint();
               // JOptionPane.showMessageDialog(this,"Delete was canceled","Informasi",JOptionPane.INFORMATION_MESSAGE);
              }
            }catch(Error e){

            }
            
            actionDiscovery();
            actionReleaseField();
            
        }catch (SQLException se) {
            JOptionPane.showMessageDialog(null,
                        "Problem Deleting Rule! error :" + se,
                        "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
     }
     
 }
 private void actionUpdate(){
     if (fieldId.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "No Rule Selected"+"\nPlease select one from table");
     }
     else{
     try {
            query = "UPDATE rule SET protocol='"+cboProtocol.getSelectedItem().toString()+"', source_ip='"+fieldSourceIp.getText()
                    +"', source_port='"+fieldSourcePort.getText()+"', dest_ip='"+fieldDestIp.getText()
                    +"', dest_port='"+fieldDestPort.getText()+"', action='"+cboAction.getSelectedItem().toString()
                    +"' WHERE orde='" + fieldId.getText() + "'";
            PreparedStatement presttmt = dbConn.connection.prepareStatement(query);
            presttmt.executeUpdate(query);
                      
            Rule.counter = 0;
            vRuleList.clear();
            loadRulesDatabase();
            jTableRules.clearSelection();
            jTableRules.revalidate();
            jTableRules.repaint();
            jTableRules.removeFocusListener(null);//hilangkan selected file
//            actionDiscovery();
            actionReleaseField();
            JOptionPane.showMessageDialog(null, "Rule data Updated");

        }catch (SQLException se) {
            JOptionPane.showMessageDialog(null,
                        "Error updating data Rule :" + se,
                        "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
 }
 }
 private void actionDBFullBackup(){
     String names;
    ConfigurationProperties conf = new ConfigurationProperties();
    conf.loadProperties();
    String dbMysql = conf.getLoadDbMysql();
    String userMysql = conf.getLoadUsrMysql();
    String passMysql = conf.getLoadPassMysql();
    
     JFileChooser fc = new JFileChooser("$HOME");
     fc.setDialogTitle("choose folder to save");
     fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//     fc.setAcceptAllFileFilterUsed(false);
        
        if (fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDir();"+fc.getCurrentDirectory());
            file = fc.getSelectedFile();
//            names = fc.getName().
        }
        else{
              return;
        }

        String fileLocation = file.getPath().toString();
        
     DatabaseOperation dbOperation = new DatabaseOperation();
     dbOperation.backupDB(dbMysql, userMysql, passMysql, fileLocation);//panggil method dbBackup
     System.out.println(fileLocation /*+names*/);
//     dbOperation.BackupSQL();
        
 }
 //restore db dan contents
 private void actionDBFullRestore(){
    ConfigurationProperties conf = new ConfigurationProperties();
    conf.loadProperties();
    
    String userMysql = conf.getLoadUsrMysql();
    String passMysql = conf.getLoadPassMysql();
    
     JFileChooser fc = new JFileChooser("$HOME");
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
              file = fc.getSelectedFile();
        }
        else{
              return;
        }

    String fileLocation = file.getPath().toString();
    DatabaseOperation dbOperation = new DatabaseOperation();
    dbOperation.restoreFullDB(userMysql, passMysql, fileLocation);
    actionLoadDB();//sesudah restore diload ulang
 }
 
 private void actionDBAddRule(){
     ConfigurationProperties conf = new ConfigurationProperties();
    conf.loadProperties();
    
    String userMysql = conf.getLoadUsrMysql();
    String passMysql = conf.getLoadPassMysql();
    
     JFileChooser fc = new JFileChooser("$HOME");
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
              file = fc.getSelectedFile();
        }
        else{
              return;
        }

    String fileLocation = file.getPath().toString();
    DatabaseOperation dbOperation = new DatabaseOperation();
    dbOperation.addRuleDB(userMysql, passMysql, fileLocation);
    actionLoadDB();//sesudah restore diload ulang
 }
 //delete db and it's table
 private void actionDBFullDelete(){
     ConfigurationProperties conf = new ConfigurationProperties();
     conf.loadProperties();
     String dbName = conf.getLoadDbMysql();
     try {
         try{
           int option = JOptionPane.showConfirmDialog(this, "Are you sure to delete DB and it's contain?",
                        "Warning",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
           
            if(option==JOptionPane.YES_OPTION){
                query = "drop database "+dbName+"";
                PreparedStatement presttmt = dbConn.connection.prepareStatement(query);
                presttmt.executeUpdate(query);       
                actionReset();
            
            JOptionPane.showMessageDialog(null, "Database Deleted.");
            }else{
                
            } 
         }catch(Error e){} 
     }catch (SQLException se) {
          JOptionPane.showMessageDialog(null,
                        "Can not delete database. \nerror :" + se,
                        "Error", JOptionPane.ERROR_MESSAGE);
            //dispose();
     }
 }
 
 public void actionExecute() throws IOException, InterruptedException{
     if(jTableRules.getRowCount()!=0){
        long startTime = System.currentTimeMillis();
        jTextAreaActivity.append("\nExecuting Anomaly ....");
        ConfigurationProperties conf = new ConfigurationProperties();
        conf.loadProperties();
        String pass = conf.getLoadPassLinux();
        String interfaceIn = conf.getLoadInterfaceIn();
        String interfaceOut = conf.getLoadInterfaceOut();
        RuleExecute ruleExec = new RuleExecute();
        try{
            ruleExec.grantFlush(pass);
            ruleExec.grantExec(pass,interfaceIn, interfaceOut);
            ruleExec.grantShow(pass);

            }catch(Error e){
                System.out.println("Error at: "+e);
        }
        
        long endTime = System.currentTimeMillis();
        jTextAreaActivity.append("\n("+jTableRules.getRowCount()+" Rules Executed in: " + (endTime - startTime) + " ms)");
        JOptionPane.showMessageDialog(null, "Rule execute to Iptables Finished");
        //        int out = ruleExec.getC();
   //        jTextAreaActivity.
     }
     else{
         JOptionPane.showMessageDialog(null,
                        "Table is empty. Please load data first." ,
                        "", JOptionPane.INFORMATION_MESSAGE);
         
     }
 }
 
  void displayClickTable(){
      int row = jTableRules.getSelectedRow();
      fieldId.setText(jTableRules.getValueAt(row,0).toString());
      
      if(jTableRules.getValueAt(row,1).toString().equals("tcp")){
          cboProtocol.setSelectedItem("tcp");
      }
      else if(jTableRules.getValueAt(row,1).toString().equals("udp")){
          cboProtocol.setSelectedItem("udp");
      }
//      else{
//          cboProtocol.setSelectedItem("icmp");
//      }
      fieldSourceIp.setText(jTableRules.getValueAt(row, 2).toString());
      fieldSourcePort.setText(jTableRules.getValueAt(row,3).toString());
      fieldDestIp.setText(jTableRules.getValueAt(row,4).toString());
      fieldDestPort.setText(jTableRules.getValueAt(row,5).toString());
      if(jTableRules.getValueAt(row,6).toString().equals("accept")){
          cboAction.setSelectedItem("accept");
      }else{
          cboAction.setSelectedItem("drop");
      }
      
  }


  private void buttonTranslate_actionPerformed(ActionEvent e) {
     actionTranslate();
  }
  
  private void jCheckBoxLog_stateChanged(ChangeEvent e) {
    if (jCheckBoxLog.isSelected()) {
          logDetail = LOG_DETAILED;
    }
    else {
          logDetail = LOG_BASIC;
    }
  }
  
  public void actionLoadDB(){
      jTextAreaActivity.append("\nLoading database ....");
      actionReleaseField();
      Rule.counter = 0;
      vRuleList.clear();
      loadRulesDatabase();//load rules dari database dahulu
      jTableRules.revalidate();
      jTableRules.repaint();
//      uncomment jika ingin load langsung discover
//      ruleTree.generateTree();
//      ((DefaultTreeModel)jTreeRules.getModel()).setRoot(ruleTree.root);
//      ((DefaultTreeModel)jTreeRules.getModel()).reload();
//      jTreeRules.revalidate();
//      jTreeRules.repaint();
//      jTextAreaMessage.setCaretPosition(0);
//      if(jTableRules.contains(null)==false){
//          System.out.println("sukses");
//      }else{
//          System.out.println("gagal");
//      }
      bBarLoad.setEnabled(true);
      bBarDiscover.setEnabled(true);
      bBarReset.setEnabled(true);
      
  }
  
  public void loadRulesDatabase(){
      dbConn = new DatabaseConnection();
      dbConn.createConnection();
      //operasi tampilkan dbConn
//      tableModelRules = new TableModelRules();
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
             //simulasi table apakah valid
             //System.out.println(""+ruleData[0]+", "+ruleData[1]+", "+ruleData[2]+", "+ruleData[3]+", "+ruleData[4]+", "+ruleData[5]+", "+ruleData[6]+ "");
             //proses stream array ke string
             linedb = (String)""+ruleData[1]+", "+ruleData[2]+", "+ruleData[3]+", "+ruleData[4]+", "+ruleData[5]+", "+ruleData[6]+ "";//
             System.out.println(linedb);
             Rule ruleDb = new Rule(linedb);
//             System.out.println(ruleDb);
             vRuleList.add(ruleDb);
          }
      }catch (SQLException se){
          se.printStackTrace();

      }
  }
  private void actionDiscovery(){
      if(jTableRules.getRowCount()!=0){//kondisi jika tabel masih kosong
        long startTime = System.currentTimeMillis();
        jTextAreaActivity.append("\nDiscovering Anomaly ....");
        jTableRules.revalidate();
        jTableRules.repaint();
        ruleTree.generateTree();
        ((DefaultTreeModel)jTreeRules.getModel()).setRoot(ruleTree.root);
        ((DefaultTreeModel)jTreeRules.getModel()).reload();
        jTreeRules.revalidate();
        jTreeRules.repaint();
        long endTime = System.currentTimeMillis();

        jTextAreaActivity.append("\n("+jTableRules.getRowCount()+" Rules Discovered in: " + (endTime - startTime) + " ms)");  
      }
      else{
         JOptionPane.showMessageDialog(null,
                        "Table is empty. Please load data first." ,
                        "", JOptionPane.INFORMATION_MESSAGE);
      }

  }
  private void actionLoadFile(){
      JFileChooser fc = new JFileChooser("$HOME");
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
              file = fc.getSelectedFile();
        }
        else{
              return;
        }
    System.out.println("Loading Rules from File..."+file);
    
    jTextAreaMessage.setText("");
    loadRulesFile(file);//argument file method loadRules, file object File
    jTableRules.revalidate();//validasi ulang isi tabel
    jTableRules.repaint();//menampilkan isi tabel
//    ruleTree.generateTree();//membuat tree dari class RuleTree
//    ((DefaultTreeModel)jTreeRules.getModel()).setRoot(ruleTree.root);//root: ref variable RuleTreeNode pada RuleTree
//    ((DefaultTreeModel)jTreeRules.getModel()).reload();
//    jTreeRules.revalidate();
//    jTreeRules.repaint();
//    jTextAreaMessage.setCaretPosition(0);

    buttonDiscover.setEnabled(true);
    buttonReset.setEnabled(true);
    buttonLoadDb.setEnabled(false);
    buttonLoadFile.setEnabled(false);
    System.out.println("Stream File Rule Selesai.");
  }
  
  private void loadRulesFile(File infile) {
    //FileReader file;
    Rule.counter = 0;//inisialisasi counter 0
    vRuleList.clear();//clear vector RuleList
    try {
      FileReader filetxt = new FileReader(infile);
      BufferedReader stream = new BufferedReader(filetxt);
      String lineFile;
      while (stream.ready()) {
        lineFile = stream.readLine();//baca baris
        System.out.println(lineFile);//coba nampilkan format line
        Rule rule = new Rule(lineFile);//line plaintext dari Rule.txt
        vRuleList.add(rule);//vector vRuleList(data tabel model) ditambah dengan rule hasil proses class Rule
      }
    }
    catch(FileNotFoundException fnfe) {}
    catch(IOException ioe) {}
  }
  
  private void actionReset(){
      System.out.println("Proses Reset rule...");
      Rule.counter = 0;
      vRuleList.clear();
      
      jTableRules.revalidate();
      jTableRules.repaint();
      ruleTree.generateTree();
      ((DefaultTreeModel)jTreeRules.getModel()).setRoot(ruleTree.root);
      ((DefaultTreeModel)jTreeRules.getModel()).reload();
      jTreeRules.revalidate();
      jTreeRules.repaint();
      jTextAreaMessage.setText("");
      jTextAreaActivity.setText("");
      bBarLoad.setEnabled(true);
      itemLoadFile.setEnabled(true);
      itemLoadDb.setEnabled(true);
//      bBarDiscover.setEnabled(false);
//      buttonRefresh.setEnabled(false);
      
      jCheckBoxLog.setSelected(false);
      buttonReset.setEnabled(false);
      actionReleaseField();
  }
  
  private void actionRefresh(){
      System.out.println("Proses Refresh rule...");
      jTextAreaMessage.setText("");
  
      //loadRulesFile(file);//memanggil load rule kembali utk setiap refresh
      loadRulesDatabase();
      jTableRules.revalidate();
      jTableRules.repaint();
      ruleTree.generateTree();
      ((DefaultTreeModel)jTreeRules.getModel()).setRoot(ruleTree.root);
      ((DefaultTreeModel)jTreeRules.getModel()).reload();
      jTreeRules.revalidate();
      jTreeRules.repaint();
      jTextAreaMessage.setCaretPosition(0);
  }
  
  private void actionTranslate(){
      
      System.out.println("Translating rules...");
      translationTree.buildTree();
      translationTree.translateRules();
      jEditorPaneTranslate.setText(translationTree.translation);
      jEditorPaneTranslate.setCaretPosition(0);
      if (!jDialogTranslate.isShowing()) {
        Dimension frmSize = getSize();
        Point loc = getLocation();
        jDialogTranslate.setBounds(new Rectangle(0, 0, frmSize.width, 180));
        jDialogTranslate.setLocation(loc.x, loc.y /* + frmSize.height*/);
        jDialogTranslate.show();
    }
  }
  private void actionAbout(){
      FrameAbout aboutDialog = new FrameAbout();
      aboutDialog.show();

  }
  private void actionHelp(){
      
  }
  void displayMessage(String msg) {
    if (logDetail == true) {
          jTextAreaMessage.append(msg + "\n");
      }
  }

  void displayMessage(String msg, boolean logDetail) {
    if (logDetail == true) {
          jTextAreaMessage.append(msg + "\n");
      }
  }
  void jTreeRules_mousePressed(MouseEvent e) {
    int selRow = jTreeRules.getRowForLocation(e.getX(), e.getY());
    TreePath selPath = jTreeRules.getPathForLocation(e.getX(), e.getY());
    if(selRow != -1) {
       RuleTreeNode node = (RuleTreeNode)selPath.getLastPathComponent();
       jTableRules.setRowSelectionInterval(node.rule_id()-1, node.rule_id()-1);
       jTableRules.scrollRectToVisible(jTableRules.getCellRect(node.rule_id()-1, node.rule_id()-1, false));
    }
  }

  void buttonShowTrees_actionPerformed(ActionEvent e) {
    TreeDialog dlg = new TreeDialog(this);
    dlg.validate();
    dlg.setVisible(true);
  }

  void jEditorPaneTranslate_hyperlinkUpdate(HyperlinkEvent e) {
    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
      int rule_id = e.getURL().getPort();
      if(rule_id != -1) {
        jTableRules.setRowSelectionInterval(rule_id-1, rule_id-1);
        jTableRules.scrollRectToVisible(jTableRules.getCellRect(rule_id-1, rule_id-1, false));
      }
      else {
            System.out.println("Invalid Rule number !");
      }
    }
  }
  
  @Override
 protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }
  
  private void actionReleaseField(){
      fieldId.setText("");//fieldId.setEnabled(false);
      cboProtocol.setSelectedIndex(-1);//cboProtocol.setEnabled(false);
      fieldSourceIp.setText("");//fieldSourceIp.setEnabled(false);
      fieldSourcePort.setText("");//fieldSourcePort.setEnabled(false);
      fieldDestIp.setText("");//fieldDestIp.setEnabled(false);
      fieldDestPort.setText("");//fieldDestPort.setEnabled(false);
      cboAction.setSelectedIndex(-1);//cboAction.setEnabled(false);
      fieldStart.setText("");
      fieldEnd.setText("");
      
  }
     
}
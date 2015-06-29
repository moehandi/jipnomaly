package sistem;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;


public class TreeDialog extends JDialog {
  MainFrame frame;
  JPanel jPanelContent;
  JScrollPane jScrollPaneTree = new JScrollPane();
  JTree jTreeRules = new JTree();
  JLabel jLabelOrder = new JLabel();
  JComboBox jComboBoxOrder = new JComboBox();
  GridBagLayout gridBagLayout = new GridBagLayout();

  public TreeDialog(MainFrame owner) {
    super(owner);
    frame = owner;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setTitle("Rules Tree");
    this.setSize(new Dimension(306, 312));
    jPanelContent = (JPanel) this.getContentPane();
    jPanelContent.setLayout(gridBagLayout);
    jPanelContent.setToolTipText("");
    jLabelOrder.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabelOrder.setText("Field order");
    jComboBoxOrder.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        jComboBoxOrder_itemStateChanged(e);
      }
    });
    jTreeRules.setRootVisible(false);
    jTreeRules.setShowsRootHandles(true);
    jPanelContent.add(jScrollPaneTree,  new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(9, 10, 10, 11), 212, -62));
    jScrollPaneTree.getViewport().add(jTreeRules, null);
    jPanelContent.add(jComboBoxOrder,  new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(13, 0, 0, 11), 103, 0));
    jPanelContent.add(jLabelOrder,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(13, 10, 0, 3), 2, 0));

    jComboBoxOrder.addItem("Proto-SrcA-SrcP-DstA-DstP-Action");
    jComboBoxOrder.addItem("Proto-DstA-DstP-SrcA-SrcP-Action");
    jComboBoxOrder.addItem("Proto-SrcP-SrcA-DstP-DstA-Action");
    jComboBoxOrder.addItem("Proto-DstP-DstA-SrcP-SrcA-Action");

    jComboBoxOrder.addItem("Proto-SrcA-DstA-SrcP-DstP-Action");
    jComboBoxOrder.addItem("Proto-SrcP-DstP-SrcA-DstA-Action");
    jComboBoxOrder.addItem("Proto-DstA-SrcA-DstP-SrcP-Action");
    jComboBoxOrder.addItem("Proto-DstP-SrcP-DstA-SrcA-Action");

    jComboBoxOrder.addItem("Proto-SrcA-DstP-SrcP-DstA-Action");
    jComboBoxOrder.addItem("Proto-SrcP-DstA-SrcA-DstP-Action");
    jComboBoxOrder.addItem("Proto-DstP-SrcA-DstA-SrcP-Action");
    jComboBoxOrder.addItem("Proto-DstA-SrcP-DstP-SrcA-Action");

    jComboBoxOrder_itemStateChanged(null);
  }

  void jComboBoxOrder_itemStateChanged(ItemEvent e) {
    RuleTree tree = new RuleTree(jComboBoxOrder.getSelectedIndex(), frame.vRuleList);//vRuleList object vector main frame
    tree.generateTree();
    ((DefaultTreeModel)jTreeRules.getModel()).setRoot(tree.root);
    ((DefaultTreeModel)jTreeRules.getModel()).reload();
    jTreeRules.revalidate();
    jTreeRules.repaint();
  }
}

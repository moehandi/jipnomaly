package sistem;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import sistem.network.NetAddress;
import sistem.network.NetPort;


public class TableModelRules extends AbstractTableModel {
  JTable table;
  Vector vRuleList = null;
  //nama header atau kolom
  String columnNames[] = {"Order", "Protocol", "Source IP", "Source Port",
                          "Destination IP", "Destination Port", "Action"};//, "ID DB"};
  
  public TableModelRules () {
    vRuleList = null;
    table = null;
  }
  
  public TableModelRules (JTable atable, Vector aruleList) {
    table = atable;
    vRuleList = aruleList;
  }
  
    @Override
  public String getColumnName(int col) {
      return columnNames[col].toString();
  }
  
    @Override
  public int getRowCount() { 
      return vRuleList.size();
  }
  
    @Override
  public int getColumnCount() {
      return columnNames.length; 
  }
  
    @Override
  public Object getValueAt(int row, int col) {
    DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getCellRenderer(row, col);
    switch (((Rule)vRuleList.get(row)).anomaly) {
      case Rule.ANOMALY_SHADOWING:
          renderer.setBackground(Color.red);
      break;
      case Rule.ANOMALY_REDUNDANCY:
          renderer.setBackground(Color.yellow);
      break;
      case Rule.ANOMALY_CORRELATION:
          renderer.setBackground(Color.cyan);
      break;
         case Rule.ANOMALY_GENERALIZATION:
          renderer.setBackground(Color.green);
      break;
      default:
      renderer.setBackground(Color.white);
    }
    switch (col) {
      case Rule.FIELDID_ID:
        return String.valueOf(((Rule)vRuleList.get(row)).id);
      case Rule.FIELDID_PROTOCOL:
        return ((Rule)vRuleList.get(row)).protocol;
      case Rule.FIELDID_SRCIP:
        return ((Rule)vRuleList.get(row)).src_ip;
      case Rule.FIELDID_SRCPORT:
        return ((Rule)vRuleList.get(row)).src_port;
      case Rule.FIELDID_DSTIP:
        return ((Rule)vRuleList.get(row)).dst_ip;
      case Rule.FIELDID_DSTPORT:
        return ((Rule)vRuleList.get(row)).dst_port;
      case Rule.FIELDID_ACTION:
        return ((Rule)vRuleList.get(row)).action;
//      case Rule.FIELDID_IDDB:
//        return ((Rule)vRuleList.get(row)).id_db;
    }
    return null;
  }
  
  @Override
  public boolean isCellEditable(int row, int col) {
    if (col == Rule.FIELDID_ID) {
          return false;
    }
    return false;
  }
  
    @Override
  public void setValueAt(Object value, int row, int col) {
    switch (col) {
      case Rule.FIELDID_PROTOCOL:
        ((Rule)vRuleList.get(row)).protocol = (String)value;
        break;
      case Rule.FIELDID_SRCIP:
        ((Rule)vRuleList.get(row)).src_ip = NetAddress.parseAddress((String)value);
        break;
      case Rule.FIELDID_SRCPORT:
        ((Rule)vRuleList.get(row)).src_port = NetPort.parsePort((String)value);
        break;
      case Rule.FIELDID_DSTIP:
        ((Rule)vRuleList.get(row)).dst_ip = NetAddress.parseAddress((String)value);
        break;
      case Rule.FIELDID_DSTPORT:
        ((Rule)vRuleList.get(row)).dst_port = NetPort.parsePort((String)value);
        break;
      case Rule.FIELDID_ACTION:
        ((Rule)vRuleList.get(row)).action = (String)value;
        break;

    }
  }
}
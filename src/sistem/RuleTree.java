 package sistem;

import java.util.*;
import sistem.network.NetAddress;
import sistem.network.NetPort;
 
/*
 * Fungsi class:
 * -menjalankan algoritma insertion sort
 * -validasi algoritma Anomaly Discovery
 * -fungsi method insert: protocol, src_ip, src_port, dest_ip, dest_port, dan action
 * 
 * --modifier final pd variabel artinya tidak dpt di-extend dari subclass manapun
 *   sehingga tidak dapat diganggu oleh class lain.
 * 
 *  +Overload constructor RuleTree
 *  +Overload constructor Logger
 *  Class Enumeration : objek yg meng-generate elemen dalam suatu wkatu, bysny elemn dr vector atau Array
 */
public final class RuleTree {
  int order;
  String field_order;
  MainFrame frame;
  Vector vRuleList = null;
  RuleTreeNode root = null;
  String translation = "";
  String main_action = "accept", sub_action = "drop";
  String lastAction = "";
  
  static final boolean RULE_EXPLICIT=true, RULE_IMPLICIT=false;
  static final int ORDER_SRCDST=0, ORDER_DSTSRC=1;
  static final String order_str[] = {"1:2:3:4:5:6", "1:4:5:2:3:6", "1:3:2:5:4:6", "1:5:4:3:2:6",
                                     "1:2:4:3:5:6", "1:3:5:2:4:6", "1:4:2:5:3:6", "1:5:3:4:2:6",
                                     "1:2:5:3:4:6", "1:3:4:2:5:6", "1:5:2:4:3:6", "1:4:3:5:2:6"};
  //overload constructor RuleTree pada argumen list
  public RuleTree(int aorder, Vector alist) {
    init(aorder, alist, null);
  }
  public RuleTree(String aorder, Vector alist) {
    order = -1;
    field_order = aorder;
    vRuleList = alist;
    frame = null;
  }
  public RuleTree(int aorder, Vector alist, MainFrame aframe) {
    init(aorder, alist, aframe);
  }
  public RuleTree(String aorder, Vector alist, MainFrame aframe) {
    order = -1;
    field_order = aorder;
    vRuleList = alist;
    frame = aframe;
  }

  public void init(int aorder, Vector alist, MainFrame aframe) {
    order = aorder;
    field_order = order_str[aorder];
    vRuleList = alist;
    frame = aframe;
  }
  //overload Logger, method debug memiliki nama yang sama, namun argument list yg berbeda
  //argument List = (String) dan (String, boolean)
  public void Logger(String msg) {
    if (frame != null){
        frame.displayMessage(msg);
    }
  }

  public void Logger(String msg, boolean display) {
    if (frame != null){
        frame.displayMessage(msg, display);
    }
  }

  public String Spaces(int num) {
    String s="";
    for (int i=0; i<num; i++) {
          s += "&nbsp;";
      }
    return s;
  }
//main process membuat tree, memasukan rule (insertion rule, dan checking (discovering) anomalinya
  public void generateTree() {
    if (frame != null) {
          frame.jTextAreaMessage.setText("");
    }
    Rule rule = null;
    root = new RuleTreeNode("Rules");
    for (Enumeration e=vRuleList.elements(); e.hasMoreElements();) { //e itu element dari vector vRuleList dan e memiliki byk element
      rule = (Rule)e.nextElement(); //lanjut ke elemen selanjutnya
      insertNextField(RULE_EXPLICIT, rule, root, Rule.ANOMALY_NONE);
    }
  }

  public void insertNextField(boolean origin, Rule rule, RuleTreeNode node, int anomaly) {
    int idx = field_order.indexOf(String.valueOf(node.field_id));
    idx = idx > -1 ? idx : -2;//cond ? exprtrue : exprfalse 
    if (field_order.length() == idx+1) {
          return;
      }
    int next_id = Integer.parseInt(field_order.substring(idx+2, idx+3));
    switch (next_id) {
      case Rule.FIELDID_PROTOCOL:
        insertProtocol(origin, rule, node, anomaly);
        break;
      case Rule.FIELDID_SRCIP:
        insertSourceIP(origin, rule, node, anomaly);
        break;
      case Rule.FIELDID_SRCPORT:
        insertSourcePort(origin, rule, node, anomaly);
        break;
      case Rule.FIELDID_DSTIP:
        insertDestinIP(origin, rule, node, anomaly);
        break;
      case Rule.FIELDID_DSTPORT:
        insertDestinPort(origin, rule, node, anomaly);
        break;
      case Rule.FIELDID_ACTION:
        insertAction(origin, rule, node, anomaly);
        break;
//      case Rule.FIELDID_IDDB:
//          insertIdDb(origin, rule, node, anomaly);
//          break;
    }
  }

   public void insertProtocol(boolean origin, Rule rule, RuleTreeNode node, int anomaly) {
    RuleTreeNode child = null;
    boolean value_found = false;

    for (Enumeration n=node.children(); n.hasMoreElements();) {
      child = (RuleTreeNode)n.nextElement();
      if (child.text.equals(rule.protocol)) {
        value_found = true;
        child.addRule(rule);
        Logger("Rule " + rule.id + " protocol match rule " + child.rule_id());
          insertNextField(origin, rule, child, Rule.ANOMALY_REDUNDANCY);
      }
    }
    if (!value_found && origin == RULE_EXPLICIT) {
      child = new RuleTreeNode(rule, Rule.FIELDID_PROTOCOL, rule.protocol);
      node.add(child);
      Logger("Rule " + rule.id + " protocol inserted");
        insertNextField(RULE_EXPLICIT, rule, child, Rule.ANOMALY_NONE);
    }
  }
   
  public void insertSourceIP(boolean origin, Rule rule, RuleTreeNode node, int anomaly) {
    RuleTreeNode child = null;
    NetAddress src_ip = null;
    boolean value_found = false;

    for (Enumeration n=node.children(); n.hasMoreElements();) {
      child = (RuleTreeNode)n.nextElement();
      src_ip = NetAddress.parseAddress(child.text);
      if (src_ip.isEqual(rule.src_ip)) {
        value_found = true;
        child.addRule(rule);
        Logger("Rule " + rule.id + " src_ip match rule " + child.rule_id());
        insertNextField(origin, rule, child, anomaly);
      } else {
        if (src_ip.isSubset(rule.src_ip)) {
          Logger("Rule " + rule.id + " src_ip imply rule " + child.rule_id());
          insertNextField(RULE_IMPLICIT, rule, child, Rule.ANOMALY_GENERALIZATION);
        } else if (src_ip.isSuperset(rule.src_ip)) {
          Logger("Rule " + rule.id + " src_ip is implied by rule " + child.rule_id());
          insertNextField(RULE_IMPLICIT, rule, child, Rule.ANOMALY_SHADOWING);
        }
      }
    }
    if (!value_found && origin == RULE_EXPLICIT) {
      child = new RuleTreeNode(rule, Rule.FIELDID_SRCIP, rule.src_ip.toString());
      node.add(child);
      Logger("Rule " + rule.id + " src_ip inserted");
      insertNextField(RULE_EXPLICIT, rule, child, Rule.ANOMALY_NONE);
    }
  }
  
  public void insertSourcePort(boolean origin, Rule rule, RuleTreeNode node, int anomaly) {
    RuleTreeNode child = null;
    NetPort src_port = null;
    boolean value_found = false;

    for (Enumeration n=node.children(); n.hasMoreElements();) {
      child = (RuleTreeNode)n.nextElement();
      src_port = NetPort.parsePort(child.text);
      if (src_port.isEqual(rule.src_port)) {
        value_found = true;
        child.addRule(rule);
        Logger("Rule " + rule.id + " src_port match rule " + child.rule_id());
          insertNextField(origin, rule, child, anomaly);
      } else {
        if (src_port.isSubset(rule.src_port)) {
          Logger("Rule " + rule.id + " src_port imply rule " + child.rule_id());
          if (anomaly == Rule.ANOMALY_SHADOWING) {
                insertNextField(RULE_IMPLICIT, rule, child, Rule.ANOMALY_CORRELATION);
            }
          else {
                insertNextField(RULE_IMPLICIT, rule, child, Rule.ANOMALY_GENERALIZATION);
            }
        } else if (src_port.isSuperset(rule.src_port)) {
          Logger("Rule " + rule.id + " src_port is implied by rule " + child.rule_id());
          if (anomaly == Rule.ANOMALY_GENERALIZATION) {
                insertNextField(RULE_IMPLICIT, rule, child, Rule.ANOMALY_CORRELATION);
            }
          else {
                insertNextField(RULE_IMPLICIT, rule, child, Rule.ANOMALY_SHADOWING);
            }
        }
      }
    }
    if (!value_found && origin == RULE_EXPLICIT) {
      child = new RuleTreeNode(rule, Rule.FIELDID_SRCPORT, rule.src_port.toString());
      node.add(child);
      Logger("Rule " + rule.id + " src_port inserted");
        insertNextField(RULE_EXPLICIT, rule, child, Rule.ANOMALY_NONE);
    }
  }
   
  public void insertDestinIP(boolean origin, Rule rule, RuleTreeNode node, int anomaly) {
    RuleTreeNode child = null;
    NetAddress dst_ip = null;
    boolean value_found = false;

    for (Enumeration n=node.children(); n.hasMoreElements();) {
      child = (RuleTreeNode)n.nextElement();
      dst_ip = NetAddress.parseAddress(child.text);
      if (dst_ip.isEqual(rule.dst_ip)) {
        value_found = true;
        child.addRule(rule);
        Logger("Rule " + rule.id + " dst_ip match rule " + child.rule_id());
        insertNextField(origin, rule, child, anomaly);
      } else {
        if (dst_ip.isSubset(rule.dst_ip)) {
          Logger("Rule " + rule.id + " dst_ip imply rule " + child.rule_id());
          if (anomaly == Rule.ANOMALY_SHADOWING) {
                insertNextField(RULE_IMPLICIT, rule, child, Rule.ANOMALY_CORRELATION);
            }
          else {
                insertNextField(RULE_IMPLICIT, rule, child, Rule.ANOMALY_GENERALIZATION);
            }
        } else if (dst_ip.isSuperset(rule.dst_ip)) {
          Logger("Rule " + rule.id + " dst_ip is implied by rule " + child.rule_id());
          if (anomaly == Rule.ANOMALY_GENERALIZATION) {
                insertNextField(RULE_IMPLICIT, rule, child, Rule.ANOMALY_CORRELATION);
            }
          else {
                insertNextField(RULE_IMPLICIT, rule, child, Rule.ANOMALY_SHADOWING);
            }
        }
      }
    }
    if (!value_found && origin == RULE_EXPLICIT) {
      child = new RuleTreeNode(rule, Rule.FIELDID_DSTIP, rule.dst_ip.toString());
      node.add(child);
      Logger("Rule " + rule.id + " dst_ip inserted");
      insertNextField(RULE_EXPLICIT, rule, child, Rule.ANOMALY_NONE);
    }
  } 
  
  public void insertDestinPort(boolean origin, Rule rule, RuleTreeNode node, int anomaly) {
    RuleTreeNode child = null;
    NetPort dst_port = null;
    boolean value_found = false;

    for (Enumeration n=node.children(); n.hasMoreElements();) {
      child = (RuleTreeNode)n.nextElement();
      dst_port = NetPort.parsePort(child.text);
      if (dst_port.isEqual(rule.dst_port)) {
        value_found = true;
        child.addRule(rule);
        Logger("Rule " + rule.id + " dst_port match rule " + child.rule_id());
          insertNextField(origin, rule, child, anomaly);
      } else {
        if (dst_port.isSubset(rule.dst_port)) {
          Logger("Rule " + rule.id + " dst_port imply rule " + child.rule_id());
          if (anomaly == Rule.ANOMALY_SHADOWING) {
                insertNextField(RULE_IMPLICIT, rule, child, Rule.ANOMALY_CORRELATION);
            }
          else {
                insertNextField(RULE_IMPLICIT, rule, child, Rule.ANOMALY_GENERALIZATION);
            }
        } else if (dst_port.isSuperset(rule.dst_port)) {
          Logger("Rule " + rule.id + " dst_port is implied by rule " + child.rule_id());
          if (anomaly == Rule.ANOMALY_GENERALIZATION) {
                insertNextField(RULE_IMPLICIT, rule, child, Rule.ANOMALY_CORRELATION);
            }
          else {
                insertNextField(RULE_IMPLICIT, rule, child, Rule.ANOMALY_SHADOWING);
            }
        }
      }
    }
    if (!value_found && origin == RULE_EXPLICIT) {
      child = new RuleTreeNode(rule, Rule.FIELDID_DSTPORT, rule.dst_port.toString());
      node.add(child);
      Logger("Rule " + rule.id + " dst_port inserted");
      insertNextField(RULE_EXPLICIT, rule, child, Rule.ANOMALY_NONE);
    }
  }

  public void insertAction(boolean origin, Rule rule, RuleTreeNode node, int anomaly) {
    RuleTreeNode child = null;
    boolean value_found = false;
    boolean x = false;

    Enumeration n=node.children();
    if (n.hasMoreElements()) {
      child = (RuleTreeNode)n.nextElement();
      if (anomaly == Rule.ANOMALY_CORRELATION && !child.text.equals(rule.action)) {
        child.rule().setAnomaly(Rule.ANOMALY_CORRELATION);
        Logger("Rule " + rule.id + " is in CORRELATION with rule " + child.rule_id(), true);
      } else if (anomaly == Rule.ANOMALY_CORRELATION && child.text.equals(rule.action)) {
        anomaly = Rule.ANOMALY_NONE;
        x = true;
      } else if (anomaly == Rule.ANOMALY_GENERALIZATION && !child.text.equals(rule.action)) {
        child.rule().setAnomaly(Rule.ANOMALY_SPECIALIZATION);
        Logger("Rule " + rule.id + " is GENERALIZATION of rule " + child.rule_id(), true);
      } else if (anomaly == Rule.ANOMALY_GENERALIZATION && child.text.equals(rule.action)) {
         if (child.rule().anomaly == Rule.ANOMALY_NONE) {
            anomaly = Rule.ANOMALY_NONE;
            child.rule().setAnomaly(Rule.ANOMALY_REDUNDANCY);
            Logger("Rule " + child.rule_id() + " is REDUNDANT to rule " + rule.id, true);
         }
      } else if (child.text.equals(rule.action)) {
        anomaly = Rule.ANOMALY_REDUNDANCY;
        Logger("Rule " + rule.id + " is REDUNDANT to rule " + child.rule_id(), true);
      } else if (!child.text.equals(rule.action)) {
        anomaly = Rule.ANOMALY_SHADOWING;
        Logger("Rule " + rule.id + " is SHADOWED by rule " + child.rule_id(), true);
      }
    }
    child = new RuleTreeNode(rule, Rule.FIELDID_ACTION, rule.action);
    rule.setAnomaly(anomaly);
    if (!x) {
          node.add(child);
      }
    Logger("Rule " + rule.id + " action inserted");
  }
 
}

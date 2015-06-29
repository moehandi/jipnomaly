package sistem;

import java.util.*;
import sistem.network.NetAddress;
import sistem.network.NetPort;


public class TranslationTree {
  MainFrame frame;
  Vector ruleList = null;
  RuleTreeNode root = null;
  String translation = "";
  String main_action = "accept", sub_action = "drop";
  String lastAction = "";

  public TranslationTree(Vector alist, MainFrame aframe) {
    ruleList = alist;
    frame = aframe;
  }

  public void Debug(String msg) {
    if (frame != null) {
          if (frame.logDetail) {
            frame.jTextAreaMessage.append(msg + "\n");
        }
      }
  }

  public void Debug(String msg, boolean display) {
    if (frame != null) {
          if (display) {
            frame.jTextAreaMessage.append(msg + "\n");
        }
      }
  }

  public String Spaces(int num) {
    String s="";
    for (int i=0; i<num; i++) {
          s += "&nbsp;";
      }
    return s;
  }

  public void buildTree() {
    if (frame != null) {
          frame.jTextAreaMessage.setText("");
      }

    // Build a fixed-order tree to display anomalies
    RuleTree tree = new RuleTree("1:2:3:4:5:6", ruleList, frame);
    tree.generateTree();

    Rule rule = null;
    root = new RuleTreeNode("Rules");
    for (Enumeration e=ruleList.elements(); e.hasMoreElements();) {
      rule = (Rule)e.nextElement();
      if (rule.anomaly != Rule.ANOMALY_REDUNDANCY && rule.anomaly != Rule.ANOMALY_SHADOWING) {
            root.addRule(rule);
        }
    }
    buildSubTree(root, "1:");
    RuleTreeNode node = null;
    for (Enumeration n=root.children(); n.hasMoreElements();) {
      node = (RuleTreeNode)n.nextElement();
      node.removeAllChildren();
      buildSubTree(node, "1:2:3:4:5:");
    }
  }

  public void buildSubTree(RuleTreeNode node, String order) {
    int idx;
    String tree_order;
    int num_trees = 0;
    if (node.field_id > 0) {
      idx = order.indexOf(String.valueOf(node.field_id));
      tree_order = "";
      if (idx > 0) {
            tree_order += order.substring(0, idx);
        }
      if (idx < order.length()) {
            tree_order += order.substring(idx+2);
        }
      order = tree_order;
    }
    //if (order.compareTo(new String(""))==0) {
    if (order.compareTo("")==0) {
      node.add(new RuleTreeNode(node.rule(), Rule.FIELDID_ACTION, node.rule().action));
      return;
    }
    idx = 0;
    while ((idx = order.indexOf(":", idx)) > 0) {
      idx++;
      num_trees++;
    }
    RuleTree trees[] = new RuleTree[num_trees];
    for (idx = 0; idx < trees.length; idx++) {
      tree_order = order.substring(2*idx, 2*idx+1);
      trees[idx] = new RuleTree(tree_order, node.rules);
      trees[idx].generateTree();
    }
    Vector vector = new Vector();
    for (idx = 0; idx < trees.length; idx++) {
          for (Enumeration n=trees[idx].root.children(); n.hasMoreElements();) {
            vector.add(n.nextElement());
        }
      }

    RuleTreeNode element = null, child = null;
    Vector allrules = new Vector(node.rules);
    while (!allrules.isEmpty()) {
      Object branches[] =  vector.toArray();
      vector.clear();
      Arrays.sort(branches);
      vector.addAll(Arrays.asList(branches));
      element = (RuleTreeNode)vector.firstElement();
      vector.remove(element);
      child = new RuleTreeNode(element.rules, element.field_id, element.text);
      node.add(child);
      for (Enumeration n=vector.elements(); n.hasMoreElements();) {
        RuleTreeNode branch = (RuleTreeNode)n.nextElement();
        for (Enumeration m=child.rules.elements(); m.hasMoreElements();) {
          Rule rule = (Rule)m.nextElement();
          branch.removeRule(rule);
        }
      }
      for (Enumeration m=child.rules.elements(); m.hasMoreElements();) {
            allrules.remove(m.nextElement());
        }
    }
    for (Enumeration n=node.children(); n.hasMoreElements();) {
          buildSubTree((RuleTreeNode)n.nextElement(), order);
      }
  }

  public void translateRules() {
    RuleTreeNode child = null;
    StringBuffer trans = new StringBuffer("");
    String main="", sub="";
    main_action = "accept";
    sub_action = "drop";
    translation = "<html> <head></head> <body align=\"right\">\n";
    for (Enumeration n=root.children(); n.hasMoreElements();) {
      child = (RuleTreeNode)n.nextElement();
      translateNextField(child, trans);
      if (trans.length() != 0) {
            if (child.isEveryAction(sub_action)) {
              sub += trans;
          }
            else {
              main += trans;
          }
        }
      trans = new StringBuffer("and ");
    }
    if (main.length() > 0) {
      translation += main_action + " all " + main;
      if (sub.length() > 0) {
            translation += "except all " + sub;
        }
    } else if (sub.length() > 0) {
          translation += sub_action + " all " + sub;
      }
    translation += "</body> </html>";
  }

  public boolean translateNextField(RuleTreeNode node, StringBuffer text) {
    boolean join = false;
    boolean nxt_join = false;
    switch (node.field_id) {
      case Rule.FIELDID_PROTOCOL:
        text.append("<a href=\"gopher://:").append(node.rule_id()).append("\">").append(node.text).append("</a> traffic ");
        join = true;
        break;
      case Rule.FIELDID_SRCIP:
        if (NetAddress.parseAddress(node.text).ip != 0) {
            text.append(" from address <a href=\"gopher://:").append(node.rule_id()).append("\">").append(node.text).append("</a>");
          join = true;
        }
        break;
      case Rule.FIELDID_SRCPORT:
        if (NetPort.parsePort(node.text).port != 0) {
            text.append(" from port <a href=\"gopher://:").append(node.rule_id()).append("\">").append(node.text).append("</a>");
          join = true;
        }
        break;
      case Rule.FIELDID_DSTIP:
        if (NetAddress.parseAddress(node.text).ip != 0) {
            text.append(" to address <a href=\"gopher://:").append(node.rule_id()).append("\">").append(node.text).append("</a>");
          join = true;
        }
        break;
      case Rule.FIELDID_DSTPORT:
        if (NetPort.parsePort(node.text).port != 0) {
            text.append(" to port <a href=\"gopher://:").append(node.rule_id()).append("\">").append(node.text).append("</a>");
          join = true;
        }
        break;
      case Rule.FIELDID_ACTION:
        text.append("<br>");
        return join;
    }
    RuleTreeNode child = null;
    StringBuffer trans = new StringBuffer("");
    String main="", sub="";
    for (Enumeration n=node.children(); n.hasMoreElements();) {
      child = (RuleTreeNode)n.nextElement();
      nxt_join = translateNextField(child, trans);
      if (child.isEveryAction(sub_action)) {
        if (sub.length()==0 || !nxt_join) {
              sub += trans;
          }
        else {
              sub += " or " + trans;
          }
      } else {
        if (main.length()==0 || !nxt_join) {
              main += trans;
          }
        else {
              main +=  " or " + trans;
          }
      }
      trans.setLength(0);
    }
    if (main.length() > 0) {
      if (nxt_join && node.field_id != Rule.FIELDID_PROTOCOL && child.field_id != Rule.FIELDID_ACTION) {
            text.append(" and ").append(main);
        }
      else {
            text.append(main);
        }
      if (sub.length() > 0) {
            text.append("except ").append(sub);
        }
    } else if (sub.length() > 0) {
          if (nxt_join && node.field_id != Rule.FIELDID_PROTOCOL && child.field_id != Rule.FIELDID_ACTION) {
              text.append(" and ").append(sub);
        }
          else {
            text.append(sub);
        }
      }
    return join;
  }
}

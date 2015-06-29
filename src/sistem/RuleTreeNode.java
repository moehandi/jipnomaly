package sistem;

import java.util.Enumeration;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author moehandi
 */

class RuleTreeNode extends DefaultMutableTreeNode  implements Comparable {
  Vector rules = new Vector();
  int field_id;
  String text;

  public RuleTreeNode(String atext) {
    field_id = -1;
    text = atext;
  }
  
  public RuleTreeNode(Rule arule, int afield_id, String atext) {
    rules.add(arule);
    field_id = afield_id;
    text = atext;
  }
  
  public RuleTreeNode(Vector arules, int afield_id, String atext) {
    rules = (Vector) arules.clone();
    field_id = afield_id;
    text = atext;
  }
  
  //override bawaan class java
    @Override
  public int compareTo(Object o) {
    RuleTreeNode node = (RuleTreeNode) o;
    return (node.rules.size() - rules.size());
    
  }
    
  public void addRule(Rule arule) {
    if (!rules.contains(arule)){
      rules.add(arule);
    }
  }
  
  public void removeRule(Rule arule) {
    if (rules.contains(arule)){
      rules.remove(arule);
    }
  }
  
  public boolean isEveryAction(String action) {
    Rule rule = null;
    for (Enumeration e=rules.elements(); e.hasMoreElements();) {
      rule = (Rule)e.nextElement();
      if (!rule.action.equals(action) && rule.anomaly==Rule.ANOMALY_NONE){
        return false;
      }
    }
    return true;
  }
  
  public Rule rule() {
    return ((Rule)rules.firstElement());
  }
  
  public int rule_id() {
    return ((Rule)rules.firstElement()).id;
  }
  
    @Override
  public String toString() {
    if (field_id == Rule.FIELDID_ACTION){
       return rule_id() + ": " + text;
    }
    else if (field_id > 0){
       return Rule.field_str[field_id] + " " + text; //+ " [" + rules.size() + "]";
    }
    else{
      return "";
    }
  }
}

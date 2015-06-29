package sistem;

import sistem.network.NetAddress;
import sistem.network.NetPort;

/*
 * Fungsi Class :
 * -menerjemahkan IP dan Port dari txt/DB menjadi data Vector ruleList untuk tabel model
 * -
 */
public class Rule {
  int id;
  String protocol;
  NetAddress src_ip; //reference variable dari class NetAddress utk source ip
  NetPort src_port;  //reference variable dari class NetPort utk source port
  NetAddress dst_ip; //reference variable dari class NetAddress utk destination ip
  NetPort dst_port;  //reference variable dari class NetPort utk destination port
  String action;
 // String id_db;
  int anomaly;
  char FIELD_DELIMETER = ',';
  //final: tidak ada class lain yang dapat mengextend variable ini
  static final int FIELDID_ID=0,      FIELDID_PROTOCOL=1, FIELDID_SRCIP=2,
                   FIELDID_SRCPORT=3, FIELDID_DSTIP=4,   FIELDID_DSTPORT=5,
                   FIELDID_ACTION=6;//, FIELDID_IDDB=7;
  static final String field_str[] = {"id", "pt", "sa", "sp", "da", "dp", "a"};  //, "id_db"};
  static final int ANOMALY_NONE=0, ANOMALY_REDUNDANCY=1, ANOMALY_SHADOWING=2,
                   ANOMALY_CORRELATION=3, ANOMALY_GENERALIZATION=4, ANOMALY_SPECIALIZATION=5;
  static final String anomaly_str[] = {"none", "redundant", "shadowed",
                                       "correlated", "generalized", "specialized"};
  static int counter = 0; //tidak final sehingga bisa diinisialisasikan di mainframe juga.
  
  // untuk db dari statement query menuju table mainForm
  public Rule(String[] ruleData) {
    int i =0;
    id = ++counter;
//    int i=0;
//    id = Integer.parseInt(ruleData[1]);
    protocol = ruleData[1];
    src_ip   = NetAddress.parseAddress(ruleData[2].substring(++i, i=ruleData[2].indexOf(FIELD_DELIMETER, i)).trim());
    src_port = NetPort.parsePort(ruleData[3].substring(++i, i=ruleData[3].indexOf(FIELD_DELIMETER,i)).trim());
    dst_ip   = NetAddress.parseAddress(ruleData[4].substring(++i, i=ruleData[4].indexOf(FIELD_DELIMETER,i)).trim());
    dst_port = NetPort.parsePort(ruleData[5].substring(++i, i=ruleData[5].indexOf(FIELD_DELIMETER,i)).trim());
    action   = ruleData[6].substring(++i).trim();
    //id_db    = ruleData[7].substring(++i).trim();
    anomaly  = ANOMALY_NONE;
  }
  
// untuk file txt menuju table di mainFrame
  public Rule(String ruleLine) {
    int i;
    id = ++counter;
    protocol = ruleLine.substring(0, i=ruleLine.indexOf(FIELD_DELIMETER)).trim();
    src_ip   = NetAddress.parseAddress(ruleLine.substring(++i, i=ruleLine.indexOf(FIELD_DELIMETER, i)).trim());
    src_port = NetPort.parsePort(ruleLine.substring(++i, i=ruleLine.indexOf(FIELD_DELIMETER, i)).trim());
    dst_ip   = NetAddress.parseAddress(ruleLine.substring(++i, i=ruleLine.indexOf(FIELD_DELIMETER, i)).trim());
    dst_port = NetPort.parsePort(ruleLine.substring(++i, i=ruleLine.indexOf(FIELD_DELIMETER, i)).trim());
    action   = ruleLine.substring(++i).trim();
    anomaly  = ANOMALY_NONE;
  }
     
  @Override
  public String toString() {
    return (id + ": " + protocol + ", " + src_ip   + ", " + src_port + ", "
                      + dst_ip   + ", " + dst_port + ", " + action +  "");
  }
  
  public void setAnomaly(int aanomaly) {
    if ( anomaly == ANOMALY_NONE ||
        (anomaly == ANOMALY_GENERALIZATION && aanomaly == ANOMALY_SHADOWING) ||
        (anomaly == ANOMALY_CORRELATION && aanomaly == ANOMALY_SHADOWING) ||
        (anomaly == ANOMALY_GENERALIZATION && aanomaly == ANOMALY_REDUNDANCY) ||
        (anomaly == ANOMALY_CORRELATION && aanomaly == ANOMALY_REDUNDANCY)) {
          anomaly = aanomaly;
      }
  }
}

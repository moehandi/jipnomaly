package sistem.network;

public class NetPort {
  public int port;//public agar bisa diakses class lain yg beda package

  NetPort(String aPort) {
    port = Integer.parseInt(aPort);
  }

  public static NetPort parsePort(String aPort) {
    return new NetPort(aPort);
  }
    
  @Override
  public String toString() {
    return String.valueOf(port);
  }

  public boolean isEqual(NetPort netport) {
    if (port == netport.port) {
          return true;
      }
    return false;
  }

  public boolean isSubset(NetPort netport) {
    if (port != 0 && netport.port == 0) {
          return true;
      }
    return false;
  }

  public boolean isSuperset(NetPort netport) {
    if (port == 0 && netport.port != 0) {
          return true;
      }
    return false;
  }
}

package sistem.network;


public class NetAddress {
  String address;
  public int ip;//public agar bisa diakses dari class package berbeda
  public int mask;

  NetAddress(String aaddress) {
    String ips[] = new String[4];
    String msk;
    int start, end;
    address = aaddress;
    end = address.indexOf('.');
    
    ips[3] = address.substring(0, end);
    start = end+1; end = address.indexOf('.', end+1);
    
    ips[2] = address.substring(start, end);
    start = end+1; end = address.indexOf('.', end+1);
    
    ips[1] = address.substring(start, end);
    start = end+1; end = address.indexOf('/', end+1);
    
    ips[0] = address.substring(start, end);
    start = end+1;
    
    msk = address.substring(start);
    ip = 0;
    for (int i=3; i>=0; i--) {
      ip <<= 8;
      ip = ip | Integer.parseInt(ips[i]);
    }
    
    if (ip != 0 && Integer.parseInt(msk) == 32) {
          msk = "0";
    }
    
    if (ip == 0 && Integer.parseInt(msk) == 0){
      msk = "32";
    }
    
    mask = 0;
    for (int i=0; i< Integer.parseInt(msk); i++){
      mask = mask * 2 + 1;
    }
  }

  public static NetAddress parseAddress(String aaddress) {
    return new NetAddress(aaddress);
  }

  @Override
  public String toString() {
    return address;
  }

  public boolean isEqual(NetAddress netaddr) {
    if (mask == netaddr.mask && ip == netaddr.ip){
      return true;
    }
    return false;
  }

  public boolean isSubset(NetAddress netaddr) {
    if (ip != 0 && netaddr.ip == 0){
      return true;
    }
    if (mask < netaddr.mask){
      if ((ip & ~netaddr.mask) == (netaddr.ip & ~netaddr.mask)){
        return true;
      }
    }
    return false;
  }

  public boolean isSuperset(NetAddress netaddr) {
    if (ip == 0 && netaddr.ip != 0){
      return true;
    }
    if (mask > netaddr.mask){
      if ((ip & ~mask) == (netaddr.ip & ~mask)){
        return true;
      }
    }
    return false;
  }
}

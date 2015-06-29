package sistem;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sistem.database.DatabaseConnection;

/**
 *
 * @author moehandi
 */
public class RuleExecute {
    
    private String query ="";
    private Statement st;
    private DatabaseConnection dbConn;
    private ResultSet rs;
    
    int c;    
    
    public void grantExec(String pass, String interfaceIn, String interfaceOut) throws IOException, InterruptedException{
      dbConn = new DatabaseConnection();  
      dbConn.createConnection();
      try{
          //mengambil rule dari DB dengan desc krn rule yg pertama dimasukan akan berada pada order awal.tdk sbaliknya
          query = "SELECT orde, protocol, source_ip, source_port, dest_ip, dest_port, action FROM rule ORDER by orde DESC;";//ASC;";
          st = (Statement)dbConn.connection.createStatement();
          rs = st.executeQuery(query);
          
          while(rs.next()){
             String[] ruleData = {
                                  rs.getString(1).toString(),rs.getString(2).toString(),
                                  rs.getString(3).toString(),rs.getString(4).toString(),
                                  rs.getString(5).toString(),rs.getString(6).toString(),
                                  rs.getString(7).toString()
                                };

            String mypass = pass;
            String interIn = interfaceIn;
            String interOut = interfaceOut;
                
//          command execute UNIX shell, Action hrs UPPERCAST
//            String cmdExec = "echo "+mypass+"| sudo -S iptables -I FORWARD -p "+ruleData[1]+" -i "+interIn+" -o "+interOut+" -s "+ruleData[2]+" --sport "+ruleData[3]+" -d "+ruleData[4]+" --dport "+ruleData[5]+" -j "+ruleData[6].toUpperCase()+"";
            String cmdExec = "echo "+mypass+"| sudo -S iptables -I FORWARD -p "+ruleData[1]+" -i "+interIn+" -o "+interOut+" -s "+ruleData[2]+" -d "+ruleData[4]+" --dport "+ruleData[5]+" -j "+ruleData[6].toUpperCase()+"";
            ProcessBuilder pbExec = new ProcessBuilder("bash", "-c", cmdExec);// -c = create
            pbExec.redirectErrorStream(true); // digunakan untuk menangkap pesan dan kirim ke stderr
            Process shellExec = pbExec.start();
//            System.out.println(cmdExec);
//          InputStream shellExecIn = shellExec.getInputStream(); // menangkap output yg dihasilkan command shelExec
//          ProcessBuilder pbExecIn = new ProcessBuilder("bash", "-c", cmdExec);
//          pbExec.redirectErrorStream();
//          Process shell3 = pbExecIn.start();

            int shellExitStatus = shellExec.waitFor(); //menunggu shellFlush selesai baru dieksekusi cmd selanjutnya
//        
//            pada point ini, kamu dpt memproses output yg dihasilkan command (command exec saja yg flush tidak)
//            untk instance, ini akan membaca output dan menulisnya ke System.out.(write)
//            for instance, this reads the output and writes it to System.out:
//        
//            while ((c = shellExecIn.read()) != -1) {
//                System.out.write(c);
//           
//            }
//        
//            
//        // menutup stream
//            try {
//                shellExecIn.close();
//            }catch (IOException err) {
//            System.out.println("Error: "+err);
//            }
        
            }//close ehile rs.next
          
      }catch (SQLException se){
          se.printStackTrace();
      }
    }
    
    public void grantShow(String pass) throws IOException, InterruptedException{
        
        String myPass = pass;
        String cmdShow = "echo "+myPass+" | sudo -S iptables -nL";
        
        ProcessBuilder pbShow = new ProcessBuilder("bash", "-c", cmdShow);
        pbShow.redirectErrorStream(true);
        Process shellShow = pbShow.start();
        
        InputStream shellShowIn = shellShow.getInputStream();
        ProcessBuilder pbShowOut = new ProcessBuilder("bash", "-c", cmdShow);
        pbShow.redirectErrorStream();
        Process shellProc = pbShowOut.start();
        
        int shellExitStatus = shellShow.waitFor();
        
        int c;
        while((c = shellShowIn.read()) != -1){
            System.out.write(c);       
             
        }
        
        try{
            shellShowIn.close();
        }catch(IOException err){
            System.out.println("Error: "+err);
        }       
    }
    
    public void grantFlush(String pass) throws IOException, InterruptedException{
        
        String mypass = pass;
        
        String cmdFlush = "echo "+mypass+"| sudo -S iptables -F";
         
        // buat proses ke shell cmd1
        ProcessBuilder pbFlush = new ProcessBuilder("bash", "-c", cmdFlush);
        pbFlush.redirectErrorStream(true); // digunakan untuk menangkap pesan dan kirim ke stderr
        Process shellFlush = pbFlush.start();
    }
      
}

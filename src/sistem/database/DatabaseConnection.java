
package sistem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import sistem.ConfigurationProperties;

/**
 *
 * @author moehandi
 */
public class DatabaseConnection {
    public Connection connection;
    String message = "";

    public String getMessage() {
        return message;
    }
    
    public void createConnection(){
        
        ConfigurationProperties conf = new ConfigurationProperties();
        conf.loadProperties();
        String dbName = conf.getLoadDbMysql();
        String jdbcURL = conf.getLoadJdbcUrl();
        String userMysql = conf.getLoadUsrMysql();
        String passMysql = conf.getLoadPassMysql();
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            try{
                //String url = "jdbc:mysql://localhost:3306/iptables_db";
                //koneksi = DriverManager.getConnection(url, "root", "terminal");
                String url = jdbcURL;
                connection = DriverManager.getConnection(url, userMysql, passMysql);
                
                
            }catch (SQLException se){

                message = "Database "+dbName+" not found. restore your database."
                        + "\nor go to menu Setting > Options and specify your configurations.";
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.ERROR_MESSAGE);
                //System.exit(0);
            }
        }catch(ClassNotFoundException cnfe){
            message = "Class not Found. Error: "+cnfe;
            JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.ERROR_MESSAGE);
            //System.exit(0);
        }
    }

}

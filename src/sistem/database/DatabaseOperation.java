
package sistem.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import sistem.MainFrame;

/**
 *
 * @author moehandi
 */
public class DatabaseOperation {
    
    private DatabaseConnection dbConn;
    private String query ="";
    private MainFrame main;
        
    public boolean backupDB(String dbName, String dbUserName, String dbPassword, String path) {
        MainFrame main = new MainFrame();
        String executeCmd = "mysqldump -u " + dbUserName + " -p" + dbPassword + " --add-drop-database -B " + dbName + " -r " + path+"";

        Process runtimeProcess;
        try {

            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                System.out.println("Backup created successfully");

                 JOptionPane.showMessageDialog(null, "Database backup successfully created.");
                return true;
            } else {
                System.out.println("Could not create the backup.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
    //untuk menambah rule list dari sql file lain sbg alternatif add rule manual
    public boolean addRuleDB(String dbUserName, String dbPassword, String source){
        String[] executeCmd = new String[]{"mysql", "--user=" + dbUserName, "--password=" + dbPassword, "-e", "source "+source};

        Process runtimeProcess;
        try {

            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                System.out.println("Backup restored successfully");
                JOptionPane.showMessageDialog(null, "Database sucessfully restored");
                return true;
            } else {
                System.out.println("Could not restore the backup");
                JOptionPane.showMessageDialog(null, "Couldn't restore the backup file");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
    //restore db and its table
    public boolean restoreFullDB(String dbUserName, String dbPassword, String source) {

        String[] executeCmd = new String[]{"mysql", "--user=" + dbUserName, "--password=" + dbPassword, "-e", "source "+source};

        Process runtimeProcess;
        try {

            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                System.out.println("Backup restored successfully");
                JOptionPane.showMessageDialog(null, "Database sucessfully restored");
                return true;
            } else {
                System.out.println("Could not restore the backup");
                JOptionPane.showMessageDialog(null, "Couldn't restore the backup file");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    
    public void dropRuleContainOnly(){
        try {
            query = "delete from rule";
            PreparedStatement presttmt = dbConn.connection.prepareStatement(query);
            presttmt.executeUpdate(query);
                       
            JOptionPane.showMessageDialog(null, "Rule list in Database Table Deleted.");

        }catch (SQLException se) {
            JOptionPane.showMessageDialog(null,
                        "Terjadi kesalahan hapus data! error :" + se,
                        "Kesalahan", JOptionPane.ERROR_MESSAGE);
            //dispose();
        }
    }
    
    public void dropDbFull(String databaseName){
        
        try {              
            query = "drop database "+databaseName+"";
            PreparedStatement presttmt = dbConn.connection.prepareStatement(query);
            presttmt.executeUpdate(query);
                       
            JOptionPane.showMessageDialog(null, "Database full Deleted.");

        }catch (SQLException se) {
            JOptionPane.showMessageDialog(null,
                        "Terjadi kesalahan hapus data! error :" + se,
                        "Kesalahan", JOptionPane.ERROR_MESSAGE);
            //dispose();
        }
    }
}

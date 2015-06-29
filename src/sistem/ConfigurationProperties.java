/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author moehandi
 */
public class ConfigurationProperties {
    
    Properties prop = new Properties();
    String loadUsrLinux ;
    String loadPassLinux;
    String loadUsrMysql;
    String loadPassMysql;
    String loadDbMysql;
    String loadTableMysql;
    String loadJdbcUrl;
    String loadInterfaceIn;
    String loadInterfaceOut;

    public String getLoadInterfaceIn() {
        return loadInterfaceIn;
    }

    public void setLoadInterfaceIn(String loadInterfaceIn) {
        this.loadInterfaceIn = loadInterfaceIn;
    }

    public String getLoadInterfaceOut() {
        return loadInterfaceOut;
    }

    public void setLoadInterfaceOut(String loadInterfaceOut) {
        this.loadInterfaceOut = loadInterfaceOut;
    }

    public String getLoadUsrLinux() {
        return loadUsrLinux;
    }

    public String getLoadPassLinux() {
        return loadPassLinux;
    }

    public String getLoadUsrMysql() {
        return loadUsrMysql;
    }

    public String getLoadPassMysql() {
        return loadPassMysql;
    }

    public String getLoadDbMysql() {
        return loadDbMysql;
    }

    public String getLoadTableMysql() {
        return loadTableMysql;
    }

    public String getLoadJdbcUrl() {
        return loadJdbcUrl;
    }
    
    
    public void writeProperties(String userLinux, String pasLinux, 
                                    String usrMysql, String pasMysql, String dbMysql,
                                    String tableMysql, String jdbcUrl,String interfaceIn, String interfaceOut){
        
        
            
        try{
            //set properties value Linux
            prop.setProperty("userLinux", userLinux);
            prop.setProperty("passLinux", pasLinux);
            prop.setProperty("userMysql", usrMysql);
            prop.setProperty("passMysql", pasMysql);
            prop.setProperty("dbMysql", dbMysql);
            prop.setProperty("tableMysql", tableMysql);
            prop.setProperty("jdbcUrl", jdbcUrl);
            prop.setProperty("interfaceIn", interfaceIn);
            prop.setProperty("interfaceOut", interfaceOut);
            
            
            //save properties to project root folder
            prop.store(new FileOutputStream("config.properties"), null);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void loadProperties(){
        
        try{
            prop.load(new FileInputStream("config.properties"));
            loadUsrLinux = prop.getProperty("userLinux");
            loadPassLinux = prop.getProperty("passLinux");
            loadUsrMysql = prop.getProperty("userMysql");
            loadPassMysql = prop.getProperty("passMysql");
            loadDbMysql = prop.getProperty("dbMysql");
            loadTableMysql = prop.getProperty("tableMysql");
            loadJdbcUrl = prop.getProperty("jdbcUrl");
            loadInterfaceIn = prop.getProperty("interfaceIn");
            loadInterfaceOut = prop.getProperty("interfaceOut");
            
            
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        
    }
}

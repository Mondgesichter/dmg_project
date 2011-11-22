/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.dmg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Silvano Brugnoni <silvano.brugnoni@stud.hslu.ch>
 */
public class MSSQLDatabase implements Database {
    
    private String host;
    private String instance;
    private String database;
    private String user;
    private String password;
    private Connection dbcon = null;

    public MSSQLDatabase(String host, String instance, String database, String user, String password) {
        this.host = host;
        this.instance = instance;
        this.user = user;
        this.password = password;
    }
    
    @Override
    public void connect() {
        try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //zurückgeben der neuen Verbindung
        this.dbcon = DriverManager.getConnection("jdbc:sqlserver://"+host+"\\"+instance+";databaseName="+database+"\"", user, password);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    @Override
    public void close() {
        try {
            this.dbcon.close();
            this.dbcon = null;
        } catch (SQLException ex) {
            Logger.getLogger(MSSQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void order(Date date, int partId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void getPartId(String partName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

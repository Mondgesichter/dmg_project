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
 * Implementation of the Database interface version 1.
 * Allows the application to connect to mssql server engines
 * @author Silvano Brugnoni <silvano.brugnoni@stud.hslu.ch>
 */
public class MSSQLDatabase implements Database {
    
    private String host;
    private String instance;
    private String database;
    private String user;
    private String password;
    private Connection dbcon = null;
    private String name = "MSSQL";

    public MSSQLDatabase(String host, String instance, String database, String user, String password) {
        this.host = host;
        this.instance = instance;
        this.user = user;
        this.database = database;
        this.password = password;
    }
    
    @Override
    public void connect() {
        try {
            // load driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // establish connection
            this.dbcon = DriverManager.getConnection("jdbc:sqlserver://" + host + "\\" + instance + ";databaseName=" + database, user, password);
            System.out.println(this.name + ": connection to mssqlserver " + host + "\\" + instance + "\\" + database + " established");
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    @Override
    public void close() {
        try {
            this.dbcon.close();
            this.dbcon = null;
            System.out.println(this.name + ": connection to mssqlserver " + host + " closed");
        } catch (SQLException ex) {
            Logger.getLogger(MSSQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void order(Date date, int partId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getPartId(String partName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

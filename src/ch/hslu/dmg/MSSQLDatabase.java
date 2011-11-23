/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.dmg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
    // private String host;
    // private String instance;
    // private String database;
    // private String user;
    // private String password;
    private Connection dbcon = null;
    private String name = "MSSQL";

    public MSSQLDatabase() {
        // this.host = host;
        // this.instance = instance;
        // this.user = user;
        // this.database = database;
        // this.password = password;
    }
    
    @Override
    public boolean connect(String host, String instance, String database, String user, String password) {
        boolean valid = false;
        try {
            // load driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // establish connection
            this.dbcon = DriverManager.getConnection("jdbc:sqlserver://" + host + "\\" + instance + ";databaseName=" + database, user, password);
            System.out.println(this.name + ": connection to mssqlserver " + host + "\\" + instance + "\\" + database + " established");
            valid = dbcon.isValid(3);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
        return valid;
    }
    
    @Override
    public boolean close() {
        boolean closed = false;
        try {
            this.dbcon.close();
            closed = dbcon.isClosed();
            // this.dbcon = null;
            System.out.println(this.name + ": connection to mssqlserver closed");
        } catch (SQLException ex) {
            Logger.getLogger(MSSQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return closed;
    }

    @Override
    public void order(String year, String month, String day, int partId) {
        try {
            // exec procedure
            // if the stored procedure will be called multiple times, or requires only IN parameters, you can use the SQLServerPreparedStatement class.
            // @http://msdn.microsoft.com/en-us/library/ms378046(v=sql.90).aspx
            
            PreparedStatement pstmt = dbcon.prepareCall("{call sp_planstep( ?, ? ) }");
            pstmt.setString(1, year+"-"+month+"-"+day);
            pstmt.setInt(2, partId);
            //ResultSet rs = pstmt.executeQuery();
            pstmt.execute();
            System.out.println("Query executed");
        } catch (SQLException ex) {
            Logger.getLogger(MSSQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getPartId(String partName) {
        return 17;
    }
}

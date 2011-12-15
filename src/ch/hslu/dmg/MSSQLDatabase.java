/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.dmg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the Database interface version 1.
 * Allows the application to connect to mssql server engines
 * @author Silvano Brugnoni <silvano.brugnoni@stud.hslu.ch>
 */
public class MSSQLDatabase implements Database {
    
    // Attribute
    private Connection dbcon = null;
    private String name = "MSSQL";

    /**
     * Konstruktor tut nichts.
     */
    public MSSQLDatabase() {
    }
    
    /**
     * Verbindet zur spezifizierten DB, mit entsprechendem User.
     * Die Datenbank muss laufen (in meinem Fall ist das ein Windows-Dienst "SQL Server (DMG)")
     * @param host hostname oder ip-addresse vom DBServer
     * @param instance Name der Verbindung
     * @param database Name der Datenbank
     * @param user Username
     * @param password passendes PW
     * @return ob die Verbindung steht.
     */
    @Override
    public boolean connect(String host, String instance, String database, String user, String password) {
        boolean valid = false;
        try {
            // seit JDBC API 4.0 muss man den Treiber nicht mehr explizit laden!
            // er ist in der DM.getC Methode eingebunden
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // stellt eine Verbindung her
            // DM.getC gibt ein Connection-Objekt zurück
            this.dbcon = DriverManager.getConnection("jdbc:sqlserver://" + host + "\\" + instance + ";databaseName=" + database, user, password);
            
            // debug
            System.out.println(this.name + ": connection to mssqlserver " + host + "\\" + instance + "\\" + database + " established");
            
            // seit java 1.6
            // retourniert true, wenn die verbindung nicht geschlossen wurde und immernoch gültig ist.
            // die 3 steht für die Timeout-Zeit in Sekunden
            valid = dbcon.isValid(3);
        } catch (Exception ex) {
            Logger.getLogger(MSSQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valid;
    }
    
    /**
     * Schliesst die Verbindung
     * @return gibt true zurück, wenn die Verbindung geschlossen ist, ansonsten false.
     */
    @Override
    public boolean close() {
        boolean closed = false;
        try {
            // schliesst die Verbindung
            this.dbcon.close();
            // gibt zurück, ob die Verbindung tot ist.
            // diese Methode gab es schon vor 1.6
            closed = dbcon.isClosed();
            
            // debug
            System.out.println(this.name + ": connection to mssqlserver closed");
        } catch (SQLException ex) {
            Logger.getLogger(MSSQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return closed;
    }

    /**
     * Bestellt ein Teil. Hiermit wird eine Prozedur des Servers aufgerufen,
     * welche rekursiv überprüft, ob der Liefertermin gültig ist
     * @param year Jahr
     * @param month Monat
     * @param day Tag
     * @param partId Teilenummer.
     */
    @Override
    public void order(String year, String month, String day, int partId) {
        try {
            // if the stored procedure will be called multiple times, or requires only IN parameters, you can use the SQLServerPreparedStatement class.
            // @http://msdn.microsoft.com/en-us/library/ms378046(v=sql.90).aspx
            
            // sendet ein SQL statement zur DB, die Methode prepareCall gibt ein PreparedStatement zurück.
            PreparedStatement pstmt = dbcon.prepareCall("{call sp_planstep( ?, ? ) }");
            
            // diese Methode macht das gleiche, ist aber optimiert für Treiber, die "precompilation" unterstützen
            // das bedeutet, dass Teile des Aufrufes bereits vor pstmt.execute() kompiliert werden -> Effizienz.
            // klappt aber nur mit IN Parametern.
            // PreparedStatement pstmt = dbcon.prepareStatement("{call sp_planstep( ?, ? ) }");
            
            // nun werden die Parameter gesetzt (? werden durch Werte substituiert)
            pstmt.setString(1, year+"-"+month+"-"+day);
            pstmt.setInt(2, partId);
            
            // ..und das Statement wird entgültig abgeschickt.
            pstmt.execute();
            
            // debug
            System.out.println("Query executed");
        } catch (SQLException ex) {
            Logger.getLogger(MSSQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Anhand des Teilenamens wird die TeilNr geliefert.
     * DB bzw. Prozedur benötigt Nr.
     * @param partName Teilename
     * @return TeileNr
     */
    @Override
    public int getPartId(String partName) {
        // ACHTUNG:
        // Momentan wird nur immer die TeileNr 17 retouniert, unabhängig vom Teilenamen.
        return 17;
    }
}

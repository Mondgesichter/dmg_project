/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.dmg;

/**
 * Controller module
 * @author Silvano Brugnoni <silvano.brugnoni@stud.hslu.ch>
 */
public class Controller {
    
    private Database db;
    
    public Controller() {
        db = new MSSQLDatabase("localhost", "sqlexpress", "ddb", "ddb_user", "12345");
        db.connect();
    }
    
    public static void main(String[] args) {
        Controller control = new Controller();
    }
}

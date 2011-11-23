package ch.hslu.dmg;

import ch.hslu.dmg.ui.Gui;

/**
 * Controller module.
 * @version 1
 * @author Silvano Brugnoni <silvano.brugnoni@stud.hslu.ch>
 */
public class Controller {
    
    private Database db = null;
    
    public Controller() {
        db = new MSSQLDatabase();
    }
    
    public boolean connect(String host, String instance, String database, String user, String password) {
        return db.connect(host, instance, database, user, password);
    }
    
    public boolean close() {
        return db.close();
    }
    
    /**
     * Order a part. the part is finished one day before the given delivery Date.
     * The Partname gets resolved into the partNr. You dont have to worry about that.
     * @param date The delivery date
     * @param partName Name of the part to order
     * @param units How many units
     */
    public void order(String year, String month, String day, String partName, int units) {
        // db.connect();
        for (int i = 0; i < units; i++) {
            int partId = db.getPartId(partName);
            db.order(year, month, day, partId);
        }  
        // db.close();
    }
    
    public static void main(String[] args) {
        Controller control = new Controller();
        new Gui(control);
    }
}

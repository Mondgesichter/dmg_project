package ch.hslu.dmg;

/**
 * Controller module.
 * @version 1
 * @author Silvano Brugnoni <silvano.brugnoni@stud.hslu.ch>
 */
public class Controller {
    
    private Database db = null;
    
    public Controller() {
        db = new MSSQLDatabase("localhost", "sqlexpress", "dmg", "dmg_user", "12345");
    }
    
    /**
     * Order a part. the part is finished one day before the given delivery Date.
     * The Partname gets resolved into the partNr. You dont have to worry about that.
     * @param date The delivery date
     * @param partName Name of the part to order
     * @param units How many units
     */
    public void order(java.util.Date deliveryDate, String partName, int units) {
        db.connect();
        for (int i = 0; i < units; i++) {
            int partId = db.getPartId(partName);
            db.order(deliveryDate, partId);
        }  
        db.close();
    }
    
    public static void main(String[] args) {
        Controller control = new Controller();
    }
}

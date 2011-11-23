package ch.hslu.dmg;

/**
 * Database abstraction class.
 * @version 1
 * @author Silvano Brugnoni <silvano.brugnoni@stud.hslu.ch>
 */
public interface Database {
    
    boolean connect(String host, String instance, String database, String user, String password);
    
    boolean close();
    
    void order(String year, String month, String day, int partId);
    
    int getPartId(String partName);
    
}

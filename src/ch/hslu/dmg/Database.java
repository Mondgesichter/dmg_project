package ch.hslu.dmg;

/**
 * Database abstraction class.
 * @version 1
 * @author Silvano Brugnoni <silvano.brugnoni@stud.hslu.ch>
 */
public interface Database {
    
    void connect();
    void close();
    void order(java.util.Date date, int partId);
    void getPartId(String partName);
    
}

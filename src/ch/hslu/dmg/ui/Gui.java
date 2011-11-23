/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.dmg.ui;
import ch.hslu.dmg.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * It's just a small graphical interface for cosy use of the db functionality.
 * @author Simon
 */
public class Gui extends JFrame implements ActionListener {
    
    JPanel Oui;
    JPanel Cui;
    JPanel master;
    
    JTextField units;
    JTextField date;
    JTextField partN;
    JButton order;
    JButton close;
    
    JButton connect;
    JTextField dbName;
    JTextField user;
    JTextField pw;
    
    Controller control;
    
    public Gui(Controller control) {
        this.control = control;
        
        Cui = new JPanel();
        initCui();
        
        Oui = new JPanel();
        initOui();
        
        master = new JPanel();
        initGui(); 
    }
    
    private void initGui() {
        master.add(Cui);
        Cui.setVisible(false);
        
        master.add(Oui);
        Oui.setVisible(true);
        
        this.add(master);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("Fertigungsplanung");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
    }
    
    private void initCui() {
        dbName = new JTextField("DB-Name");
        dbName.setColumns(7);
        user = new JTextField("User");
        user.setColumns(10);
        pw = new JTextField("Password");
        pw.setColumns(10);
        connect = new JButton("connect");
        connect.addActionListener(this);
        
        Cui.add(dbName);
        Cui.add(user);
        Cui.add(pw);
        Cui.add(connect);
    }
    
    private void initOui() {
        partN = new JTextField("Partname");
        partN.setColumns(10);
        date = new JTextField("Date");
        date.setColumns(10);
        units = new JTextField("Units");
        units.setColumns(10);
        order = new JButton("order");
        close = new JButton("close");
        close.addActionListener(this);
        
        Oui.add(partN);
        Oui.add(date);
        Oui.add(units);
        Oui.add(order);
        Oui.add(close);
    }

    private void switchPanels() {
        Cui.setVisible(!Cui.isVisible());
        Oui.setVisible(!Oui.isVisible());
        this.pack();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connect) {
            // connect
            switchPanels();
        }
        if (e.getSource() == close) {
            // disconnect
            switchPanels();
        }
        if (e.getSource() == order) {
            control.order(new Date(Long.parseLong(date.getText())), partN.getText(), Integer.parseInt(units.getText()));
        }
    }
}

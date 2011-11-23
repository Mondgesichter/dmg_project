/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.dmg.ui;

import ch.hslu.dmg.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
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
    JTextField host;
    JTextField instance;
    JTextField units;
    JTextField year;
    JTextField month;
    JTextField day;
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
        Cui.setVisible(true);

        master.add(Oui);
        Oui.setVisible(false);

        this.add(master);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("Fertigungsplanung");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
    }

    private void initCui() {
        host = new JTextField("localhost");
        host.setColumns(7);
        instance = new JTextField("dmg");
        instance.setColumns(7);
        dbName = new JTextField("dmg");
        dbName.setColumns(7);
        user = new JTextField("dmg_user");
        user.setColumns(10);
        pw = new JTextField("12345");
        pw.setColumns(10);
        connect = new JButton("connect");
        connect.addActionListener(this);

        Cui.add(host);
        Cui.add(instance);
        Cui.add(dbName);
        Cui.add(user);
        Cui.add(pw);
        Cui.add(connect);
    }

    private void initOui() {
        partN = new JTextField("Partname");
        partN.setColumns(10);
        year = new JTextField("2011");
        year.setColumns(5);
        month = new JTextField("11");
        month.setColumns(5);
        day = new JTextField("25");
        day.setColumns(5);
        units = new JTextField("1");
        units.setColumns(10);
        order = new JButton("order");
        order.addActionListener(this);
        close = new JButton("close");
        close.addActionListener(this);

        Oui.add(partN);
        Oui.add(year);
        Oui.add(month);
        Oui.add(day);
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
            if (control.connect(host.getText(), instance.getText(), dbName.getText(), user.getText(), pw.getText())) {
                switchPanels();
            }
        }
        if (e.getSource() == close) {
            if(control.close()) {
                switchPanels();
            }
        }
        if (e.getSource() == order) {
            control.order(year.getText(), month.getText(), day.getText(), partN.getText(), Integer.parseInt(units.getText()));
        }
    }
}

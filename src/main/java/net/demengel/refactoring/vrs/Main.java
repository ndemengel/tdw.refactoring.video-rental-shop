package net.demengel.refactoring.vrs;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import net.demengel.refactoring.vrs.ui.AvailableMoviesView;

public class Main extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Main();
            }
        });
    }

    public Main() {
        super("Video Rental Shop");
        createTabs();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setVisible(true);
    }

    private void createTabs() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Available Movies", new AvailableMoviesView());
        tabbedPane.add("Customers", new JPanel());
        tabbedPane.add("Rented Movies", new JPanel());
        add(tabbedPane);
    }
}

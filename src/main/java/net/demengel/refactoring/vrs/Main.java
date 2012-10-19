package net.demengel.refactoring.vrs;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.demengel.refactoring.vrs.ui.AvailableMoviesPanel;
import net.demengel.refactoring.vrs.ui.CustomersPanel;
import net.demengel.refactoring.vrs.ui.RentedMoviesPanel;

public class Main extends JFrame {

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Main();
            }
        });
    }

    private static Main s_instance;

    public static Main getInstance() {
        return s_instance;
    }

    private Main() {
        super("Video Rental Shop");
        s_instance = this;
        setName("mainWindow");
        createTabs();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = getSize();
        if (windowSize.height > screenSize.height) {
            windowSize.height = screenSize.height;
        }
        if (windowSize.width > screenSize.width) {
            windowSize.width = screenSize.width;
        }
        setLocation((screenSize.width - windowSize.width) / 2, (screenSize.height - windowSize.height) / 2);

        setVisible(true);
    }

    private void createTabs() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Available Movies", new AvailableMoviesPanel());
        tabbedPane.add("Customers", new CustomersPanel());
        tabbedPane.add("Rented Movies", new RentedMoviesPanel());
        add(tabbedPane);
    }
}

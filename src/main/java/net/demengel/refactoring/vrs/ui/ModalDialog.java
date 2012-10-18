package net.demengel.refactoring.vrs.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

import net.demengel.refactoring.vrs.Main;

public abstract class ModalDialog extends JDialog {

    protected ModalDialog(String pTitle) {
        super(Main.getInstance(), pTitle);

        setModalityType(ModalityType.APPLICATION_MODAL);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "CLOSE");
        getRootPane().getActionMap().put("CLOSE", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    protected void center() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = getSize();
        if (windowSize.height > screenSize.height) {
            windowSize.height = screenSize.height;
        }
        if (windowSize.width > screenSize.width) {
            windowSize.width = screenSize.width;
        }
        setLocation((screenSize.width - windowSize.width) / 2, (screenSize.height - windowSize.height) / 2);
    }
}

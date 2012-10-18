package net.demengel.refactoring.vrs.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import net.demengel.refactoring.vrs.bean.Customer;
import net.demengel.refactoring.vrs.bean.Movie;
import net.demengel.refactoring.vrs.dao.MovieDao;
import net.demengel.refactoring.vrs.manager.RentingManager;
import net.demengel.refactoring.vrs.util.PriceUtils;

public class RentMoviesDialog extends ModalDialog {

    private Customer mSelectedCustomer;
    private AbstractTableModel m_model;
    private List<Movie> m_movies;

    public RentMoviesDialog(Customer pSelectedCustomer) {
        super("Rent Movies");
        mSelectedCustomer = pSelectedCustomer;

        setLayout(new BorderLayout());

        JPanel fieldPanel = new JPanel(new FlowLayout());
        fieldPanel.add(new JLabel("Movie Code:"));
        final JTextField textField = new JTextField();
        textField.setColumns(14);
        fieldPanel.add(textField);
        JButton button = new JButton("Add To Cart");
        ActionListener lActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent pArg0) {
                Movie lMovieByCode = MovieDao.getInstance().getMovieByCode(textField.getText());
                if (lMovieByCode == null) {
                    JOptionPane.showMessageDialog(RentMoviesDialog.this, "No movie found with code: " + textField.getText(), "Unknown movie",
                            JOptionPane.WARNING_MESSAGE);
                }
                else {
                    if (m_movies == null)
                        m_movies = new ArrayList<Movie>();
                    m_movies.add(lMovieByCode);
                    m_model.fireTableDataChanged();
                }
            }
        };
        textField.addActionListener(lActionListener);
        button.addActionListener(lActionListener);
        fieldPanel.add(button);
        add(fieldPanel, BorderLayout.NORTH);

        m_model = new AbstractTableModel() {

            @Override
            public String getColumnName(int pColumnIdx) {
                String name = "";
                if (pColumnIdx == 0) {
                    name = "Code";
                }
                else if (pColumnIdx == 1) {
                    name = "Title";
                }
                else if (pColumnIdx == 2) {
                    name = "Price";
                }
                return name;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Object value = null;
                if (rowIndex == m_movies.size()) {
                    switch (columnIndex) {
                    case 0:
                        value = "Total";
                        break;
                    case 2:
                        double total = 0;
                        for (int i = 0; i < m_movies.size(); i++) {
                            total += (Double) getValueAt(i, 2);
                        }
                        value = total;
                        break;
                    }
                }
                else {
                    switch (columnIndex) {
                    // Code
                    case 0:
                        value = m_movies.get(rowIndex).getCode();
                        break;
                    // Title
                    case 1:
                        value = m_movies.get(rowIndex).getTitle();
                        break;
                    // Price
                    case 2:
                        Movie lMovie = m_movies.get(rowIndex);
                        value = PriceUtils.getRental(lMovie, new Date());
                        break;
                    }
                }
                return value;
            }

            @Override
            public int getRowCount() {
                if (m_movies != null) {
                    return m_movies.size() + 1;
                }
                return 0;
            }

            @Override
            public int getColumnCount() {
                return 3;
            }
        };
        add(new JScrollPane(new JTable(m_model)), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton printButton = new JButton("Print provisional invoice");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent pArg0) {
                RentingManager.getInstance().printProvisionalInvoice(mSelectedCustomer, m_movies);
            }
        });
        bottomPanel.add(printButton);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        center();
    }

}

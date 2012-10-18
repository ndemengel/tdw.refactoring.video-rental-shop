package net.demengel.refactoring.vrs.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import net.demengel.refactoring.vrs.bean.Customer;
import net.demengel.refactoring.vrs.dao.CustomerDao;
import net.demengel.refactoring.vrs.helper.CustomerHelper;

public class CustomersView extends JPanel {

    private AbstractTableModel m_model;
    private List<Customer> m_customers;
    private String m_nameFilter = "";
    private String m_numberFilter = "";

    public CustomersView() {
        setLayout(new BorderLayout());

        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.add(new JLabel("Filter by..."));
        filterPanel.add(new JLabel("Number:"));
        createFilterField(filterPanel, true);
        filterPanel.add(new JLabel("Name:"));
        createFilterField(filterPanel, false);
        add(filterPanel, BorderLayout.NORTH);

        m_model = new AbstractTableModel() {

            @Override
            public String getColumnName(int pColumnIdx) {
                String name = "";
                if (pColumnIdx == 0) {
                    name = "Account #";
                }
                else if (pColumnIdx == 1) {
                    name = "Name";
                }
                else if (pColumnIdx == 2) {
                    name = "Rented Movies";
                }
                else if (pColumnIdx == 3) {
                    name = "Late Returns";
                }
                return name;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                switch (columnIndex) {
                case 0:
                    return m_customers.get(rowIndex).getAccountNumber();
                case 1:
                    return m_customers.get(rowIndex).getName();
                case 2:
                    return CustomerHelper.getInstance().getCurrentRentingsForCustomer(m_customers.get(rowIndex).getAccountNumber()).size();
                case 3:
                    return CustomerHelper.getInstance().getLateReturnsForCustomer(m_customers.get(rowIndex).getAccountNumber()).size();
                default:
                    // should never get there
                    return null;
                }
            }

            @Override
            public int getRowCount() {
                if (m_customers != null)
                    return m_customers.size();
                return 0;
            }

            @Override
            public int getColumnCount() {
                return 4;
            }
        };
        add(new JScrollPane(new JTable(m_model)), BorderLayout.CENTER);

        m_customers = CustomerDao.getInstance().listAllCustomers();
    }

    private void createFilterField(JPanel jPanel, final boolean filterByNumber) {
        final JTextField field = new JTextField();
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent pE) {
                if (filterByNumber) {
                    if (!field.getText().equals(m_numberFilter)) {
                        m_numberFilter = field.getText();
                        m_customers = CustomerDao.getInstance().findCustomersByAccountNumberStart(m_numberFilter);
                        m_model.fireTableDataChanged();
                    }
                } else {
                    if (!field.getText().equals(m_nameFilter)) {
                        m_nameFilter = field.getText();
                        m_customers = CustomerDao.getInstance().findCustomersByNameLike(m_nameFilter);
                        m_model.fireTableDataChanged();
                    }
                }
            }
        });
        field.setColumns(15);
        jPanel.add(field);
    }

}

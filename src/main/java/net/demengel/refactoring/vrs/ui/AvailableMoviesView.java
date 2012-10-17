package net.demengel.refactoring.vrs.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import net.demengel.refactoring.vrs.bean.Movie;
import net.demengel.refactoring.vrs.dao.MovieDao;
import net.demengel.refactoring.vrs.dao.ReferentialDao;
import net.demengel.refactoring.vrs.util.ReferentialProperties;

public class AvailableMoviesView extends JPanel {

    private AbstractTableModel m_model;
    private List mDisplayedMovies;
    private String m_filterString;

    public AvailableMoviesView() {
        setLayout(new BorderLayout());
        
        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.add(new JLabel("Filter:"));
        createFilterField(filterPanel);
        add(filterPanel, BorderLayout.NORTH);
        
        createTableModel();
        add(new JScrollPane(new JTable(m_model)), BorderLayout.CENTER);

        mDisplayedMovies = MovieDao.getInstance().listAllMovies();
    }

    private void createFilterField(JPanel jPanel) {
        final JTextField jTextField = new JTextField();
        jTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent pE) {
                if (!jTextField.getText().equals(m_filterString)) {
                    m_filterString = jTextField.getText();
                    
                    mDisplayedMovies = new ArrayList();

                    List movies = MovieDao.getInstance().listAllMovies();
                    for (int i = 0; i < movies.size(); i++) {
                        Movie movie = (Movie) movies.get(i);
                        if (movie.getTitle().toLowerCase().startsWith(m_filterString.toLowerCase()))
                            mDisplayedMovies.add(movie);
                    }
                 
                    m_model.fireTableDataChanged();
                }
            }
        });
        jTextField.setColumns(50);
        jPanel.add(jTextField);
    }

    private void createTableModel() {
        m_model = new AbstractTableModel() {

            @Override
            public String getColumnName(int pColumnIdx) {
                String name = "";
                if (pColumnIdx == 0) {
                    name = "Title";
                }
                else if (pColumnIdx == 1) {
                    name = "Release Year";
                }
                else if (pColumnIdx == 2) {
                    name = "Available Quantity";
                }
                if (pColumnIdx == 3) {
                    name = "Price";
                }
                if (pColumnIdx == 4) {
                    name = "Director";
                }
                return name;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Object value = null;
                switch (columnIndex) {
                case 0:
                    value = ((Movie) mDisplayedMovies.get(rowIndex)).getTitle();
                    break;
                case 1:
                    Calendar cal = Calendar.getInstance();
                    cal.setTime( ((Movie) mDisplayedMovies.get(rowIndex)).getReleaseDate());
                    value = cal.get(Calendar.YEAR);
                    break;
                case 2:
                    value = ((Movie) mDisplayedMovies.get(rowIndex)).getOwnedQuantity();
                    break;
                case 3:
                    Movie lMovie = (Movie) mDisplayedMovies.get(rowIndex);
                    value=lMovie.getForcedPrice();
                    if (value == null) {
                        if (new Date().getTime() - lMovie.getRentingStart().getTime() < 90 * 24 * 3600 * 1000) {
                            value = ReferentialDao.getInstance().get(ReferentialProperties.PRICE_FOR_MOVIES_NO_OLDER_THAN_3_MONTHS);
                        }
                        if (new Date().getTime() - lMovie.getRentingStart().getTime() < 365 * 24 * 3600 * 1000) {
                            value = ReferentialDao.getInstance().get(ReferentialProperties.PRICE_FOR_MOVIES_NO_OLDER_THAN_1_YEAR);
                        }
                        value = ReferentialDao.getInstance().get(ReferentialProperties.PRICE_FOR_MOVIES_OLDER_THAN_1_YEAR);
                    }
                    break;
                case 4:
                    value = ((Movie) mDisplayedMovies.get(rowIndex)).getDirector();
                    break;
                case 5:
                    StringBuffer sb = new StringBuffer();
                    for(Iterator castIterator = ((Movie) mDisplayedMovies.get(rowIndex)).getCast().iterator(); castIterator.hasNext(); ) {
                        if (sb.length() != 0)
                            sb.append(", ");
                        sb.append(castIterator.next());
                    }
                    value = sb.toString();
                    break;
                }
                return value;
            }

            @Override
            public int getRowCount() {
                if (mDisplayedMovies != null) {
                    return mDisplayedMovies.size();
                }
                return 0;
            }

            @Override
            public int getColumnCount() {
                return 6;
            }
        };
    }
}

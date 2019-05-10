/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Niek J Nijland
 */
    class PlannerScreenTableContent extends AbstractTableModel {
        private String[] columnNames = {" ",
                                        "Ordernummer",
                                        "Naam",
                                        "Adres",
                                        "Woonplaats"};
        private Object[][] data = new Object[][]{{false, "00000001", "Bosman henk", "straatnaam 5", "Zwolle"}, 
                                       {true, "00000010", "Ali B", "naamstraat 3", "Zwolle"},
                                       {false, "00000011", "v. van oorbeelddata", "huisweg 2", "Zwolle"},
                                       {false, "00000100", "korte naam", "slaaplaan 1", "Zwolle"},
                                       {false, "00000101", "naam achternaam", "huisweg 6", "Zwolle"}};

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col > 0) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
    }

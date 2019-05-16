/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TableSelectionDemoProject/src/components/TableSelectionDemo.java
 */
    class PlannerScreenTableContent extends AbstractTableModel {
        
        private String[] columnNames = {" ","Ordernummer","Naam","Adres","Woonplaats"};
        private Object[][] data;
        
        public PlannerScreenTableContent() {
            ArrayList<Order> OrderList = Order.getOrders();
            data = new Object[OrderList.size()][5];
            
            //get all orders from the database and add them to the data portion of the JTable
            for(int i = 0;i < OrderList.size();i++) {
                Order order = OrderList.get(i);
                Customer customer = order.getCustomer();
                data[i][0] = false;
                data[i][1] = order.getID();
                data[i][2] = customer.getCustomerName();
                data[i][3] = customer.getDeliveryAddressLine2();
                data[i][4] = customer.getCustomerCity();
            }
      }


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

        //disables editing of the table on any other column then the first one
        @Override
        public boolean isCellEditable(int row, int col) {
            return col <= 0;
        }

        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
        
    }

package kbs2;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class AdministratorScreen extends JFrame implements ActionListener, TableModelListener {

    private JTabbedPane JTPAdminTabs;
    private DefaultTableModel DTMOrders, DTMStock, DTMCustomers;
    private JTable JTStock, JTCustomers, JTOrders;
    private JFrame frame;
    private JButton JBLogout, JBFilter;
    private ArrayList<Product> products;
    private ArrayList<Customer> customers;
    private User user;
    private JTextField JTFDate;
    private LocalDate date = LocalDate.now();
    private Object[] columnCustomers, columnProducts, columnOrders;

    //get input for filterig order list
    //return: the date format selected
    public LocalDate getFormatText() {
        try {
            String[] yearAndMonth = JTFDate.getText().split("-");
            int year = Integer.parseInt(yearAndMonth[0]);
            int day = 1;
            int month = Integer.parseInt(yearAndMonth[1]);
            //check for missing 0
            if (yearAndMonth[1].length() > 1 && yearAndMonth[1].charAt(0) != 0 || yearAndMonth[1].charAt(0) != 1) {
                month = Integer.parseInt("0" + yearAndMonth[1]);
            }
            this.date = LocalDate.of(year, month, day);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datum incorect gespecificeerd. het gewenste format is: YYYY-MM.");
        } finally {
            return this.date;
        }
    }

    public AdministratorScreen(User ActiveUser) {
        frame = new JFrame();
        frame.setTitle("Systeembeheer");
        frame.setLayout(new FlowLayout());
        this.ActiveUser = ActiveUser;

        if (!user.getLevel().equals("Administrator")) {
            logout();
        }
        // fill products tabel
        columnProducts = new Object[]{"Product_ID", "Productnaam", "Voorraad", "Prijs p/s", "bewerken product"};
        Object[][] dataProducts = this.getStockRows();

        DTMStock = new DefaultTableModel(dataProducts, columnProducts);
        JTStock = new JTable(dataProducts, columnProducts);

        //allow sorting in tables
        JTStock.setRowSorter(new TableRowSorter<TableModel>(DTMStock));
        JTStock.setFillsViewportHeight(true);

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(JTStock.getModel());
        JTStock.setRowSorter(sorter);

        JScrollPane spProducts = new JScrollPane(JTStock);
        spProducts.setPreferredSize(new Dimension(775, 450));
        JPanel panelProducts = new JPanel();
        panelProducts.setLayout(new BorderLayout());
        panelProducts.add(spProducts);

        //Button for edititng products
        JTStock.getColumn("bewerken product").setCellRenderer(new ButtonRenderer());
        JTStock.getColumn("bewerken product").setCellEditor(new ButtonEditor(new JCheckBox()));
        JTStock.getModel().addTableModelListener(this);

        //fill customers table
        columnCustomers = new Object[]{"Klantnaam", "Adres", "Woonplaats", "Afleveradres", "bewerken klant"};
        Object[][] dataCustomers = this.getCustomerRows();

        DTMCustomers = new DefaultTableModel(dataCustomers, columnCustomers);
        JTCustomers = new JTable(dataCustomers, columnCustomers);

        //allow sorting in tables
        JTCustomers.setRowSorter(new TableRowSorter<TableModel>(DTMCustomers));
        JTCustomers.setFillsViewportHeight(true);

        TableRowSorter<TableModel> sorter2 = new TableRowSorter<TableModel>(JTCustomers.getModel());
        JTCustomers.setRowSorter(sorter2);

        JScrollPane spCustomers = new JScrollPane(JTCustomers);
        JPanel panelCustomers = new JPanel();
        panelCustomers.setLayout(new BorderLayout());
        panelCustomers.add(spCustomers);

        // Button for edititng customer
        JTCustomers.getColumn("bewerken klant").setCellRenderer(new ButtonRenderer());
        JTCustomers.getColumn("bewerken klant").setCellEditor(new ButtonEditor(new JCheckBox()));
        JTCustomers.getModel().addTableModelListener(this);

        // data in ORDERS tab
        columnOrders = new Object[]{"Ordernummer", "KlantID", "Klantnaam"};
        Object[][] dataOrders = this.getOrderRows();

        DTMOrders = new DefaultTableModel(dataOrders, columnOrders);
        JTOrders = new JTable(DTMOrders);

        //allow sorting in tables
        JTOrders.setRowSorter(new TableRowSorter<TableModel>(DTMOrders));
        JTOrders.setFillsViewportHeight(true);
      
        //set default current date
        String DateMonth = date.getYear() + "-" + date.getMonthValue();

        //create the textfield
        JTFDate = new JTextField(DateMonth, 20);

        //submit btn for date input
        JBFilter = new JButton("filteren");
        JBFilter.addActionListener(this);

        JScrollPane spOrders = new JScrollPane(JTOrders);
        JPanel panelOrders = new JPanel(new BorderLayout());
        JPanel panelOrdersFilter = new JPanel(new FlowLayout());

        panelOrdersFilter.add(JTFDate);
        panelOrdersFilter.add(JBFilter);

        panelOrders.add(panelOrdersFilter, BorderLayout.NORTH);
        panelOrders.add(spOrders, BorderLayout.CENTER);

        Panel p = new Panel();
        p.setLayout(new GridLayout(1, 5));
        p.setPreferredSize(new Dimension(800, 50));

        //labels at top
        JTPAdminTabs = new JTabbedPane();
        JTPAdminTabs.add("Producten", panelProducts);
        JTPAdminTabs.add("Klantgegevens", panelCustomers);
        JTPAdminTabs.add("Bestellingen", panelOrders);

        // LOGOUT button
        JBLogout = style.button("Uitloggen");
        JBLogout.addActionListener(this);

        p.add(new JLabel(""));
        p.add(new JLabel(""));
        p.add(new JLabel(""));
        p.add(new JLabel(""));
        p.add(JBLogout);

        frame.add(JTPAdminTabs);
        frame.add(p);

        frame.setResizable(false);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //creates the content for the tables
    //return: returns 2dimensional object array with products.
    private Object[][] getStockRows() {
        this.products = Product.getProducts();
        Object[][] rows = new Object[products.size()][6];
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            rows[i] = new Object[]{p.getID(), p.getName(), p.getStock(), p.getPricePerPiece(), columnProducts[columnProducts.length - 1]};
        }

        return rows;
    }

    //creates the content for the tables
    //return: returns 2dimensional object array with customers.
    private Object[][] getCustomerRows() {
        this.customers = Customer.getCustomers();
        Object[][] rows = new Object[customers.size()][6];
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            rows[i] = new Object[]{c.getCustomerName(), c.getDeliveryAddressLine2(), c.getDeliveryPostalCode(), c.getCustomerCity(), columnCustomers[columnCustomers.length - 1]};
        }

        return rows;
    }

    //creates the content for the tables
    //return: returns 2dimensional object array with orders.
    private Object[][] getOrderRows() {
        ArrayList<Order> orders = Order.getOrders(this.date);
        Object[][] rows = new Object[orders.size()][6];
        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            rows[i] = new Object[]{o.getID(), o.getCustomer().getID(), o.getCustomer().getCustomerName()};
        }

        return rows;
    }

    //logout for user and redirect to login
    public void logout() {
        this.user = null;
        frame.setVisible(false);
        frame.dispose();
        new LoginScreen();
    }

    @Override
    public void tableChanged(TableModelEvent tme) {
        //check if we have a click from a table
        if (this.JTCustomers.getEditingRow() >= 0) {    //customers table
            //get customer seen in the clicked row
            Customer customer = this.customers.get(this.JTCustomers.getEditingRow());
            //create the edit screen
            EditCustomer editCustomerDialog = new EditCustomer(this, customer);
            editCustomerDialog.setVisible(true);
            //update table
            this.DTMCustomers.setDataVector(this.getCustomerRows(), this.columnCustomers);

        } else if (this.JTStock.getEditingRow() >= 0) { //products table
            //get product seen in the clicked row
            Product product = this.products.get(this.JTStock.getEditingRow());
            //create the edit screen
            EditStock editStockDialog = new EditStock(this, product);
            editStockDialog.setVisible(true);
            //update table
            this.DTMStock.setDataVector(this.getStockRows(), this.columnProducts);
        }
    }

    private void logout(){
        ActiveUser = null;
        this.dispose();
        LoginScreen LS = new LoginScreen();
        LS.setVisible(true);
    }

        JTCustomers = new JTable(dataCustomers,columnCustomers);   
            JTCustomers.setFillsViewportHeight(true);

        JScrollPane spCustomers = new JScrollPane(JTCustomers); 
        JPanel panelCustomers = new JPanel();
            panelCustomers.setLayout(new BorderLayout());
            panelCustomers.add(spCustomers);

    // Button for EDIT 
        customers = new JCheckBox();

        JBedit = new JButton("Edit");
            JTCustomers.getColumn("edit").setCellRenderer(new ButtonRenderer());
            //JCheckBox checkBox = null;
            JTCustomers.getColumn("edit").setCellEditor(new ButtonEditor(customers, "customers"));   
   
    //    // data in ORDERS tab 

        String dataOrders[][] = { 
            {"1234", "443", "Klantnaam"}, 
            {"1234", "443", "Klantnaam"},
            {"1234", "443", "Klantnaam"},
            {"1234", "443", "Klantnaam"},
        }; 

        String columnOrders[] = {"Ordernummer", "KlantID", "Klantnaam"};

        JTOrders = new JTable(dataOrders,columnOrders);   
            JTOrders.setFillsViewportHeight(true);

        JScrollPane spOrders = new JScrollPane(JTOrders); 
        JPanel panelOrders = new JPanel();
            panelOrders.setLayout(new BorderLayout());
            panelOrders.add(spOrders);

        JTPAdminTabs = new JTabbedPane();
            JTPAdminTabs.add("Producten", panelProducts);
            JTPAdminTabs.add("Klantgegevens", panelCustomers);
            JTPAdminTabs.add("Ordegegevens", panelOrders);

        // LOGOUT button
        JBlogout = new JButton("Logout");

        frame.add(JTPAdminTabs);
        frame.add(JBlogout);
        frame.setSize(800,600);    
        frame.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void editStock() {
        System.out.println("Start dialog");
        EditStock dialoog = new EditStock(this);
        dialoog.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBLogout) {
            //logout
            logout();

        } else if (e.getSource() == JBFilter) {
            //update the date o filter on
            this.date = this.getFormatText();
            //update table
            this.DTMOrders.setDataVector(this.getOrderRows(), this.columnOrders);
        }
    }
}

package kbs2;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdministratorScreen extends JFrame implements ActionListener, TableModelListener {

    private JTabbedPane JTPAdminTabs;
    private JTable JTStock;
    private JTable JTCustomers;
    private JTable JTOrders;
    private JFrame frame;
    private JButton JBLogout;
    private JButton JBedit;
    private ButtonEditor button;
    private String buttonType;
    private ArrayList<Product> products;
    private ArrayList<Customer> customers;
    private User ActiveUser;

    public void setButton(String type) {
        this.buttonType = type;
    }

    public String getButton() {
        return buttonType;
    }

    public AdministratorScreen(User ActiveUser) {
        frame = new JFrame();
        frame.setTitle("Systeembeheer");
        frame.setLayout(new FlowLayout());
        this.ActiveUser = ActiveUser;

        // fill products tabel
        Object[][] dataProducts = this.getStockRows();

        String[] columnProducts = {"Product_ID", "Productnaam", "Voorraad", "Prijs p/s", "aanvinken product"};

        JTStock = new JTable(dataProducts, columnProducts);
        JTStock.setFillsViewportHeight(true);

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(JTStock.getModel());
        JTStock.setRowSorter(sorter);

        JScrollPane spProducts = new JScrollPane(JTStock);
        spProducts.setPreferredSize(new Dimension(775, 450));
        JPanel panelProducts = new JPanel();
        panelProducts.setLayout(new BorderLayout());
        panelProducts.add(spProducts);

        //Button for edititng products
        JTStock.getColumn("aanvinken product").setCellRenderer(new ButtonRenderer());
        JTStock.getColumn("aanvinken product").setCellEditor(new ButtonEditor(new JCheckBox()));
        JTStock.getModel().addTableModelListener(this);

        //fill customers table
        Object[][] dataCustomers = this.getCustomerRows();

        String[] columnCustomers = {"Voornaam", "Achternaam", "Adres", "Woonplaats", "Afleveradres", "aanvinken klant"};

        JTCustomers = new JTable(dataCustomers, columnCustomers);
        JTCustomers.setFillsViewportHeight(true);

        TableRowSorter<TableModel> sorter2 = new TableRowSorter<TableModel>(JTCustomers.getModel());
        JTCustomers.setRowSorter(sorter2);

        JScrollPane spCustomers = new JScrollPane(JTCustomers);
        JPanel panelCustomers = new JPanel();
        panelCustomers.setLayout(new BorderLayout());
        panelCustomers.add(spCustomers);

        // Button for edititng customer
        JTCustomers.getColumn("aanvinken klant").setCellRenderer(new ButtonRenderer());
        JTCustomers.getColumn("aanvinken klant").setCellEditor(new ButtonEditor(new JCheckBox()));
        JTCustomers.getModel().addTableModelListener(this);

        // data in ORDERS tab
        Object[][] dataOrders = this.getOrderRows();

        String[] columnOrders = {"Ordernummer", "KlantID", "Klantnaam"};

        JTOrders = new JTable(dataOrders, columnOrders);
        JTOrders.setFillsViewportHeight(true);

        TableRowSorter<TableModel> sorter3 = new TableRowSorter<TableModel>(JTOrders.getModel());
        JTOrders.setRowSorter(sorter3);

        JScrollPane spOrders = new JScrollPane(JTOrders);
        JPanel panelOrders = new JPanel();
        panelOrders.setLayout(new BorderLayout());
        panelOrders.add(spOrders);

        //labels at top
        JTPAdminTabs = new JTabbedPane();
        JTPAdminTabs.add("Producten", panelProducts);
        JTPAdminTabs.add("Klantgegevens", panelCustomers);
        JTPAdminTabs.add("Bestellingen", panelOrders);

        // LOGOUT button
        JBLogout = style.button("Uitloggen");
        JBLogout.addActionListener(this);

        frame.add(JTPAdminTabs);
        frame.add(JBLogout);
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
            rows[i] = new Object[]{p.getID(), p.getName(), p.getStock(), p.getPricePerPiece(), "aanvinken product"};
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
            rows[i] = new Object[]{c.getCustomerName(), c.getCustomerName(), c.getDeliveryAddressLine2(), c.getDeliveryPostalCode(), c.getCustomerCity(), "aanvinken klant"};
        }

        return rows;
    }

    //creates the content for the tables
    //return: returns 2dimensional object array with orders.
    //todo: alter the wuery maybe... now we only get 50..
    private Object[][] getOrderRows() {
        ArrayList<Order> orders = Order.getOrders();
        Object[][] rows = new Object[orders.size()][6];
        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            rows[i] = new Object[]{o.getID(), o.getCustomer().getID(), o.getCustomer().getCustomerName()};
        }

        return rows;
    }

    @Override
    public void tableChanged(TableModelEvent tme) {
        //check if we have a click from a table
        if (this.JTCustomers.getEditingRow() >= 0) {    //customers table
//            System.out.println("customers");
//            System.out.println(this.JTCustomers.getEditingRow());
//            System.out.println(this.customers.get(this.JTCustomers.getEditingRow()));
            Customer customer = this.customers.get(this.JTCustomers.getEditingRow());
            EditCustomer editCustomerDialog = new EditCustomer(this, customer);
            editCustomerDialog.setVisible(true);
            new AdministratorScreen(this.ActiveUser);
            dispose();  //not working
        } else if (this.JTStock.getEditingRow() >= 0) { //products table
//            System.out.println("products");
//            System.out.println(this.JTStock.getEditingRow());
//            System.out.println(this.products.get(this.JTStock.getEditingRow()));
            Product product = this.products.get(this.JTStock.getEditingRow());
            EditStock editStockDialog = new EditStock(this, product);
            editStockDialog.setVisible(true);
            new AdministratorScreen(this.ActiveUser);
            dispose();  //not working
        } else {    //anything else
//            System.out.println(tme);
//            System.out.println(this.JTCustomers.getEditingRow());
//            System.out.println(this.JTStock.getEditingRow());
//            System.out.println("something else is striggering");
        }

    }

    private void logout(){
        ActiveUser = null;
        this.dispose();
        LoginScreen LS = new LoginScreen();
        LS.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBLogout) {
            this.ActiveUser = null;
            new LoginScreen();
            dispose();
        } else if (e.getSource() == products) {
            System.out.println("Products button clicked");
        }
    }

}

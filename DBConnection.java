/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Niek J Nijland and Randy groot Roessink
 *
 * 1: https://dev.mysql.com/downloads/connector/j/ Download connector. stap 2:
 * select connector J to install. stap 3: rechtmuisknop netbeans project ->
 * properties -> libraries -> compile -> classpath -> + voeg nu de mysql jar
 * driver file toe. stap 3: in case of error [TIMEZONE] --> run "SET GLOBAL
 * time_zone = '+8:00';" in MySQL
 *
 */
//final for class and private for constructor wil force static use of this class.
public final class DBConnection {

    private static String host = "jdbc:mysql://localhost/wideworldimporters?useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static String username = "root";
    private static String password = "";
    protected static String statusMsg;

    //disables instantiation of this class
    private DBConnection() {
    }

    //returns a connection for the other static methods
    private static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DBConnection.host, DBConnection.username, DBConnection.password);
            return conn;

        } catch (ClassNotFoundException | SQLException ex) {
            DBConnection.statusMsg = ex.getMessage();
            return null;
        }

    }

    //executes select sql statement
    //param1: query to run
    //return: list of return rows with string values (empty list is no result)
    protected static ArrayList<ArrayList<String>> selectQuery(String selectQuery) {
        return DBConnection.selectQuery(selectQuery, new ArrayList<String>());
    }

    //executes select sql statement
    //param1: query to run
    //param2: prepared values to add (NOTE: strings only)
    //return: list of return rows with string values (empty list is no result)
    protected static ArrayList<ArrayList<String>> selectQuery(String selectQuery, ArrayList<String> stringsToSet) {
        //initializatie
        PreparedStatement statement = null; //for optional prepares
        Connection connection = null;
        ResultSet rs;
        //1 dmension for rows and second for colum values
        ArrayList<ArrayList<String>> resultSet = new ArrayList<>(new ArrayList<>());

        try {
            connection = DBConnection.getConnection();

            statement = connection.prepareStatement(selectQuery);
            //add bind prepared values to indexes
            for (int i = 0; i < stringsToSet.size(); i++) {
                statement.setString(i + 1, stringsToSet.get(i));
            }

            rs = statement.executeQuery();

            //get the amount of colums returned
            ResultSetMetaData rsmd = rs.getMetaData();

            //get each column of each row and save it(all strings)
            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();  //new row

                for (int colInt = 1; colInt <= rsmd.getColumnCount(); colInt++) {
                    row.add(rs.getString(rsmd.getColumnName(colInt)));  //add each value to new row
                }
                resultSet.add(row); //add row to list
            }

            //close all used objects
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            DBConnection.statusMsg = se.getMessage();
        } catch (Exception e) {
            //Handle errors for Class.forName
            DBConnection.statusMsg = e.getMessage();
        } finally {
            //try to close statement
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {
                // nothing todo
            }
            //try to close connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                DBConnection.statusMsg = se.getMessage();
            }//end finally try
        }//end try

        return resultSet;
    }

    //executes insert sql statement
    //param1: query to run
    //return: numer of rows it inserted (0 is none)
    protected static int insertQuery(String insertQuery) {
        return DBConnection.insertQuery(insertQuery, new ArrayList<String>());
    }

    //executes insert sql statement
    //param1: query to run
    //param2: prepared values to add (NOTE: strings only)
    //return: numer of rows it inserted (0 is none)
    protected static int insertQuery(String insertQuery, ArrayList<String> stringsToSet) {
        //initializatie
        PreparedStatement statement = null;
        Connection connection = null;
        int rs = 0;
        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(insertQuery);

            //add bind prepared values to indexes
            for (int i = 0; i < stringsToSet.size(); i++) {
                statement.setString(i + 1, stringsToSet.get(i));
            }

            rs = statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            DBConnection.statusMsg = se.getMessage();
        } catch (Exception e) {
            //Handle errors for Class.forName
            DBConnection.statusMsg = e.getMessage();
        } finally {
            //try to close statement
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {
                // nothing todo
            }
            //try to close connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                DBConnection.statusMsg = se.getMessage();
            }//end finally try
        }//end try

        return rs;
    }

    //param1: query to run
    //param2: prepared values to add (NOTE: strings only)
    //return: numer of rows it inserted (0 is none)
    protected static int updateQuery(String updateQuery) {
        return DBConnection.insertQuery(updateQuery, new ArrayList<String>());
    }

    //executes update sql statement
    //param1: query to run
    //param2: prepared values to add (NOTE: strings only)
    //return: numer of rows it updated (0 is none)
    protected static int updateQuery(String updateQuery, ArrayList<String> stringsToSet) {
        //initializatie
        PreparedStatement statement = null;
        Connection connection = null;
        int rs = 0;
        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(updateQuery);

            //add bind prepared values to indexes
            for (int i = 0; i < stringsToSet.size(); i++) {
                statement.setString(i + 1, stringsToSet.get(i));
            }

            rs = statement.executeUpdate();
            statement.close();
            connection.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            DBConnection.statusMsg = se.getMessage();
        } catch (Exception e) {
            //Handle errors for Class.forName
            DBConnection.statusMsg = e.getMessage();
        } finally {
            //try to close statement
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {
                // nothing todo
            }
            //try to close connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                DBConnection.statusMsg = se.getMessage();
            }//end finally try
        }//end try

        return rs;
    }

    //get the highest (id) number for the specified column
    //param1: name of table to use
    //param2: name of column to get max number from
    //return: max number +1 (new number to use default 1)
    protected static int getNewId(String TName, String IDField) {
        ArrayList<ArrayList<String>> rs = DBConnection.selectQuery("SELECT MAX(" + IDField + ") From " + TName, new ArrayList<String>());
        try {
            String newID = rs.get(0).get(0);    //get number value(2 dimensional array to string)
            //when none present return 1
            if (newID.equals("0") || newID.equals("null")) {
                return 1;
            }
            //return the new number
            try {
                return Integer.parseInt(newID) + 1;
            } catch (NumberFormatException ex) {
                return 1;
            }
            //check for any errors in the query
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("no id found: " + DBConnection.statusMsg);
            return 0;
        }
    }

    //test cases
//    public static void main(String[] args) {
//        //update test
//        ArrayList<String> prepares = new ArrayList<String>();
//        prepares.add("10");
//        int update = DBConnection.updateQuery("UPDATE customer SET latitude = ? WHERE CustomerID = 3", prepares);
//        if (update > 0) {
//            System.out.println("we updated the item.");
//        } else {
//            System.out.println(DBConnection.statusMsg); //check status(also error info)
//        }
//
//        //prepare for id example
//        ArrayList<String> prepares2 = new ArrayList<String>();
//        prepares2.add("" + DBConnection.getNewId("routes", "routeID")); //be careful there is a possibility for sql injection
//        prepares2.add("20");
//        //insert example
//        int insert = DBConnection.insertQuery("INSERT INTO routes (routeID, creationDate, distanceKM, driverID) VALUES(?, NOW(), ?, null)", prepares2);
//
//        if (insert > 0) {
//            System.out.println("we inserted the item.");
//        } else {
//            System.out.println(DBConnection.statusMsg); //check status(also error info)
//        }
//
//        //select example
//        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT CustomerID, CustomerName FROM customers");
//        for (int i = 0; i < rows.size(); i++) {
//            System.out.println(rows.get(i));
//        }
//    }
}

package kbs2;


import java.util.ArrayList;
import java.util.Map;

public class getCoordinates {


    private String[] address;
    private String ad;

    public getCoordinates(){
        this.address = getDbAddress();
        for (int i = 0;i < this.address.length; i++){
            System.out.println(this.address[i]);
        }
//        System.out.println(getAddress());
    }

    public String[] getDbAddress(){

//        System.out.println("1");
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT DeliveryAddressLine2 FROM customers WHERE CustomerID > 1055");
//        System.out.println("1");
        String[] result = new String[rows.size()];

        for(int i = 0; i < rows.size(); i++){
            ArrayList<String> row = rows.get(i);
//            System.out.println(row);
            this.ad = row.get(0);
//            System.out.println(ad);

            result[i] = lonlat(rows.get(i));

        }

        return result;
    }

    public String lonlat(ArrayList<String> buh){
        Map<String, Double> coords;
//        System.out.println("2");
        String ListString = "";

        for (String s: buh){
            ListString += s;
        }
//        System.out.println(ListString);
        coords = OpenStreetMapUtils.getInstance().getCoordinates(ListString);

        return("latitude :" + coords.get("lat"))+(" longitude:" + coords.get("lon"));
    }

    public String[] getAddress() {
        return address;
    }

    public static void main(String[] args) {
        String[] instance = new getCoordinates().getAddress();

    }
}

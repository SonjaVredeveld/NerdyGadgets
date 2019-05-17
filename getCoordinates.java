package kbs2;


import java.util.ArrayList;
import java.util.Map;

public class getCoordinates {

    // http://julien.gunnm.org/geek/programming/2015/09/13/how-to-get-geocoding-information-in-java-without-google-maps-api/
    // https://dzone.com/articles/google-maps-java-swing

    private String[] address;
    private String ad;
    private String CID;

    public getCoordinates(){
        this.address = getDbAddress();
        for (int i = 0;i < this.address.length; i++){
            System.out.println(this.address[i]);
        }
//        System.out.println(getAddress());
    }

    public String[] getDbAddress(){

//        System.out.println("1");
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT DeliveryAddressLine2, CustomerID FROM customers WHERE CustomerID > 1055");
//        System.out.println("1");
        String[] result = new String[rows.size()];

        for(int i = 0; i < rows.size(); i++){

            result[i] = lonlat(rows.get(i));

        }

        return result;
    }


    public String lonlat(ArrayList<String> AddressList){
        Map<String, Double> coords;
        String CustomerID = AddressList.get(1);
        coords = OpenStreetMapUtils.getInstance().getCoordinates(AddressList.get(0));

        int update = DBConnection.executeQuery("UPDATE customers SET latitude = "+coords.get("lat")+", longitude = "+coords.get("lon")+" WHERE CustomerID =  "+CustomerID+"");
        if (update > 0) {
            System.out.println("we updated the item.");
        } else {
            System.out.println(DBConnection.statusMsg); //check status(also error info)
        }

        return("latitude :" + coords.get("lat"))+(" longitude:" + coords.get("lon")+ ", " + CustomerID);
    }

    public String[] getAddress() {
        return address;
    }

    public static void main(String[] args) {
        String[] instance = new getCoordinates().getAddress();

//        UPDATE table_name
//        SET column1 = value1, column2 = value2, ...
//        WHERE condition;

    }
}

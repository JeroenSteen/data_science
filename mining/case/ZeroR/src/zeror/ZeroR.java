package zeror;

//Exceptions
import java.io.FileNotFoundException;
import java.io.IOException;

//Utils
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

//Libs
import com.csvreader.CsvReader;


public class ZeroR {

     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ZeroR obj = new ZeroR();
	obj.run();
        
    }
    
    public void run() {
        try {

            //Construct a CSV object
            CsvReader sales = new CsvReader("verkoopCount.csv");
            
            //Read the Headers
            sales.readHeaders();
            //Get all Headers
            //TODO CTRL + 6: Make it dynamic
            System.out.print(Arrays.toString(sales.getHeaders()));
                        
            ArrayList<Map> al = new ArrayList<Map>();
            
            //Read all Records
            while (sales.readRecord()) {
                
                //Find the columns..
                String department   = sales.get("department");
                String status       = sales.get("status");
                String age          = sales.get("age");
                String salary       = sales.get("salary");
                String count        = sales.get("count");
                //We found the columns..
                System.out.println(department +" "+ status +" "+ age +" "+ salary +" "+ count);
                
                //Make a map..
                Map m = new HashMap();
                m.put("department", department);
                m.put("department", status);
                m.put("age", age);
                m.put("salary", salary);
                m.put("count", count);
                //Place the map in our ArrayList..
                al.add(m);
                
            }

            //We have a Array with maps..
            System.out.print(al);
            //Close the constructed CSV object
            sales.close();

        } catch (FileNotFoundException e) {
            //File not found error
            e.printStackTrace();
        } catch (IOException e) {
            //Input and Output error
            e.printStackTrace();
        }

    }

}

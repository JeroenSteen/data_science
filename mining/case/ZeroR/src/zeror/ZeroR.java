package zeror;

//Exceptions
import java.io.FileNotFoundException;
import java.io.IOException;

//Utils
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
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
            //ArrayList header = sales.getHeaders();
            System.out.println(sales.getHeaders());
                        
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
                //System.out.println(department +" "+ status +" "+ age +" "+ salary +" "+ count);
                
                //Make a map..
                Map m = new HashMap();
                m.put("department", department);
                m.put("status", status);
                m.put("age", age);
                m.put("salary", salary);
                m.put("count", count);
                //Place the map in our ArrayList..
                al.add(m);
                
            }

            //We have a Array with maps..
            //System.out.print(al);
            
            double veelCounter     = 0;
            double weinigCounter   = 0;
            
            for (Map m : al) {
                //System.out.print(m.get("count"));
                
		if( m.get("count").equals("veel")) {
                    veelCounter++;
                } else if( m.get("count").equals("weinig")) {
                    weinigCounter++;
                }
            }
            
            System.out.println("veel: " + veelCounter);
            System.out.println("weinig: " + weinigCounter);
            
            //Confusion
            if(veelCounter > weinigCounter) {
                System.out.println("positive predictive value: " + veelCounter / (veelCounter + weinigCounter) );
            } else {
                System.out.println("positive predictive value: " + weinigCounter / (veelCounter + weinigCounter) );
            }
            
            //OneR
            //predictors
            ArrayList departments   = new ArrayList<String>();
            ArrayList statuses      = new ArrayList<String>();
            ArrayList ages          = new ArrayList<String>();
            ArrayList salaries      = new ArrayList<String>();
            ArrayList counts        = new ArrayList<String>();
            for (Map m : al) {
                departments.add(m.get("department"));
                statuses.add(m.get("status"));
                ages.add(m.get("age"));
                salaries.add(m.get("salary"));
                counts.add(m.get("count"));
            }
            System.out.println(Collections.frequency(departments, "sales"));
            System.out.println(Collections.frequency(departments, "systems"));
            System.out.println(Collections.frequency(departments, "marketing"));
            System.out.println(Collections.frequency(departments, "secretary"));
            //all factors
            //frequency of factor
            
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

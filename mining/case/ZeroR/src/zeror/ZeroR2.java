package zeror;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


public class ZeroR2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ZeroR obj = new ZeroR();
	obj.run();
        
    }
    
    public void run() {

	String csv              = "verkoopCount.csv";
	BufferedReader buffer   = null;
	String line             = "";
	String split            = ";";

        
	try {
            
            Map<String, ArrayList<String>> maps  = new HashMap<String, ArrayList<String>>();
            //Map<String, String> maps = new HashMap<String, String>();
            buffer                   = new BufferedReader(new FileReader(csv));
            
            int iterator            = 0;
            //Loop all Lines from CSV file
            while ((line = buffer.readLine()) != null) {

                //Use comma as separator
                String[] sales = line.split(split);
                              
                //Key and value pair
                /*maps.put("department", sales[0]);
                maps.put("status", sales[1]);
                maps.put("age", sales[2]);
                maps.put("salary", sales[3]);
                maps.put("count", sales[4]);*/
                
                ArrayList<String> l = new ArrayList<String>();
                l.add(sales[1]);
                l.add(sales[2]);
                l.add(sales[3]);
                l.add(sales[4]);
                
                maps.put(Integer.toString(iterator), l) ;
                
                iterator++;
            }
            
            //loop map
            for (Map.Entry<String, ArrayList<String>> entry : maps.entrySet()) {

                System.out.println(entry.getKey() + entry.getValue());
            }

	} catch (FileNotFoundException e) {
            e.printStackTrace();
	} catch (IOException e) {
            e.printStackTrace();
	} finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
	}

	//System.out.println(maps);
    }
    
}

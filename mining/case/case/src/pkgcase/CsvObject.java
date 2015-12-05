package pkgcase;

//Libs
import com.csvreader.CsvReader;

//Exceptions
import java.io.FileNotFoundException;
import java.io.IOException;

//Utils
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

public class CsvObject {
    
    public static String[] headers;
    public static String[] predictors;
    public static String target;
    public static ArrayList<String> targetValues;
    public static HashSet<String> targetFactors;
    public static ArrayList<Map> records;
    
    //Constructor for making the CSVObject
    public CsvObject(String csvPath) {
        
        try {
            
            //Construct a CSV object
            CsvReader csv = new CsvReader("verkoopCount.csv");
            
            //Read the headers
            csv.readHeaders();
            this.headers = csv.getHeaders();
            System.out.println("headers: "+Arrays.toString(this.headers));
            
            //Find predictors
            this.predictors = Arrays.copyOfRange(headers, 0, headers.length-1);
            
            //Find target column, last one
            this.target = headers[headers.length - 1];
            System.out.println("target: " + this.target);
            
            
            //Make records list
            this.records = new ArrayList<Map>();
            //Loop all csv records
            while (csv.readRecord()) {
                
                //Make a map
                Map m = new HashMap();
                
                //Find all headers              
                for(String h: this.headers) {             
                    //Make header with value
                    m.put(h, csv.get(h)); 
                }
                
                //Place map in records
                this.records.add(m);
            }
            System.out.println("records: "+this.records);
            
            
            this.targetValues = new ArrayList<String>();
            //Find target values; by finding his records
            for(Map t: this.records) {
                //Find target value of current record
                String tv = t.get(this.target).toString();
                this.targetValues.add(tv);
                //System.out.println("target value: "+tv);
            }
            //Find factors from target
            this.targetFactors = new HashSet<>(this.targetValues);
            
   
        } catch (FileNotFoundException e) {
            
            //File not found error
            e.printStackTrace();
            
        } catch (IOException e) {
            
            //Input and Output error
            e.printStackTrace();
            
        }
        
    }   
    
}

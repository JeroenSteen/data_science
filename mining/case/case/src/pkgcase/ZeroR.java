package pkgcase;

//Utils
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

public class ZeroR {

    public static String target;
    public static ArrayList<String> targetValues;
    public static HashSet<String> targetFactors;

    //Constructor for the ZeroR algorithm
    public ZeroR(CsvObject csv) {
        
        //Get headers from csv
        String[] headers = csv.headers;
        //Find target column, last one
        this.target         = csv.target;
        this.targetValues   = csv.targetValues;
        this.targetFactors  = csv.targetFactors;
        
        //Make a map to count the frequency per factor
        Map ff = new HashMap();
        //Count frequency of each factor
        for (String factorKey : this.targetValues) {
            //Find frequency of current factor
            int f = Collections.frequency(this.targetValues, factorKey);
            //Save frequency appearance for current factor
            ff.put(factorKey, f);
        }
        
        //Log frequency of factors
        System.out.println("frequency of factors: "+ff);
        
        //Find highest frequency
        int highestFrequency = (Integer)Collections.max(ff.values());
        System.out.println("highest frequency: "+highestFrequency);
        
        //Loop the frequency of factors    
        for (Object f : ff.keySet()) {
            if (ff.get(f).equals(highestFrequency)) {
                System.out.println("winner is: "+f);
            }
        }
        
        //Confusion matrix
        /*if(veelCounter > weinigCounter) {
            System.out.println("positive predictive value: " + veelCounter / (veelCounter + weinigCounter) );
        } else {
            System.out.println("positive predictive value: " + weinigCounter / (veelCounter + weinigCounter) );
        }*/
            
        
    }

}

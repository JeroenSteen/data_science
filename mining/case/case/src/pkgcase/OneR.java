package pkgcase;

//Utils
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class OneR {
    
    public static String target;
    public static ArrayList<String> targetValues;
    public static HashSet<String> targetFactors;
    
    //Constructor for the OneR algorithm
    public OneR(CsvObject csv) {
        
        //Get headers from csv
        String[] headers = csv.headers;
        //Find target column, last one
        this.target         = csv.target;
        this.targetValues   = csv.targetValues;
        this.targetFactors  = csv.targetFactors;

        //Construct predictors
        Map predictors      = new HashMap();
        
        //Find values; by finding his records
        for(Map r: csv.records) {
            //Loop predictor columns
            for (String h : headers) {
                //Only predictors here; ignore target
                if(h != this.target) {
                    
                    //Find current predictor value
                    String predictorValue           = r.get(h).toString();
                    
                    //Construct new predictor; for setting values
                    if(predictors.get(h) == null) {
                        
                        //Make new list for predictor
                        ArrayList<String> predictor     = new ArrayList<String>();
                        //Place value in predictor list
                        predictor.add(predictorValue);
                        //Place predictor to other predictors
                        predictors.put(h, predictor);
                    } else {
                        
                        //Predictor exists, find him
                        ArrayList<String> predictor     = (ArrayList)predictors.get(h);
                        //Place value in predictor list
                        predictor.add(predictorValue);
                        //Update predictor
                        predictors.put(h, predictor);
                    }

                }
            }
        }
                
        System.out.println("predictors: "+predictors);
        //Make a map to construct the frequency tables per predictor
        Map ft = new HashMap();
        //For each predictor
        for (Object p : predictors.keySet()) {
            //Find all values
            ArrayList<String> predictorValues   = (ArrayList)predictors.get(p);
            //Find factors from values
            HashSet<String> predictorFactors    = new HashSet<>(predictorValues);
            //Loop all factors
            //for(String f: factors) {
                //int valueFrequency = Collections.frequency(predictorValues, f);
                //System.out.println(f+" shows "+valueFrequency);
            //}
            
            //Make new predictor frequency table; for setting counting "factor" <=> "target factor" association
            Map fp = new HashMap();
            //Loop all records; for finding target value
            for(Map record: csv.records) {
                //Loop all desired target factors
                for(String predictorFactor: predictorFactors) {

                    for(String targetFactor: this.targetFactors) {
                        /*
                        //Found current target factor
                        if(predictorFactors.equals(tv)) {
                            //Find predictor value of current record
                            String pv = record.get(p).toString();

                            if(fp.get(tv) == null) {
                                fp.put(tv, p);
                            } else {
                                fp.get(tv);
                            }
                        }
                        */
                        
                        //Desired target and current count of predict factor; targetFactor, predictorFactor (veel, senior)
                        if(record.get(target).equals(targetFactor) && record.get(p).equals(predictorFactor)) {
                            //Never heard of predictor factor
                            if(fp.get(record.get(p)) == null) {
                                
                                //Never heard of target factor in predictor factor
                                Map fpt = new HashMap();
                                fpt.put(targetFactor, 1);
                                
                                fp.put(predictorFactor, fpt);
                            } else {
                                Map fpt = (Map)fp.get(predictorFactor);
                                
                                int tmpf = (int)fpt.get(predictorFactor);
                                                                
                                fp.put(predictorFactor, ++tmpf);
                            }
                        } else {
                            //System.out.println("No match");
                        }
                        //fp.put();
                    }
                    
                }                
                
            }
            //Place frequency table for predictor to other tables
            ft.put(p, fp);
            
          
        }
        
        System.out.println("frequency tables"+ft);
    }
    
}

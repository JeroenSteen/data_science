package pkgcase;

//Utils
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

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
        Map predictorErrors = new HashMap();
        
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
        System.out.println(" ");
        //Make a map to construct the frequency tables per predictor
        Map ft = new HashMap();
        //For each predictor
        for (Object p : predictors.keySet()) {
            //Find all values
            ArrayList<String> predictorValues   = (ArrayList)predictors.get(p);
            //Find factors from values
            HashSet<String> predictorFactors    = new HashSet<>(predictorValues);
            
            
            //Make new predictor frequency table; for setting counting "factor" <=> "target factor" association
            Map fp = new HashMap();
            //Loop all records; for finding target value
            for(Map record: csv.records) {
                //Loop all desired target factors
                for(String predictorFactor: predictorFactors) {

                    for(String targetFactor: this.targetFactors) {
                        
                        //Desired target and current count of predict factor; targetFactor, predictorFactor (veel, senior)
                        if(record.get(target).equals(targetFactor) && record.get(p).equals(predictorFactor)) {
                            
                            //Never heard of predictor factor
                            if(fp.get(predictorFactor) == null) {
                                
                                //Never heard of target factor in predictor factor
                                Map fpt = new HashMap();
                                fpt.put(targetFactor, 1);
                                
                                //Save predictor factor with target counter
                                fp.put(predictorFactor, fpt);
                            } else {
                                //Predictor factor exists..
                                Map fpt = (Map)fp.get(predictorFactor);
                                
                                //But, never heard of target counter
                                if(fpt.get(targetFactor) == null) {
                                    
                                    //Make a target counter
                                    fpt.put(targetFactor, 1);
                                    //Save predictor factor with target counter
                                    fp.put(predictorFactor, fpt);
                                    
                                } else {
                                    //We have a target counter
                                    int tmpt = (int)fpt.get(targetFactor);
                                    
                                    //Save target counter
                                    fpt.put(targetFactor, ++tmpt);
                                    //Save predictor factor with target counter
                                    fp.put(predictorFactor, fpt);
                                }
                                
                            }
                        } else {
                            //System.out.println("No match");
                        }

                    }
                    
                }                
            }
            
            //Loop all predictor factors; to set the "error"
            for(String f: predictorFactors) {
                //Find the frequency of the predictor factor
                double freqPredictorFactor     = Collections.frequency(predictorValues, f);
                                
                //Loop all target factors
                for(String targetF: targetFactors) {
                    //Find the predictor factor Map
                    Map association     = (Map)fp.get(f);
                    
                    try {
                        //Find the target value
                        double freqTarget      = (double)(int)association.get(targetF);
                        double errorOutcome    = freqTarget / freqPredictorFactor;
                        
                        //"Error" = association amount / predictor factor amount
                        //System.out.println(freqTarget +" / "+ freqPredictorFactor +" = " +errorOutcome);
                        
                        association.put(targetF, errorOutcome);
                        fp.put(f, association);
                        
                    } catch(NullPointerException e) {
                        //System.out.println("Empty not existing..");
                    }
                }
            }
            
            //Current frequency table
            System.out.println("frequency table "+p+": "+fp);
            float totalError = 0.0f;
            //Loop all factors..
            for (Object k : fp.keySet()) {
                //Factor of frequency table, with target values
                Map fpf = (Map)fp.get(k);
                                
                //Find the strongest factor association value, related to the target 
                double maxAss = (double)Collections.max(fpf.values());
                
                boolean pickedAss = false;
                //Find the strongest factor association winner
                for (Object fpfs : fpf.keySet()) {
                    double curAss = (double)fpf.get(fpfs);
                    //Value matches the strongest association
                    if(curAss == maxAss && pickedAss == false) {
                        totalError = (float)(totalError + curAss);
                        //Log  association winner
                        System.out.println(k+" => "+fpfs + "/" + curAss);
                        pickedAss = true;
                    }
                }
            }
            
            predictorErrors.put(p, totalError);
            System.out.println("total errors: "+totalError);            
            System.out.println(" ");
            
            //"Error" calculated, so.. place frequency table for predictor to others
            ft.put(p, fp);
        }

        //All frequency tables
        System.out.println("frequency tables: "+ft);
        //All total errors
        System.out.println("frequency tables: "+predictorErrors);
        System.out.println(" ");
        
        //Lowest error
        double minErr = (double)(float)Collections.min(predictorErrors.values());
        //Find the best predictor..
        for (Object predictorError : predictorErrors.keySet()) {
            double curErr = (double)(float)predictorErrors.get(predictorError);
            //Value matches the strongest association
            if(curErr == minErr) {
                //Log best predictor
                System.out.println("Best predictor: "+predictorError);
            }
        }
        
    }
    
}

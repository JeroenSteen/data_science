package pkgcase;

//Utils
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class StatisticalModeling {
    
    public static String[] headers;
    public static String target;
    public static ArrayList<String> targetValues;
    public static HashSet<String> targetFactors;
    public static Map targetCounts;
    public static Map targetProbs;
    
    //Constructor for Statistical Modeling
    public StatisticalModeling(CsvObject csv, Map sr) {
        
        //Get headers from csv
        this.headers        = csv.headers;
        //Find target column, last one
        this.target         = csv.target;
        this.targetValues   = csv.targetValues;
        this.targetFactors  = csv.targetFactors;
        
        Map predictors      = constructPredictors(csv);
        //Handle numeric values
        Map handledPredictors = handleNumerics(predictors, csv);
        
        Map counts          = countAssociations(predictors, csv);
        System.out.println("associations counts: "+counts);
        Map maxs            = countTargets(csv);
        this.targetCounts   = maxs;
        System.out.println("target counts: "+maxs);
        
        Map probs           = probAssociations(counts, this.targetCounts, csv); 
        System.out.println("associations probs: "+probs);
        System.out.println(" ");
        
        Map mprobs          = countTargetProbs(maxs);
        this.targetProbs    = mprobs;
        System.out.println("target probs: "+mprobs);
        
        //Statistical record to find his Target value
        System.out.println("statistical record: "+sr);
        Map al              = allLikelihoods(sr, probs);
        System.out.println("likelihoods: "+al);
        
    }
    
    public Map constructPredictors(CsvObject csv) {
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
        return predictors;
    }
    
    public Map handleNumerics(Map predictors, CsvObject csv) {
        /*
        * Numeric attibutes
        normalize atributes : gemiddelde en standaard afwijking berekenen variantie= (x- gemiddelde)^2/ (N-1) ; N=aantal
        standardlize = (x - minimum)/ (maximum - minimum); 
        */

        for (Object p : predictors.keySet()) {
            for(String targetFactor: this.targetFactors) {
                //System.out.println("size: "+csv.records.size());
                double[] targetHandles      = new double[csv.records.size()];
                int targetHandleIterator    = 0;
                for(Map r: csv.records) {
                    if(r.get(target).equals(targetFactor)) {
                        try {
                            //Is an integer..
                            int num = Integer.parseInt((String)r.get(p));
                            ++targetHandleIterator;
                            //System.out.println("handle iterator: "+targetHandleIterator);
                            
                            targetHandles[targetHandleIterator] = num;
                            //System.out.println("handle: "+r.get(p)+" "+targetFactor+", number");
                        } catch (NumberFormatException e) {
                            //Not an integer..
                        }
                    }
                }
                //System.out.println(p+" handles: "+Arrays.toString(targetHandles));
                System.out.println("mean of handles: "+mean(targetHandles));
                System.out.println("sd of handles: "+deviation(targetHandles));
                
            }
        }
        return predictors;
    }
    
    //Count from all Predictors their Factor vs Target Factor occurrences
    public Map countAssociations(Map predictors, CsvObject csv) {
        
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
            
            ft.put(p, fp);
        }
        
        return ft;
    }
    
    //Find the max frequency of all target factors
    public Map countTargets(CsvObject csv) {
        //Make a map for the max frequency for all target factors
        Map counts = new HashMap();
        //Find all target factors
        HashSet<String> targetFactors = this.targetFactors;
        
        //Loop all target factors
        for(String targetFactor: targetFactors) {
            //For each target factor, find each occurrence
            for(Map record: csv.records) {
                //Found desirable target factor
                if( targetFactor.equals(record.get(this.target)) ){
                    
                    //Target factor not counted earlier
                    if(counts.get(targetFactor) == null) {
                        counts.put(targetFactor, 1);
                    } else {
                        //Target factor is known
                        int tfreq = (int)counts.get(targetFactor);
                        //And we see you again, so add one..
                        counts.put(targetFactor, ++tfreq);
                    }
                    
                }        
            }
        }

        return counts;
    }
    
    //Find the probability for each target
    public Map countTargetProbs(Map targets) {
        float total = 0.0f;
        
        //Loop all target counts: Calculate total
        for (Object t : targets.keySet()) {
            //Found target count
            total = total + (float)(int)targets.get(t);
        }
        
        //Loop all target counts: Calculate prob per Target
        for (Object t : targets.keySet()) {
            targets.put(t, (float)(int)targets.get(t) / total);
        }
        
        return targets;
    }
    
    //Probability from all Predictors their Factor vs Target Factor occurrences
    public Map probAssociations(Map counts, Map maxs, CsvObject csv) {
        //Make a map for the probs
        Map probs = new HashMap();
        
        //Loop all target counts
        for (Object t : maxs.keySet()) {
            float max = (float)(int)maxs.get(t);
            System.out.println(max);
            
            //Loop all predictors..
            for (Object p : counts.keySet()) {
                //Find the predictor map
                Map pm = (Map)counts.get(p);

                //Loop all factors in predictor map
                for (Object f : pm.keySet()) {
                    //System.out.println("probs of target: "+t+", predictor: "+p+", factor: "+f);
                    
                    //Find the factor map
                    Map fm = (Map)pm.get(f);

                    //Backup association number
                    float n = 0;
                    if(fm.get(t) == null) {
                        n = 0;
                    } else {
                        //Find current association for: predictor factor with target factor
                        n = (int)fm.get(t);
                    }
                    
                    
                    //Prob = association number / max target factor
                    float probability = n / max;
                    
                    
                    //Find predictor in probs map
                    if(probs.get(p) == null) {
                        //Make a predictor map
                        Map probsP = new HashMap();
                        //Make a factor map
                        Map probsF = new HashMap();
                        //Place for target: probability
                        probsF.put(t, probability);
                        //Place factor map in predictor map
                        probsP.put(f, probsF);
                        
                        //Finally place predictor map in probs
                        probs.put(p, probsP);
                    } else {
                        //Predictor is known
                        Map probsP = (Map)probs.get(p);
                        
                        Map probsF;
                        //Factor is unknown
                        if(probsP.get(f) == null){
                            //Make a factor map
                            probsF = new HashMap();
                        } else {
                            //Get the factor map
                            probsF = (Map)probsP.get(f);
                        }
                        
                        //Place for target: probability
                        probsF.put(t, probability);
                        //Place factor map in predictor map
                        probsP.put(f, probsF);
                        
                        //Finally place predictor map in probs
                        probs.put(p, probsP);
                    }
                }     
            }
        }
         
        return probs;
    }
    
    public float laplaceEstimator(float t, float n) {
        //t = counter/teller
        //n = denominator/noemer
        //u = small constant
        float u = 1;
        
        return (t + u/3) / (n + u);
    }
    
    public Map allLikelihoods(Map sr, Map probs) {
        //Make a new map for all likelihoods
        Map al = new HashMap();
        
        //Find prob for each factor value from "new statistical record"
        for (Object f : sr.keySet()) {
            //Factor
            String factor   = (String)f;
            //Factor value
            String fvalue   = (String)sr.get(f);
            
            //Find all target factors
            HashSet<String> targetFactors = this.targetFactors;
            //Loop all target factors
            for(String targetFactor: targetFactors) {
                                
                //Loop all predictors
                for (Object ps : probs.keySet()) {
                    Map psm = (Map)probs.get(ps);
                    
                    //Loop all factors
                    for (Object fs : psm.keySet()) {
                        Map fsm = (Map)psm.get(fs);
                        
                        //System.out.println(fsm);
                        if(fsm.get(targetFactor) != null) {
                            float tsm = (float)fsm.get(targetFactor);
                            if(tsm == 0) {
                                float targetCount = (float)this.targetCounts.get(targetFactor);
                                tsm = this.laplaceEstimator(tsm, targetCount);
                            }
                            
                            //We found values that match the record
                            if(factor.equals(ps) && fvalue.equals(fs)) {
                                //So we found the prob
                                //System.out.println(factor+" "+fvalue+": "+targetFactor+": "+tsm);
                                //Current target is unknown
                                if(al.get(targetFactor) == null) {
                                    //System.out.println("Unknown");
                                    al.put(targetFactor, tsm);
                                } else {
                                    //System.out.println("Known");
                                    //Known..
                                    al.put(targetFactor, (float)al.get(targetFactor) * tsm);
                                }
                            }
                            
                        }
                    }
                }
            }
        }
        
        //Return likelihoods
        return al;
    }
    
    public static double mean(double[] m) {
        double sum = 0;
        for (int i = 0; i < m.length; i++) {
            sum += m[i];
        }
        return sum / m.length;
    }
    
    public static double deviation(double[] nums) {
	double mean = mean(nums);
	double squareSum = 0;

	for (int i = 0; i < nums.length; i++) {
		squareSum += Math.pow(nums[i] - mean, 2);
	}

	return Math.sqrt((squareSum) / (nums.length - 1));
    }
   
    
}

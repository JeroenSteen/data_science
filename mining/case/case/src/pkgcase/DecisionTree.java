
package pkgcase;

//Utils
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class DecisionTree {
    
    public String[] predictors;
    public String target;
    public HashSet<String> targetFactors;
    public HashMap associations;
    
    public DecisionTree(CsvObject csv) {
        predictors      = csv.predictors;
        target          = csv.target;
        targetFactors   = csv.targetFactors;
        
        
        //Construct map for associations
        ConstructAssociations(csv);
        System.out.println("associations: "+associations);
        
        //Calculate entropy of the target
        double targetEntropy = TargetEntropy(csv, "veel", "weinig");
        System.out.println("target entropy: "+targetEntropy);
        System.out.println(" ");
        
        //Construct map for all gains
        Map gs = new HashMap();
        //Loop all predictors
        for(String p: predictors) {
            System.out.println("predictor: "+p);
            
            //Entropy of predictor
            double e    = PredictorEntropy(p, "veel", "weinig");
            //Gain of predictor
            double g    = Gain(targetEntropy, e);
            System.out.println(" ");
            
            gs.put(p, g);
        }
        System.out.println("gains: "+gs);
        
        DecisionNode(gs);
        
    }
    
    public void ConstructAssociations(CsvObject csv) {
        associations    = new HashMap();
        
        //Loop all predictors
        for(String p: predictors) {
            //Construct predictor map
            Map pm  = new HashMap();
            //Place predictor map in associations
            associations.put(p, pm);
        }
        
        //Loop all predictors
        for(String p: predictors) {
            //Find predictor map
            Map pm  = (Map)associations.get(p);
            //Loop all target factors
            for(String t: targetFactors) {
                //Construct target map
                Map tm  = new HashMap();
                //Place target map in predictor map
                pm.put(t, tm);
                //Place predictor map in associations
                associations.put(pm, tm);
            }
        }
        
        //Loop all predictors
        for(String p: predictors) {
            //Find predictor map
            Map pm  = (Map)associations.get(p);
            //Loop all target factors
            for(String t: targetFactors) {
                Map tm = (Map)pm.get(t);
                //Loop all records
                for(Map r: csv.records) {
                    if(r.get(target).equals(t)) {
                        //New factor
                        if(tm.get(r.get(p)) == null) {
                            //Make factor key and numeric value
                            tm.put(r.get(p), (double)1);
                        } else {
                            //Factor exists
                            double factorFreq = (double)tm.get(r.get(p));
                            tm.put(r.get(p), ++factorFreq);
                        }
                        //Save all maps in associations
                        pm.put(t, tm);
                        associations.put(p, pm);
                    }
                }
            }
        }
    }
    
    //Probability is the measure of the likeliness that an event will occur.
    public double Prob(double y, double n, String t) {
        //Calculate probability of yes
        if(t.equals("y")) return (double) y / (y+n);
        //Calculate probability of no
        if(t.equals("n")) return (double) n / (y+n);
        //Default probability
        return (double)0;
    }
    
    public double TargetEntropy(CsvObject csv, String ty, String tn) {
        //Frequency of yes and no
        double y = (double)0;
        double n = (double)0;
        //Loop all records
        for(Map r: csv.records) {
            //Loop all target factors
            for(String t: targetFactors) {
                //Current target factor
                String tv = (String)r.get(target);
                try {
                    //Current target factor is desired, and is possitive
                    if(tv.equals(t) && tv.equals(ty)) ++y;
                    //Current target factor is desired, and is negative
                    if(tv.equals(t) && tv.equals(tn)) ++n;
                } catch(Exception e){
                    //System.out.println("NaN");
                }
            }
        }
        return Entropy(y, n);
    }
    
    //Entropy H(S) is a measure of the amount of uncertainty in the (data) set S (i.e. entropy characterizes the (data) set S).
    public double Entropy(double y, double n) {
        double sum      = y + n;
        double logy     = Log( Prob(y,n,"y"), 2); 
        double logn     = Log( Prob(y,n,"n"), 2); 
        
        //Calculate the unpureness
        double e = 1/(sum) * (-y * logy -n * logn);
        //Entropy shows that it's pure
        if((String.valueOf(e).equals("NaN"))) e = (double)0;

        return e;
    }
    
    //Entropy of target and predictor associations
    public double PredictorEntropy(String p, String ty, String tn) {
        //Find predictor in associations map
        Map pm = (Map)associations.get(p);
        
        //Construct map for factors
        HashSet factors = new HashSet();
        
        //Find target values
        Map possitives  = (Map)pm.get(ty);   
        Map negatives   = (Map)pm.get(tn);
        
        //Find total sum
        double totalsum = (double)0;      
        //Loop all target factors
        for(String t: targetFactors) {
            //Find target map
            Map tm = (Map)pm.get(t);
            //Merge to make factors complete
            factors.addAll(tm.keySet());
            //Loop all factors
            for(Object f: tm.keySet()){
                totalsum = totalsum + (double)tm.get(f);
            }
        }
        
        //Entropy sum
        double entropysum = (double)0;
        //Loop all factors to calculate each of his probability * entropy
        for(Object f: factors) {
            double possitive    = (double)0;
            double negative     = (double)0;
            
            try {
                possitive    = (double)possitives.get(f);
            } catch (Exception e) {
                //System.out.println("No value for "+f); 
            }
            try {
                negative    = (double)negatives.get(f);
            } catch (Exception e) {
                //System.out.println("No value for "+f);
            }
            
            double sum          = possitive + negative;
            double prob         = sum  / totalsum;
            double entropy      = Entropy((double)possitive,(double)negative);
            double e            = prob * entropy;
            entropysum          = entropysum + e;
            
            System.out.println("factor: "+f+" possitive: "+possitive+" negative: "+negative+" entropy: "+entropy);
        }
        
        return entropysum;
    }
    
    //Information gain IG(A) is the measure of the difference in entropy from before to after the set S is split on an attribute A.
    //In other words, how much uncertainty in S was reduced after splitting set S on attribute A.
    public double Gain(double targetEntropy, double newEntropy) {
        //Target entropy - new entropy
        double gain = targetEntropy - newEntropy;
        System.out.println("gain: "+targetEntropy+" - "+newEntropy+" = "+gain);
        
        return gain;
    }
    
    //Find the best decision node
    public void DecisionNode(Map gains) {
        //Find the highest uncertainty reducement
        double reducement = (double)Collections.max(gains.values());
        
        //Loop all gains    
        for (Object g : gains.keySet()) {
            if (gains.get(g).equals(reducement)) {
                System.out.println("decision node: "+g);
            }
        }
    }
    
    //The logarithm of a number is the exponent to which another fixed value, the base, must be raised to produce that number.
    public double Log(double x, double base) {
        return (double) (Math.log(x) / Math.log(base));
    }
    
    
}

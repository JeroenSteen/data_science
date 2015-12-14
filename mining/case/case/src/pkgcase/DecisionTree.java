package pkgcase;
import java.util.Map;


public class DecisionTree {
    
    public String[] predictors;
    
    public DecisionTree(CsvObject csv) {
        predictors = csv.predictors;
        
        for(String p: predictors) {
            for(Map r: csv.records) {
                System.out.println(r.get(p));
            }
        } 
    }
    
    //Probability is the measure of the likeliness that an event will occur.
    public double Prob(double y, double n, String t) {
        //Calculate probability of yes
        if(t.equals("y")) return (double) y / (y+n);
        //Calculate probability of no
        if(t.equals("n")) return (double) y / (y+n);
        //Default probability
        return (double)0;
    }
    
    //Entropy H(S) is a measure of the amount of uncertainty in the (data) set S (i.e. entropy characterizes the (data) set S).
    public double Entropy(double y, double n) {
        double sum      = y + n;
        double logy     = Log( Prob(y,n,"y"), 2); 
        double logn     = Log( Prob(y,n,"n"), 2); 
        
        return 1/(sum) * (-y * logy -n * logn);
    }
    
    //Entropy of target and predictor associations
    public double PredictorEntropy() {
        //Foreach attribute
        //Probability of target and attribute association
        //Entropy of target and attribute association
        
        //Attribute sum of all probability * entropy
        
        //Default entropy
        return (double)0;
    }
    
    //Information gain IG(A) is the measure of the difference in entropy from before to after the set S is split on an attribute A.
    //In other words, how much uncertainty in S was reduced after splitting set S on attribute A.
    public double Gain() {
        return Entropy((double)3, (double)3) - Entropy((double)3, (double)3);
    }
    
    //The logarithm of a number is the exponent to which another fixed value, the base, must be raised to produce that number.
    public double Log(double x, double base) {
        return (double) (Math.log(x) / Math.log(base));
    }
    
    
}

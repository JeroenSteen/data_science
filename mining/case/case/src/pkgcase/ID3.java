
package pkgcase;

//Utils
import java.util.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import java.lang.Object;

public class ID3 {
    
    public CsvObject csvObj;
    public String[] headers;
    public String[] predictors;
    public String target;
    public Map<String, ArrayList> headerValues;
    public Map<String, HashSet<String>> headerFactors;
    public HashSet<String> targetFactors;
    public HashMap associations;
    
    //Find all predictors
    //Find all (target)factors
    //Find per factor associations with each target factor
    //Find per factor sum of associations of all target factors
    //Find per predictor from all factors sum of associations of all target factors
    //Find the info value per factor
    
    //Constructor for ID3 Decision tree algorithm
    public ID3(CsvObject csv) {
        csvObj          = csv;
        headers         = csv.headers; 
        predictors      = csv.predictors;
        target          = csv.target;
        headerValues    = new HashMap();
        headerFactors   = new HashMap();
        targetFactors   = csv.targetFactors;
        associations    = new HashMap();
        
        
        setHeadersValues();
        setHeadersFactors();
        setAssociations();
        setInfo(associations);
    }
    
    //Set values for all Headers
    public void setHeadersValues() {
        //Loop all headers
        for(String h: headers) {
            //Loop all records
            for(Map r: csvObj.records) {
                //Header does not exists
                if(headerValues.get(h) == null) {
                    //Make ArrayList for current header
                    ArrayList ha = new ArrayList();
                    //Add current record in ArrayList
                    ha.add(r.get(h));                 
                    //Save map for current header
                    headerValues.put(h, ha);
                } else {
                    //Header exists
                    ArrayList ha = (ArrayList)headerValues.get(h);
                    //Add current record in ArrayList
                    ha.add(r.get(h));
                    //Save map for current header
                    headerValues.put(h, ha);
                } 
            }
        }
        //System.out.println(headerValues);
    }
    
    //Set factors for all Headers
    public void setHeadersFactors() {
        //Loop all header Values
        for (Map.Entry<String, ArrayList> h : headerValues.entrySet()) {
            //Header name
            String key              = h.getKey();
            //Header values
            ArrayList ha            = h.getValue();
            //Find unique values from the ArrayList
            HashSet<String> hs      = new HashSet<>(ha);
            //Save the factors for current header
            headerFactors.put(key, hs);
        }
        //System.out.println(headerFactors);
    }
    
    public ArrayList getHeaderValues(String header) {
        return headerValues.get(header);
    }
    public HashSet<String> getHeaderFactors(String header) {
        return headerFactors.get(header);
    }
    
    public void setAssociations() {
        //Find target factors
        HashSet<String> tfs = getHeaderFactors(target);
        //Loop all predictors
        for(String p : predictors) {
            //Find factors of current predictor
            HashSet<String> pfs = getHeaderFactors(p);
            //Loop all predictor factors
            for(String pf: pfs) {
               //Loop all target factors
                for(String tf: tfs) {
                    //Loop all records
                    for(Map r: csvObj.records) {
                        //Match for current association
                        if(r.get(target).equals(tf) && r.get(p).equals(pf)) {
                            
                            //Construct: predict => factor => target factor => association
                            
                            //Predictors does not exist in associations map
                            if(associations.get(p) == null){
                                //Construct predictor map
                                Map pm  = new HashMap();
                                //Construct current factor map
                                Map fm  = new HashMap();
                                //Save current target factor association
                                fm.put(tf, 1f);
                                //Save factor map in predictor map
                                pm.put(pf, fm);
                                //Save predictor map
                                associations.put(p, pm);
                            } else {
                                //Predictors exists in associations map
                                Map pm  = (Map)associations.get(p);
                                
                                Map fm;
                                //Factor map does not exist
                                if(pm.get(pf) == null) {
                                    //Construct current factor map
                                    fm  = new HashMap();
                                    //Save current target factor association
                                    fm.put(tf, 1f);
                                } else {
                                    //Find factor map
                                    fm = (Map)pm.get(pf);
                                    //Target association does not exist
                                    if(fm.get(tf) == null) {
                                        //Save current target factor association
                                        fm.put(tf, 1f);
                                    } else {
                                        //Target factor association exists
                                        float tv = (float)fm.get(tf);
                                        //Update target factor association
                                        fm.put(tf, ++tv);
                                    }
                                }
                                
                                //Save factor map in predictor map
                                pm.put(pf, fm);
                                //Save predictor map
                                associations.put(p, pm);
                            }        
                        }
                    }
                }
            }
        }
        System.out.println("associations: "+associations);
    }
    
    public HashMap cloneAssociations() {
        HashMap clone = new HashMap();
        HashSet<String> targetFactors = getHeaderFactors(target);
        
        //Find predictors
        for (String p : predictors) {
            HashSet<String> pfs = getHeaderFactors(p);
            Map am = (Map)associations.get(p);
            //Loop predictor factors
            for (String pf : pfs) {
                Map fm = (Map)am.get(pf);
                //Loop target factors
                for (String t : targetFactors) {
                    if(fm.get(t) != null) {
                        float tm = (float)fm.get(t);
                        
                        //Predictor does not exist in clone
                        if(clone.get(p) == null) {
                            //Make new predictor map for clone
                            Map pc = new HashMap();
                            Map fc = new HashMap();

                            pc.put(pf, fc);
                            fc.put(t, tm);
                            //Put new predictor map in clone
                            clone.put(p, pc);
                        } else {
                            Map pc = (Map)clone.get(p);
                            if(pc.get(pf) == null) {
                                Map fc = new HashMap();
                                fc.put(t, tm);
                                
                                pc.put(pf, fc);
                                clone.put(p, pc);
                            } else {
                                Map fc = (Map)pc.get(pf);
                                fc.put(t, tm);
                                //Put new predictor map in clone
                                clone.put(p, pc);
                            }
                        } 
                    }
                }
            }
        }
        return clone;
    }
    
    public void setInfo(HashMap associations) {
        /*
        Sum factor:
        predictor => factor => sum per target factor associations
        */
        HashMap sumPerFactorAssociations = cloneAssociations();
        //sumPerFactorAssociations = (HashMap)associations.clone();
        //sumPerFactorAssociations.putAll(associations);
        //sumPerFactorAssociations = associations;
        //System.out.println("associations copy: "+sumPerFactorAssociations);
        
        //Loop all predictors
        for (String p : predictors) {
            //Find current header factors
            HashSet<String> headerFactors = getHeaderFactors(p);
            //Loop all header factors
            for (String pf : headerFactors) {
                //Find predictor map
                Map pm = (Map)associations.get(p);
                //Find current factor
                Map fm = (Map)pm.get(pf);
                //Sum values of current factor
                float fsum = 0.0f;
                //Loop all target factor values
                for (Object tv : fm.values()) {
                    fsum = fsum + (float)tv;
                }
                
                //Predictor in sum per factor associations map does not exist
                Map sumP    = (Map)sumPerFactorAssociations.get(p);
                Map sumF    = new HashMap();
                sumF.put("sum", fsum);
                sumP.put(pf, sumF);
                
                //Update
                sumPerFactorAssociations.put(p, sumP);
            }
        }
        //System.out.println("sum per factor associations: "+sumPerFactorAssociations);
        
        /*
        Sum factor:
        predictor => factor => total sum of all target factor associations
        */
        Map sumAllFactorAssociations = new HashMap();
        //Loop all predictors
        for (String p : predictors) {
            float psum = 0.0f;
            //Find predictor factors
            HashSet<String> predictorFactors = getHeaderFactors(p);
            //Loop all predictor factors
            for (String pf : predictorFactors) {
                Map sumP = (Map)sumPerFactorAssociations.get(p);
                Map sumF = (Map)sumP.get(pf);
                psum = psum + (float)sumF.get("sum");
            }
            sumAllFactorAssociations.put(p, psum);
        }
        //System.out.println("sum of all factor associations: "+sumAllFactorAssociations);
        
        /*
        Calculate info, per predictor:
        pfa         = per predictor factor association with each target factor      (associations)
        sumpfa      = per predictor factor association with all target factors      (sumPerFactorAssociations)
        sumpfas     = all predictor factors associations with all target factors    (sumAllFactorAssociations)
        
        Example:
        sumpfa = pfa1 + pfa2
        1 / (sumpfa) * ( - pfa1 * 2LOG(pfa1/sumpfa) - pfa2  * 2LOG(pfa2/sumpfa))
        */
        //Loop all predictors
        for (String p : predictors) {
            //System.out.println(p);
            
            //Find predictor factors
            HashSet<String> predictorFactors = getHeaderFactors(p);
            //Loop all predictor factors
            for (String pf : predictorFactors) {
                //System.out.println(pf);
                HashMap pm          = (HashMap)associations.get(p);
                Map pfm             = (Map)pm.get(pf);
                
                Map assocs      = (Map)sumPerFactorAssociations.get(p);
                Map fassocs     = (Map)assocs.get(pf);
                float sumpfa = 0.0f;
                if(fassocs.get("sum") != null) {
                    sumpfa    = (float)fassocs.get("sum");
                }
                
                ArrayList<Float> pfas = new ArrayList<Float>();
                //Loop all target factors
                for (String tf : targetFactors) {
                    //Per predictor factor with target association
                    float pfa = 0f;
                    if(pfm.get(tf) != null) {
                        pfa         = (float)pfm.get(tf);
                    }
                    
                    //pfa1 * 2LOG(pfa1/sumpfa)
                    //System.out.println(pfa +" "+sumpfa+" "+Math.log(2));
                    pfas.add((Float)pfa);
                }
                
                System.out.println(calcInfo(pfas,sumpfa));
                
                //System.out.println(1 / sumpfa * ( - pfa1 * 2LOG(pfa1/sumpfa) - pfa2  * 2LOG(pfa2/sumpfa)));
            }
        }
       
    }
    
    public float calcInfo(ArrayList<Float> pfas, float sumpfa) {
        //System.out.println(1 / sumpfa * ( - pfa1 * 2LOG(pfa1/sumpfa) - pfa2  * 2LOG(pfa2/sumpfa)));
        float info      = 1 / sumpfa;
        float infas     = 0.0f;
        
        for (Float pfa : pfas) {
            infas = infas - pfa * ((float)(Math.log(pfa/sumpfa) / Math.log(2)));
	}

        return info * infas;
    }
    
}

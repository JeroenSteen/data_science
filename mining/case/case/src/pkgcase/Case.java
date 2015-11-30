package pkgcase;

//Utils
import java.util.HashMap;
import java.util.Map;

public class Case {

    /**
    * @param args the command line arguments
    */
    public static void main(String[] args) {
        Case obj = new Case();
	obj.run();
    }
    
    public void run() {
        
        
        String csvPath          = "verkoopCount.csv";
        
        System.out.println("*** ZeroR ***");
        CsvObject csv           = new CsvObject(csvPath);
        ZeroR zr                = new ZeroR(csv);
        System.out.println(" ");
        
        System.out.println("*** OneR ***");
        OneR or                 = new OneR(csv);
        System.out.println(" ");
        
        System.out.println("*** Statistical Modeling ***");
        Map sr                  = new HashMap();
        sr.put("status", "senior");
        sr.put("department", "marketing");
        sr.put("age", "2630");
        sr.put("salary", "46K50K");
        StatisticalModeling sm  = new StatisticalModeling(csv, sr);
        System.out.println(" ");
        
    }
    
}

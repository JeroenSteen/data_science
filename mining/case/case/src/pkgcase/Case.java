package pkgcase;

public class Case {

    /**
    * @param args the command line arguments
    */
    public static void main(String[] args) {
        Case obj = new Case();
	obj.run();
    }
    
    public void run() {
        
        
        String csvPath  = "verkoopCount.csv";
        
        System.out.println("*** ZeroR ***");
        CsvObject csv   = new CsvObject(csvPath);
        ZeroR zr        = new ZeroR(csv);
        System.out.println(" ");
        
        System.out.println("*** OneR ***");
        OneR or         = new OneR(csv);
        System.out.println(" ");
        
    }
    
}

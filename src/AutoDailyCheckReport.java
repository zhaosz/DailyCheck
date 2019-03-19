import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;


public class AutoDailyCheckReport {
	
public static void main(String[] args) throws Exception {
  

	AutoDailyCheck Dailycheck = new AutoDailyCheck();
	
	System.out.println("testsssss");
	
	
	InputStream in = AutoDailyCheckReport.class.getClassLoader().getResourceAsStream("FTPFromMainframe.propeties"); 
    Properties properties=new Properties(); 
    properties.load(in);
    String address          = properties.getProperty("address");
    String ID               = properties.getProperty("ID");
    String password         = properties.getProperty("password");
    String MainframePatch   = properties.getProperty("MainframePatch");  
    String MainframeFileName= properties.getProperty("MainframeFileName");
    String localfilename    = properties.getProperty("localfilename");
    String localbatchlist   = properties.getProperty("localbatchlist");
    String reportpath       = properties.getProperty("reportPath");
    String reportname       = properties.getProperty("reportname");
    
    Dailycheck.logon(address, ID, password);
    
    Dailycheck.subJCL("TFL#SBJ");
    
    Dailycheck.logoff();
    
    Dailycheck.logon(address, ID, password);
    
    Dailycheck.FTPfile(MainframePatch,MainframeFileName, localfilename);
    
    Dailycheck.logoff();

    
    Map<String, ArrayList<String>> Dailyreport   =  Dailycheck.SpareReport(localfilename);

    Map<String, ArrayList<String>> CompareReport =  Dailycheck.CompareBatchList(Dailyreport.get("BatchtodayW"), localbatchlist);

    Dailycheck.WriteintoDoc(reportname, reportpath, Dailyreport, CompareReport);
  
   
}
}

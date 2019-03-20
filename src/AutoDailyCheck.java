import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;

public class AutoDailyCheck {
	
	
    FTPClient ftp   = null;
	
	String IP       = null;
	
	String user     = null;
	
	String password = null;
	

	/**
	 * This method is for z/OS FTP logon.
     * @param IP z/OS ip.
     * @param user z/OS tso user.
     * @param password z/OS tso user password.
     */
	public boolean logon(String IP,String user,String password)throws Exception{
		
		ftp = new FTPClient();
		
//		System.out.println("test step2");
		System.out.println(IP);
		ftp.connect(IP);

        boolean blogin = ftp.login(user,password);    
       
         System.out.println(ftp.getReplyString());
  //      System.out.println("test step 4");
        if(!blogin) {
          System.out.println("connection failed, please check your ID and password");
          ftp.disconnect();
          ftp = null;
        }
        else {
          this.IP = IP;
          this.user = user;
          this.password = password;         
        }
        
        return blogin;
	}
	
	
	
	/**
	 * This method is to submit a JCL file to jes of z/OS.
     * @param file the JCL file.
     */
	public String subJCL(String file) throws Exception {
		  
		
		if(ftp == null){
		  System.out.println("Please logon z/OS firstly");
		  return null;
		}
		else{
		  System.out.println("++++++++++ subJCL is Started");
		
          ftp.site("filetype=jes");
          System.out.println(ftp.getReplyString());
           
         File file1 = new  File("CFL#SUBJ.txt");
          
          ftp.storeFile("CFL#SUBJ",new FileInputStream(file1));
          String jobid = ftp.getReplyString();
       
          jobid = jobid.substring(jobid.indexOf("JOB"),jobid.indexOf("JOB")+8);
		
          return jobid;
        }
	}
	
	
	
	
	public void FTPfile( String MainframePatch,String MainframeFileName,String localfilename) {
	
		try {
	
	 	 ftp.setRemoteVerificationEnabled(false);
	     ftp.enterLocalPassiveMode();
	     ftp.changeWorkingDirectory(MainframePatch);
			OutputStream os = null;
		
			String localFileName = localfilename;
			File localFile = new File(localFileName);
			os = new FileOutputStream(localFile);
	        ftp.retrieveFile(MainframeFileName, os);
	 
	
			 StringBuilder result1 = new StringBuilder();
			 BufferedReader br = new BufferedReader(new FileReader(localfilename));
		
			 String s = null;
	         while((s = br.readLine())!=null){//使用readLine方法，一次读一行         
	            	  result1.append(System.lineSeparator()+s);
	    
	         }
		 br.close();
	 	
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		} catch (SocketException e) {
			
			e.printStackTrace();
		} catch (IOException e) {		
			e.printStackTrace();
		}
		
	}
	
	/**
     * This method is for z/OS FTP logoff.
     */
	public void logoff()throws Exception{
		
		ftp.disconnect();//disconnect from z/OS
		ftp = null;//clean the JVM heap
		
	}
	
	
	
	public Map<String, ArrayList<String>> SpareReport(String localfilename) {
		
		 Map<String, ArrayList<String>> report= new HashMap<String, ArrayList<String>>();
		  ArrayList<String> ERRORAPPLN        = new ArrayList<String>();
		  ArrayList<String> BatchbeforeToday  = new ArrayList<String>();
		  ArrayList<String> BatchtodayES      = new ArrayList<String>();
		  ArrayList<String> BatchtodayW       = new ArrayList<String>();
		  ArrayList<String> BatchTomorrow     = new ArrayList<String>();
		  SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		  String curentdate =df.format(new Date());
		  int cuerentdateNB=Integer.parseInt(curentdate); 
			 BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(localfilename));
				
				 String s = null;
				
			      while((s = br.readLine())!=null){//使用readLine方法，一次读一行
			    String STATE=s.substring(28,29);
		    	  int batchdateNB=Integer.parseInt(s.substring(17,23));
			   if (STATE.equalsIgnoreCase("E")) {
				   
				ERRORAPPLN.add(s.substring(0,29));
			}	
			   
			   if (batchdateNB <cuerentdateNB && !STATE.equals("C")) {
			 BatchbeforeToday.add(s.substring(0,29));

			}
			 if (batchdateNB <=cuerentdateNB && !STATE.equals("W") && !STATE.equals("C")) {
			BatchtodayES.add(s.substring(0,29));
			}
			 if (batchdateNB ==cuerentdateNB && STATE.equals("W")) {
				BatchtodayW.add(s.substring(0,29));
			}
			 if (batchdateNB >cuerentdateNB ) {
					BatchTomorrow.add(s.substring(0,29));
				}
			    }
			      br.close();
			      report.put("ERRORAPPLN", ERRORAPPLN);
			      report.put("BatchbeforeToday", BatchbeforeToday);
			      report.put("BatchtodayES", BatchtodayES);
			      report.put("BatchtodayW", BatchtodayW);
			      report.put("BatchTomorrow", BatchTomorrow);
			      return report;
			      
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return report;
	}
	
	public Map<String,ArrayList<String>> CompareBatchList(ArrayList<String> Todaybatchlist,String Localbatchpath) {
		 
	    ArrayList<String> local     = new ArrayList<String>();
		ArrayList<String> mainframe = Todaybatchlist;
		Map<String, Integer> LocalCMainframe=new HashMap<String, Integer>();
		Map<String, Integer> MainframeCLocal=new HashMap<String, Integer>();
		Map<String, ArrayList<String>> CompareReport=new HashMap<String, ArrayList<String>>();
		ArrayList<String> localdiff=new ArrayList<String>();
		ArrayList<String> mainframediff=new ArrayList<String>();
		
		try {
			BufferedReader br =new BufferedReader(new FileReader(Localbatchpath));
			String s = null;
			while((s = br.readLine())!=null){
				local.add(s.substring(0,17));
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		for(String string:local){//将shortList放到map中，map的value任意数字即可
			LocalCMainframe.put(string.substring(0,17),0);
			}
		//	localdiff.clear();//清空shortList，用于存放longList中有map中没有的数据
			Integer in;
			for(String string:mainframe){
			   in=LocalCMainframe.get(string.substring(0,17));
			if(null==in){
			localdiff.add(string);//longList中有map中没有的数据
			}
			}
		//
			for(String string:mainframe){//将shortList放到map中，map的value任意数字即可
				MainframeCLocal.put(string.substring(0,17),0);
				}
		//		mainframediff.clear();//清空shortList，用于存放longList中有map中没有的数据
				Integer in2;
				for(String string:local){
				   in2=MainframeCLocal.get(string.substring(0,17));
				if(null==in2){
				mainframediff.add(string);//longList中有map中没有的数据
				}
				}
			CompareReport.put("localdiff", localdiff);
			CompareReport.put("mainframediff", mainframediff);
			
				return CompareReport;
}	
	
	public void WriteintoDoc(String reportname,String reportpath,Map<String, ArrayList<String>> Report,
			Map<String,ArrayList<String>> CompareReport) {
		ArrayList<String> ERRORAPPLN       = Report.get("ERRORAPPLN");
		ArrayList<String> BatchbeforeToday = Report.get("BatchbeforeToday");
	    ArrayList<String> BatchtodayES     = Report.get("BatchtodayES");
		ArrayList<String> BatchtodayW      = Report.get("BatchtodayW");
		ArrayList<String> BatchTomorrow    = Report.get("BatchTomorrow");
		ArrayList<String> localdiff        = CompareReport.get("localdiff");
		ArrayList<String> mainframediff    = CompareReport.get("mainframediff");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		String curentdate   = df.format(new Date());
		String dailyString  = curentdate +reportname;
		
      File dailycheck=new File(reportpath,dailyString);
      try {
		dailycheck.createNewFile();

		FileWriter fw = new FileWriter(dailycheck.getAbsoluteFile());
	      BufferedWriter bw = new BufferedWriter(fw);
	      
	      bw.write("                       "+curentdate +"   "+"FDW Daily Check" +System.lineSeparator());
	      
	      bw.write(System.lineSeparator()+System.lineSeparator()+System.lineSeparator());
	      
	      bw.write("Error Application" +System.lineSeparator());
	      
	      if(ERRORAPPLN.size()==0)
	      {
	    	  bw.write("NO ERROR APPLICATION");
	    	  
	      }
	      else {
	      for (String S1 : ERRORAPPLN) {  
	    	 bw.write(S1+System.lineSeparator());
	    	 
	      }
	      }
	      bw.write(System.lineSeparator()+System.lineSeparator()+System.lineSeparator());
	      bw.write("Batch before yeterday"+System.lineSeparator());
    
	      if(BatchbeforeToday.size()==0)
	      {
	    	  bw.write("NO WAIT,RUNNNIG,ERROR APPLICATION");
	    	  
		      }
	      else {
	      for (String S2 : BatchbeforeToday) {
	    	  
	    	  bw.write(S2+System.lineSeparator());
	    	 
	      }}
	      bw.write(System.lineSeparator()+System.lineSeparator()+System.lineSeparator());
	      bw.write("2.applications running now"+System.lineSeparator());
	      for (String S3 : BatchtodayES) {
	    	  
	    	  bw.write(S3+System.lineSeparator());
	    	 
	      }
	      bw.write(System.lineSeparator()+System.lineSeparator()+System.lineSeparator());
	      bw.write("3.current plan for today opc schedule" +System.lineSeparator());
	      for (String S4 : BatchtodayW) {
	    	  
	    	  bw.write(S4+System.lineSeparator());
	    	  
	      }
	      
	      bw.write(System.lineSeparator()+System.lineSeparator()+System.lineSeparator());
	      bw.write("Local is different" +System.lineSeparator());
	      
	      if(localdiff.size()==0)
	      {
	    	  bw.write("NO DIFFERENT FOUND");
	    	 
		      }
	      else {
	      for (String S6 : localdiff) {	    	  
	    	  bw.write(S6+System.lineSeparator());
	    	  
	      }}
	      bw.write(System.lineSeparator()+System.lineSeparator()+System.lineSeparator());
	      bw.write("Mainframe is different" +System.lineSeparator());
	      
	      if(mainframediff.size()==0)
	      {
	    	  bw.write("NO DIFFERENT FOUND");
	    	  
		      }
	      else {
          for (String S7 : mainframediff) {

	    	  
	    	  bw.write(S7+System.lineSeparator());
	    	 
	      }}
	      bw.write(System.lineSeparator()+System.lineSeparator()+System.lineSeparator());
	      bw.write("opc schedule for tomorrow" +System.lineSeparator());
	        for (String S5 : BatchTomorrow) {
	    	  
	    	  bw.write(S5+System.lineSeparator());
	    	 
	      }
	      
	        bw.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   
	}
	
}

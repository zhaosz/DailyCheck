import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SendMailToReviewer {
    public void uploadfile() {
    	 try {
			FileInputStream in=new FileInputStream(new File("C:\\Users\\YuPu\\Desktop\\Report\\2019.03.22Dailycheck.doc"));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

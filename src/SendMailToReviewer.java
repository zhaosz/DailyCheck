import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class SendMailToReviewer {
    public void uploadfile() {
    	 try {
			FileInputStream in=new FileInputStream(new File("C:\\Users\\YuPu\\Desktop\\Report\\D190322.DailyChk.doc"));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static void downloadFtpFile(String ftpHost, String ftpUserName,
            String ftpPassword, int ftpPort, String ftpPath, String localPath,
            String fileName) {
    	FTPClient ftpClient = null;

    	try {
    	ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
    	ftpClient.setControlEncoding("UTF-8"); // 中文支持
    	ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
    	ftpClient.enterLocalPassiveMode();
    	ftpClient.changeWorkingDirectory(ftpPath);

    	File localFile = new File(localPath + File.separatorChar + fileName);
    	OutputStream os = new FileOutputStream(localFile);
    	ftpClient.retrieveFile(fileName, os);
    	os.close();
    	ftpClient.logout();

    	} catch (FileNotFoundException e) {
    	System.out.println("没有找到" + ftpPath + "文件");
    	e.printStackTrace();
    	} catch (SocketException e) {
    	System.out.println("连接FTP失败.");
    	e.printStackTrace();
    	} catch (IOException e) {
    	e.printStackTrace();
    	System.out.println("文件读取错误。");
    	e.printStackTrace();
    	}
    }
    	public static boolean uploadFile(String ftpHost, String ftpUserName,
    	        String ftpPassword, int ftpPort, String ftpPath,
    	        String fileName,InputStream input) {
    	boolean success = false;
    	FTPClient ftpClient = null;
    	try {
    	int reply;
    	ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
    	reply = ftpClient.getReplyCode();
    	if (!FTPReply.isPositiveCompletion(reply)) {
    	ftpClient.disconnect();
    	return success;
    	}
    	ftpClient.setControlEncoding("UTF-8"); // 中文支持
    	ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
    	ftpClient.enterLocalPassiveMode();
    	ftpClient.changeWorkingDirectory(ftpPath);

    	ftpClient.storeFile(fileName, input);

    	input.close();
    	ftpClient.logout();
    	success = true;
    	} catch (IOException e) {
    	e.printStackTrace();
    	} finally {
    	if (ftpClient.isConnected()) {
    	try {
    	ftpClient.disconnect();
    	} catch (IOException ioe) {
    	}
    	}
    	}
    	return success;
    	}
		private static FTPClient getFTPClient(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort) {
			// TODO Auto-generated method stub
			return null;
		}
    


}

import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.White;

	
	public class Graph extends JFrame
	{
	//Container ct;

//	BackgroundPanel bgp;
		
		
	JButton jb;
	JButton jb2;
	JButton jb3;
	JPanel jp1, jp2,jp3,jp4;
	
	public static void main(String[] args)
	{
	   try {
		new Graph();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
class Progress extends Thread{
		
		private int[] num = {1,10,20,30,40,50,60,70,80,90,100};
		private JProgressBar bar;
		private JButton button;
		
		public Progress(JProgressBar progressBar, JButton button) {
			this.bar = progressBar;
			this.button = button;
		}
		
		public void run() {
			bar.setStringPainted(true);
			bar.setIndeterminate(false);//采用确定的进度条样式
		
			for(int i = 0; i < num.length; i++) {
				
				try {
					bar.setValue(num[i]);
					Thread.sleep(100);
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			bar.setString("Complete");
			//给按钮添加事件监听器，升级结束后退出系统
			button.setText("Complete");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
			
		}
		
	}

	
	
	
	public Graph() throws IOException
	{
		Scanner infor=new Scanner(System.in); 
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		progressBar.setString("In Progress");
		AutoDailyCheck Dailycheck = new AutoDailyCheck();
		
		InputStream in = AutoDailyCheckReport.class.getClassLoader().getResourceAsStream("FTPFromMainframe.propeties"); 
	    Properties properties=new Properties(); 
	    properties.load(in);
	    String address          = properties.getProperty("address");
	    String ID               = properties.getProperty("ID");
	    String password         = properties.getProperty("password");
	    
	//ct=this.getContentPane();
	   this.setLayout(new GridLayout(4, 1));
 jp1 = new JPanel();
 jp2 = new JPanel();
 jp3 = new JPanel();
 jp4 = new JPanel();
	  
//	bgp=new BackgroundPanel((new ImageIcon("20190320111916.jpg")).getImage());
	//   bgp.setBounds(0,0,1000,500);
	 //  ct.add(bgp);
	   String str1[] = {"GLIM","CFL-FDW", "FIW","AAD","BPR"};  
	   final JTextField tf = new JTextField();
	   final JTextField tf2 = new JTextField();
	   final JTextField tf3 = new JTextField();
	   final JTextField tf4 = new JTextField();
	   TextArea ta = new TextArea();
	   ta.setBounds(300,0,200,100);
	   JComboBox jcb =new JComboBox(str1);
	   tf.setText("AP Ledger Daily Check Report");
		tf.setFont(new Font("",Font.PLAIN,29));
		tf.setBounds(300,0,500,100);
		tf.setForeground(Color.blue);
		tf.setEditable(false);
		tf.setBorder(null);
 	   
		tf2.setText("Choose application for report    ");
		tf2.setFont(new Font("",Font.PLAIN,20));
		tf2.setBounds(200,120,500,100);
		tf2.setBorder(null);
		tf2.setForeground(Color.blue);
		tf2.setEditable(false);
		
		
		
	jb=new JButton("Daily Check Report");
	jb2=new JButton("View Report");
	jb3=new JButton("Send Report to mailbox");
	jb.setBounds(300,240,160,30);
	jb2.setBounds(500,240,160,30);
	jb3.setBounds(600,240,160,30);
	
	tf3.setFont(new Font("",Font.PLAIN,23));
	
	tf3.setBounds(300,300,300,100);
	tf3.setBorder(null);
	tf3.setForeground(Color.blue);
	tf3.setText("                                        ");
	
	
	tf2.setEditable(false);
	
	jcb.setFont(new Font("",Font.PLAIN,16));
	//tf4.setText("CFL-FDW");
	//tf4.setFont(new Font("",Font.PLAIN,20));
	//tf4.setEnabled(false);
	//tf4.setBounds(600,120,500,100);
	//tf4.setBorder(null);
	jp1.add(tf);
	jp2.add(tf2);
	jp2.add(jcb);
	jp3.add(jb);
	jp3.add(jb2);
	jp3.add(jb3);
	jp4.add(ta);
//	jp4.add(progressBar);
//	jp4.add(tf3);
	this.add(jp1);
	this.add(jp2);
	this.add(jp3);
	this.add(jp4);
/*	 this.add(tf);
	   this.add(jb);
	   this.add(jb2);
	   this.add(jcb);
	   this.add(tf2);
	   this.add(tf3);
	   this.add(tf4);*/
	   
	   jb.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				new Progress(progressBar, jb).start();
			
				try {
					ta.setText(infor.nextLine());
					AutoDailyCheckReport.main(null);
					
					
					tf3.setText("FDW Report Produced");
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	   
	   jb2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				try {
					SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
					String curentdate   = df.format(new Date());
					String dailyString  ="D" +curentdate +".";
					Desktop.getDesktop().open(new File("C:\\Users\\YuPu\\Desktop\\Report\\"+dailyString+"DailyChk.doc"));
				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	   
	   jb3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				try {
					
					Dailycheck.logon(address, ID, password);
				    
				    Dailycheck.UploadReport();
				    
				    Dailycheck.subJCL2("CFL#SENT");
				    
				    tf3.setText("Report have sent to mailbox");
				    
				    Dailycheck.logoff(); 
				
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	   
	  
	   this.setTitle("AP Ledger Daily Check Report");
	this.setFont(new Font("",Font.BOLD,22));
	   this.setSize(1000,500);
	  // this.setLocation(400,300);
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   this.setVisible(true);
	   this.setLocationRelativeTo(null);
	}
	}
	class BackgroundPanel extends JPanel
	{
	Image im;
	public BackgroundPanel(Image im)
	{
	   this.im=im;
	   this.setOpaque(true);
	}
	//Draw the back ground.
	public void paintComponent(Graphics g)
	{
	   super.paintComponents(g);
	   g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);

	}
	}


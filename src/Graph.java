import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	   new Graph();
	}
	public Graph()
	{
	   
	//ct=this.getContentPane();
	   this.setLayout(new GridLayout(4, 1));
 jp1 = new JPanel();
 jp2 = new JPanel();
 jp3 = new JPanel();
 jp4 = new JPanel();
	  
//	bgp=new BackgroundPanel((new ImageIcon("20190320111916.jpg")).getImage());
	//   bgp.setBounds(0,0,1000,500);
	 //  ct.add(bgp);
	   String str1[] = {"CFL-FDW", "FIW", "GLIM", "AAD","BPR"};  
	   final JTextField tf = new JTextField();
	   final JTextField tf2 = new JTextField();
	   final JTextField tf3 = new JTextField();
	   final JTextField tf4 = new JTextField();
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
	jb3=new JButton("Sent Report to Reviewer");
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
			
				try {
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


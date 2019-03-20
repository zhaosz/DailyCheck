import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.White;

	
	public class Graph extends JFrame
	{
	Container ct;

	BackgroundPanel bgp;

	JButton jb;
	JButton jb2;
	
	public static void main(String[] args)
	{
	   new Graph();
	}
	public Graph()
	{
	   
	ct=this.getContentPane();
	   this.setLayout(null);

	  
	bgp=new BackgroundPanel((new ImageIcon("20190320111916.jpg")).getImage());
	   bgp.setBounds(0,0,1000,500);
	   ct.add(bgp);
	  
	   final JTextField tf = new JTextField();
	   final JTextField tf2 = new JTextField();
	   final JTextField tf3 = new JTextField();
	   final JTextField tf4 = new JTextField();
	   tf.setText("AP Ledger Daily Check Report");
		tf.setFont(new Font("",Font.PLAIN,29));
		tf.setEnabled(false);
		tf.setBounds(300,0,500,100);
		tf.setBorder(null);
	//	tf.setCaretColor(Color.red);
 	   
		tf2.setText("Choose a application for report                     CFL-FDW");
		tf2.setFont(new Font("",Font.PLAIN,20));
		tf2.setEnabled(false);
		tf2.setBounds(200,120,500,100);
		tf2.setBorder(null);
	//	tf2.setCaretColor(Color.gray);
		
		
		
	jb=new JButton("Daily Check Report");
	jb2=new JButton("View Report");
	jb.setBounds(300,240,160,30);
	jb2.setBounds(500,240,160,30);
	
	
	tf3.setFont(new Font("",Font.PLAIN,23));
	tf3.setEnabled(false);
	tf3.setBounds(300,300,300,100);
	tf3.setBorder(null);
	
	//tf4.setText("CFL-FDW");
	//tf4.setFont(new Font("",Font.PLAIN,20));
	//tf4.setEnabled(false);
	//tf4.setBounds(600,120,500,100);
	//tf4.setBorder(null);
	
	 ct.add(tf);
	   ct.add(jb);
	   ct.add(jb2);
	   ct.add(tf2);
	   ct.add(tf3);
	   ct.add(tf4);
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
					
					Desktop.getDesktop().open(new File("C:\\Users\\YuPu\\Desktop\\Report\\2019.03.20Dailycheck.doc"));	
					
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


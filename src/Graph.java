import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Graph {
     public void Window() {
    	JFrame jf = new JFrame("AP Operation Team Daily check"); //建立一个窗口 
 		FlowLayout fl = new FlowLayout(); //使用流布局 
 		final JTextField tf = new JTextField();
 		tf.setText("This is for AP Ledger Daily Check Report");
 		tf.setFont(new Font("",Font.BOLD,22));
 		tf.setEnabled(false);
 		tf.setBounds(250,0,500,100);
 		jf.setLayout(null);//修改布局管理 
 		JButton jb1 = new JButton("Button1"); //创建一个按钮 
 		JLabel lab = new JLabel("");
 		lab.setBounds(250,160,500,340);
 		lab.setFont(new Font("",Font.BOLD,22));
 	
 		jb1.addActionListener(new ActionListener() {
 		
 			public void actionPerformed(ActionEvent e) {
 			
 				try {
 					lab.setText("FDW Daily Check report produce successful");
 				
					AutoDailyCheckReport.main(null);
 					
 				} catch (Exception e1) {
 					// TODO Auto-generated catch block
 					e1.printStackTrace();
 				}
 			}
 		});
 		jf.add(tf);
 		jf.add(jb1); //把按钮jb1放入窗口 
 		jf.add(lab);
 		jb1.setBounds(400,120, 200, 60);
 		jb1.setText("CLS-FDW Daily Check Report");
 	
 		jf.setSize(1000, 500); //设置窗口的大小 
 		jf.setLocation(400,200);//设置窗口的初始位置 
 		jf.setTitle("AP Operation Team Daily check");
 		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗口 
 		jf.setLocationRelativeTo(null);
 		jf.setVisible(true); //显示窗口
		
	}
     public static void main(String[] args) {
		Graph graph =new Graph();
		graph.Window();
	}
}

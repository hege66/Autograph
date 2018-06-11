package com.hege.qm;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Toolkit;

public class QMFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1512677415080462588L;

	JLabel name = new JLabel("输入你的名字:");  
	JTextField nameField = new JTextField();
	JButton bt = new JButton("设计");
	JLabel label = new JLabel("样式:");  
	JComboBox<String> comboBox=new JComboBox<String>();  
	
	//JTextField jtf = new JTextField();
	QMService qm = new QMService();
	
	
	public QMFrame() throws IOException {
		//初始化界面
		init();
		
		//添加按钮组件
		addCompoment();
		
		//为按钮添加监听器
		addListener();
	}
	
	//为按钮添加监听器
	private void addListener() {
		bt.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
		
				
				String style = getStyle(comboBox.getSelectedItem().toString());
				String name = nameField.getText();
				
				if(name.trim().equals(" ") || name.equals("")) {
					JOptionPane.showMessageDialog(null, "请输入姓名!");
					
				}else {
					qm.create(name, style);
					
					Container panel = getContentPane();
					ImagePanel imagePanel = null;
					File img = new File("D:\\" +name +".gif");
					try {
						
						imagePanel = new ImagePanel(ImageIO.read(img));
						
					} catch (Exception e1) {
						throw new RuntimeException(e1);
					}
					
					imagePanel.setBounds(110, 120, 535, 205);
					panel.add(imagePanel);
					imagePanel.updateUI();
					panel.revalidate();
				}
				
				
				
			}
		});
	}

	//添加按钮组件
	private void addCompoment() {
		this.getContentPane().add(bt);
		this.add(name);
		this.add(nameField);
		this.add(label);  
        comboBox.addItem("个性签");  
        comboBox.addItem("连笔签");  
        comboBox.addItem("潇洒签");  
        comboBox.addItem("草体签");  
        this.add(comboBox);  
      
		//this.getContentPane().add(jtf);
	}

	//初始化界面
	private void init() throws IOException {
		this.setTitle("艺术签名by hege");
		this.setBounds(400,230,750,400);
		this.setLocationRelativeTo(null); 
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		Font font = new Font("华文行楷",2,15);
		name.setFont(font);
		label.setFont(font);
		comboBox.setFont(font);
		bt.setFont(font);
		nameField.setFont(new Font("华文行楷",2,17));
		name.setBounds(135, 40, 100, 50);
		nameField.setBounds(235, 47, 100, 35);
		label.setBounds(345, 47, 100, 35);
		comboBox.setBounds(395,45,100,35);
		bt.setBounds(515, 45, 65, 35);
		
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(QMFrame.class.getResource("/image/icon.jpg")));
		//jtf.setBounds(310,150,200,50);
	}
	
	
	/**
	 * 得到字体的代码
	 * @param style
	 * @return
	 */
	private String getStyle(String style) {
		
		Map<String,String> styles = new HashMap<String,String>();
		styles.put("个性签", "jfcs.ttf");
		styles.put("连笔签", "qmt.ttf");
		styles.put("潇洒签", "bzcs.ttf");
		styles.put("草体签", "lfc.ttf");
		
		return styles.get(style);
	}
	
}


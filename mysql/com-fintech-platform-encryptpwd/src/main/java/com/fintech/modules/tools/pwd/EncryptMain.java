package com.fintech.modules.tools.pwd;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.springframework.core.io.ClassPathResource;

/**
 * 生成密码GUI main
 * @description:
 * @author
 * @date: 2016年5月16日 下午4:34:13
 */
public class EncryptMain extends JFrame implements ClipboardOwner {
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EncryptMain frame = new EncryptMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the frame.
	 */
	public EncryptMain(){
		try{
			//获取密匙
			Properties properties = new Properties();   
	        ClassPathResource cp = new ClassPathResource("key.properties");
	        properties.load(cp.getInputStream());
	        final String key = properties.getProperty("key");
		
	        //构造UI
			setResizable(false);
			setTitle("密文生成工具");
			Image a = this.getToolkit().getImage("top.gif");
			this.setIconImage(a);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//setBounds(100, 100, 450, 300);
			
			java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
					.getScreenSize();
			setBounds((screenSize.width - 778) / 2, (screenSize.height - 696) / 2,
					450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel label_1 = new JLabel("秘钥：");
			label_1.setHorizontalAlignment(SwingConstants.RIGHT);
			label_1.setBounds(80, 25, 54, 15);
			contentPane.add(label_1);
			
			JLabel lblNewLabel_1 = new JLabel(key);
			lblNewLabel_1.setBounds(144, 25, 227, 15);
			contentPane.add(lblNewLabel_1);
			
			
			JLabel lblNewLabel = new JLabel("明文：");
			lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel.setBounds(80, 67, 54, 15);
			contentPane.add(lblNewLabel);
			
			final JTextPane textPane_1 = new JTextPane();
			textPane_1.setBounds(144, 67, 227, 21);
			textPane_1.setText("");
			contentPane.add(textPane_1);
			
			JLabel label = new JLabel("密文：");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setBounds(80, 119, 54, 15);
			contentPane.add(label);
			
			final JTextPane textPane_2 = new JTextPane();
			textPane_2.setBounds(144, 113, 227, 21);
			textPane_2.setText("");
			contentPane.add(textPane_2);
			
			//加密按钮
			JButton button = new JButton("加密");
			button.setBounds(80, 206, 93, 23);
			contentPane.add(button);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String plainText = textPane_1.getText();
					String secretText = DESUtil.getEncryptString(plainText, key);
//					System.out.println("=======start=======plainText："+plainText+",secretText:"+secretText);
					textPane_2.setText(secretText);
					AlertDialog dialog = new AlertDialog("操作成功");
					dialog.show(true);
				}
			});
			
			//复制密文按钮
			final Clipboard clipboard = this.getToolkit().getSystemClipboard();
			JButton button_1 = new JButton("复制密文");
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//将生成的密文复制到内存中
					StringSelection contents = new StringSelection(textPane_2.getText());
					clipboard.setContents(contents, EncryptMain.this);
					
					AlertDialog dialog = new AlertDialog("成功复制密文，请使用ctrl+v粘贴密文");
					dialog.show(true);
//					System.out.println("=====成功复制密文，请使用ctrl+v粘贴密文=====");
				}
			});
			button_1.setBounds(278, 206, 93, 23);
			contentPane.add(button_1);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// TODO Auto-generated method stub
		
	}
}

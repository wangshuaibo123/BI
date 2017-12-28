package com.jy.modules.tools.pwd;

import java.awt.EventQueue;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
/**
 * @description:动态生成密码
 * @author chengang
 * @date: 2016年7月19日 上午11:26:44
 */
public class GenPWD extends JFrame implements ClipboardOwner{

	private static final long serialVersionUID = 6294889858929713046L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenPWD frame = new GenPWD();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public GenPWD() throws IOException {
        //构造UI
		setResizable(false);
		setTitle("密码生成工具");
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
		
		final JLabel label_1 = new JLabel("位数：");
		label_1.setBounds(98, 59, 47, 23);
		contentPane.add(label_1);
		
		final JTextPane textPane_1 = new JTextPane();
		textPane_1.setText("8");
		textPane_1.setBounds(144, 61, 173, 21);
		contentPane.add(textPane_1);
		
		final JLabel label = new JLabel("密码：");
		label.setBounds(98, 118, 47, 23);
		contentPane.add(label);
		final JTextPane textPane = new JTextPane();
		textPane.setBounds(144, 120, 173, 21);
		contentPane.add(textPane);
		
		
		JButton button = new JButton("生成密码");
		button.setBounds(116, 211, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("复制");
		button_1.setBounds(243, 211, 93, 23);
		contentPane.add(button_1);
		
		final JLabel lblMsg = new JLabel("");
		lblMsg.setBounds(174, 164, 118, 23);
		contentPane.add(lblMsg);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String plainText = textPane_1.getText();
				int count = Integer.parseInt(textPane_1.getText());
				String secretText ="";
				if(8 == count){
					secretText = GenSeed.generatePwd();
				}else{
					secretText = GenSeed.genPwd(count);
				}
				
				textPane.setText(secretText);
				System.out.println("=======start=======密码位数："+plainText+",secretText:"+secretText);
				lblMsg.setText("成功生成密码");
			}
		});
		//复制
		final Clipboard clipboard = this.getToolkit().getSystemClipboard();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//将生成的密文复制到内存中
				StringSelection contents = new StringSelection(textPane.getText());
				clipboard.setContents(contents, GenPWD.this);
				
				System.out.println("=====成功复制,请使用ctrl+v粘贴");
				lblMsg.setText("请使用ctrl+v粘贴密码");
			}
		});
		 
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		
	}
	
	
}

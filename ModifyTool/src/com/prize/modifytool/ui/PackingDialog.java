package com.prize.modifytool.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import com.prize.modifytool.constant.Constant;
import com.prize.modifytool.utils.ModifyUtil;
import com.prize.modifytool.utils.PackingUtil;
import com.prize.modifytool.utils.PackingUtil.PackingProgressInterface;
import com.prize.modifytool.utils.ReadInfoUtil;
import com.prize.modifytool.utils.StringUtil;

import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class PackingDialog extends JDialog {

	private static final long serialVersionUID = 2L;
	
	private String packPath;
	private String checkPath;
	
	private JPanel contentPane;
	private JTextField packPathText;
	private JTextField checkPathText;
	private JLabel lblState;
	private JButton btnStart;
	private Thread packingThread;
	private boolean isPacking=false;
	
	public PackingDialog(Frame aFrame) {
		super(aFrame, true);
		setResizable(false);
		setBounds(100, 100, 550, 406);
		setTitle("打包软件");
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		
		JLabel label = new JLabel("打包输出目录 ");
		horizontalBox_1.add(label);
		
		packPathText = new JTextField();
		packPathText.setColumns(10);
		horizontalBox_1.add(packPathText);
		
		JButton btnPackChooser = new JButton(".....");
		horizontalBox_1.add(btnPackChooser);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		
		JLabel label_1 = new JLabel("校验软件目录 ");
		horizontalBox_2.add(label_1);
		
		checkPathText = new JTextField();
		checkPathText.setColumns(10);
		horizontalBox_2.add(checkPathText);
		
		JButton btnCheckChooser = new JButton(".....");
		horizontalBox_2.add(btnCheckChooser);
		
		Box horizontalBox_3 = Box.createHorizontalBox();
		
		btnStart = new JButton("开始打包软件");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startPacking();
			}
		});
		btnStart.setFont(new Font("宋体", Font.PLAIN, 16));
		horizontalBox_3.add(btnStart);
		
		Box horizontalBox_4 = Box.createHorizontalBox();
		
		JLabel lblNewLabel_1 = new JLabel("打包状态：");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 14));
		horizontalBox_4.add(lblNewLabel_1);
		
		lblState = new JLabel("未开始");
		lblState.setForeground(Color.RED);
		lblState.setFont(new Font("宋体", Font.PLAIN, 16));
		horizontalBox_4.add(lblState);
		Document packPathTextDT = packPathText.getDocument();
		packPathTextDT.addDocumentListener(new TextFieldDocumentListener(packPathText));
		Document checkPathTextDT = checkPathText.getDocument();
		checkPathTextDT.addDocumentListener(new TextFieldDocumentListener(checkPathText));
		btnPackChooser.addActionListener(new BtnChooserActionListener(packPathText, "选择打包软件输出目录"));
		btnCheckChooser.addActionListener(new BtnChooserActionListener(checkPathText, "选择检验软件目录"));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(25)
					.addComponent(horizontalBox_1, GroupLayout.PREFERRED_SIZE, 463, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(25)
					.addComponent(horizontalBox_2, GroupLayout.PREFERRED_SIZE, 463, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(45)
					.addComponent(horizontalBox_4, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(205)
					.addComponent(horizontalBox_3, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addComponent(horizontalBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addComponent(horizontalBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(137)
					.addComponent(horizontalBox_4, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(60)
					.addComponent(horizontalBox_3, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
		);
		contentPane.setLayout(gl_contentPane);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				init();
			}
		}).start();
	}
	
	private void init(){
		String checkSumGeneratePath=ReadInfoUtil.getCheckSumGeneratePath();
		if(!StringUtil.isEmpty(checkSumGeneratePath)){
			File file=new File(checkSumGeneratePath);
			if(file.exists()){
				checkPathText.setText(checkSumGeneratePath);
			}else{
				ModifyUtil.modifyCheckSumGeneratePath("");
			}
		}
	}
	
	@Override
	protected void processWindowEvent(WindowEvent e) {
		boolean flag = false;
		if(e.getID()==WindowEvent.WINDOW_CLOSING){
			if(isPacking){
				int result= JOptionPane.showConfirmDialog(this,"正在进行打包操作,确认要关闭吗？","关闭",JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.NO_OPTION){
					flag=true;
				}else{
					//结束打包线程
					if(packingThread!=null){
						packingThread.interrupt();
					}
				}
			}
			if(!flag){
				super.processWindowEvent(e);}
		}
	}
	/**
	 * JTextField内容变化的监听
	 * 
	 * @author
	 *
	 */
	private class TextFieldDocumentListener implements DocumentListener {

		private JTextField mJTextField;
		private String strText;
		
		public TextFieldDocumentListener(JTextField textField) {
			mJTextField = textField;
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			strText = mJTextField.getText().trim();
			storageData(mJTextField, strText);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			strText = mJTextField.getText().trim();
			storageData(mJTextField, strText);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			strText = mJTextField.getText().trim();
			storageData(mJTextField, strText);
		}

		private void storageData(JTextField textField, String strText) {
			if(textField==packPathText){
				packPath=strText;
			}else if(textField==checkPathText){
				checkPath=strText;
				ModifyUtil.modifyCheckSumGeneratePath(checkPath);
			}else{
				
			}
		}
	}
	
	
	private class BtnChooserActionListener implements ActionListener {
		private JTextField mJTextField;
		private String mTitle;

		public BtnChooserActionListener(JTextField textField, String title) {
			mJTextField = textField;
			mTitle = title;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle(mTitle);
			jfc.setDialogType(JFileChooser.OPEN_DIALOG);
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int res = jfc.showOpenDialog(null);
			if (res == JFileChooser.APPROVE_OPTION) {
				File dir = jfc.getSelectedFile();
				mJTextField.setText(dir.getAbsolutePath());
			}
		}
	}
	
	private void startPacking(){
		if(packPath==null){
			lblState.setText("打包输出目录不能为空");
			return;
		}
		if(checkPath==null){
			lblState.setText("校验软件目录不能为空");
			return;
		}

		packingThread =new Thread(new Runnable() {
			
			@Override
			public void run() {
				btnStart.setEnabled(false);
				String compilePath = PackingUtil.getInstance().getCompileOutPath(Constant.PROJECT_BOOT_PATH);
				PackingUtil.getInstance().setPackingListener(new PackingProgressInterface() {
					
					@Override
					public void packingProgress(String description) {
						lblState.setText(description);
					}
				});
				PackingUtil.getInstance().ZipRom(compilePath, packPath, checkPath);
				isPacking=false;
				btnStart.setEnabled(true);
			}
		});
		isPacking = true;
		packingThread.start();
	}
}

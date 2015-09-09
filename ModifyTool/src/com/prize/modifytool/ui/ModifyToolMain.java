package com.prize.modifytool.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import com.prize.modifytool.bean.DataBean;
import com.prize.modifytool.constant.Constant;
import com.prize.modifytool.utils.ModifyUtil;
import com.prize.modifytool.utils.ReadInfoUtil;
import com.prize.modifytool.utils.StringUtil;

import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;

public class ModifyToolMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField bootPathText;
	private JTextField versionText;
	private JTextField bootAnimationText;
	private JTextField logoText;
	private JTextField bootRingText;
	private JTextField nameText;
	private JTextField bootPathText2;

	private JCheckBox chckbxVersion;
	private JCheckBox chckbxLogo;
	private JCheckBox chckbxBootAnimation;
	private JCheckBox chckbxBootRing;
	private JCheckBox chckbxName;
	private JTextField screenLightText;
	private JCheckBox chckbxTheme;
	private JCheckBox chckbxScreenLight;
	private JCheckBox chckbxVirtualKey;
	private JCheckBox chckbxScreenOffTime;
	
	private JRadioButton rdbtncloseVitrual;
	private JRadioButton rdbtnopenVitrual;	
	private JRadioButton rdbtnVolumeMax; 
	private JRadioButton rdbtnVolumeDefault;
	private JRadioButton rdbtnOpenFalseTounch;
	private JRadioButton rdbtnCloseFalseTounch;
	private JRadioButton rdbtnOpenCallVibration;
	private JRadioButton rdbtnColseCallVibration;
	
	
	private JComboBox cbxTheme;
	private JComboBox cbxFontSize;
	private JComboBox cbxRam;
	private JComboBox cbxRom;
	private JComboBox cbxResolution;
	private JComboBox cbxTimeformat;
	
	private JCheckBox chckbxFontSize;
	private JCheckBox chckbxRam;
	private JCheckBox chckbxRom;
	private JCheckBox chckbxResolution;
	private JCheckBox chckbxTimeformat;
	private JCheckBox chckbxVolumeMax;
	private JCheckBox chckbxFalseTouch;
	private JCheckBox chckbxCallVibration;
	
	private CustomDialog customDialog;
	private Box horizontalBox_8;
	private JCheckBox chckbxShutdownAnimation;
	private JCheckBox chckbxShutDownRing;
	private JCheckBox chckbxLockWallpaper;
	private JCheckBox chckbxThemeWallpaper;
	private JCheckBox chckbxCallRing;
	private JTextField shutDownAnimationText;
	private JTextField shutDownRingText;
	private JTextField lockWallpaperText;
	private JTextField screenOffTimeText;
	private JTextField themeWallpaperText;
	private JTextField defaultCallRingText;
	
	private ButtonGroup virtualKeyGroup;
	private ButtonGroup ringVolumeGroup;
	private ButtonGroup falseTouchGroup;
	private ButtonGroup callVibrationyGroup;	
	private ButtonGroup autoBrightnessGroup;
	private ButtonGroup beautyGroup;
	private ButtonGroup fingerPrintGroup;
	private ButtonGroup intelligentwakeGroup;
	private ButtonGroup standbyWallpaperGroup;
	private ButtonGroup standbyWallpaperHideGroup;
	
	
	private JButton btnPacking;
	private JLabel lblResult;
	private JButton btnOk;
	private Box horizontalBox_23;
	private JCheckBox chckbxAddMedia;
	private JTextField addMediaText;
	private JButton btnChooserAddMedia;
	private Box horizontalBox_24;
	private JCheckBox chckbxWallPapersNum;
	private JTextField wallPapersNumText;
	private Box horizontalBox_25;
	private JCheckBox chckboxAutoBrightness;
	private JRadioButton rdbtnOpenAutoBrightness;
	private JRadioButton rdbtnCloseAutoBrightness;
	private Box horizontalBox_26;
	private JCheckBox chckboxBeauty;
	private JRadioButton rdbtnOpenBeauty;
	private JRadioButton rdbtnCloseBeauty;
	private Box horizontalBox_27;
	private JCheckBox chckboxPocketMode;
	private JRadioButton rdbtnOpenPocketMode;
	private JRadioButton rdbtnClosePocketMode;
	private Box horizontalBox_28;
	private JCheckBox chckbxRingtones;
	private JComboBox cbxRingtones;
	private Box horizontalBox_29;
	private JCheckBox chckbxNotifications;
	private JComboBox cbxNotifications;
	private Box horizontalBox_30;
	private JCheckBox chckbxMmsRing;
	private JComboBox cbxMmsRing;
	private Box horizontalBox_31;
	private JCheckBox chckbxAlarms;
	private JComboBox cbxAlarms;
	private Box horizontalBox_32;
	private JLabel lblVerison;
	private Box horizontalBox_35;
	private JCheckBox chckbxIntelligentwake;
	private JRadioButton rdbtnOpenIntelligentwake;
	private JRadioButton rdbtnCloseIntelligentwake;
	private Box horizontalBox_36;
	private JTextField standbyWallpaperText;
	private JButton btnChooserStandbyWallpaper;
	private Box horizontalBox_33;
	private JCheckBox chckbxBrand;
	private JTextField brandText;
	private Box horizontalBox_34;
	private JCheckBox chckbxCpu;
	private JTextField cpuText;
	private Box horizontalBox_37;
	private Box horizontalBox_38;
	private JComboBox cbxStandWallpaper;
	private JLabel lblNewLabel_2;
	
	private JRadioButton rdbtnAddWallpaper;
	private JRadioButton rdbtnUpdateWallpaper;
	private JRadioButton rdbtnDeleteWallpaper;
	private JRadioButton rdbtnDefaultWallpaper;
	private JCheckBox chckbxStandbyWallpaper;
	private Box horizontalBox_40;
	private JCheckBox chckbxVirtualKeyHide;
	private JRadioButton rdbtnopenVitrualHide;
	private JRadioButton rdbtncloseVitrualHide;
	private JTextField offsetText;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModifyToolMain frame = new ModifyToolMain();
					frame.setResizable(false);
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
	public ModifyToolMain() {
		setTitle("ModifyTool");
		customDialog = new CustomDialog(ModifyToolMain.this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1075, 661);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Box horizontalBox_top1 = Box.createHorizontalBox();
		horizontalBox_top1.setBounds(5, 5, 986, 23);
		contentPane.add(horizontalBox_top1);

		JLabel lblNewLabel = new JLabel("\u9879\u76EE\u6839\u76EE\u5F55\uFF1A");
		horizontalBox_top1.add(lblNewLabel);

		bootPathText = new JTextField();
		bootPathText.setHorizontalAlignment(SwingConstants.LEFT);
		horizontalBox_top1.add(bootPathText);
		bootPathText.setColumns(10);

		JButton btnChooserBootPath = new JButton(".....");
		horizontalBox_top1.add(btnChooserBootPath);

		Box horizontalBox_1 = Box.createHorizontalBox();
		horizontalBox_1.setBounds(30, 70, 415, 23);
		contentPane.add(horizontalBox_1);

		chckbxVersion = new JCheckBox(" 版本号   ");
		horizontalBox_1.add(chckbxVersion);

		versionText = new JTextField();
		versionText.setEnabled(false);
		horizontalBox_1.add(versionText);
		versionText.setColumns(10);

		Box horizontalBox_end = Box.createHorizontalBox();
		horizontalBox_end.setBounds(470, 586, 150, 30);
		contentPane.add(horizontalBox_end);

		btnOk = new JButton("\u786E \u5B9A");
		btnOk.setVerticalAlignment(SwingConstants.TOP);
		horizontalBox_end.add(btnOk);
		
		btnPacking = new JButton("打包");
		btnPacking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Constant.PROJECT_BOOT_PATH == null) {
					customDialog.setTitle("提示信息");
					customDialog.setMessage("项目根目录不能为空");
					customDialog.pack();
					customDialog.setLocationRelativeTo(ModifyToolMain.this);
					customDialog.setVisible(true);
					return;
				}
				if (DataBean.newVerisonNumber==null) {
					customDialog.setTitle("提示信息");
					customDialog.setMessage("软件版本号不能为空");
					customDialog.pack();
					customDialog.setLocationRelativeTo(ModifyToolMain.this);
					customDialog.setVisible(true);
					return;
				}
				PackingDialog mPackingDialog=new PackingDialog(ModifyToolMain.this);
				mPackingDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				mPackingDialog.setVisible(true);
			}
		});
		horizontalBox_end.add(btnPacking);
		btnOk.addActionListener(new BtnOnclickActionListener());

		Box horizontalBox_2 = Box.createHorizontalBox();
		horizontalBox_2.setBounds(550, 70, 463, 23);
		contentPane.add(horizontalBox_2);

		chckbxBootAnimation = new JCheckBox("\u5F00\u673A\u52A8\u753B   ");
		horizontalBox_2.add(chckbxBootAnimation);

		bootAnimationText = new JTextField();
		bootAnimationText.setEnabled(false);
		bootAnimationText.setColumns(10);
		horizontalBox_2.add(bootAnimationText);

		JButton btnChooserBootAnim = new JButton(".....");
		horizontalBox_2.add(btnChooserBootAnim);

		Box horizontalBox_3 = Box.createHorizontalBox();
		horizontalBox_3.setBounds(550, 100, 463, 23);
		contentPane.add(horizontalBox_3);

		chckbxLogo = new JCheckBox("\u5F00\u673ALogo  ");
		horizontalBox_3.add(chckbxLogo);

		logoText = new JTextField();
		logoText.setEnabled(false);
		logoText.setColumns(10);
		horizontalBox_3.add(logoText);

		JButton btnChooserLogo = new JButton(".....");
		horizontalBox_3.add(btnChooserLogo);

		Box horizontalBox_4 = Box.createHorizontalBox();
		horizontalBox_4.setBounds(550, 130, 463, 23);
		contentPane.add(horizontalBox_4);

		chckbxBootRing = new JCheckBox("\u5F00\u673A\u94C3\u58F0  ");
		horizontalBox_4.add(chckbxBootRing);

		bootRingText = new JTextField();
		bootRingText.setEnabled(false);
		bootRingText.setColumns(10);
		horizontalBox_4.add(bootRingText);

		JButton btnChooserBootRing = new JButton(".....");
		horizontalBox_4.add(btnChooserBootRing);

		Box horizontalBox_5 = Box.createHorizontalBox();
		horizontalBox_5.setBounds(30, 190, 415, 23);
		contentPane.add(horizontalBox_5);

		chckbxName = new JCheckBox("\u624B\u673A\u578B\u53F7\u3001\u84DD\u7259\u3001WIFI\u540D\u79F0");
		horizontalBox_5.add(chckbxName);

		nameText = new JTextField();
		nameText.setEnabled(false);
		nameText.setColumns(10);
		horizontalBox_5.add(nameText);

		Box horizontalBox_top2 = Box.createHorizontalBox();
		horizontalBox_top2.setBounds(5, 35, 986, 23);
		contentPane.add(horizontalBox_top2);

		JLabel label = new JLabel("客户根目录：");
		horizontalBox_top2.add(label);

		bootPathText2 = new JTextField();
		bootPathText2.setEditable(false);
		bootPathText2.setHorizontalAlignment(SwingConstants.LEFT);
		bootPathText2.setColumns(10);
		horizontalBox_top2.add(bootPathText2);

		JButton btnChooserBootPath2 = new JButton(".....");
		horizontalBox_top2.add(btnChooserBootPath2);

		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBounds(550, 400, 200, 23);
		contentPane.add(horizontalBox);

		chckbxTheme = new JCheckBox(" \u9ED8\u8BA4\u4E3B\u9898      ");
		horizontalBox.add(chckbxTheme);

		cbxTheme = new JComboBox();
		cbxTheme.setEnabled(false);
		cbxTheme.setModel(new DefaultComboBoxModel(
				new String[] { "\u5C81\u6708\u5982\u6B4C", "\u6697\u591C\u661F\u7A7A", "\u7F24\u7EB7\u8272\u5F69",
						"\u590F\u65E5\u5FC3\u60C5", "\u590F\u5929\u7684\u98CE", "\u661F\u5149\u7480\u74A8" }));
		cbxTheme.setSelectedIndex(0);
		horizontalBox.add(cbxTheme);

		Box horizontalBox_6 = Box.createHorizontalBox();
		horizontalBox_6.setBounds(30, 220, 415, 23);
		contentPane.add(horizontalBox_6);

		chckbxScreenLight = new JCheckBox("\u9ED8\u8BA4\u4EAE\u5EA6");
		horizontalBox_6.add(chckbxScreenLight);

		screenLightText = new JTextField();
		screenLightText.setEnabled(false);
		screenLightText.setColumns(10);
		horizontalBox_6.add(screenLightText);

		Box horizontalBox_7 = Box.createHorizontalBox();
		horizontalBox_7.setBounds(30, 340, 415, 23);
		contentPane.add(horizontalBox_7);

		chckbxVirtualKey = new JCheckBox("虚拟按键         ");
		horizontalBox_7.add(chckbxVirtualKey);

		rdbtnopenVitrual = new JRadioButton("打开     ");
		horizontalBox_7.add(rdbtnopenVitrual);
		
		rdbtncloseVitrual = new JRadioButton("关闭");
		horizontalBox_7.add(rdbtncloseVitrual);
		
		JLabel lblNewLabel_1 = new JLabel("   范围: 0~255   ");
		horizontalBox_6.add(lblNewLabel_1);
		
		horizontalBox_8 = Box.createHorizontalBox();
		horizontalBox_8.setBounds(550, 160, 463, 23);
		contentPane.add(horizontalBox_8);
		
		chckbxShutdownAnimation = new JCheckBox("关机动画   ");
		horizontalBox_8.add(chckbxShutdownAnimation);
		
		shutDownAnimationText = new JTextField();
		shutDownAnimationText.setEnabled(false);
		shutDownAnimationText.setColumns(10);
		horizontalBox_8.add(shutDownAnimationText);
		
		JButton btnChooserShutDownAnim = new JButton(".....");
		horizontalBox_8.add(btnChooserShutDownAnim);
		
		Box horizontalBox_9 = Box.createHorizontalBox();
		horizontalBox_9.setBounds(550, 190, 463, 23);
		contentPane.add(horizontalBox_9);
		
		chckbxShutDownRing = new JCheckBox("关机铃声   ");
		horizontalBox_9.add(chckbxShutDownRing);
		
		shutDownRingText = new JTextField();
		shutDownRingText.setEnabled(false);
		shutDownRingText.setColumns(10);
		horizontalBox_9.add(shutDownRingText);
		
		JButton btnChooserShutDownRing = new JButton(".....");
		horizontalBox_9.add(btnChooserShutDownRing);
		
		Box horizontalBox_10 = Box.createHorizontalBox();
		horizontalBox_10.setBounds(550, 310, 463, 23);
		contentPane.add(horizontalBox_10);
		
		chckbxLockWallpaper = new JCheckBox("锁屏壁纸   ");
		horizontalBox_10.add(chckbxLockWallpaper);
		
		lockWallpaperText = new JTextField();
		lockWallpaperText.setEnabled(false);
		lockWallpaperText.setColumns(10);
		horizontalBox_10.add(lockWallpaperText);
		
		JButton btnChooserLockWallpaper = new JButton(".....");
		horizontalBox_10.add(btnChooserLockWallpaper);
		
		Box horizontalBox_11 = Box.createHorizontalBox();
		horizontalBox_11.setBounds(30, 250, 415, 23);
		contentPane.add(horizontalBox_11);
		
		chckbxScreenOffTime = new JCheckBox("自动灭屏时间");
		horizontalBox_11.add(chckbxScreenOffTime);
		
		screenOffTimeText = new JTextField();
		screenOffTimeText.setEnabled(false);
		screenOffTimeText.setColumns(10);
		horizontalBox_11.add(screenOffTimeText);
		
		JLabel label_1 = new JLabel("   单位：毫秒   ");
		horizontalBox_11.add(label_1);
		
		Box horizontalBox_14 = Box.createHorizontalBox();
		horizontalBox_14.setBounds(800, 400, 200, 23);
		contentPane.add(horizontalBox_14);
		
		chckbxFontSize = new JCheckBox(" 字体大小      ");
		horizontalBox_14.add(chckbxFontSize);
		
		cbxFontSize = new JComboBox();
		cbxFontSize.setModel(new DefaultComboBoxModel(new String[] {"小", "普通", "大", "超大"}));
		cbxFontSize.setSelectedIndex(0);
		cbxFontSize.setEnabled(false);
		horizontalBox_14.add(cbxFontSize);
		
		Box horizontalBox_15 = Box.createHorizontalBox();
		horizontalBox_15.setBounds(550, 430, 200, 23);
		contentPane.add(horizontalBox_15);
		
		chckbxRam = new JCheckBox(" 运行内存      ");
		horizontalBox_15.add(chckbxRam);
		
		cbxRam = new JComboBox();
		cbxRam.setModel(new DefaultComboBoxModel(new String[] {"1G", "2G", "3G"}));
		cbxRam.setSelectedIndex(0);
		cbxRam.setEnabled(false);
		horizontalBox_15.add(cbxRam);
		
		Box horizontalBox_16 = Box.createHorizontalBox();
		horizontalBox_16.setBounds(800, 430, 200, 23);
		contentPane.add(horizontalBox_16);
		
		chckbxRom = new JCheckBox(" 手机存储      ");
		horizontalBox_16.add(chckbxRom);
		
		cbxRom = new JComboBox();
		cbxRom.setModel(new DefaultComboBoxModel(new String[] {"8G改16G", "8G改32G", "16G改32G", "16G改64G"}));
		cbxRom.setSelectedIndex(0);
		cbxRom.setEnabled(false);
		horizontalBox_16.add(cbxRom);
		
		Box horizontalBox_17 = Box.createHorizontalBox();
		horizontalBox_17.setBounds(550, 460, 200, 23);
		contentPane.add(horizontalBox_17);
		
		chckbxResolution = new JCheckBox(" 屏分辨率      ");
		horizontalBox_17.add(chckbxResolution);
		
		cbxResolution = new JComboBox();
		cbxResolution.setModel(new DefaultComboBoxModel(new String[] {"720x1280", "1080x1920", "1440x2560"}));
		cbxResolution.setSelectedIndex(0);
		cbxResolution.setEnabled(false);
		horizontalBox_17.add(cbxResolution);
		
		Box horizontalBox_18 = Box.createHorizontalBox();
		horizontalBox_18.setBounds(800, 460, 200, 23);
		contentPane.add(horizontalBox_18);
		
		chckbxTimeformat = new JCheckBox(" 时间格式      ");
		horizontalBox_18.add(chckbxTimeformat);
		
		cbxTimeformat = new JComboBox();
		cbxTimeformat.setModel(new DefaultComboBoxModel(new String[] {"24小时格式", "12小时格式"}));
		cbxTimeformat.setSelectedIndex(0);
		cbxTimeformat.setEnabled(false);
		horizontalBox_18.add(cbxTimeformat);
		
		Box horizontalBox_12 = Box.createHorizontalBox();
		horizontalBox_12.setBounds(30, 370, 415, 23);
		contentPane.add(horizontalBox_12);
		
		chckbxVolumeMax = new JCheckBox("铃声音量最大");
		horizontalBox_12.add(chckbxVolumeMax);
		
		rdbtnVolumeMax = new JRadioButton("最大     ");
		horizontalBox_12.add(rdbtnVolumeMax);
		
		rdbtnVolumeDefault = new JRadioButton("默认");
		horizontalBox_12.add(rdbtnVolumeDefault);
		
		Box horizontalBox_13 = Box.createHorizontalBox();
		horizontalBox_13.setBounds(30, 400, 415, 23);
		contentPane.add(horizontalBox_13);
		
		chckbxFalseTouch = new JCheckBox("防误触开关    ");
		horizontalBox_13.add(chckbxFalseTouch);
		
		rdbtnOpenFalseTounch = new JRadioButton("打开     ");
		horizontalBox_13.add(rdbtnOpenFalseTounch);
		
		rdbtnCloseFalseTounch = new JRadioButton("关闭");
		horizontalBox_13.add(rdbtnCloseFalseTounch);
		
		Box horizontalBox_19 = Box.createHorizontalBox();
		horizontalBox_19.setBounds(30, 430, 415, 23);
		contentPane.add(horizontalBox_19);
		
		chckbxCallVibration = new JCheckBox("来电振动        ");
		horizontalBox_19.add(chckbxCallVibration);
		
		rdbtnOpenCallVibration = new JRadioButton("打开     ");
		horizontalBox_19.add(rdbtnOpenCallVibration);
		
		rdbtnColseCallVibration = new JRadioButton("关闭");
		horizontalBox_19.add(rdbtnColseCallVibration);
		
		Box horizontalBox_20 = Box.createHorizontalBox();
		horizontalBox_20.setBounds(550, 220, 463, 23);
		contentPane.add(horizontalBox_20);
		
		chckbxThemeWallpaper = new JCheckBox("主题壁纸   ");
		horizontalBox_20.add(chckbxThemeWallpaper);
		
		themeWallpaperText = new JTextField();
		themeWallpaperText.setEnabled(false);
		themeWallpaperText.setColumns(10);
		horizontalBox_20.add(themeWallpaperText);
		
		JButton btnChooserDefaultWallpaper = new JButton(".....");
		horizontalBox_20.add(btnChooserDefaultWallpaper);
		
		Box horizontalBox_21 = Box.createHorizontalBox();
		horizontalBox_21.setBounds(550, 250, 463, 23);
		contentPane.add(horizontalBox_21);
		
		chckbxCallRing = new JCheckBox("来电铃声   ");
		horizontalBox_21.add(chckbxCallRing);
		
		defaultCallRingText = new JTextField();
		defaultCallRingText.setEnabled(false);
		defaultCallRingText.setColumns(10);
		horizontalBox_21.add(defaultCallRingText);
		
		Box horizontalBox_22 = Box.createHorizontalBox();
		horizontalBox_22.setBounds(30, 586, 420, 30);
		contentPane.add(horizontalBox_22);
		
		lblResult = new JLabel("");
		lblResult.setFont(new Font("宋体", Font.PLAIN, 16));
		lblResult.setForeground(Color.RED);
		horizontalBox_22.add(lblResult);
		
		horizontalBox_23 = Box.createHorizontalBox();
		horizontalBox_23.setBounds(550, 280, 463, 23);
		contentPane.add(horizontalBox_23);
		
		chckbxAddMedia = new JCheckBox("内置音视频");
		horizontalBox_23.add(chckbxAddMedia);
		
		addMediaText = new JTextField();
		addMediaText.setEnabled(false);
		addMediaText.setColumns(10);
		horizontalBox_23.add(addMediaText);
		
		btnChooserAddMedia = new JButton(".....");
		horizontalBox_23.add(btnChooserAddMedia);
		
		horizontalBox_24 = Box.createHorizontalBox();
		horizontalBox_24.setBounds(30, 160, 415, 23);
		contentPane.add(horizontalBox_24);
		
		chckbxWallPapersNum = new JCheckBox("墙纸数量");
		horizontalBox_24.add(chckbxWallPapersNum);
		
		wallPapersNumText = new JTextField();
		wallPapersNumText.setEnabled(false);
		wallPapersNumText.setColumns(10);
		horizontalBox_24.add(wallPapersNumText);
		
		JButton btnChooserCallRing = new JButton(".....");
		horizontalBox_21.add(btnChooserCallRing);
		
		horizontalBox_25 = Box.createHorizontalBox();
		horizontalBox_25.setBounds(30, 460, 415, 23);
		contentPane.add(horizontalBox_25);
		
		chckboxAutoBrightness = new JCheckBox("自动亮度        ");
		horizontalBox_25.add(chckboxAutoBrightness);
		
		rdbtnOpenAutoBrightness = new JRadioButton("打开     ");
		horizontalBox_25.add(rdbtnOpenAutoBrightness);
		
		rdbtnCloseAutoBrightness = new JRadioButton("关闭");
		horizontalBox_25.add(rdbtnCloseAutoBrightness);
		
		horizontalBox_26 = Box.createHorizontalBox();
		horizontalBox_26.setBounds(30, 490, 415, 23);
		contentPane.add(horizontalBox_26);
		
		chckboxBeauty = new JCheckBox("美颜开关        ");
		horizontalBox_26.add(chckboxBeauty);
		
		rdbtnOpenBeauty = new JRadioButton("打开     ");
		horizontalBox_26.add(rdbtnOpenBeauty);
		
		rdbtnCloseBeauty = new JRadioButton("关闭");
		horizontalBox_26.add(rdbtnCloseBeauty);
		
		horizontalBox_27 = Box.createHorizontalBox();
		horizontalBox_27.setBounds(30, 520, 415, 23);
		contentPane.add(horizontalBox_27);
		
		chckboxPocketMode = new JCheckBox("口袋模式        ");
		horizontalBox_27.add(chckboxPocketMode);
		
		rdbtnOpenPocketMode = new JRadioButton("打开     ");
		horizontalBox_27.add(rdbtnOpenPocketMode);
		
		rdbtnClosePocketMode = new JRadioButton("关闭");
		horizontalBox_27.add(rdbtnClosePocketMode);
		
		horizontalBox_28 = Box.createHorizontalBox();
		horizontalBox_28.setBounds(550, 490, 230, 23);
		contentPane.add(horizontalBox_28);
		
		chckbxRingtones = new JCheckBox(" 手机铃声      ");
		horizontalBox_28.add(chckbxRingtones);
		
		cbxRingtones = new JComboBox();
		cbxRingtones.setEnabled(false);
		horizontalBox_28.add(cbxRingtones);
		
		horizontalBox_29 = Box.createHorizontalBox();
		horizontalBox_29.setBounds(800, 490, 255, 23);
		contentPane.add(horizontalBox_29);
		
		chckbxNotifications = new JCheckBox(" 默认通知声");
		horizontalBox_29.add(chckbxNotifications);
		
		cbxNotifications = new JComboBox();
		cbxNotifications.setMaximumRowCount(6);
		cbxNotifications.setEnabled(false);
		horizontalBox_29.add(cbxNotifications);
		
		horizontalBox_30 = Box.createHorizontalBox();
		horizontalBox_30.setBounds(800, 520, 255, 23);
		contentPane.add(horizontalBox_30);
		
		chckbxMmsRing = new JCheckBox(" 信息提示声");
		horizontalBox_30.add(chckbxMmsRing);
		
		cbxMmsRing = new JComboBox();
		cbxMmsRing.setEnabled(false);
		horizontalBox_30.add(cbxMmsRing);
		
		horizontalBox_31 = Box.createHorizontalBox();
		horizontalBox_31.setBounds(550, 520, 230, 23);
		contentPane.add(horizontalBox_31);
		
		chckbxAlarms = new JCheckBox(" 默认闹铃声  ");
		horizontalBox_31.add(chckbxAlarms);
		
		cbxAlarms = new JComboBox();
		cbxAlarms.setEnabled(false);
		horizontalBox_31.add(cbxAlarms);
		
		horizontalBox_32 = Box.createHorizontalBox();
		horizontalBox_32.setBounds(650, 600, 400, 20);
		contentPane.add(horizontalBox_32);
		
		lblVerison = new JLabel("  Copyright©2015 PRIZE 1.0.2.2015.09.07_beta");
		lblVerison.setForeground(Color.BLACK);
		lblVerison.setFont(new Font("宋体", Font.PLAIN, 14));
		horizontalBox_32.add(lblVerison);
		
		horizontalBox_35 = Box.createHorizontalBox();
		horizontalBox_35.setBounds(30, 550, 415, 23);
		contentPane.add(horizontalBox_35);
		
		chckbxIntelligentwake = new JCheckBox("智能唤醒        ");
		horizontalBox_35.add(chckbxIntelligentwake);
		
		rdbtnOpenIntelligentwake = new JRadioButton("打开     ");
		horizontalBox_35.add(rdbtnOpenIntelligentwake);
		
		rdbtnCloseIntelligentwake = new JRadioButton("关闭");
		horizontalBox_35.add(rdbtnCloseIntelligentwake);
		
		horizontalBox_36 = Box.createHorizontalBox();
		horizontalBox_36.setBounds(630, 345, 220, 23);
		contentPane.add(horizontalBox_36);
		
		standbyWallpaperText = new JTextField();
		standbyWallpaperText.setColumns(10);
		horizontalBox_36.add(standbyWallpaperText);
		
		btnChooserStandbyWallpaper = new JButton(".....");
		horizontalBox_36.add(btnChooserStandbyWallpaper);
		
		horizontalBox_33 = Box.createHorizontalBox();
		horizontalBox_33.setBounds(30, 100, 415, 23);
		contentPane.add(horizontalBox_33);
		
		chckbxBrand = new JCheckBox("品牌名称");
		horizontalBox_33.add(chckbxBrand);
		
		brandText = new JTextField();
		brandText.setEnabled(false);
		brandText.setColumns(10);
		horizontalBox_33.add(brandText);
		
		horizontalBox_34 = Box.createHorizontalBox();
		horizontalBox_34.setBounds(30, 130, 415, 23);
		contentPane.add(horizontalBox_34);
		
		chckbxCpu = new JCheckBox("CPU名称");
		horizontalBox_34.add(chckbxCpu);
		
		cpuText = new JTextField();
		cpuText.setEnabled(false);
		cpuText.setColumns(10);
		horizontalBox_34.add(cpuText);
		
		horizontalBox_37 = Box.createHorizontalBox();
		horizontalBox_37.setBorder(new LineBorder(Color.LIGHT_GRAY));
		horizontalBox_37.setBounds(550, 340, 505, 54);
		contentPane.add(horizontalBox_37);
		
		chckbxStandbyWallpaper = new JCheckBox("待机壁纸");
		horizontalBox_37.add(chckbxStandbyWallpaper);
		
		horizontalBox_38 = Box.createHorizontalBox();
		horizontalBox_38.setBounds(860, 345, 190, 23);
		contentPane.add(horizontalBox_38);
		
		lblNewLabel_2 = new JLabel(" 已有壁纸 ");
		horizontalBox_38.add(lblNewLabel_2);
		
		cbxStandWallpaper = new JComboBox();
		horizontalBox_38.add(cbxStandWallpaper);
		
		Box horizontalBox_39 = Box.createHorizontalBox();
		horizontalBox_39.setBounds(630, 370, 400, 23);
		contentPane.add(horizontalBox_39);
		
		rdbtnAddWallpaper = new JRadioButton("增加   ");
		horizontalBox_39.add(rdbtnAddWallpaper);
		
		rdbtnUpdateWallpaper = new JRadioButton(" 修改   ");
		horizontalBox_39.add(rdbtnUpdateWallpaper);
		
		rdbtnDeleteWallpaper = new JRadioButton(" 删除   ");
		horizontalBox_39.add(rdbtnDeleteWallpaper);
		rdbtnDefaultWallpaper = new JRadioButton(" 默认待机壁纸   ");
		horizontalBox_39.add(rdbtnDefaultWallpaper);
		
		horizontalBox_40 = Box.createHorizontalBox();
		horizontalBox_40.setBounds(30, 310, 250, 23);
		contentPane.add(horizontalBox_40);
		
		chckbxVirtualKeyHide = new JCheckBox("虚拟按键隐藏");
		horizontalBox_40.add(chckbxVirtualKeyHide);
		
		rdbtnopenVitrualHide = new JRadioButton("打开     ");
		horizontalBox_40.add(rdbtnopenVitrualHide);
		
		rdbtncloseVitrualHide = new JRadioButton("关闭");
		horizontalBox_40.add(rdbtncloseVitrualHide);
		
		Box horizontalBox_41 = Box.createHorizontalBox();
		horizontalBox_41.setBounds(285, 310, 150, 23);
		contentPane.add(horizontalBox_41);
		
		JLabel lblNewLabel_3 = new JLabel("偏移量 ");
		horizontalBox_41.add(lblNewLabel_3);
		
		offsetText = new JTextField();
		horizontalBox_41.add(offsetText);
		offsetText.setColumns(10);
		
		Document bootPathTextDT = bootPathText.getDocument();
		bootPathTextDT.addDocumentListener(new TextFieldDocumentListener(bootPathText));

		Document bootPathText2DT = bootPathText2.getDocument();
		bootPathText2DT.addDocumentListener(new TextFieldDocumentListener(bootPathText2));

		Document versionTextDT = versionText.getDocument();
		versionTextDT.addDocumentListener(new TextFieldDocumentListener(versionText));

		Document bootAnimationTextDT = bootAnimationText.getDocument();
		bootAnimationTextDT.addDocumentListener(new TextFieldDocumentListener(bootAnimationText));

		Document logoTextDT = logoText.getDocument();
		logoTextDT.addDocumentListener(new TextFieldDocumentListener(logoText));

		Document ringTextDT = bootRingText.getDocument();
		ringTextDT.addDocumentListener(new TextFieldDocumentListener(bootRingText));

		Document nameTextDT = nameText.getDocument();
		nameTextDT.addDocumentListener(new TextFieldDocumentListener(nameText));

		Document screenLightTextDT = screenLightText.getDocument();
		screenLightTextDT.addDocumentListener(new TextFieldDocumentListener(screenLightText));

		Document screenOffTimeTextDT = screenOffTimeText.getDocument();
		screenOffTimeTextDT.addDocumentListener(new TextFieldDocumentListener(screenOffTimeText));
		
		Document shutDownAnimationTextDT = shutDownAnimationText.getDocument();
		shutDownAnimationTextDT.addDocumentListener(new TextFieldDocumentListener(shutDownAnimationText));
		Document shutDownRingTextDT = shutDownRingText.getDocument();
		shutDownRingTextDT.addDocumentListener(new TextFieldDocumentListener(shutDownRingText));
		Document lockWallpaperTextDT = lockWallpaperText.getDocument();
		lockWallpaperTextDT.addDocumentListener(new TextFieldDocumentListener(lockWallpaperText));
		Document defaultWallpaperTextDT = themeWallpaperText.getDocument();
		defaultWallpaperTextDT.addDocumentListener(new TextFieldDocumentListener(themeWallpaperText));
		Document defaultCallRingTextDT = defaultCallRingText.getDocument();
		defaultCallRingTextDT.addDocumentListener(new TextFieldDocumentListener(defaultCallRingText));
		Document addMediaTextTextDT = addMediaText.getDocument();
		addMediaTextTextDT.addDocumentListener(new TextFieldDocumentListener(addMediaText));
		Document wallPapersNumTextDT = wallPapersNumText.getDocument();
		wallPapersNumTextDT.addDocumentListener(new TextFieldDocumentListener(wallPapersNumText));
		Document brandTextTextDT = brandText.getDocument();
		brandTextTextDT.addDocumentListener(new TextFieldDocumentListener(brandText));
		Document cpuTextDT = cpuText.getDocument();
		cpuTextDT.addDocumentListener(new TextFieldDocumentListener(cpuText));
		Document standbyWallpaperTextDT = standbyWallpaperText.getDocument();
		standbyWallpaperTextDT.addDocumentListener(new TextFieldDocumentListener(standbyWallpaperText));
		
		btnChooserBootPath.addActionListener(new BtnChooserActionListener(bootPathText, "选择项目的根路径"));
		btnChooserBootPath2.addActionListener(new BtnChooserActionListener(bootPathText2, "选择客户的根路径"));
		btnChooserBootAnim.addActionListener(new BtnChooserActionListener(bootAnimationText, "选择开机动画"));
		btnChooserLogo.addActionListener(new BtnChooserActionListener(logoText, "选择开机Logo"));
		btnChooserBootRing.addActionListener(new BtnChooserActionListener(bootRingText, "选择开机铃声"));
		btnChooserShutDownAnim.addActionListener(new BtnChooserActionListener(shutDownAnimationText, "选择关机动画"));
		btnChooserShutDownRing.addActionListener(new BtnChooserActionListener(shutDownRingText, "选择关机铃声"));
		btnChooserLockWallpaper.addActionListener(new BtnChooserActionListener(lockWallpaperText, "选择锁屏壁纸"));
		btnChooserDefaultWallpaper.addActionListener(new BtnChooserActionListener(themeWallpaperText, "选择默认壁纸"));
		btnChooserCallRing.addActionListener(new BtnChooserActionListener(defaultCallRingText, "选择来电铃声"));
		btnChooserAddMedia.addActionListener(new BtnChooserActionListener(addMediaText, "添加內置音视频"));
		btnChooserStandbyWallpaper.addActionListener(new BtnChooserActionListener(standbyWallpaperText, "选择待机壁纸"));
		
		chckbxVersion.addChangeListener(new CheckBoxChangeListener(chckbxVersion, versionText));
		chckbxBootAnimation.addChangeListener(new CheckBoxChangeListener(chckbxBootAnimation, bootAnimationText));
		chckbxLogo.addChangeListener(new CheckBoxChangeListener(chckbxLogo, logoText));
		chckbxBootRing.addChangeListener(new CheckBoxChangeListener(chckbxBootRing, bootRingText));
		chckbxName.addChangeListener(new CheckBoxChangeListener(chckbxName, nameText));
		chckbxScreenLight.addChangeListener(new CheckBoxChangeListener(chckbxScreenLight, screenLightText));
		chckbxScreenOffTime.addChangeListener(new CheckBoxChangeListener(chckbxScreenOffTime,screenOffTimeText));
		chckbxShutdownAnimation.addChangeListener(new CheckBoxChangeListener(chckbxShutdownAnimation,shutDownAnimationText));
		chckbxShutDownRing.addChangeListener(new CheckBoxChangeListener(chckbxShutDownRing,shutDownRingText));
		chckbxLockWallpaper.addChangeListener(new CheckBoxChangeListener(chckbxLockWallpaper,lockWallpaperText));
		chckbxThemeWallpaper.addChangeListener(new CheckBoxChangeListener(chckbxThemeWallpaper,themeWallpaperText));
		chckbxCallRing.addChangeListener(new CheckBoxChangeListener(chckbxCallRing,defaultCallRingText));
		chckbxBrand.addChangeListener(new CheckBoxChangeListener(chckbxBrand, brandText));
		chckbxCpu.addChangeListener(new CheckBoxChangeListener(chckbxCpu, cpuText));
		
		chckbxVirtualKeyHide.addChangeListener(new CheckBoxChangeListener(chckbxVirtualKeyHide));
		chckbxVirtualKey.addChangeListener(new CheckBoxChangeListener(chckbxVirtualKey));
		chckbxVolumeMax.addChangeListener(new CheckBoxChangeListener(chckbxVolumeMax));
		chckbxFalseTouch.addChangeListener(new CheckBoxChangeListener(chckbxFalseTouch));
		chckbxCallVibration.addChangeListener(new CheckBoxChangeListener(chckbxCallVibration));
		chckboxAutoBrightness.addChangeListener(new CheckBoxChangeListener(chckboxAutoBrightness));
		chckboxBeauty.addChangeListener(new CheckBoxChangeListener(chckboxBeauty));
		chckboxPocketMode.addChangeListener(new CheckBoxChangeListener(chckboxPocketMode));
		chckbxTheme.addChangeListener(new CheckBoxChangeListener(chckbxTheme, cbxTheme));
		chckbxFontSize.addChangeListener(new CheckBoxChangeListener(chckbxFontSize, cbxFontSize));
		chckbxRam.addChangeListener(new CheckBoxChangeListener(chckbxRam, cbxRam));
		chckbxRom.addChangeListener(new CheckBoxChangeListener(chckbxRom, cbxRom));
		chckbxResolution.addChangeListener(new CheckBoxChangeListener(chckbxResolution, cbxResolution));
		chckbxTimeformat.addChangeListener(new CheckBoxChangeListener(chckbxTimeformat, cbxTimeformat));
		chckbxAddMedia.addChangeListener(new CheckBoxChangeListener(chckbxAddMedia, addMediaText));
		chckbxWallPapersNum.addChangeListener(new CheckBoxChangeListener(chckbxWallPapersNum, wallPapersNumText));
		chckbxRingtones.addChangeListener(new CheckBoxChangeListener(chckbxRingtones,cbxRingtones));
		chckbxNotifications.addChangeListener(new CheckBoxChangeListener(chckbxNotifications,cbxNotifications ));
		chckbxMmsRing.addChangeListener(new CheckBoxChangeListener(chckbxMmsRing, cbxMmsRing));
		chckbxAlarms.addChangeListener(new CheckBoxChangeListener(chckbxAlarms, cbxAlarms));
		chckbxIntelligentwake.addChangeListener(new CheckBoxChangeListener(chckbxIntelligentwake));
		chckbxStandbyWallpaper.addChangeListener(new CheckBoxChangeListener(chckbxStandbyWallpaper));
		
		rdbtnopenVitrual.addActionListener(new RadioBtnActionListener(rdbtnopenVitrual));
		rdbtncloseVitrual.addActionListener(new RadioBtnActionListener(rdbtncloseVitrual));
		rdbtnVolumeMax.addActionListener(new RadioBtnActionListener(rdbtnVolumeMax));
		rdbtnVolumeDefault.addActionListener(new RadioBtnActionListener(rdbtnVolumeDefault));
		rdbtnOpenFalseTounch.addActionListener(new RadioBtnActionListener(rdbtnOpenFalseTounch));
		rdbtnCloseFalseTounch.addActionListener(new RadioBtnActionListener(rdbtnCloseFalseTounch));
		rdbtnOpenCallVibration.addActionListener(new RadioBtnActionListener(rdbtnOpenCallVibration));
		rdbtnColseCallVibration.addActionListener(new RadioBtnActionListener(rdbtnColseCallVibration));		
		rdbtnOpenAutoBrightness.addActionListener(new RadioBtnActionListener(rdbtnOpenAutoBrightness));
		rdbtnCloseAutoBrightness.addActionListener(new RadioBtnActionListener(rdbtnCloseAutoBrightness));
		rdbtnOpenBeauty.addActionListener(new RadioBtnActionListener(rdbtnOpenBeauty));
		rdbtnCloseBeauty.addActionListener(new RadioBtnActionListener(rdbtnCloseBeauty));
		rdbtnOpenPocketMode.addActionListener(new RadioBtnActionListener(rdbtnOpenPocketMode));
		rdbtnClosePocketMode.addActionListener(new RadioBtnActionListener(rdbtnClosePocketMode));
		rdbtnOpenIntelligentwake.addActionListener(new RadioBtnActionListener(rdbtnOpenIntelligentwake));
		rdbtnCloseIntelligentwake.addActionListener(new RadioBtnActionListener(rdbtnCloseIntelligentwake));
		rdbtnAddWallpaper.addActionListener(new RadioBtnActionListener(rdbtnAddWallpaper));
		rdbtnUpdateWallpaper.addActionListener(new RadioBtnActionListener(rdbtnUpdateWallpaper));
		rdbtnDeleteWallpaper.addActionListener(new RadioBtnActionListener(rdbtnDeleteWallpaper));
		rdbtnDefaultWallpaper.addActionListener(new RadioBtnActionListener(rdbtnDefaultWallpaper));
		rdbtnopenVitrualHide.addActionListener(new RadioBtnActionListener(rdbtnopenVitrualHide));
		rdbtncloseVitrualHide.addActionListener(new RadioBtnActionListener(rdbtncloseVitrualHide));
		
		virtualKeyGroup = new ButtonGroup();
		ringVolumeGroup = new ButtonGroup();
		falseTouchGroup = new ButtonGroup();
		callVibrationyGroup = new ButtonGroup();
		autoBrightnessGroup = new ButtonGroup();
		beautyGroup = new ButtonGroup();
		fingerPrintGroup = new ButtonGroup();
		intelligentwakeGroup=new ButtonGroup();
		standbyWallpaperGroup=new ButtonGroup();
		standbyWallpaperHideGroup=new ButtonGroup();
		
		virtualKeyGroup.add (rdbtnopenVitrual);
		virtualKeyGroup.add (rdbtncloseVitrual);
		
		ringVolumeGroup.add(rdbtnVolumeMax);		
		ringVolumeGroup.add(rdbtnVolumeDefault);
		
		falseTouchGroup.add(rdbtnOpenFalseTounch);		
		falseTouchGroup.add(rdbtnCloseFalseTounch);
		
		callVibrationyGroup.add(rdbtnOpenCallVibration);		
		callVibrationyGroup.add(rdbtnColseCallVibration);
		
		autoBrightnessGroup.add(rdbtnOpenAutoBrightness);
		autoBrightnessGroup.add(rdbtnCloseAutoBrightness);
		beautyGroup.add(rdbtnOpenBeauty);
		beautyGroup.add(rdbtnCloseBeauty);
		fingerPrintGroup.add(rdbtnOpenPocketMode);
		fingerPrintGroup.add(rdbtnClosePocketMode);
		intelligentwakeGroup.add(rdbtnOpenIntelligentwake);
		intelligentwakeGroup.add(rdbtnCloseIntelligentwake);
		standbyWallpaperGroup.add(rdbtnAddWallpaper);
		standbyWallpaperGroup.add(rdbtnUpdateWallpaper);
		standbyWallpaperGroup.add(rdbtnDeleteWallpaper);
		standbyWallpaperGroup.add(rdbtnDefaultWallpaper);
		
		standbyWallpaperHideGroup.add(rdbtnopenVitrualHide);
		standbyWallpaperHideGroup.add(rdbtncloseVitrualHide);
		
	}

	private class BtnOnclickActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					if (checkModifyCCondition()) {
						submitModify();
					}
				}
			}).start();

		}
	}

	private class RadioBtnActionListener implements ActionListener {

		private JRadioButton mJRadioButton;

		public RadioBtnActionListener(JRadioButton radioButton) {
			this.mJRadioButton = radioButton;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean ischecked = mJRadioButton.isSelected();

			if (mJRadioButton == rdbtnopenVitrual) {
				if (ischecked) {
					DataBean.isOpenVirtualKey = true;
				} else {
					DataBean.isOpenVirtualKey = false;
				}
			} else if (mJRadioButton == rdbtncloseVitrual) {
				if (ischecked) {
					DataBean.isOpenVirtualKey = false;
				} else {
					DataBean.isOpenVirtualKey = true;
				}
			}
			if (mJRadioButton == rdbtnVolumeMax) {
				if (ischecked) {
					DataBean.isVolumeMax=true;
				} else {
					DataBean.isVolumeMax=false;
				}
			} else if (mJRadioButton == rdbtnVolumeDefault) {
				if (ischecked) {
					DataBean.isVolumeMax=false;
				} else {
					DataBean.isVolumeMax=true;
				}
			} else if (mJRadioButton == rdbtnOpenFalseTounch) {
				if (ischecked) {
					DataBean.isOpenFalseTouch=true;
				} else {
					DataBean.isOpenFalseTouch=false;
				}
			} else if (mJRadioButton == rdbtnCloseFalseTounch) {
				if (ischecked) {
					DataBean.isOpenFalseTouch=false;
				} else {
					DataBean.isOpenFalseTouch=true;
				}
			} else if (mJRadioButton == rdbtnOpenCallVibration) {
				if (ischecked) {
					DataBean.isCallVibration=true;
				} else {
					DataBean.isCallVibration=false;
				}
			} else if (mJRadioButton == rdbtnColseCallVibration) {
				if (ischecked) {
					DataBean.isCallVibration=false;
				} else {
					DataBean.isCallVibration=true;
				}
			} else if(mJRadioButton==rdbtnOpenAutoBrightness){
				if (ischecked) {
					DataBean.autoBrightness=true;
				} else {
					DataBean.autoBrightness=false;
				}
			}else if(mJRadioButton==rdbtnCloseAutoBrightness){
				if (ischecked) {
					DataBean.autoBrightness=false;
				} else {
					DataBean.autoBrightness=true;
				}
			}else if(mJRadioButton==rdbtnOpenBeauty){
				if (ischecked) {
					DataBean.beauty=true;
				} else {
					DataBean.beauty=false;
				}
			}else if(mJRadioButton==rdbtnCloseBeauty){
				if (ischecked) {
					DataBean.beauty=false;
				} else {
					DataBean.beauty=true;
				}
			}else if(mJRadioButton==rdbtnOpenPocketMode){
				if (ischecked) {
					DataBean.pocketMode=true;
				} else {
					DataBean.pocketMode=false;
				}
			}else if(mJRadioButton==rdbtnClosePocketMode){
				if (ischecked) {
					DataBean.pocketMode=false;
				} else {
					DataBean.pocketMode=true;
				}
			}else if(mJRadioButton==rdbtnOpenIntelligentwake){
				if (ischecked) {
					DataBean.isIntelligentwake=true;
				} else {
					DataBean.isIntelligentwake=false;
				}
			}else if(mJRadioButton==rdbtnCloseIntelligentwake){
				if (ischecked) {
					DataBean.isIntelligentwake=false;
				} else {
					DataBean.isIntelligentwake=true;
				}
			}else if(mJRadioButton==rdbtnAddWallpaper){
				DataBean.standWallpaperTag=1;
			}else if(mJRadioButton==rdbtnUpdateWallpaper){
				DataBean.standWallpaperTag=2;
			}else if(mJRadioButton==rdbtnDeleteWallpaper){
				DataBean.standWallpaperTag=3;
			}else if(mJRadioButton==rdbtnDefaultWallpaper){
				DataBean.standWallpaperTag=4;
			}else if(mJRadioButton==rdbtnopenVitrualHide){
				if (ischecked) {
					DataBean.isOpenVirtualKeyHide=true;
				} else {
					DataBean.isOpenVirtualKeyHide=false;
				}
			}else if(mJRadioButton==rdbtncloseVitrualHide){
				if (ischecked) {
					DataBean.isOpenVirtualKeyHide=false;
				} else {
					DataBean.isOpenVirtualKeyHide=true;
				}
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
			if(mJTextField==bootPathText2){
				jfc.setCurrentDirectory(new File(Constant.PROJECT_BOOT_PATH+"\\prize_project\\pcba"));
			}
			int res = jfc.showOpenDialog(null);
			if (res == JFileChooser.APPROVE_OPTION) {
				File dir = jfc.getSelectedFile();
				mJTextField.setText(dir.getAbsolutePath().trim());
			}
			
			if(mJTextField==bootPathText2){
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						initData();
					}
				}).start();
			}
		}

	}
	
	/**
	 * 初始化
	 */
	@SuppressWarnings("unchecked")
	private void initData(){
		DataBean.oldVerisonNumber=ReadInfoUtil.getVersionNumber();
		versionText.setText(DataBean.oldVerisonNumber);
		//修改title
		String[] splits=Constant.CUSTOMER_BOOT_PATH.split("\\\\");
		if(splits!=null){
			String title=splits[splits.length-2]+"-"+splits[splits.length-1];
			setTitle(title);
		}
		
		List<String> ringtoneList=ModifyUtil.getRingList("ringtones");
		List<String> notificationsList=ModifyUtil.getRingList("notifications");
		List<String> alarmsList=ModifyUtil.getRingList("alarms");
		String[] standWallpaperArray=ReadInfoUtil.getAllStandbyWallpaper();
		if(ringtoneList!=null){
			for(int i=0;i<ringtoneList.size();i++){
				cbxRingtones.addItem(ringtoneList.get(i));
			}
		}
		if(notificationsList!=null){
			for(int i=0;i<notificationsList.size();i++){
				cbxNotifications.addItem(notificationsList.get(i));
				cbxMmsRing.addItem(notificationsList.get(i));
			}
		}
		if(alarmsList!=null){
			for(int i=0;i<alarmsList.size();i++){
				cbxAlarms.addItem(alarmsList.get(i));
			}
		}
		if(standWallpaperArray!=null){
			for(int i=0;i<standWallpaperArray.length;i++){
				cbxStandWallpaper.addItem(standWallpaperArray[i]);
			}
		}
		DataBean.productName=ReadInfoUtil.getDeviceName();
		DataBean.defaultScreenLight=ReadInfoUtil.getDefaultScreenLight();
		DataBean.screenOffTime=ReadInfoUtil.getScreenOffTime();
		DataBean.wallPapersNum=ReadInfoUtil.getWallPapersNum();
		DataBean.brandName=ReadInfoUtil.getBrandName();
		
		nameText.setText(DataBean.productName);
		screenLightText.setText(DataBean.defaultScreenLight);
		screenOffTimeText.setText(DataBean.screenOffTime);
		wallPapersNumText.setText(DataBean.wallPapersNum);
		brandText.setText(DataBean.brandName);
	}
	
	private class CheckBoxChangeListener implements ChangeListener {

		private JCheckBox mJCheckBox;
		private JTextField mJTextField;
		private JComboBox mJComboBox;
		
		public CheckBoxChangeListener(JCheckBox checkBox) {
			this.mJCheckBox = checkBox;
		}
		
		public CheckBoxChangeListener(JCheckBox checkBox, JTextField textField) {
			this.mJCheckBox = checkBox;
			this.mJTextField = textField;
		}

		public CheckBoxChangeListener(JCheckBox checkBox, JComboBox comboBox) {
			this.mJCheckBox = checkBox;
			this.mJComboBox = comboBox;
		}
		
		@Override
		public void stateChanged(ChangeEvent arg0) {

			boolean ischecked = mJCheckBox.isSelected();
			String text = null;
			if (mJTextField != null) {
				if (ischecked) {
					mJTextField.setEnabled(true);
				} else {
					mJTextField.setEnabled(false);
				}
				text = mJTextField.getText();
			}

			if (mJCheckBox == chckbxVersion) {
				DataBean.isModifyVersion = ischecked;
			} else if (mJCheckBox == chckbxLogo) {
				DataBean.isModifyLogo = ischecked;
			} else if (mJCheckBox == chckbxBootAnimation) {
				DataBean.isModifyBootAnim = ischecked;
			} else if (mJCheckBox == chckbxBootRing) {
				DataBean.isModifyBootRing = ischecked;
			} else if (mJCheckBox == chckbxName) {
				DataBean.isModifyName = ischecked;
			} else if (mJCheckBox == chckbxScreenLight) {
				DataBean.isModifyScreenLight = ischecked;
			} else if (mJCheckBox == chckbxScreenOffTime) {
				DataBean.isModifyScreenOffTime = ischecked;
			}else if(mJCheckBox==chckbxBrand){
				DataBean.isModifyBrandName = ischecked;
			}else if(mJCheckBox==chckbxCpu){
				DataBean.isModifyCpuName = ischecked;
				//*** JComboBox***//
			} else if(mJCheckBox==chckbxRingtones){
				DataBean.isModifyRingtones=ischecked;
				cbxRingtones.setEnabled(ischecked);
			} else if(mJCheckBox==chckbxNotifications){
				DataBean.isModifyNotificationsRing=ischecked;
				cbxNotifications.setEnabled(ischecked);
			} else if(mJCheckBox==chckbxMmsRing){
				DataBean.isModifyMmsRing=ischecked;
				cbxMmsRing.setEnabled(ischecked);
			} else if(mJCheckBox==chckbxAlarms){
				DataBean.isModifyAlarmsRing=ischecked;
				cbxAlarms.setEnabled(ischecked);
			} else if(mJCheckBox==chckbxTheme){
				DataBean.isModifyTheme = ischecked;
				cbxTheme.setEnabled(ischecked);
			}else if(mJCheckBox==chckbxFontSize){
				DataBean.isModifyFontSize = ischecked;
				cbxFontSize.setEnabled(ischecked);
			}else if(mJCheckBox==chckbxRam){
				DataBean.isModifyRamSize = ischecked;
				cbxRam.setEnabled(ischecked);
			} else if (mJCheckBox == chckbxRom) {
				DataBean.isModifyRomSize = ischecked;
				cbxRom.setEnabled(ischecked);
			} else if (mJCheckBox == chckbxResolution) {
				DataBean.isModifyTpResolution = ischecked;
				cbxResolution.setEnabled(ischecked);
			} else if (mJCheckBox == chckbxTimeformat) {
				DataBean.isModifyTimeformat = ischecked;
				cbxTimeformat.setEnabled(ischecked);
			} else if(mJCheckBox==chckbxVirtualKey){
				DataBean.isModifyVirtualKey = ischecked;
			}else if(mJCheckBox==chckbxVolumeMax){
				DataBean.isModifyVolumeMax = ischecked;
			}else if(mJCheckBox==chckbxFalseTouch){
				DataBean.isModifyFalseTouch = ischecked;
			}else if(mJCheckBox==chckbxCallVibration){
				DataBean.isModifyCallVibration = ischecked;
			} else if (mJCheckBox == chckbxLockWallpaper) {
				DataBean.isModifyLockWallpaper = ischecked;
			} else if (mJCheckBox == chckbxCallRing) {
				DataBean.isModifyCallRing = ischecked;
			}else if(mJCheckBox ==chckbxShutdownAnimation){
				DataBean.isModifyShutDownAnim= ischecked;
			}else if(mJCheckBox ==chckbxShutDownRing){
				DataBean.isModifyShutDownRing= ischecked;
			}else if(mJCheckBox==chckbxAddMedia){
				DataBean.isModifyAddMedia=ischecked;
			}else if(mJCheckBox==chckbxWallPapersNum){
				DataBean.isModifyWallPapersNum=ischecked;
			}else if(mJCheckBox==chckboxAutoBrightness){
				DataBean.isModifyAutoBrightness=ischecked;
			}else if(mJCheckBox==chckboxBeauty){
				DataBean.isModifyBeauty=ischecked;
			}else if(mJCheckBox==chckboxPocketMode){
				DataBean.isModifyPocketMode=ischecked;
			}else if(mJCheckBox==chckbxIntelligentwake){
				DataBean.isModifyIntelligentwake=ischecked;
			}else if(mJCheckBox==chckbxStandbyWallpaper){
				DataBean.isModifyStandbyWallpaper=ischecked;
			}else if(mJCheckBox==chckbxVirtualKeyHide){
				DataBean.isModifyVirtualKeyHide=ischecked;
			}else{
				
			}
				
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
			lblResult.setText("");
			if (mJTextField == bootPathText2) {
				Constant.CUSTOMER_BOOT_PATH = strText;
			} else if (mJTextField == bootPathText) {
				Constant.PROJECT_BOOT_PATH = strText;
			} else if (mJTextField == versionText) {
				DataBean.newVerisonNumber = strText;
			} else if (mJTextField == logoText) {
				DataBean.bootLogo = strText;
			} else if (mJTextField == bootAnimationText) {
				DataBean.bootAnim = strText;
			} else if (mJTextField == bootRingText) {
				DataBean.bootRing = strText;
			} else if (mJTextField == nameText) {
				DataBean.productName = strText;
			} else if (mJTextField == screenLightText) {
				DataBean.defaultScreenLight = strText;
			}else if (mJTextField ==shutDownAnimationText ) {
				DataBean.shutDownAnim = strText;
			}else if (mJTextField ==shutDownRingText ) {
				DataBean.shutDownRing = strText;
			}else if (mJTextField ==lockWallpaperText ) {
				DataBean.lockWallpaper = strText;
			}else if (mJTextField ==themeWallpaperText ) {
				DataBean.themeWallpaper = strText;
			}else if (mJTextField == defaultCallRingText) {
				DataBean.callRing= strText;
			} else if (mJTextField == screenOffTimeText){
				DataBean.screenOffTime = strText;
			}else if(mJTextField==addMediaText){
				DataBean.addMediaPath=strText;
			}else if(mJTextField==wallPapersNumText){
				DataBean.wallPapersNum=strText;
			}else if(mJTextField==brandText){
				DataBean.brandName=strText;
			}else if(mJTextField==cpuText){
				DataBean.cpuName=strText;
			}else if(mJTextField==standbyWallpaperText){
				DataBean.standbyWallpaper=strText;
			}else{
				
			}

		}
	}

	/**
	 * 获取修改的主题包名
	 * 
	 * @param index
	 */
	private void getThemePackageName(int index) {
		switch (index) {
		case 0:
			DataBean.themePackageName = "com.coco.themes.suiyueruge20150707";
			break;
		case 1:
			DataBean.themePackageName = "com.coco.themes.anyexingkong20150707";
			break;
		case 2:
			DataBean.themePackageName = "com.coco.themes.binfensecai20150707";
			break;
		case 3:
			DataBean.themePackageName = "com.coco.themes.xiarixinqing20150707";
			break;
		case 4:
			DataBean.themePackageName = "com.coco.themes.xiatiandefeng20150707";
			break;
		case 5:
			DataBean.themePackageName = "com.coco.themes.xingguangcuican20150707";
			break;
		default:
			DataBean.themePackageName = "com.coco.themes.suiyueruge20150707";
			break;
		}
	}
	
	private String getFontSize(int index) {
		String resultSize = "1.0f";
		switch (index) {
		case 0:
			resultSize = "0.9f";
			break;
		case 1:
			resultSize = "1.0f";
			break;
		case 2:
			resultSize = "1.1f";
			break;
		case 3:
			resultSize = "1.2f";
			break;
		}
		return resultSize;
	}

	/**
	 * 
	 * @return
	 */
	private boolean checkModifyCCondition() {
		if (Constant.PROJECT_BOOT_PATH == null || Constant.PROJECT_BOOT_PATH.length() <= 0) {
			customDialog.setTitle("提示信息");
			customDialog.setMessage("项目根目录不能为空");
			customDialog.pack();
			customDialog.setLocationRelativeTo(ModifyToolMain.this);
			customDialog.setVisible(true);
			return false;
		} else {
			File file = new File(Constant.PROJECT_BOOT_PATH);
			if (!file.exists()) {
				customDialog.setTitle("提示信息");
				customDialog.setMessage("项目根目录不存在");
				customDialog.pack();
				customDialog.setLocationRelativeTo(ModifyToolMain.this);
				customDialog.setVisible(true);
				return false;
			}
		}

		if (Constant.CUSTOMER_BOOT_PATH == null || Constant.CUSTOMER_BOOT_PATH.length() <= 0) {
			customDialog.setTitle("提示信息");
			customDialog.setMessage("客户根目录不能为空");
			customDialog.pack();
			customDialog.setLocationRelativeTo(ModifyToolMain.this);
			customDialog.setVisible(true);
			return false;
		} else {
			File file = new File(Constant.CUSTOMER_BOOT_PATH);
			if (!file.exists()) {
				customDialog.setTitle("提示信息");
				customDialog.setMessage("客户根目录不存在");
				customDialog.pack();
				customDialog.setLocationRelativeTo(ModifyToolMain.this);
				customDialog.setVisible(true);
				return false;
			}
		}
		
		if(DataBean.isModifyWallPapersNum && !StringUtil.isDigital(DataBean.wallPapersNum)){
			customDialog.setTitle("提示信息");
			customDialog.setMessage("墙纸数量只能为数字");
			customDialog.pack();
			customDialog.setLocationRelativeTo(ModifyToolMain.this);
			customDialog.setVisible(true);
			return false;
		}
		return true;
	}
	
	/**
	 * 提交需要修改的信息
	 * 
	 * @return 是否修改成功
	 */
	private boolean submitModify() {
		lblResult.setText("开始修改....");
		btnOk.setEnabled(false);
		if (DataBean.isModifyVersion) {
			lblResult.setText("当前状态:正在版本号....");
			ModifyUtil.updateVersion(DataBean.newVerisonNumber);
		}
		if (DataBean.isModifyLogo) {
			lblResult.setText("当前状态:正在修改开机Logo....");
			ModifyUtil.updateBootLogo();
		}
		if (DataBean.isModifyBootAnim) {
			lblResult.setText("当前状态:正在修改开机动画....");
			ModifyUtil.updateBootAnim();
		}
		if (DataBean.isModifyBootRing) {
			lblResult.setText("当前状态:正在修改开机铃声....");
			ModifyUtil.updateBootRing();
		}
		if (DataBean.isModifyName) {
			lblResult.setText("当前状态:正在修改设备、Wifi、热点名称....");
			ModifyUtil.updateProductName();
		}
		if (DataBean.isModifyTheme) {
			lblResult.setText("当前状态:正在修改主题....");
			getThemePackageName(cbxTheme.getSelectedIndex());
			ModifyUtil.updateTheme(DataBean.themePackageName);
		}
		if (DataBean.isModifyScreenLight) {
			lblResult.setText("当前状态:正在修改屏幕亮度....");
			String strText = DataBean.defaultScreenLight;
			if (strText != null && strText.length() > 0) {
				boolean isShowDialog = false;
				if (!StringUtil.isDigital(strText)) {
					isShowDialog = true;
				} else {
					int value = Integer.valueOf(strText);
					if (value > 255 || value < 0) {
						isShowDialog = true;
					}
				}
				if (isShowDialog) {
					customDialog.setTitle("提示信息");
					customDialog.setMessage("屏幕的亮度只能是数值,范围:0-255");
					customDialog.pack();
					customDialog.setLocationRelativeTo(ModifyToolMain.this);
					customDialog.setVisible(true);
				} else {
					int light = Integer.valueOf(strText);
					ModifyUtil.updateDefaultLight(light,false);
				}
			}

		}
		if (DataBean.isModifyVirtualKey) {
			lblResult.setText("当前状态:正在修改虚拟键....");
			ModifyUtil.updateVirtualKey(DataBean.isOpenVirtualKey);
		}
		if (DataBean.isModifyScreenOffTime && DataBean.screenOffTime != null) {
			lblResult.setText("当前状态:正在修改灭屏时间....");
			ModifyUtil.updateScreenOffTime(DataBean.screenOffTime);
		}
		if(DataBean.isModifyVolumeMax){
			lblResult.setText("当前状态:正在修改音量最大值....");
			ModifyUtil.updateVolumeMax(DataBean.isVolumeMax);
		}
		if (DataBean.isModifyFalseTouch) {
			lblResult.setText("当前状态:正在修改防误触....");
			ModifyUtil.updateFalseTouch(DataBean.isOpenFalseTouch);
		}
		if(DataBean.isModifyCallVibration){
			lblResult.setText("当前状态:正在修改来电震动....");
			ModifyUtil.updateCallVibration(DataBean.isCallVibration);
		}
		if(DataBean.isModifyLockWallpaper){
			lblResult.setText("当前状态:正在修改锁屏壁纸....");
			ModifyUtil.updateLockWallpaper(DataBean.lockWallpaper);
		}
		if(DataBean.isModifyCallRing){
			lblResult.setText("当前状态:正在修改来电铃声....");
			ModifyUtil.updateCallRing(DataBean.callRing);
		}
		if (DataBean.isModifyFontSize) {
			lblResult.setText("当前状态:正在修改字体大小....");
			DataBean.fontSize = getFontSize(cbxFontSize.getSelectedIndex());
			ModifyUtil.updateFontSize(DataBean.fontSize);
		}
		if(DataBean.isModifyShutDownRing){
			lblResult.setText("当前状态:正在修改关机铃声....");
			ModifyUtil.updateShutDownRing(DataBean.shutDownRing);
		}
	    if(DataBean.isModifyShutDownAnim){
	    	lblResult.setText("当前状态:正在修改关机动画....");
			ModifyUtil.updateShutDownAnim(DataBean.shutDownAnim);
		}
		if (DataBean.isModifyTpResolution) {
			lblResult.setText("当前状态:正在修改屏幕分辨率....");
			DataBean.tpResolution = cbxResolution.getSelectedItem().toString();
			ModifyUtil.updateTpResolution(DataBean.tpResolution);
		}
		if (DataBean.isModifyTimeformat) {
			lblResult.setText("当前状态:正在修改时间格式....");
			if (cbxTimeformat.getSelectedItem().toString().equals("24小时格式")) {
				DataBean.timeformat = "24";
			} else {
				DataBean.timeformat = "12";
			}
			ModifyUtil.updateTimeformat(DataBean.timeformat);
		}
		if (DataBean.isModifyRomSize) {
			lblResult.setText("当前状态:正在修改手机存储大小....");
			if (cbxRom.getSelectedItem().toString().equals("8G改16G")) {
				DataBean.romSize = "816";
			} else if (cbxRom.getSelectedItem().toString().equals("8G改32G")) {
				DataBean.romSize = "832";
			} else if (cbxRom.getSelectedItem().toString().equals("16G改32G")) {
				DataBean.romSize = "1632";
			} else {
				DataBean.romSize = "1664";
			}
			ModifyUtil.updateRom(DataBean.romSize);
		}
		if (DataBean.isModifyRamSize) {
			lblResult.setText("当前状态:正在修改运行内存大小....");
			DataBean.ramSize = cbxRam.getSelectedItem().toString();
			ModifyUtil.updateRam(DataBean.ramSize);
		}
		if(DataBean.isModifyAddMedia){
			lblResult.setText("当前状态:正在内置音视频....");
			ModifyUtil.updateAddMeida(DataBean.addMediaPath);
		}
		if(DataBean.isModifyWallPapersNum){
			lblResult.setText("当前状态:正在修改墙纸数量....");
			ModifyUtil.updateWallPapersNum(DataBean.wallPapersNum);
		}
		if(DataBean.isModifyAutoBrightness){
			lblResult.setText("当前状态:正在修改自动亮度开关....");
			ModifyUtil.updateDefaultLight(77,DataBean.autoBrightness);
		}
		if(DataBean.isModifyBeauty){
			lblResult.setText("当前状态:正在修改美颜开关....");
			ModifyUtil.updateBeauty(DataBean.beauty);
		}
		if(DataBean.isModifyPocketMode){
			lblResult.setText("当前状态:正在修改口袋模式....");
			ModifyUtil.updatePocketMode(DataBean.pocketMode);
		}
		if(DataBean.isModifyRingtones){
			lblResult.setText("当前状态:正在修改默认的来电铃声....");
			DataBean.Ringtones=cbxRingtones.getSelectedItem().toString();
			ModifyUtil.updateRingTones(DataBean.Ringtones);
		}
		if(DataBean.isModifyNotificationsRing){
			lblResult.setText("当前状态:正在修改默认的提示音....");
			DataBean.NotificationsRing=cbxNotifications.getSelectedItem().toString();
			ModifyUtil.updateNotificationsRing(DataBean.NotificationsRing);
		}
		if(DataBean.isModifyAlarmsRing){
			lblResult.setText("当前状态:正在修改默认的闹钟提示音....");
			DataBean.AlarmsRing=cbxAlarms.getSelectedItem().toString();
			ModifyUtil.updateAlarmsRing(DataBean.AlarmsRing);
		}
		if(DataBean.isModifyMmsRing){
			lblResult.setText("当前状态:正在修改默认的短信提示音....");
			DataBean.MmsRing=cbxMmsRing.getSelectedItem().toString();
			ModifyUtil.updateMmsRing(DataBean.MmsRing);
		}
		if(DataBean.isModifyBrandName){
			lblResult.setText("当前状态:正在修改品牌名称....");
			ModifyUtil.updateBrandName(DataBean.brandName);
		}
		
		if(DataBean.isModifyCpuName){
			lblResult.setText("当前状态:正在修改CPU名称....");
			ModifyUtil.updateCpuName(DataBean.cpuName);
		}
		
		if(DataBean.isModifyIntelligentwake){
			lblResult.setText("当前状态:正在修改智能唤醒的开关....");
			System.out.println("isIntelligentwake:"+DataBean.isIntelligentwake);
			ModifyUtil.updateIntelligentwake(DataBean.isIntelligentwake);
		}
		
		if(DataBean.isModifyStandbyWallpaper){
			lblResult.setText("当前状态:正在修改待机壁纸....");		
			String interWallPaper=cbxStandWallpaper.getSelectedItem().toString();
			ModifyUtil.updateStandbyWallpaper(DataBean.standbyWallpaper,interWallPaper,DataBean.standWallpaperTag);
		}
		
		if(DataBean.isModifyVirtualKeyHide){
			lblResult.setText("当前状态:正在修改虚拟按键隐藏....");
			String offset=offsetText.getText().trim();
			ModifyUtil.updateVirtualButton(DataBean.isOpenVirtualKeyHide,offset);
		}
		
		lblResult.setText("修改成功");
		btnOk.setEnabled(true);
		return true;
	}
}

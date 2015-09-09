package com.prize.modifytool.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import com.prize.modifytool.bean.DataBean;
import com.prize.modifytool.constant.Constant;

public class ModifyUtil {

	private static String projectAllAudioPath = Constant.PROJECT_BOOT_PATH
			+ "\\frameworks\\base\\data\\sounds\\AllAudio.mk";
	private static String customerSoundPath = Constant.CUSTOMER_BOOT_PATH + "\\frameworks\\base\\data\\sounds";
	private static String customerAllAudioPath = customerSoundPath + "\\AllAudio.mk";
	
	private final static String VERSION_TAG = "BUILD_DISPLAY_ID_CUSTOM";
	private final static String BOOT_LOGO = "BOOT_LOGO";
	private static String projectConfig_path;
	private static String systemProp_path;
	
	/**
	 * 修改虚拟按键隐藏开关
	 * @param isOpen [是否打开开关],offset 偏移量
	 * @return
	 */
	public static boolean updateVirtualButton(boolean isOpen,String offset){
		System.out.println("----->updateVirtualButton isOpen="+isOpen+" ,offset="+offset);
		String projectKey="PRIZE_SUPPORT_HIDING_NAVBAR";
		String projectValue="no";
		String systemKey="qemu.hw.mainkeys";
		String systemValue="1";
		String navigationKey="default_nav_bar_style";
		String navigationValue="1";
		String dimensMaterialKey="touch_offset_of_navbar";
		String dimensMaterialValue="0";
		if(isOpen){
			projectValue="yes";
			systemValue="0";
			navigationValue="0";
			dimensMaterialValue=offset;
			copyVitualFile();
		}
		modifyProjectConfig(projectKey, projectValue);
		modifySystemPropValue(systemKey,systemValue);
		modifyNavigationBarValue(navigationKey,navigationValue);
		modifyDimensScreenValue(dimensMaterialKey,dimensMaterialValue);
		return true;
	}
	
	/**
	 * 修改待机壁纸操作
	 * @param exterWallPaper 外部壁纸
	 * @param interWallPaper 内部壁纸
	 * @param tag 操作符  1：增加   2：修改  3:删除已经存在的壁纸  4：默认待机壁纸
	 * @return
	 */
	public static boolean updateStandbyWallpaper(String exterWallPaper,String interWallPaper,int tag){
		System.out.println("----->updateStandbyWallpaper exterWallPaper="+exterWallPaper+" ,interWallPaper="+interWallPaper+" ,tag="+tag);
		switch(tag){
		case 1:
			addStandbyWallpaper(exterWallPaper);
			break;
		case 2:
			modifyStandbyWallpaper(exterWallPaper, interWallPaper);
			break;
		case 3:
			deleteStandbyWallpaper(interWallPaper);
			break;
		case 4:
			defaultStandbyWallpaper(interWallPaper);
			break;
			default:
				break;
		}
		return true;
	}
	
	/**
	 * 修改智能唤醒的开关
	 * @param isOpen
	 * @return
	 */
	public static boolean updateIntelligentwake(boolean isOpen){
		String customer_defaultXml_parent=Constant.CUSTOMER_BOOT_PATH+"\\frameworks\\base\\packages\\SettingsProvider\\res\\values";
		String customer_defaultXml=customer_defaultXml_parent+"\\defaults.xml";
		String project_defaultXml=Constant.PROJECT_BOOT_PATH+"\\frameworks\\base\\packages\\SettingsProvider\\res\\values\\defaults.xml";
		File file =new File(customer_defaultXml_parent);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			copyFile(project_defaultXml, customer_defaultXml, false);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = null;
			org.w3c.dom.Document document = null;
			db = dbf.newDocumentBuilder();
			document = db.parse(new File(customer_defaultXml));
			
			DOMReader reader = new DOMReader();
			Document df_document = reader.read(document);
			Element rootElement = df_document.getRootElement();
			List<Element> childList = rootElement.elements();
			if (childList == null || childList.size() <= 0) {
				return false;
			}
			for (int i = 0; i < childList.size(); i++) {
				Element childElement = childList.get(i);
				Attribute attribute = childElement.attribute("name");
				if (attribute != null && attribute.getValue().equals("def_prize_sleep_gesture")) {
					if(isOpen){
						childElement.setText("true");
					}else{
						childElement.setText("false");
					}
					break;
				}
			}

			// 提交修改的内容
			try {
				XMLWriter writer = new XMLWriter(new FileWriter(new File(customer_defaultXml)));
				writer.write(df_document);
				writer.close();
			} catch (Exception ex) {
				MyLogger.print("updateIntelligentwake--->"+ex.getMessage());
				ex.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			MyLogger.print("updateIntelligentwake--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateIntelligentwake--->"+e.getMessage());
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			MyLogger.print("updateIntelligentwake--->"+e.getMessage());
			e.printStackTrace();
		}catch (SAXException e) {
			MyLogger.print("updateIntelligentwake--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 修改Cpu名称
	 * @param cpuName
	 * @return
	 */
	public static boolean updateCpuName(String cpuName){
		String customer_cpuName_parent=Constant.CUSTOMER_BOOT_PATH+"\\kernel-3.10\\arch\\arm64\\kernel";
		String customer_cpuName_path=customer_cpuName_parent+"\\setup.c";
		String project_cpuName_path=Constant.PROJECT_BOOT_PATH+"\\kernel-3.10\\arch\\arm64\\kernel\\setup.c";
		File file=new File(customer_cpuName_parent);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			copyFile(project_cpuName_path, customer_cpuName_path, false);
			FileInputStream fis = new FileInputStream(customer_cpuName_path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				//seq_printf(m, "Hardware\t: %s\n", machine_name);
				if (line.contains("seq_printf(m, \"Hardware")) {
					System.out.println("aaa:"+line);
					line=line.replace("machine_name", "\""+cpuName +"\"");
				}
				sb.append(line + "\n");
			}
			sb.setLength(sb.length() - 1);		
			OutputStreamWriter outstreamWriter = new OutputStreamWriter(new FileOutputStream(customer_cpuName_path)); 
			BufferedWriter writer = new BufferedWriter(outstreamWriter);
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			MyLogger.print("updateCpuName--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateCpuName--->"+e.getMessage());
			e.printStackTrace();
		}
		
/*		// 修改settting中对应的默认值 字段名 cpu_core_number
		String prizeSetting_cpu_core_number_default = Constant.CUSTOMER_BOOT_PATH
				+ "\\packages\\apps\\PrizeSettings\\res\\values\\strings.xml";
		String prizeSetting_cpu_core_number_rTW = Constant.CUSTOMER_BOOT_PATH
				+ "\\packages\\apps\\PrizeSettings\\res\\values-zh-rTW\\strings.xml";
		String prizeSetting_cpu_core_number_rCN = Constant.CUSTOMER_BOOT_PATH
				+ "\\packages\\apps\\PrizeSettings\\res\\values-zh-rCN\\strings.xml";
		String prizeSetting_cpu_core_number_rHK = Constant.CUSTOMER_BOOT_PATH
				+ "\\packages\\apps\\PrizeSettings\\res\\values-zh-rHK\\strings.xml";

		String[] array = new String[] { prizeSetting_cpu_core_number_default, prizeSetting_cpu_core_number_rTW,
				prizeSetting_cpu_core_number_rCN, prizeSetting_cpu_core_number_rHK };*/
		return true;
	}
	
	/**
	 * 修改品牌名称
	 * @param brandName
	 * @return
	 */
	public static boolean updateBrandName(String brandName){
		String projectConfig_boot_path = Constant.CUSTOMER_BOOT_PATH + "\\device\\prize";
		projectConfiFilePath(projectConfig_boot_path);
		try {
			FileInputStream fis = new FileInputStream(projectConfig_path);
			InputStreamReader isr = new InputStreamReader(fis,"GBK");
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.startsWith("PRODUCT_BRAND_CUSTOM")) {
					arrs = line.split("=");
					line = arrs[0].trim() + " = " + DataBean.brandName;
				}
				sb.append(line + "\n");
			}
			sb.setLength(sb.length() - 1);		
			OutputStreamWriter outstreamWriter = new OutputStreamWriter(new FileOutputStream(projectConfig_path),"GBK"); 
			BufferedWriter writer = new BufferedWriter(outstreamWriter);
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			MyLogger.print("updateBrandName--->"+e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			MyLogger.print("updateBrandName--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateBrandName--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 修改默认的短信提示音
	 * @param ringName
	 * @return
	 */
	public static boolean updateMmsRing(String ringName){
		return true;
	}
	
	/**
	 * 修改默认的来电铃声
	 * @param ringName
	 * @return
	 */
	public static boolean updateRingTones(String ringName){
		String project_full_base=Constant.PROJECT_BOOT_PATH+"\\build\\target\\product\\full_base.mk";
		String coustomer_full_base_parent=Constant.CUSTOMER_BOOT_PATH+"\\build\\target\\product";
		String customer_full_base=coustomer_full_base_parent+"\\full_base.mk";
		
		File file=new File(coustomer_full_base_parent);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			copyFile(customer_full_base, project_full_base, false);
			FileInputStream fis = new FileInputStream(customer_full_base);
			InputStreamReader isr = new InputStreamReader(fis,"GBK");
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.contains("ro.config.ringtone")) {
					arrs = line.split("=");
				    line = arrs[0] + "=" + ringName+" \\";
				}
				sb.append(line + "\n");
			}
			sb.setLength(sb.length() - 1);
			OutputStreamWriter outstreamWriter = new OutputStreamWriter(new FileOutputStream(customer_full_base),"GBK"); 
			BufferedWriter writer = new BufferedWriter(outstreamWriter);
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			MyLogger.print("updateRingTones--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateRingTones--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 修改默认的通知音 
	 * @param ringName
	 * @return
	 */
	public static boolean updateNotificationsRing(String ringName){
		String project_full_base=Constant.PROJECT_BOOT_PATH+"\\build\\target\\product\\full_base.mk";
		String coustomer_full_base_parent=Constant.CUSTOMER_BOOT_PATH+"\\build\\target\\product";
		String customer_full_base=coustomer_full_base_parent+"\\full_base.mk";
		
		File file=new File(coustomer_full_base_parent);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			copyFile(customer_full_base, project_full_base, false);
			FileInputStream fis = new FileInputStream(customer_full_base);
			InputStreamReader isr = new InputStreamReader(fis,"GBK");
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.contains("ro.config.notification_sound")) {
					arrs = line.split("=");
				    line = arrs[0] + "=" + ringName+" \\";
				}
				sb.append(line + "\n");
			}
			sb.setLength(sb.length() - 1);
			OutputStreamWriter outstreamWriter = new OutputStreamWriter(new FileOutputStream(customer_full_base),"GBK"); 
			BufferedWriter writer = new BufferedWriter(outstreamWriter);
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String project_core_base=Constant.PROJECT_BOOT_PATH+"\\build\\target\\product\\core_base.mk";
		String coustomer_core_base_parent=Constant.CUSTOMER_BOOT_PATH+"\\build\\target\\product";
		String customer_core_base=coustomer_core_base_parent+"\\core_base.mk";
		
		File file2=new File(coustomer_core_base_parent);
		if(!file2.exists()){
			file2.mkdirs();
		}
		try {
			copyFile(project_core_base, customer_core_base, false);
			FileInputStream fis = new FileInputStream(customer_core_base);
			InputStreamReader isr = new InputStreamReader(fis,"GBK");
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.contains("ro.config.notification_sound")) {
					arrs = line.split("=");
				    line = arrs[0] + "=" + ringName+" \\";
				}
				sb.append(line + "\n");
			}
			sb.setLength(sb.length() - 1);
			OutputStreamWriter outstreamWriter = new OutputStreamWriter(new FileOutputStream(customer_core_base),"GBK"); 
			BufferedWriter writer = new BufferedWriter(outstreamWriter);
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			MyLogger.print("updateNotificationsRing--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateNotificationsRing--->"+e.getMessage());
			e.printStackTrace();
		}
		
		return true;
	}
	
	/**
	 * 修改默认的闹钟提示音
	 * @param ringName
	 * @return
	 */
	public static boolean updateAlarmsRing(String ringName){
		String project_core_base=Constant.PROJECT_BOOT_PATH+"\\build\\target\\product\\core_base.mk";
		String coustomer_core_base_parent=Constant.CUSTOMER_BOOT_PATH+"\\build\\target\\product";
		String customer_core_base=coustomer_core_base_parent+"\\core_base.mk";
		
		File file=new File(coustomer_core_base_parent);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			copyFile(project_core_base, customer_core_base, false);
			FileInputStream fis = new FileInputStream(customer_core_base);
			InputStreamReader isr = new InputStreamReader(fis,"GBK");
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.contains("ro.config.alarm_alert")) {
					arrs = line.split("=");
				    line = arrs[0] + "=" + ringName+" \\";
				}
				sb.append(line + "\n");
			}
			sb.setLength(sb.length() - 1);
			OutputStreamWriter outstreamWriter = new OutputStreamWriter(new FileOutputStream(customer_core_base),"GBK"); 
			BufferedWriter writer = new BufferedWriter(outstreamWriter);
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			MyLogger.print("updateAlarmsRing--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateAlarmsRing--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 修改口袋模式
	 * @param isOpen [是否打开开关]
	 * @return
	 */
	public static boolean updatePocketMode(boolean isOpen){
		String key="PRIZE_POCKET_MODE";
		String value="no";
		if(isOpen){
			value="yes";
		}
		return modifyProjectConfig(key, value);
	}
	
	/**
	 * 修改美颜的开关
	 * @param isOpen [是否打开开关]
	 * @return
	 */
	public static boolean updateBeauty(boolean isOpen){
		String key="PRIZE_CAMERA_FACEBEAUTY";
		String value="no";
		if(isOpen){
			value="yes";
		}
		return modifyProjectConfig(key, value);
	}
		
	/**
	 * 修改墙纸数量
	 * @param paperNum [墙纸数量]
	 * @return
	 */
	public static boolean updateWallPapersNum(String paperNum){
		String projectConfig_boot_path = Constant.CUSTOMER_BOOT_PATH + "\\device\\prize";
		projectConfiFilePath(projectConfig_boot_path);
		try {
			FileInputStream fis = new FileInputStream(projectConfig_path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.contains("L_SW_TOTAL_COPIED_COOEE_WALLPAPERS")) {
					arrs = line.split("=");
				    line = arrs[0] + "= " + paperNum;
				    System.out.println("ddddd:"+line);
				}
				sb.append(line + "\n");
			}
			sb.setLength(sb.length() - 1);
			BufferedWriter writer = new BufferedWriter(new FileWriter(projectConfig_path));
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			MyLogger.print("updateWallPapersNum--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateWallPapersNum--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 修改内置音视频
	 * @param mediaPath
	 * @return
	 */
	public static boolean updateAddMeida(String mediaPath){
		String coustomer_music_path=Constant.CUSTOMER_BOOT_PATH+"\\frameworks\\base\\data\\sounds\\music";		
		File allAudioFile=new File(customerAllAudioPath);
		try {
			if (!allAudioFile.exists()) {
				copyFile(projectAllAudioPath, customerAllAudioPath,false);
			}			
			File file=new File(coustomer_music_path);
			if(!file.exists()){
				file.mkdirs();
			}
			File resourceFile=new File(mediaPath);
			if(resourceFile.isDirectory()){
				File[] listFile=resourceFile.listFiles();
				if(listFile==null || listFile.length<=0){
					return false;
				}
				for(int index=0;index<listFile.length;index++){
					File temp=listFile[index];
					String path=temp.getPath();
					if(path.endsWith(".mp3") || path.endsWith(".mp4")){
						appendStr(customerAllAudioPath, "\t"+"$(LOCAL_PATH)/music/"+temp.getName()+":system/media/audio/music/"+temp.getName()); 
						copyFile(path, coustomer_music_path+"\\"+temp.getName(),true);	
						}
				}
			}else{
				if(mediaPath.endsWith(".mp3") || mediaPath.endsWith(".mp4")){
					appendStr(customerAllAudioPath, "\t"+"$(LOCAL_PATH)/music/"+resourceFile.getName()+":system/media/audio/music/"+resourceFile.getName()); 
					copyFile(mediaPath, coustomer_music_path+"\\"+resourceFile.getName(),true);	
					}
			}

		} catch (FileNotFoundException e) {
			MyLogger.print("updateAddMeida--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateAddMeida--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 修改时间格式
	 * 
	 * @param timeFormat
	 *            [时间格式]
	 * @return
	 */
	public static boolean updateTimeformat(String timeFormat) {
		String time_format_xml_path_parent = Constant.CUSTOMER_BOOT_PATH
				+ "\\frameworks\\base\\packages\\SettingsProvider\\res\\values";
		String time_format_xml_path = time_format_xml_path_parent + "\\defaults.xml";
		String project_time_format_xml = Constant.PROJECT_BOOT_PATH
				+ "\\frameworks\\base\\packages\\SettingsProvider\\res\\values\\defaults.xml";
		File file_xml = new File(time_format_xml_path_parent);
		if (!file_xml.exists()) {
			file_xml.mkdirs();
		}
		try {
			copyFile(project_time_format_xml, time_format_xml_path, false);
		} catch (FileNotFoundException e) {
			MyLogger.print("updateTimeformat--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateTimeformat--->"+e.getMessage());
			e.printStackTrace();
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		org.w3c.dom.Document document = null;
		try {
			db = dbf.newDocumentBuilder();
			document = db.parse(new File(time_format_xml_path));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		DOMReader reader = new DOMReader();
		Document df_document = reader.read(document);
		Element rootElement = df_document.getRootElement();
		List<Element> childList = rootElement.elements();
		if (childList == null || childList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < childList.size(); i++) {
			Element childElement = childList.get(i);
			Attribute attribute = childElement.attribute("name");
			if (attribute != null && attribute.getValue().equals("time_12_24")) {
				if (timeFormat.equals("12")) {
					childElement.setText("12");
				} else {
					childElement.setText("24");
				}
				break;
			}
		}

		// 提交修改的内容
		try {
			XMLWriter writer = new XMLWriter(new FileWriter(new File(time_format_xml_path)));
			writer.write(df_document);
			writer.close();
		} catch (Exception ex) {
			MyLogger.print("updateTimeformat--->"+ex.getMessage());
			ex.printStackTrace();
		}		
		return true;
	}

	/**
	 * 修改屏幕分辨率
	 * 
	 * @param resolution
	 *            [屏幕分辨率]
	 * @return
	 */
	public static boolean updateTpResolution(String resolution) {
		String px;
		boolean isFirst = true;
		boolean hasEnterMethod = false;
		String parentPath = Constant.CUSTOMER_BOOT_PATH + "\\frameworks\\base\\core\\java\\android\\widget";
		String path = parentPath + "\\TextView.java";
		String mainFilePath = Constant.PROJECT_BOOT_PATH
				+ "\\frameworks\\base\\core\\java\\android\\widget\\TextView.java";
		if (resolution.equals("720x1280")) {
			px = "720";
		} else if (resolution.equals("1080x1920")) {
			px = "1080";
		} else if (resolution.equals("1440x2560")) {
			px = "1440";
		} else {
			return false;
		}
		File file = new File(parentPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			copyFile(mainFilePath, path, false);
			FileInputStream fis = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.contains("private void setText")) {
					hasEnterMethod = true;
				}
				if (line.contains("text = hwDetectH921xishengELUOSI(text);")) {
					isFirst = false;
				}
				if (isFirst && hasEnterMethod && line.contains("if (!isSuggestionsEnabled()) {")) {
					sb.append("\t" + "\t" + "text = hwDetectH921xishengELUOSI(text);" + "\n");
					hasEnterMethod = false;
				}
				if (line.contains("private CharSequence hwDetectH921xishengELUOSI(CharSequence text){")) {
					isFirst = false;
					break;
				}
				sb.append(line + "\n");
			}
			if (isFirst) {
				sb.setLength(sb.lastIndexOf("}") - 1);
				sb.append("\n");
			}
			if (px.equals("720")) {
				sb.append("\t" + "private CharSequence hwDetectH921xishengELUOSI(CharSequence text){" + "\n");
				sb.append("\t" + "\t"
						+ "if(mContext.getPackageName().equals(\"com.antutu.ABenchMark\") && (text != \"\")){" + "\n");
				sb.append("\t" + "\t" + "\t" + "if(text.toString().trim().contains (\"480 x 854\")){" + "\n");
				sb.append("\t" + "\t" + "\t" + "\t" + "text = \"720 x 1080\";" + "\n");
				sb.append("\t" + "\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "if(text.toString().trim().contains(\"240\")){" + "\n");
				sb.append(
						"\t" + "\t" + "\t" + "String mmstr24=text.toString().trim().replace(\"240\",\"320\");" + "\n");
				sb.append("\t" + "\t" + "\t" + "text=(CharSequence)mmstr24;" + "\n");
				sb.append("\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t"
						+ "if(mContext.getPackageName().equals(\"com.ludashi.benchmark\") && (text != \"\")){" + "\n");
				sb.append("\t" + "\t" + "\t" + "if(text.toString().trim().contains (\"854x480\")){" + "\n");
				sb.append("\t" + "\t" + "\t" + "\t"
						+ "String mstr1=text.toString().trim().replace(\"854x480\",\"720x1280\");" + "\n");
				sb.append("\t" + "\t" + "\t" + "\t" + "text = (CharSequence)mstr1;" + "\n");
				sb.append("\t" + "\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "if(mContext.getPackageName().equals(\"com.antutu.tester\") && (text != \"\")){"
						+ "\n");
				sb.append("\t" + "\t" + "\t" + "if(text.toString().trim().contains (\"480x854\")){" + "\n");
				sb.append("\t" + "\t" + "\t" + "\t"
						+ "String mstr2=text.toString().trim().replace(\"480x854\",\"720x1280\");" + "\n");
				sb.append("\t" + "\t" + "\t" + "\t" + "text = (CharSequence)mstr2;" + "\n");
				sb.append("\t" + "\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "return text;" + "\n");
				sb.append("\t" + "}" + "\n");
			} else if (px.equals("1080")) {
				sb.append("\t" + "private CharSequence hwDetectH921xishengELUOSI(CharSequence text){" + "\n");
				sb.append("\t" + "\t"
						+ "if(mContext.getPackageName().equals(\"com.antutu.ABenchMark\") && (text != \"\")){" + "\n");
				sb.append("\t" + "\t" + "\t" + "if(text.toString().trim().contains (\"720 x 1280\")){" + "\n");
				sb.append("\t" + "\t" + "\t" + "\t" + "text = \"1080 x 1920\";" + "\n");
				sb.append("\t" + "\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "if(text.toString().trim().contains(\"320\")){" + "\n");
				sb.append(
						"\t" + "\t" + "\t" + "String mmstr24=text.toString().trim().replace(\"320\",\"480\");" + "\n");
				sb.append("\t" + "\t" + "\t" + "text=(CharSequence)mmstr24;" + "\n");
				sb.append("\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t"
						+ "if(mContext.getPackageName().equals(\"com.ludashi.benchmark\") && (text != \"\")){" + "\n");
				sb.append("\t" + "\t" + "\t" + "if(text.toString().trim().contains (\"1280x720\")){" + "\n");
				sb.append("\t" + "\t" + "\t" + "\t"
						+ "String mstr1=text.toString().trim().replace(\"1280x720\",\"1080x1920\");" + "\n");
				sb.append("\t" + "\t" + "\t" + "\t" + "text = (CharSequence)mstr1;" + "\n");
				sb.append("\t" + "\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "if(mContext.getPackageName().equals(\"com.antutu.tester\") && (text != \"\")){"
						+ "\n");
				sb.append("\t" + "\t" + "\t" + "if(text.toString().trim().contains (\"720x1280\")){" + "\n");
				sb.append("\t" + "\t" + "\t" + "\t"
						+ "String mstr2=text.toString().trim().replace(\"720x1280\",\"1080x1920\");" + "\n");
				sb.append("\t" + "\t" + "\t" + "\t" + "text = (CharSequence)mstr2;" + "\n");
				sb.append("\t" + "\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "return text;" + "\n");
				sb.append("\t" + "}" + "\n");
			} else {
				sb.append("\t" + "private CharSequence hwDetectH921xishengELUOSI(CharSequence text){" + "\n");
				sb.append("\t" + "\t"
						+ "if(mContext.getPackageName().equals(\"com.antutu.ABenchMark\") && (text != \"\")){" + "\n");
				sb.append("\t" + "\t" + "\t" + "if(text.toString().trim().contains (\"1080 x 1920\")){" + "\n");
				sb.append("\t" + "\t" + "\t" + "\t" + "text = \"1440 x 2560\";" + "\n");
				sb.append("\t" + "\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "if(text.toString().trim().contains(\"480\")){" + "\n");
				sb.append(
						"\t" + "\t" + "\t" + "String mmstr24=text.toString().trim().replace(\"480\",\"640\");" + "\n");
				sb.append("\t" + "\t" + "\t" + "text=(CharSequence)mmstr24;" + "\n");
				sb.append("\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t"
						+ "if(mContext.getPackageName().equals(\"com.ludashi.benchmark\") && (text != \"\")){" + "\n");
				sb.append("\t" + "\t" + "\t" + "if(text.toString().trim().contains (\"1920x1080\")){" + "\n");
				sb.append("\t" + "\t" + "\t" + "\t"
						+ "String mstr1=text.toString().trim().replace(\"1920x1080\",\"1440x2560\");" + "\n");
				sb.append("\t" + "\t" + "\t" + "\t" + "text = (CharSequence)mstr1;" + "\n");
				sb.append("\t" + "\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "if(mContext.getPackageName().equals(\"com.antutu.tester\") && (text != \"\")){"
						+ "\n");
				sb.append("\t" + "\t" + "\t" + "if(text.toString().trim().contains (\"1080x1920\")){" + "\n");
				sb.append("\t" + "\t" + "\t" + "\t"
						+ "String mstr2=text.toString().trim().replace(\"1080x1920\",\"1440x2560\");" + "\n");
				sb.append("\t" + "\t" + "\t" + "\t" + "text = (CharSequence)mstr2;" + "\n");
				sb.append("\t" + "\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "}" + "\n");
				sb.append("\t" + "\t" + "return text;" + "\n");
				sb.append("\t" + "}" + "\n");
			}
			sb.append("}");
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			MyLogger.print("updateTpResolution--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateTpResolution--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 修改手机存储大小
	 * 
	 * @param romsize
	 * @return
	 */
	public static boolean updateRom(String romsize) {
		String coustomer_rom_path_parent = Constant.CUSTOMER_BOOT_PATH
				+ "\\kernel-3.10\\fs";
		String coustomer_rom_path = coustomer_rom_path_parent + "\\statfs.c";
		String project_rom_path = Constant.PROJECT_BOOT_PATH
				+ "\\kernel-3.10\\fs\\statfs.c";
		File file = new File(coustomer_rom_path_parent);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			copyFile(project_rom_path, coustomer_rom_path, false);
			boolean hasEnterMethod = false; //是否进入需要修改的方法
			boolean hasModified = false; //判断是否原先修改过
			FileInputStream fis = new FileInputStream(coustomer_rom_path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.contains("int user_statfs(const char __user *pathname, struct kstatfs *st)")) {
					hasEnterMethod = true;
				}
				if (line.contains("if ((strcmp(\"/data/media\", pathname) == 0) && st != 0) {")) {
					hasModified = true;
				}
				if (hasEnterMethod && line.contains("return error;")) {
					if (romsize.equals("816")) {
						sb.append("\t"+"if ((strcmp(\"/data\", pathname) == 0) && st != 0) {"+"\n");
						sb.append("\t"+"\t"+"st->f_blocks =st->f_blocks * 33/10 + 223744;"+"\n");
						sb.append("\t"+"\t"+"st->f_bavail =st->f_bavail * 33/10 + 96279;"+"\n");
						sb.append("\t"+"}"+"\n");
						sb.append("\t"+"if ((strcmp(\"/data/media\", pathname) == 0) && st != 0) {"+"\n");
						sb.append("\t"+"\t"+"st->f_blocks =st->f_blocks * 33/10 + 223744;"+"\n");
						sb.append("\t"+"\t"+"st->f_bavail =st->f_bavail * 33/10 + 96278;"+"\n");
						sb.append("\t"+"}"+"\n");
						hasModified = false;
						hasEnterMethod = false;
					} else if (romsize.equals("832")) {
						sb.append("\t"+"if ((strcmp(\"/data\", pathname) == 0) && st != 0) {"+"\n");
						sb.append("\t"+"\t"+"st->f_blocks = 8*1024*1024+12800;"+"\n");
						sb.append("\t"+"\t"+"st->f_bavail = st->f_bavail*79/10+30157;"+"\n");
						sb.append("\t"+"}"+"\n");
						sb.append("\t"+"if ((strcmp(\"/data/media\", pathname) == 0) && st != 0) {"+"\n");
						sb.append("\t"+"\t"+"st->f_blocks = 8*1024*1024+12800;"+"\n");
						sb.append("\t"+"\t"+"st->f_bavail = st->f_bavail*79/10+30157;"+"\n");
						sb.append("\t"+"}"+"\n");
						hasModified = false;
						hasEnterMethod = false;
					} else if (romsize.equals("1632")) {
						sb.append("\t"+"if ((strcmp(\"/data/media\", pathname) == 0) && st != 0) {"+"\n");
						sb.append("\t"+"\t"+"st->f_blocks = 8*1024*1024+12800;"+"\n");
						sb.append("\t"+"\t"+"st->f_bavail = st->f_bavail*26/10+24320;"+"\n");
						sb.append("\t"+"}"+"\n");
						sb.append("\t"+"if ((strcmp(\"/data\", pathname) == 0) && st != 0) {"+"\n");
						sb.append("\t"+"\t"+"st->f_blocks = 8*1024*1024+24320;"+"\n");
						sb.append("\t"+"\t"+"st->f_bavail = st->f_bavail*26/10+24320;"+"\n");
						sb.append("\t"+"}"+"\n");
						hasModified = false;
						hasEnterMethod = false;
					} else {
						sb.append("\t"+"if ((strcmp(\"/data/media\", pathname) == 0) && st != 0) {"+"\n");
						sb.append("\t"+"\t"+"st->f_blocks = 16*1024*1024+12800;"+"\n");
						sb.append("\t"+"\t"+"st->f_bavail = st->f_bavail*48/10+115712;"+"\n");
						sb.append("\t"+"}"+"\n");
						sb.append("\t"+"if ((strcmp(\"/data\", pathname) == 0) && st != 0) {"+"\n");
						sb.append("\t"+"\t"+"st->f_blocks = 16*1024*1024+12800;"+"\n");
						sb.append("\t"+"\t"+"st->f_bavail = st->f_bavail*48/10+115712;"+"\n");
						sb.append("\t"+"}"+"\n");
						hasModified = false;
						hasEnterMethod = false;
					}
				}
				if (!hasModified) {
					sb.append(line + "\n");
				}
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(coustomer_rom_path));
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			MyLogger.print("updateRom--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateRom--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 修改手机运行内存
	 * 
	 * @param ramSize
	 *            [运行内存大小]
	 * @return
	 */
	public static boolean updateRam(String ramSize) {
		String coustomer_ram_path_parent = Constant.CUSTOMER_BOOT_PATH
				+ "\\kernel-3.10\\fs\\proc";
		String coustomer_ram_path = coustomer_ram_path_parent + "\\meminfo.c";
		String project_ram_path = Constant.PROJECT_BOOT_PATH
				+ "\\kernel-3.10\\fs\\proc\\meminfo.c";
		File file = new File(coustomer_ram_path_parent);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			copyFile(project_ram_path, coustomer_ram_path, false);
			boolean hasModified = false; //判断是否原先修改过
			FileInputStream fis = new FileInputStream(coustomer_ram_path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.contains("ram_size") || line.contains("ram_free_size") || line.contains("K(i.totalram),") || line.contains("K(i.freeram),")) {
					hasModified = true;
				}
				if (line.contains("struct sysinfo i;")) {
					sb.append("\t"+"long ram_size;"+"\n");
					sb.append("\t"+"long ram_free_size;"+"\n");
				}
				if (line.contains("seq_printf(m,")) {
					sb.append("\t"+"ram_size=K(i.totalram);"+"\n");
					sb.append("\t"+"ram_free_size = K(i.freeram);"+"\n");
					if (ramSize.equals("1G")) {
						sb.append("\t"+"ram_size=912*1024;"+"\n");
						sb.append("\t"+"ram_free_size = K(i.freeram) + 300*1024;"+"\n");
					} else if(ramSize.equals("2G")) {
						sb.append("\t"+"ram_size=1912*1024;"+"\n");
						sb.append("\t"+"ram_free_size = K(i.freeram) + 1024*1024;"+"\n");
					} else if(ramSize.equals("3G")) {
						sb.append("\t"+"ram_size=2912*1024;"+"\n");
						sb.append("\t"+"ram_free_size = K(i.freeram) + 1800*1024;"+"\n");
					}
				}
				if (line.contains("K(i.bufferram),")) {
					sb.append("\t"+"\t"+"(ram_size),"+"\n");
					sb.append("\t"+"\t"+"(ram_free_size),"+"\n");
				}
				if (!hasModified) {
					sb.append(line + "\n");
				}
				hasModified = false;
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(coustomer_ram_path));
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			MyLogger.print("updateRam--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateRam--->"+e.getMessage());
			e.printStackTrace();
		}
		
		return true;
	}

	/**
	 * 修改默认字体大小
	 * 
	 * @param fontSize
	 *            [字体大小]
	 * @return
	 */
	public static boolean updateFontSize(String fontSize) {
		String coustomer_fontSize_path_parent = Constant.CUSTOMER_BOOT_PATH
				+ "\\frameworks\\base\\core\\java\\android\\content\\res";
		String coustomer_fontSize_path = coustomer_fontSize_path_parent + "\\Configuration.java";
		String project_fontSize_path = Constant.PROJECT_BOOT_PATH
				+ "\\frameworks\\base\\core\\java\\android\\content\\res\\Configuration.java";
		File file = new File(coustomer_fontSize_path_parent);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			copyFile(project_fontSize_path, coustomer_fontSize_path, false);
			FileInputStream fis = new FileInputStream(coustomer_fontSize_path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.contains("fontScale = 1") || line.contains("fontScale = 1.1")
						|| line.contains("fontScale = 1.2") || line.contains("fontScale = 0.9")) {
					arrs = line.split("=");
					line = arrs[0] + "= " + fontSize + ";";
				}
				sb.append(line + "\n");
			}
			sb.setLength(sb.length() - 1);// 去掉最后一个换行符
			BufferedWriter writer = new BufferedWriter(new FileWriter(coustomer_fontSize_path));
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();

		} catch (FileNotFoundException e) {
			MyLogger.print("updateFontSize--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateFontSize--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 修改来电振动开关
	 * 
	 * @param isCallVibration
	 *            [来电是否振动]
	 * @return
	 */
	public static boolean updateCallVibration(boolean isCallVibration) {
		String customer_CallVibration_path_parent = Constant.CUSTOMER_BOOT_PATH
				+ "\\frameworks\\base\\media\\java\\com\\mediatek\\audioprofile";
		String customer_CallVibration_path = customer_CallVibration_path_parent + "\\AudioProfileManager.java";
		String project_CallVibration_path = Constant.PROJECT_BOOT_PATH
				+ "\\frameworks\\base\\media\\java\\com\\mediatek\\audioprofile\\AudioProfileManager.java";

		File file = new File(customer_CallVibration_path_parent);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			copyFile(project_CallVibration_path, customer_CallVibration_path, false);
			FileInputStream fis = new FileInputStream(customer_CallVibration_path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.contains("final boolean DEFAULT_VIBRATION_GENERAL")) {
					arrs = line.split("=");
					System.out.println(arrs[0] + "= " + arrs[1]);
					if (isCallVibration) {
						line = arrs[0] + "= " + "true;";
					} else {
						line = arrs[0] + "= " + "false;";
					}
				}
				sb.append(line + "\n");
			}
			sb.setLength(sb.length() - 1);// 去掉最后一个换行符
			BufferedWriter writer = new BufferedWriter(new FileWriter(customer_CallVibration_path));
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			MyLogger.print("updateCallVibration--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateCallVibration--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 修改防误触开关
	 * @param isOpenFalseTouch [是否打开防误触开关]
	 * @return
	 */
	public static boolean updateFalseTouch(boolean isOpenFalseTouch){
		String customer_FalseTouch_path_parent = Constant.CUSTOMER_BOOT_PATH + "\\frameworks\\base\\packages\\SettingsProvider\\res\\values";
		String customer_FalseTouch_path = customer_FalseTouch_path_parent + "\\defaults.xml";
		String project_FalseTouch_path=Constant.PROJECT_BOOT_PATH+"\\frameworks\\base\\packages\\SettingsProvider\\res\\values\\defaults.xml";
		
		File file=new File(customer_FalseTouch_path_parent);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			copyFile(project_FalseTouch_path, customer_FalseTouch_path, false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        org.w3c.dom.Document document2 = null;
		try {
			db = dbf.newDocumentBuilder();
			document2 = db.parse(new File(customer_FalseTouch_path));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

        DOMReader domReader = new DOMReader();
        Document document3 = domReader.read(document2);
        Element rootElement = document3.getRootElement();
		List<Element> childList = rootElement.elements();
		if (childList == null || childList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < childList.size(); i++) {
			Element childElement = childList.get(i);
			Attribute attribute = childElement.attribute("name");
			if (attribute != null && attribute.getValue().equals("def_prize_antifake_touch_enabled")) {
				if(isOpenFalseTouch){
					childElement.setText("true");
				}else{
					childElement.setText("false");
				}
				
				break;
			}
		}

		//提交修改的内容
        try{
            XMLWriter writer = new XMLWriter(new FileWriter(new File(customer_FalseTouch_path)));
            writer.write(document3);
            writer.close();
        }catch(Exception ex){
        	MyLogger.print("updateFalseTouch--->"+ex.getMessage());
            ex.printStackTrace();
        }         
		return true;
	}
	
	/**
	 * 修改铃声音量是否为最大值
	 * @param isVolumeMax [是否开启最大值] 
	 * @return
	 */
	public static boolean updateVolumeMax(boolean isVolumeMax){
		String customer_VolumeMax_path_parent = Constant.CUSTOMER_BOOT_PATH + "\\frameworks\\base\\packages\\SettingsProvider\\res\\values";
		String customer_VolumeMax_path = customer_VolumeMax_path_parent + "\\mtk_defaults.xml";
		String project_VolumeMax_path=Constant.PROJECT_BOOT_PATH+"\\frameworks\\base\\packages\\SettingsProvider\\res\\values\\mtk_defaults.xml";
		
		File file=new File(customer_VolumeMax_path_parent);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			copyFile(project_VolumeMax_path, customer_VolumeMax_path, false);
		} catch (FileNotFoundException e) {
			MyLogger.print("updateVolumeMax--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateVolumeMax--->"+e.getMessage());
			e.printStackTrace();
		}
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        org.w3c.dom.Document document2 = null;
		try {
			db = dbf.newDocumentBuilder();
			document2 = db.parse(new File(customer_VolumeMax_path));
		} catch (SAXException e) {
			MyLogger.print("updateVolumeMax--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateVolumeMax--->"+e.getMessage());
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			MyLogger.print("updateVolumeMax--->"+e.getMessage());
			e.printStackTrace();
		}

        DOMReader domReader = new DOMReader();
        // 将JAXP类型的Documentת转换成dom4j的Document
        Document document3 = domReader.read(document2);
        Element rootElement = document3.getRootElement();
        List<Element> childList=rootElement.elements();
		if (childList == null || childList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < childList.size(); i++) {
			Element childElement = childList.get(i);
			Attribute attribute = childElement.attribute("name");
			if (attribute != null && attribute.getValue().equals("def_active_profile")) {
				if (isVolumeMax) {
					childElement.setText("mtk_audioprofile_outdoor");
				} else {
					childElement.setText("mtk_audioprofile_grneral");
				}
				break;
			}
		}
        try{
            XMLWriter writer = new XMLWriter(new FileWriter(new File(customer_VolumeMax_path)));
            writer.write(document3);
            writer.close();
        }catch(Exception ex){
        	MyLogger.print("updateVolumeMax--->"+ex.getMessage());
            ex.printStackTrace();
        }         
		return true;
	}
	
	/**
	 * 修改默认来电铃声
	 * @param callRingPath [来电铃声的路径]
	 * @return
	 */
	public static boolean updateCallRing(String callRingPath) {
		File sourceFile = new File(callRingPath);
		if(!sourceFile.exists()){
			return false;
		}
		String sourceName = sourceFile.getName();
		String customer_sounds_path_parent = Constant.CUSTOMER_BOOT_PATH + "\\frameworks\\base\\data\\sounds";
		String customer_sounds_path = customer_sounds_path_parent + "\\" + sourceName;
		File file=new File(customer_sounds_path_parent);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			copyFile(callRingPath, customer_sounds_path, true);
			copyFile(projectAllAudioPath, customerAllAudioPath, false);
			appendStr(customerAllAudioPath, "\t"+"$(LOCAL_PATH)/"+sourceName+":system/media/audio/ringtones/"+sourceName); 
			
			String coustomer_full_base_path_parent=Constant.CUSTOMER_BOOT_PATH+"\\build\\target\\product";
			String coustomer_full_base_path=coustomer_full_base_path_parent+"\\full_base.mk";
			String project_full_base_path = Constant.PROJECT_BOOT_PATH + "\\build\\target\\product\\full_base.mk";
			File fullBaseFile=new File(coustomer_full_base_path_parent);
			if(!fullBaseFile.exists()){
				fullBaseFile.mkdirs();
			}
			copyFile(project_full_base_path, coustomer_full_base_path, false);
			
			FileInputStream fis = new FileInputStream(coustomer_full_base_path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.contains("ro.config.ringtone")) {
					arrs = line.split("=");
					line = arrs[0] + "="+sourceName+" \\";
				}
				sb.append(line + "\n");
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(coustomer_full_base_path));
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();
		
		} catch (FileNotFoundException e) {
			MyLogger.print("updateCallRing--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateCallRing--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 修改默认的墙纸
	 * @param defaultWallpaperPath [墙纸的路径]
	 * @return
	 */
	public static boolean updateDefaultWallpaper(String defaultWallpaperPath){
		return true;
	}
	
	/**
	 * 修改锁屏壁纸
	 * @param lockWallPaperPath [锁屏壁纸的路径]
	 * @return
	 */
	public static boolean updateLockWallpaper(String lockWallPaperPath){
		
		File sourceFile = new File(lockWallPaperPath);
		if (!sourceFile.exists()) {
			return false;
		}
		String tempName=sourceFile.getParentFile().getName();
		String fileName=sourceFile.getName();
		System.out.println("lockwallpaper parentName = " + tempName+" ,fileName = "+fileName);
		String customer_defaultWallpaper_path_parent=Constant.CUSTOMER_BOOT_PATH+"\\frameworks\\base\\core\\res\\res\\"+tempName;
		String customer_defaultWallpaper_path=customer_defaultWallpaper_path_parent+"\\"+fileName;
		try {
			File file = new File(customer_defaultWallpaper_path_parent);
			if (!file.exists()) {
				file.mkdirs();
			}
			copyFile(lockWallPaperPath, customer_defaultWallpaper_path, true);
		} catch (FileNotFoundException e) {
			MyLogger.print("updateLockWallpaper--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateLockWallpaper--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 修改关机铃声
	 * @param newRingPath [关机铃声的路径]
	 * @return
	 */
	public static boolean updateShutDownRing(String newRingPath){
		String customer_prop_path_parent=Constant.CUSTOMER_BOOT_PATH+"\\device\\prize\\prize6735m_65c_l1";
		String customer_prop_path=customer_prop_path_parent+"\\system.prop";
		String project_prop_path=Constant.PROJECT_BOOT_PATH+"\\device\\prize\\prize6735m_65c_l1\\system.prop";
		File file=new File(customer_prop_path_parent);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			copyFile(project_prop_path, customer_prop_path, false);
			appendStr(customer_prop_path, "ro.operator.optr=CUST");
			
			File file2=new File(customerSoundPath);
			if(!file2.exists()){
				file2.mkdirs();
			}
			copyFile(projectAllAudioPath, customerAllAudioPath,false);
			appendStr(customerAllAudioPath, "\t"+"$(LOCAL_PATH)/shutaudio.mp3:system/media/shutaudio.mp3"); 
			copyFile(DataBean.shutDownAnim, customerSoundPath+"\\shutaudio.mp3",true);
		} catch (FileNotFoundException e) {
			MyLogger.print("updateShutDownRing--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateShutDownRing--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 修改关机动画
	 * @param newAnimPath [关机动画的路径]
	 * @return
	 */
	public static boolean updateShutDownAnim(String newAnimPath){
		String customer_prop_path_parent=Constant.CUSTOMER_BOOT_PATH+"\\device\\prize\\prize6735m_65c_l1";
		String customer_prop_path=customer_prop_path_parent+"\\system.prop";
		String project_prop_path=Constant.PROJECT_BOOT_PATH+"\\device\\prize\\prize6735m_65c_l1\\system.prop";
		File file=new File(customer_prop_path_parent);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			copyFile(project_prop_path, customer_prop_path, false);
			appendStr(customer_prop_path, "ro.operator.optr=CUST");
			
			File file2=new File(customerSoundPath);
			if(!file2.exists()){
				file2.mkdirs();
			}
			copyFile(projectAllAudioPath, customerAllAudioPath,false);
			appendStr(customerAllAudioPath, "\t"+"$(LOCAL_PATH)/shutanimation.zip:system/media/shutanimation.zip"); 
			copyFile(DataBean.shutDownAnim, customerSoundPath+"\\shutanimation.zip",true);
		} catch (FileNotFoundException e) {
			MyLogger.print("updateShutDownAnim--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateShutDownAnim--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 修改灭屏的时间
	 * @param time [灭屏时间]
	 * @return
	 */
	public static boolean updateScreenOffTime(String time) {
		String customer_screenOffTime_path_parent = Constant.CUSTOMER_BOOT_PATH + "\\frameworks\\base\\packages\\SettingsProvider\\res\\values";
		String customer_screenOffTime_path = customer_screenOffTime_path_parent + "\\defaults.xml";
		String project_screenOffTime_path=Constant.PROJECT_BOOT_PATH+"\\frameworks\\base\\packages\\SettingsProvider\\res\\values\\defaults.xml";
		
		File file=new File(customer_screenOffTime_path_parent);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			copyFile(project_screenOffTime_path, customer_screenOffTime_path, false);
		} catch (FileNotFoundException e) {
			MyLogger.print("updateScreenOffTime--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateScreenOffTime--->"+e.getMessage());
			e.printStackTrace();
		}
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        org.w3c.dom.Document document2 = null;
		try {
			db = dbf.newDocumentBuilder();
			document2 = db.parse(new File(customer_screenOffTime_path));
		} catch (SAXException e) {
			MyLogger.print("updateScreenOffTime--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateScreenOffTime--->"+e.getMessage());
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			MyLogger.print("updateScreenOffTime--->"+e.getMessage());
			e.printStackTrace();
		}

        DOMReader domReader = new DOMReader();
        // 将JAXP类型的Documentת转换成dom4j的Document
        Document document3 = domReader.read(document2);
        Element rootElement = document3.getRootElement();
        List<Element> childList=rootElement.elements();
		if (childList == null || childList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < childList.size(); i++) {
			Element childElement = childList.get(i);
			Attribute attribute = childElement.attribute("name");
			if (attribute != null && attribute.getValue().equals("def_screen_off_timeout")) {
				childElement.setText(time);
				break;
			}
		}

		//提交修改的内容
        try{
            XMLWriter writer = new XMLWriter(new FileWriter(new File(customer_screenOffTime_path)));
            writer.write(document3);
            writer.close();
        }catch(Exception ex){
        	MyLogger.print("updateScreenOffTime--->"+ex.getMessage());
            ex.printStackTrace();
        }         
		return true;
	}
	
	/**
	 * 修改虚拟按键
	 * @param isOpen [是否打开虚拟按键]
	 * @return
	 */
	public static boolean updateVirtualKey(boolean isOpen){
		String customer_systeProp_path_parent = Constant.CUSTOMER_BOOT_PATH + "\\device\\prize\\prize6735m_65c_l1";
		String customer_systeProp_path = customer_systeProp_path_parent + "\\system.prop";
		String project_systeProp_path=Constant.PROJECT_BOOT_PATH+"\\device\\prize\\prize6735m_65c_l1\\system.prop";
		String customer_ks_default_layout_parent = Constant.CUSTOMER_BOOT_PATH + "\\packages\\apps\\PrebuildApps\\cooee\\xml";
		String customer_ks_default_layout_path = customer_ks_default_layout_parent + "\\ks_default_layout.xml";
		String project_ks_default_layout_path=Constant.PROJECT_BOOT_PATH+"\\packages\\apps\\PrebuildApps\\cooee\\xml\\ks_default_layout.xml";
		//修改system.prop文件中的qemu.hw.mainkeys值
		
		File file = new File(customer_systeProp_path_parent);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			copyFile(project_systeProp_path, customer_systeProp_path, false);
			
			FileInputStream fis = new FileInputStream(customer_systeProp_path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.startsWith("qemu.hw.mainkeys")) {
					arrs = line.split("=");
					if (isOpen) {
						line = arrs[0].trim() + " = 0";
					} else {
						line = arrs[0].trim() + " = 1";
					}
				}
				sb.append(line + "\n");
			}
			sb.setLength(sb.length() - 1);
			BufferedWriter writer = new BufferedWriter(new FileWriter(customer_systeProp_path));
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();

			// 修改ks_default_layout.xml中的transparent_navigation值
			File file2 = new File(customer_ks_default_layout_parent);
			if (!file2.exists()) {
				file2.mkdirs();
			}
			copyFile(project_ks_default_layout_path, customer_ks_default_layout_path, false);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = null;
	        org.w3c.dom.Document document2 = null;
			try {
				db = dbf.newDocumentBuilder();
				document2 = db.parse(new File(customer_ks_default_layout_path));
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}catch (ParserConfigurationException e) {
				e.printStackTrace();
			}

	        DOMReader domReader = new DOMReader();
	        // 将JAXP类型的Documentת转换成dom4j的Document
	        Document document3 = domReader.read(document2);
	        Element rootElement = document3.getRootElement();
	        Element childElement =rootElement.element("general_config");
	        Iterator attrIterator = childElement.attributeIterator();
			while (attrIterator.hasNext()) {
				Attribute attribute = (Attribute) attrIterator.next();
				if("transparent_navigation".equals(attribute.getName())){
					if (isOpen) {
						attribute.setValue("true");
					} else {
						attribute.setValue("false");
					}
				}
			}

			//提交修改的内容
	        try{
	            XMLWriter xmlWriter = new XMLWriter(new FileWriter(new File(customer_ks_default_layout_path)));
	            xmlWriter.write(document3);
	            xmlWriter.close();
	        }catch(Exception ex){
	        	MyLogger.print("updateVirtualKey--->"+ex.getMessage());
	            ex.printStackTrace();
	        }         
			
		} catch (FileNotFoundException e) {
			MyLogger.print("updateVirtualKey--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateVirtualKey--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	
	/**
	 * 修改屏幕亮度
	 * @param value  [0-255] 默认值为77
	 * @param AutoBrightness  自动亮度是否打开
	 * @return
	 */
	public static boolean updateDefaultLight(int value,boolean AutoBrightness) {
		String customer_screenhight_path_parent = Constant.CUSTOMER_BOOT_PATH
				+ "\\frameworks\\base\\packages\\SettingsProvider\\res\\values";
		String customer_screenhight_path = customer_screenhight_path_parent + "\\defaults.xml";
		String project_screenhight_path = Constant.PROJECT_BOOT_PATH
				+ "\\frameworks\\base\\packages\\SettingsProvider\\res\\values\\defaults.xml";
		File file = new File(customer_screenhight_path_parent);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			copyFile(project_screenhight_path, customer_screenhight_path, false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        org.w3c.dom.Document document2 = null;
		try {
			db = dbf.newDocumentBuilder();
			document2 = db.parse(new File(customer_screenhight_path));
		} catch (SAXException e) {
			MyLogger.print("updateDefaultLight--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateDefaultLight--->"+e.getMessage());
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			MyLogger.print("updateDefaultLight--->"+e.getMessage());
			e.printStackTrace();
		}

        DOMReader domReader = new DOMReader();
        // 将JAXP类型的Documentת转换成dom4j的Document
        Document document3 = domReader.read(document2);
        Element rootElement = document3.getRootElement();
        List<Element> childList=rootElement.elements();
		if (childList == null || childList.size() <= 0) {
			System.out.println("updateDefaultLight()--->childList == null || childList.size() <= 0");
			return false;
		}
		for (int i = 0; i < childList.size(); i++) {
			Element childElement = childList.get(i);
	        Iterator attrIterator = childElement.attributeIterator();
			while (attrIterator.hasNext()) {
				Attribute attribute = (Attribute) attrIterator.next();
				if(attribute.getValue().equals("def_screen_brightness")){
					childElement.setText(String.valueOf(value));
				}
				if(attribute.getValue().equals("def_screen_brightness_automatic_mode")){
					if(AutoBrightness){
						childElement.setText("true");
					}else{
						childElement.setText("false");
					}
				}
			}
		}

		//提交修改的内容
        try{
            XMLWriter writer = new XMLWriter(new FileWriter(new File(customer_screenhight_path)));
            writer.write(document3);
            writer.close();
        }catch(Exception ex){
        	MyLogger.print("updateDefaultLight--->"+ex.getMessage());
            ex.printStackTrace();
        }         
		return true;
	}
	
	/**
	 * 修改默认主题
	 * @param themePackName  [主题包名]
	 * @return
	 */
	public static boolean updateTheme(String themePackName){
		String customer_themeConfig_path_parent = Constant.CUSTOMER_BOOT_PATH+"\\packages\\apps\\PrebuildApps\\cooee\\xml";
		String customer_themeConfig_path = customer_themeConfig_path_parent+"\\ks_feature_config.xml";
		String project_themeConfig_path = Constant.PROJECT_BOOT_PATH+"\\packages\\apps\\PrebuildApps\\cooee\\xml\\ks_feature_config.xml";
		File file=new File(customer_themeConfig_path_parent);
		if(!file.exists()){
			file.mkdirs();
		}
		
		try {
			copyFile(project_themeConfig_path, customer_themeConfig_path,false);
		} catch (FileNotFoundException e) {
			MyLogger.print("updateTheme--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateTheme--->"+e.getMessage());
			e.printStackTrace();
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        org.w3c.dom.Document document2 = null;
		try {
			db = dbf.newDocumentBuilder();
			document2 = db.parse(new File(customer_themeConfig_path));
		} catch (SAXException e) {
			MyLogger.print("updateTheme--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateTheme--->"+e.getMessage());
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			MyLogger.print("updateTheme--->"+e.getMessage());
			e.printStackTrace();
		}

        DOMReader domReader = new DOMReader();
        // 将JAXP类型的Documentת转换成dom4j的Document
        Document document3 = domReader.read(document2);
        Element rootElement = document3.getRootElement();
        Element childElement=rootElement.element("general_config");
        
        Iterator attrIt = childElement.attributeIterator();
		while (attrIt.hasNext()) {
			Attribute attribute = (Attribute) attrIt.next();
			if(attribute.getName().equals("default_theme_package_name")){
				System.out.println("old Attribute name="+attribute.getName()+" value="+attribute.getValue());
				attribute.setValue(themePackName);
				break;
			}
		}
        
		//提交修改的内容
        try{
            XMLWriter writer = new XMLWriter(new FileWriter(new File(customer_themeConfig_path)));
            writer.write(document3);
            writer.close();
        }catch(Exception ex){
        	MyLogger.print("updateTheme--->"+ex.getMessage());
            ex.printStackTrace();
        }         
		return true;
	}
	
	/**
	 * 修改开机动画
	 * @return
	 */
	public static boolean updateBootAnim() {
		System.out.println("updateBootAnim "+projectAllAudioPath);
		File file=new File(customerSoundPath);
		if(!file.exists()){
			file.mkdirs();
		}
		File allAudioFile=new File(customerAllAudioPath);
		try {
			if (!allAudioFile.exists()) {
				copyFile(projectAllAudioPath, customerAllAudioPath,false);
			}
			appendStr(customerAllAudioPath, "\t"+"$(LOCAL_PATH)/bootanimation.zip:system/media/bootanimation.zip"); 
			// 复制开机动画资源
			copyFile(DataBean.bootAnim, customerSoundPath+"\\bootanimation.zip",true);
		} catch (FileNotFoundException e) {
			MyLogger.print("updateBootAnim--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateBootAnim--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 修改开机铃声
	 * @return
	 */
	public static boolean updateBootRing() {
		File file=new File(customerSoundPath);
		if(!file.exists()){
			file.mkdir();
		}
		File allAudioFile=new File(customerAllAudioPath);
		try {
			if (!allAudioFile.exists()) {
				copyFile(projectAllAudioPath, customerAllAudioPath,false);
			}
			appendStr(customerAllAudioPath, "\t"+"$(LOCAL_PATH)/bootaudio.mp3:system/media/bootaudio.mp3"); 
			// 复制开机铃声
			copyFile(DataBean.bootRing, customerSoundPath+"\\bootaudio.mp3",true);
		} catch (FileNotFoundException e) {
			MyLogger.print("updateBootRing--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateBootRing--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 修改设备 、WIFI、蓝牙的名称
	 * @return
	 */
	public static boolean updateProductName() {
		String projectConfig_boot_path = Constant.CUSTOMER_BOOT_PATH + "\\device\\prize";
		projectConfiFilePath(projectConfig_boot_path);
		try {
			FileInputStream fis = new FileInputStream(projectConfig_path);
			InputStreamReader isr = new InputStreamReader(fis,"GBK");
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.startsWith("PRODUCT_MODEL_CUSTOM")) {
					arrs = line.split("=");
					line = arrs[0].trim() + " = " + DataBean.productName;
				}
				sb.append(line + "\n");
			}
			sb.setLength(sb.length() - 1);// 去掉最后一个换行符
			
			OutputStreamWriter outstreamWriter = new OutputStreamWriter(new FileOutputStream(projectConfig_path),"GBK"); 
			BufferedWriter writer = new BufferedWriter(outstreamWriter);
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			MyLogger.print("updateProductName--->"+e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			MyLogger.print("updateProductName--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateProductName--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 修改开机Logo
	 */
	public static boolean updateBootLogo() {
		StringBuilder sb = new StringBuilder();
		String BOOT_LOGO = getLogoFileName();
		sb.append(Constant.CUSTOMER_BOOT_PATH);
		sb.append("\\");
		sb.append("bootable");
		sb.append("\\");
		sb.append("bootloader");
		sb.append("\\");
		sb.append("lk");
		sb.append("\\");
		sb.append("dev");
		sb.append("\\");
		sb.append("logo");
		sb.append("\\");
		sb.append(BOOT_LOGO);
		try {
			File file = new File(sb.toString());
			if (!file.exists()) {
				if (file.mkdirs()) {
					System.out.println("Logo path = " + sb.toString()+" create success!!");
				} else {
					System.out.println("Logo path = " + sb.toString()+" create fail!!");
					return false;
				}
			}
			String kernelPath = sb.toString() + "\\" + BOOT_LOGO + "_kernel.bmp";
			String ubootlPath = sb.toString() + "\\" + BOOT_LOGO + "_uboot.bmp";

			copyFile(DataBean.bootLogo, kernelPath,true);
			copyFile(DataBean.bootLogo, ubootlPath,true);
		} catch (FileNotFoundException e) {
			MyLogger.print("updateBootLogo--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("updateBootLogo--->"+e.getMessage());
			e.printStackTrace();
		}catch(SecurityException e){
			MyLogger.print("updateBootLogo--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 修改ProjectConfig.mk中的软件版本号
	 * 
	 * @param newVersion
	 */
	public static void updateVersion(String newVersion) {
		String projectConfig_boot_path = Constant.CUSTOMER_BOOT_PATH + "\\device\\prize";
		projectConfiFilePath(projectConfig_boot_path);
		System.out.println("projectConfig_path = " + projectConfig_path);
		if (projectConfig_path == null) {
			return;
		}
		try {
			modifyVersion(projectConfig_path, DataBean.newVerisonNumber);
		} catch (IOException e) {
			MyLogger.print("updateVersion--->"+e.getMessage());
			e.printStackTrace();
			System.out.println("IOException = " + e.getMessage());
		}
	}

	private static void projectConfiFilePath(String path) {
		try {
			File file = new File(path);
			if (file.exists()) {
				if (file.isDirectory()) {
					File[] listFile = file.listFiles();
					if (listFile == null) {
					}
					for (int i = 0; i < listFile.length; i++) {
						projectConfiFilePath(listFile[i].getPath());
						if(listFile[i].getName().startsWith("prize67")){
							systemProp_path=listFile[i].getName();
						}
					}
				} else {
					if (file.getName().equals("ProjectConfig.mk")) {
						projectConfig_path = file.getPath();
					}
				}

			} else {
				System.out.println(path + "is not exist");
			}
		} catch (Exception e) {
			MyLogger.print("projectConfiFilePath--->"+e.getMessage());
			e.printStackTrace();
			System.out.println("Exception = " + e.getMessage());
		}
	}

	/**
	 * 修改版本号
	 * 
	 * @param path
	 * @param tag
	 * @throws IOException
	 */
	private static void modifyVersion(String path, String newVersion) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		InputStreamReader isr = new InputStreamReader(fis,"GBK");
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		String[] arrs = null;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			if (line.startsWith(VERSION_TAG)) {
				arrs = line.split("=");
				System.out.println(arrs[0] + " = " + arrs[1]);
				line = arrs[0].trim() + " = " + newVersion;
			}
			sb.append(line + "\n");
		}
		sb.setLength(sb.length() - 1);// 去掉最后一个换行符
		OutputStreamWriter outstreamWriter = new OutputStreamWriter(new FileOutputStream(path),"GBK"); 
		BufferedWriter writer = new BufferedWriter(outstreamWriter);
		writer.write(sb.toString());
		writer.close();
		br.close();
		isr.close();
		fis.close();
	}

	/**
	 * 获取ProjectConfig.mk中logo对应的分辨率信息
	 * 
	 * @return
	 */
	private static String getLogoFileName() {
		String projectConfig_boot_path = Constant.CUSTOMER_BOOT_PATH + "\\device\\prize";
		projectConfiFilePath(projectConfig_boot_path);
		String result = null;
		try {
			FileInputStream fis = new FileInputStream(projectConfig_path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			while ((line = br.readLine()) != null) {
				if (line.startsWith(BOOT_LOGO)) {
					arrs = line.split("=");
					System.out.println(arrs[0] + " = " + arrs[1]);
					result = arrs[1].trim();
					break;
				}
			}
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			MyLogger.print("getLogoFileName--->"+e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			MyLogger.print("getLogoFileName--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("getLogoFileName--->"+e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 *复制文件
	 * @param source
	 * @param dest
	 * @param isCover [是否复制和替换文件]
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void copyFile(String source,String dest,boolean isCover) throws FileNotFoundException, IOException {
		boolean isCreateNewFile = false;
		File file=new File(dest);
		if (file.exists() && isCover) {
			file.delete();
			file.createNewFile();
			isCreateNewFile = true;
		}
		if (!file.exists()) {
			file.createNewFile();
			isCreateNewFile = true;
		}
		
		if (!isCreateNewFile) {
			return;
		}
		FileInputStream fis = new FileInputStream(source);
        FileOutputStream fos = new FileOutputStream(dest);
 
        int len = 0;
        byte[] buf = new byte[1024*5];
        while ((len = fis.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }
        fis.close();
        fos.close();
    }
	
	/**
	 * 追加一行记录
	 * 
	 * @param destPath
	 *            [目标文件地址]
	 * @param content
	 *            [添加内容]
	 */
	private static void appendStr(String destPath, String content) throws IOException {
		FileInputStream fis = new FileInputStream(destPath);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		StringBuffer sb = new StringBuffer();
		boolean isExist = false;
		String lastLine=null;
		while ((line = br.readLine()) != null) {
			if (line.contains(content)) {
				isExist = true;
				break;
			}
			sb.append(line + "\n");
			String temp=line.trim();
			lastLine = temp;
		}
		if (!isExist) {
			//如果最后一行没有以\结尾
			if(!lastLine.endsWith("\\")){
				replaceAll(sb,lastLine,lastLine+" \\");
			}
			sb.append(content);
			BufferedWriter writer = new BufferedWriter(new FileWriter(destPath));
			writer.write(sb.toString());
			writer.close();
		}
		br.close();
		isr.close();
		fis.close();
	}
	
    public static StringBuffer replaceAll(StringBuffer sb, String oldStr, String newStr) {
        int i = sb.indexOf(oldStr);
        int oldLen = oldStr.length();
        int newLen = newStr.length();
        while (i > -1) {
            sb.delete(i, i + oldLen);
            sb.insert(i, newStr);
            i = sb.indexOf(oldStr, i + newLen);
        }
        return sb;
    }
		
	/**
	 * 修改ProjectConfig.mk中的开关
	 * @param key
	 * @param value
	 * @return
	 */
	private static boolean modifyProjectConfig(String key,String value){
		String projectConfig_boot_path = Constant.CUSTOMER_BOOT_PATH + "\\device\\prize";
		projectConfiFilePath(projectConfig_boot_path);
		try {
			FileInputStream fis = new FileInputStream(projectConfig_path);
			InputStreamReader isr = new InputStreamReader(fis,"GBK");
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.startsWith(key)) {
					arrs = line.split("=");
				    line = arrs[0] + "= " + value;
				}
				sb.append(line + "\n");
			}
			sb.setLength(sb.length() - 1);
			OutputStreamWriter outstreamWriter = new OutputStreamWriter(new FileOutputStream(projectConfig_path),"GBK"); 
			BufferedWriter writer = new BufferedWriter(outstreamWriter);
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			MyLogger.print("modifyProjectConfig--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("modifyProjectConfig--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 获取铃声资源
	 * @param type [ringtones,notifications,alarms]
	 * @return
	 */
	public static List<String> getRingList(String type){
		List<String> list=new ArrayList<String>();
		String allAudioPath=customerAllAudioPath;
		File file=new File(customerAllAudioPath);
		if(!file.exists()){
			allAudioPath=projectAllAudioPath;
		}
		try {
			FileInputStream fis = new FileInputStream(allAudioPath);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			while ((line = br.readLine()) != null) {
				line=line.trim();
				if(line!=null){
					String key="$(LOCAL_PATH)/"+type;
					if (line.startsWith(key)) {
						String[] attr=line.split("/");
						String str=attr[attr.length-1];
						str=str.substring(0, str.length()-2);
						list.add(str.trim());
						System.out.println(type+" = "+str);
					}
				}
			}
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			MyLogger.print("getRingList--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("getRingList--->"+e.getMessage());
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 修改校验软件的路径
	 * @param path
	 * @return
	 */
	public static boolean modifyCheckSumGeneratePath(String path){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try{
		org.w3c.dom.Document document = null;
		db = dbf.newDocumentBuilder();
		document = db.parse(new File("./config.xml"));
		DOMReader reader = new DOMReader();
		Document df_document = reader.read(document);
		Element rootElement = df_document.getRootElement();
		List<Element> childList = rootElement.elements();
		if (childList == null || childList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < childList.size(); i++) {
			Element childElement = childList.get(i);
			Attribute attribute = childElement.attribute("name");
			if (attribute != null && attribute.getValue().equals("CheckSum_Generate_exe")) {
				childElement.setText(path);
				break;
			}
		}

		// 提交修改的内容
		try {
			XMLWriter writer = new XMLWriter(new FileWriter(new File("./config.xml")));
			writer.write(df_document);
			writer.close();
		} catch (Exception ex) {
			MyLogger.print("modifyCheckSumGeneratePath--->"+ex.getMessage());
			ex.printStackTrace();
		}
		
	} catch (FileNotFoundException e) {
		MyLogger.print("modifyCheckSumGeneratePath--->"+e.getMessage());
		e.printStackTrace();
	} catch (IOException e) {
		MyLogger.print("modifyCheckSumGeneratePath--->"+e.getMessage());
		e.printStackTrace();
	}catch (ParserConfigurationException e) {
		MyLogger.print("modifyCheckSumGeneratePath--->"+e.getMessage());
		e.printStackTrace();
	}catch (SAXException e) {
		MyLogger.print("modifyCheckSumGeneratePath--->"+e.getMessage());
		e.printStackTrace();
	}
		return true;
	}
		
	/**
	 * 修改已经存在的待机壁纸
	 * @param exterWallPaper  [外部壁纸的父路径]
	 * @param interWallPaper  [需要替换的图片名字]
	 */
	private static void modifyStandbyWallpaper(String exterWallPaper,String interWallPaper){
		String interWallpapers_parent=Constant.PROJECT_BOOT_PATH+"\\packages\\apps\\PrebuildApps\\cooee\\wallpapers";
		String interWallpapers=interWallpapers_parent+"\\"+interWallPaper;
		String interWallpapers_small=interWallpapers.replace(".jpg", "_small.jpg");
		File file=new File(exterWallPaper);
		File[] listFile=file.listFiles();
		try {
			for(int i=0;i<listFile.length;i++){
				String exterWallPaperPath=listFile[i].getPath();
				if(exterWallPaperPath.contains("_small")){
					copyFile(exterWallPaperPath, interWallpapers_small, true);
				}else{
					copyFile(exterWallPaperPath, interWallpapers, true);
				}
			}	
			
		} catch (FileNotFoundException e) {
			MyLogger.print("modifyStandbyWallpaper--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("modifyStandbyWallpaper--->"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 *  设置默认的待机壁纸
	 * @param interWallPaper
	 */
	private static void defaultStandbyWallpaper(String interWallPaper){
		if(interWallPaper.endsWith("wallpaper00.jpg")){
			return;
		}
		System.out.println("defaultStandbyWallpaper = "+interWallPaper);
		String tempName="\\prize_temp.jpg";
		String tempName_small="\\prize_temp_small.jpg";
		String interWallpapers_parent=Constant.PROJECT_BOOT_PATH+"\\packages\\apps\\PrebuildApps\\cooee\\wallpapers";
		String interWallpapers00=interWallpapers_parent+"\\wallpaper00.jpg";
		String interWallpapers00_small=interWallpapers_parent+"\\wallpaper00_small.jpg";
		
		try{
			File defaultWallPaper=new File(interWallpapers00);
			File defaultWallPaper_small=new File(interWallpapers00_small);
			defaultWallPaper.renameTo(new File(interWallpapers_parent+tempName));
			defaultWallPaper_small.renameTo(new File(interWallpapers_parent+tempName_small));
			
			File wallPaper=new File(interWallpapers_parent+"\\"+interWallPaper);
			wallPaper.renameTo(new File(interWallpapers00));		
			String interWallPaper_small=StringUtil.insertString(interWallPaper,interWallPaper.length()-4,"_small");
			File wallPaper_small=new File(interWallpapers_parent+"\\"+interWallPaper_small);
			wallPaper_small.renameTo(new File(interWallpapers00_small));
			
			File tempFile=new File(interWallpapers_parent+tempName);
			tempFile.renameTo(new File(interWallpapers_parent+"\\"+interWallPaper));
			File tempFileSmall=new File(interWallpapers_parent+tempName_small);
			tempFileSmall.renameTo(new File(interWallpapers_parent+"\\"+interWallPaper_small));
		}catch(Exception e){
			MyLogger.print("----------->defaultStandbyWallpaper:"+e.getMessage());
			System.out.println("defaultStandbyWallpaper Exception:"+e.getMessage() );
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加新壁纸
	 * @param exterWallPaper 壁纸文件所在目录
	 */
	public static void addStandbyWallpaper(String exterWallPaper) {
		String interWallpapers_parent=Constant.PROJECT_BOOT_PATH+"\\packages\\apps\\PrebuildApps\\cooee\\wallpapers";	
		String[] wallArray = ReadInfoUtil.getAllStandbyWallpaper();
		int wallPaperLength = wallArray.length;
		try {
			(new File(interWallpapers_parent)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			
			File wallPath = new File(exterWallPaper);
			String[] file = wallPath.list();// 获取文件目录列表
			for (String s : file) {
				System.out.println(s);
			}
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (exterWallPaper.endsWith(File.separator)) {// File.separator与系统相关的默认名称分割符
					temp = new File(exterWallPaper + file[i]);
				} else {
					temp = new File(exterWallPaper + File.separator + file[i]);
				}

				if (temp.isFile()) {//如果是文件的话就拷贝文件
					String filePath = temp.getPath();
					String fileName = temp.getName();
					System.out.println("filePath : " + filePath + ", fileName : " + fileName);
					FileInputStream input = new FileInputStream(temp);
					String newFileNum = null;//用于保存文件编号
					if(wallPaperLength < 10){  //如果文件数量小于10，需在编号前加0
						newFileNum = "0" + wallPaperLength;
					} else {
						newFileNum = "" + wallPaperLength;
					}
					int pointIndex = fileName.lastIndexOf(".");//获取文件"."的位置
					System.out.println("---addStandbyWallpaper2---pointIndex="+pointIndex+", the length of file="+fileName.length());
					//获取壁纸文件格式
					String fileFormat = fileName.substring(pointIndex, fileName.length());
					System.out.println("---addStandbyWallpaper2---fileFormat="+fileFormat);
					if(fileName.contains("_small")){//壁纸分带_small和不带_small的
						fileName = "wallpaper" + newFileNum + "_small" + fileFormat;
					}else{
						fileName = "wallpaper" + newFileNum + fileFormat;					 
					}
					
					FileOutputStream output = new FileOutputStream(interWallpapers_parent + "/" + fileName);
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.close();
					input.close();
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();

		}

	}
		
	/**
	 * 删除指定壁纸
	 * @param interWallPaperName 被删除壁纸的名字
	 */
	public static void deleteStandbyWallpaper(String interWallPaperName) {
		String filePath = Constant.PROJECT_BOOT_PATH+"\\packages\\apps\\PrebuildApps\\cooee\\wallpapers\\"+interWallPaperName;
		try {
			String smallFilePath = filePath;			
			smallFilePath=StringUtil.insertString(smallFilePath,smallFilePath.length()-4,"_small");
			File delFile = new File(filePath);
			File smallDelFile = new File(smallFilePath);
			delFile.delete();
			smallDelFile.delete();
			rename(delFile,smallDelFile);

		} catch (Exception e) {
			System.out.println("deleteStandbyWallpaper Exception:"+e.getMessage());
			e.printStackTrace();

		}
	}
		
	/**
	 * 修改虚拟按键，复制系统需要修改的3个文件,system.prop和config.xml及dimens_material.xml
	 */	
	private static void copyVitualFile(){
		String projectConfig_boot_path = Constant.CUSTOMER_BOOT_PATH + "\\device\\prize";
		
		projectConfiFilePath(projectConfig_boot_path);
		System.out.println("systemProp_path="+systemProp_path);
				
		try {
			copyFile(Constant.PROJECT_BOOT_PATH + "\\device\\prize\\"+systemProp_path+"\\system.prop",
					Constant.CUSTOMER_BOOT_PATH + "\\device\\prize\\"+systemProp_path+"\\system.prop",false);
			
			copyFile(Constant.PROJECT_BOOT_PATH + "\\frameworks\\base\\core\\prize\\res\\res\\values\\config.xml",
					Constant.CUSTOMER_BOOT_PATH + "\\frameworks\\base\\core\\prize\\res\\res\\values\\config.xml",false);
			
			copyFile(Constant.PROJECT_BOOT_PATH + "\\frameworks\\base\\core\\prize\\res\\res\\values\\dimens_material.xml",
					Constant.CUSTOMER_BOOT_PATH + "\\frameworks\\base\\core\\prize\\res\\res\\values\\dimens_material.xml",false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改虚拟按键，修改system.prop数值
	 * @param key 
	 * @param value
	 */
	private static void modifySystemPropValue(String key,String value) {
		String projectConfig_boot_path = Constant.CUSTOMER_BOOT_PATH + "\\device\\prize";
		projectConfiFilePath(projectConfig_boot_path);
		String projectConfig_absolution_boot_path = Constant.CUSTOMER_BOOT_PATH + "\\device\\prize\\"+systemProp_path+"\\system.prop";
		try {
			FileInputStream fis = new FileInputStream(projectConfig_absolution_boot_path);
			InputStreamReader isr = new InputStreamReader(fis,"GBK");
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.startsWith(key)) {
					arrs = line.split("=");
					line = arrs[0].trim() + "=" + value;
				}
				sb.append(line + "\n");
			}
			sb.setLength(sb.length() - 1);		
			OutputStreamWriter outstreamWriter = new OutputStreamWriter(new FileOutputStream(projectConfig_absolution_boot_path),"GBK"); 
			BufferedWriter writer = new BufferedWriter(outstreamWriter);
			writer.write(sb.toString());
			writer.close();
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改虚拟按键，修改config.xml数值
	 * @param key
	 * @param value
	 */
	private static boolean modifyNavigationBarValue(String key,String value) {
		String projectConfig_boot_path = Constant.CUSTOMER_BOOT_PATH + "\\frameworks\\base\\core\\prize\\res\\res\\values\\config.xml";
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try{
			org.w3c.dom.Document document = null;
			db = dbf.newDocumentBuilder();
			document = db.parse(new File(projectConfig_boot_path));
			DOMReader reader = new DOMReader();
			Document df_document = reader.read(document);
			Element rootElement = df_document.getRootElement();
			List<Element> childList = rootElement.elements();
			if (childList == null || childList.size() <= 0) {
				return false;
			}
			for (int i = 0; i < childList.size(); i++) {
				Element childElement = childList.get(i);
				Attribute attribute = childElement.attribute("name");
				if (attribute != null && attribute.getValue().equals(key)) {
					childElement.setText(value);
					break;
				}
			}

			// 提交修改的内容
			XMLWriter writer = new XMLWriter(new FileWriter(new File(projectConfig_boot_path)));
			writer.write(df_document);
			writer.close();		
		} catch (FileNotFoundException e) {
			MyLogger.print("modifyNavigationBarValue--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("modifyNavigationBarValue--->"+e.getMessage());
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			MyLogger.print("modifyNavigationBarValue--->"+e.getMessage());
			e.printStackTrace();
		}catch (SAXException e) {
			MyLogger.print("modifyNavigationBarValue--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 修改虚拟按键，修改dimens_material.xml数值
	 * @param key
	 * @param value
	 */
	private static boolean modifyDimensScreenValue(String key,String value){
		String projectConfig_boot_path = Constant.CUSTOMER_BOOT_PATH + "\\frameworks\\base\\core\\prize\\res\\res\\values\\dimens_material.xml";
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try{
			org.w3c.dom.Document document = null;
			db = dbf.newDocumentBuilder();
			document = db.parse(new File(projectConfig_boot_path));
			DOMReader reader = new DOMReader();
			Document df_document = reader.read(document);
			Element rootElement = df_document.getRootElement();
			List<Element> childList = rootElement.elements();
			if (childList == null || childList.size() <= 0) {
				return false;
			}
			for (int i = 0; i < childList.size(); i++) {
				Element childElement = childList.get(i);
				Attribute attribute = childElement.attribute("name");
				if (attribute != null && attribute.getValue().equals(key)) {
					childElement.setText(value);
					break;
				}
			}

			// 提交修改的内容
			XMLWriter writer = new XMLWriter(new FileWriter(new File(projectConfig_boot_path)));
			writer.write(df_document);
			writer.close();
		} catch (FileNotFoundException e) {
			MyLogger.print("modifyNavigationBarValue--->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			MyLogger.print("modifyNavigationBarValue--->"+e.getMessage());
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			MyLogger.print("modifyNavigationBarValue--->"+e.getMessage());
			e.printStackTrace();
		}catch (SAXException e) {
			MyLogger.print("modifyNavigationBarValue--->"+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	
	/**
	 * 删除两个文件后重新命名编号在被删文件后面的名字
	 * @param filePath  壁纸文件
	 * @param smallFilePath  与之对应缩略图的文件
	 * @param
	 */
	
	private static void rename(File filePath, File smallFilePath){
		String numString = filePath.getName().substring(9,11);
		Integer wallpaperSerNum = Integer.parseInt(numString);
		System.out.println("---rename---被删除的壁纸的编号是wallpaperSerNum:" + wallpaperSerNum);		
		//读取删除之后的 所有壁纸
		String[] wallArray=ReadInfoUtil.getAllStandbyWallpaper();
		for(int i=wallpaperSerNum;i<wallArray.length;i++){
			String wallPaperPath=filePath.getParent()+"\\"+wallArray[i];
			String smallPaperPath=StringUtil.insertString(wallPaperPath,(int)(wallPaperPath.length()-4),"_small");			
			String newWallPaperPath="";
			String newSmallPaperPath="";
			if(i>=10){				 
				newWallPaperPath=wallPaperPath.replace(String.valueOf(i+1), String.valueOf(i));
				newSmallPaperPath=smallPaperPath.replace(String.valueOf(i+1), String.valueOf(i));
			}else if(i==9){
				newWallPaperPath=wallPaperPath.replace(String.valueOf(i+1), String.valueOf("0"+i));
				newSmallPaperPath=smallPaperPath.replace(String.valueOf(i+1), String.valueOf("0"+i));
			}else{
				CharSequence target="0"+(i+1);
	            CharSequence replacement="0"+i;
				newWallPaperPath=wallPaperPath.replace(target, replacement);
				newSmallPaperPath=smallPaperPath.replace(target, replacement);
			}
			
			new File(wallPaperPath).renameTo(new File(newWallPaperPath));
			new File(smallPaperPath).renameTo(new File(newSmallPaperPath));
		}
	}
	
}

package com.prize.modifytool.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.xml.sax.SAXException;

import com.prize.modifytool.constant.Constant;

public class ReadInfoUtil {
	
	private static String projectConfig_path;
	
	
	/**
	 * 读取品牌名称
	 * @return
	 */
	public static String getBrandName(){
		String brandName="";
		projectConfig_path=null;
		String projectConfig_boot_path = Constant.CUSTOMER_BOOT_PATH + "\\device\\prize";
		projectConfiFilePath(projectConfig_boot_path);
		if(projectConfig_path==null){
			return brandName;
		}
		try {
			FileInputStream fis = new FileInputStream(projectConfig_path);
			InputStreamReader isr = new InputStreamReader(fis,"GBK");
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("PRODUCT_BRAND_CUSTOM")) {
					arrs = line.split("=");
					brandName= arrs[1].trim();
					break;
				}
			}
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
		return brandName;
	}
	
	/**
	 * 手机型号、蓝牙、WIFI名称
	 * @return
	 */
	public static String getDeviceName(){
		String name="";
		projectConfig_path=null;
		String projectConfig_boot_path = Constant.CUSTOMER_BOOT_PATH + "\\device\\prize";
		projectConfiFilePath(projectConfig_boot_path);
		if(projectConfig_path==null){
			return name;
		}
		try {
			FileInputStream fis = new FileInputStream(projectConfig_path);
			InputStreamReader isr = new InputStreamReader(fis,"GBK");
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("PRODUCT_MODEL_CUSTOM")) {
					arrs = line.split("=");
					name=arrs[1].trim();
					break;
				}
			}
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
		
		return name;
	}
	
	/**
	 * 默认的屏幕亮度
	 * @return
	 */
	public static String getDefaultScreenLight(){
		String defaultLight="";
		String screenlight_path = Constant.CUSTOMER_BOOT_PATH
				+ "\\frameworks\\base\\packages\\SettingsProvider\\res\\values\\defaults.xml";
		String project_screenlight_path = Constant.PROJECT_BOOT_PATH
				+ "\\frameworks\\base\\packages\\SettingsProvider\\res\\values\\defaults.xml";
		File file = new File(screenlight_path);
		if (!file.exists()) {
			screenlight_path=project_screenlight_path;
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        org.w3c.dom.Document document2 = null;
		try {
			db = dbf.newDocumentBuilder();
			document2 = db.parse(new File(screenlight_path));
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
        List<Element> childList=rootElement.elements();
		if (childList == null || childList.size() <= 0) {
			System.out.println("updateDefaultLight()--->childList == null || childList.size() <= 0");
			return defaultLight;
		}
		for (int i = 0; i < childList.size(); i++) {
			Element childElement = childList.get(i);
	        Iterator attrIterator = childElement.attributeIterator();
			while (attrIterator.hasNext()) {
				Attribute attribute = (Attribute) attrIterator.next();
				if(attribute.getValue().equals("def_screen_brightness")){
					defaultLight=childElement.getText();
					break;
				}
			}
		}        
		return defaultLight;
	}
	
	/**
	 * 灭屏时间
	 * @return
	 */
	public static String getScreenOffTime(){
		String offTime="";
		String screenOffTime_path =  Constant.CUSTOMER_BOOT_PATH + "\\frameworks\\base\\packages\\SettingsProvider\\res\\values\\defaults.xml";
		String project_screenOffTime_path=Constant.PROJECT_BOOT_PATH+"\\frameworks\\base\\packages\\SettingsProvider\\res\\values\\defaults.xml";
		
		File file=new File(screenOffTime_path);
		if(!file.exists()){
			screenOffTime_path=project_screenOffTime_path;
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        org.w3c.dom.Document document2 = null;
		try {
			db = dbf.newDocumentBuilder();
			document2 = db.parse(new File(screenOffTime_path));
		} catch (Exception e) {
			e.printStackTrace();
		} 
        DOMReader domReader = new DOMReader();
        Document document3 = domReader.read(document2);
        Element rootElement = document3.getRootElement();
        List<Element> childList=rootElement.elements();
		if (childList == null || childList.size() <= 0) {
			return offTime;
		}
		for (int i = 0; i < childList.size(); i++) {
			Element childElement = childList.get(i);
			Attribute attribute = childElement.attribute("name");
			if (attribute != null && attribute.getValue().equals("def_screen_off_timeout")) {
				offTime=childElement.getText();
				break;
			}
		}        
		return offTime;
	}
	
	/**
	 * 墙纸数量
	 * @return
	 */
	public static String getWallPapersNum(){
		String papersNum="";
		projectConfig_path=null;
		String projectConfig_boot_path = Constant.CUSTOMER_BOOT_PATH + "\\device\\prize";
		projectConfiFilePath(projectConfig_boot_path);
		if(projectConfig_path==null){
			return papersNum;
		}
		try {
			FileInputStream fis = new FileInputStream(projectConfig_path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			while ((line = br.readLine()) != null) {
				if (line.contains("L_SW_TOTAL_COPIED_COOEE_WALLPAPERS")) {
					arrs = line.split("=");
				    papersNum=arrs[1].trim();
				    break;
				}
			}
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return papersNum;
	}
	
	/**
	 * 读取版本号
	 * @return
	 */
	public static String getVersionNumber(){
		if(Constant.CUSTOMER_BOOT_PATH==null){
			return null;
		}
		File file=new File(Constant.CUSTOMER_BOOT_PATH + "\\device\\prize");
		if(!file.exists()){
			return null;
		}
		String result = null;
		String projectConfig_boot_path = Constant.CUSTOMER_BOOT_PATH + "\\device\\prize";
		projectConfiFilePath(projectConfig_boot_path);
		try {
			FileInputStream fis = new FileInputStream(projectConfig_path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String[] arrs = null;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("BUILD_DISPLAY_ID_CUSTOM")) {
					arrs = line.split("=");
					result=arrs[1].trim();
					break;
				}
			}
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
		return result;
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
					}
				} else {
					if (file.getName().equals("ProjectConfig.mk")) {
						projectConfig_path = file.getPath();
					}
				}

			} else {
				System.out.println(path + " is not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception = " + e.getMessage());
		}
	}
	
	/**
	 * 读取校验软件地址
	 * @return
	 */
	public static String getCheckSumGeneratePath(){
		String result="";
		File file=new File("./config.xml");
		if(!file.exists()){
			return null;
		}
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		org.w3c.dom.Document document = null;
		try {
			db = dbf.newDocumentBuilder();
			document = db.parse(file);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DOMReader reader = new DOMReader();
		Document df_document = reader.read(document);
		Element rootElement = df_document.getRootElement();
		List<Element> childList = rootElement.elements();
		if (childList == null || childList.size() <= 0) {
			return null;
		}
		for (int i = 0; i < childList.size(); i++) {
			Element childElement = childList.get(i);
			Attribute attribute = childElement.attribute("name");
			if (attribute != null && attribute.getValue().equals("CheckSum_Generate_exe")) {
				result=childElement.getText();
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * 获取已有的所有待机墙纸   附：待机一张墙纸包含墙纸和墙纸缩略图  此处指读取正常大小的墙纸
	 * @return
	 */
	public static String[] getAllStandbyWallpaper(){
		String customer_wallpapers_parent=Constant.PROJECT_BOOT_PATH+"\\packages\\apps\\PrebuildApps\\cooee\\wallpapers";
		File file=new File(customer_wallpapers_parent);
		if(!file.exists()){
			return null;
		}
		File[] listFile=file.listFiles();
		int count=listFile.length;
		if(listFile==null || count==0){
			return null;
		}
		int index=0;
		String[] array=new String[count/2];
		for(int i=0;i<count;i++){
			String name=listFile[i].getName();
			if(!name.contains("_small.")){
				array[index]=name;
				index++;
			}
		}
		Arrays.sort(array);
		return array;
	}
	
}

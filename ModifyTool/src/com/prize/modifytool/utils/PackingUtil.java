package com.prize.modifytool.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.prize.modifytool.bean.DataBean;

public class PackingUtil {
	
	private PackingProgressInterface mPackingProgressInterface;

	public void setPackingListener(PackingProgressInterface mInterface) {
		this.mPackingProgressInterface = mInterface;
	}
	public interface PackingProgressInterface{
		public void packingProgress(String description);
	}
	
	private static PackingUtil instance;
	public static PackingUtil getInstance(){
		if(instance==null){
			instance=new PackingUtil();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param srcPath [编译输出目录]
	 * @param target [打包输出根目录]
	 * @param checkSum [校验软件目录]
	 * @return
	 */
	public  boolean ZipRom(String srcPath, String target, String checkSum) {
		if (srcPath == null || null == target || null == checkSum) {
			System.out.println("srcPath="+srcPath);
			System.out.println("target="+target);
			System.out.println("checkSum="+checkSum);
			return false;
		}
		if (!srcPath.endsWith("\\"))
			srcPath = srcPath + "\\";
		if (!target.endsWith("\\"))
			target = target + "\\";
		String[] imgList = { "boot.img", "cache.img", "lk.bin", "logo.bin",
				"MT6735M_Android_scatter.txt",
				"preloader_prize6735m_65c_l1.bin", "ota_scatter.txt",
				"recovery.img", "secro.img", "system.img", "trustzone.bin",
				"userdata.img" };
		String[][] preFix = { { "obj\\CGEN", "APDB_" },
				{ "system\\etc\\mddb", "BPLGUInfo" } };
		InputStream is = null;
		OutputStream os = null;
		byte[] buffer = new byte[1024 * 1024];
		String destPath=target + DataBean.newVerisonNumber;		
		File file2=new File(destPath);
		if(!file2.exists()){
			file2.mkdirs();
		}
		// copy file
		for (String string : imgList) {
			String sourcePath=srcPath + string;
			File file=new File(sourcePath);
			if(!file.exists()){
				if(string.equals("MT6735M_Android_scatter.txt")){
					string="MT6735_Android_scatter.txt";
				}
				if(string.equals("preloader_prize6735m_65c_l1.bin")){
					string="preloader_prize6735_65c_l1.bin";
				}
			}

			mPackingProgressInterface.packingProgress("正在复制  "+string);
			try {
				is = new FileInputStream(srcPath + string);
				os = new FileOutputStream(destPath+"\\"+string);
				for (int length = 0; (length = is.read(buffer)) > 0;) {
					os.write(buffer, 0, length);
				}
				os.flush();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				closeInputStream(is);
				closeOutputStream(os);
			}

		}
		// ** end of copy .img file*\\

		// ** start of copy modem file\\
		File srcFile = null;
		String[] file = null;
		for (String[] b : preFix) {
			try {
				srcFile = new File(srcPath + b[0]);

				file = srcFile.list();
				for (String filename : file) {
					if (filename.startsWith(b[1])) {
						System.out.println("sun="+srcPath + b[0] + "\\"
								+ filename);
						is = new FileInputStream(srcPath + b[0] + "\\"
								+ filename);
						os = new FileOutputStream(target +DataBean.newVerisonNumber+"\\"+ filename);
						if (null == os || null == is) {
							return false;
						} else {
							for (int length = 0; (length = is.read(buffer)) > 0;) {
								os.write(buffer, 0, length);
							}
							os.flush();
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				closeInputStream(is);
				closeOutputStream(os);
			}
		}
		mPackingProgressInterface.packingProgress("正在校验....");

		if (checkSum(checkSum, target+DataBean.newVerisonNumber+"\\")) {
			try {
				Thread.sleep(43000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boolean isComplete=false;
			
			for(int i=0;i<20;i++){
				System.out.println("i="+i);
				if(ischeckComplete(target+DataBean.newVerisonNumber+"\\")){
					isComplete=true;
					break;
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(isComplete){
				mPackingProgressInterface.packingProgress("校验成功，正在打包...");
				if(zipFiles(target)){
					mPackingProgressInterface.packingProgress("打包成功");
				}else{
					mPackingProgressInterface.packingProgress("打包失败");
				}
			}
		}else{
			mPackingProgressInterface.packingProgress("校验失败");
		}

		return true;
	}
	
	/**
	 * 判断校验是否完成
	 * @param target
	 * @return
	 */
	private boolean ischeckComplete(String target) {
		String[] filelist = new File(target).list();
		for (String string : filelist) {
			if (string.endsWith("Checksum.ini")) {
				return true;
			}
		}
		return false;
	}
	
	private  void closeInputStream(InputStream is) {
		if (is == null) {
			return;
		}
		try {
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeOutputStream(OutputStream os) {
		if (os == null) {
			return;
		}
		try {
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkSum(String checksum, String target) {
		try {
			String[] ss = { "cmd.exe", "/c", "start", checksum };
			Process checkProcess=Runtime.getRuntime().exec(ss, null, new File(target));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
			return false;
		}
		return true;
	}

	private boolean zipFiles(String target) {
		ZipOutputStream zipOutputStream = null;
		InputStream is = null;
		byte[] buffer = new byte[1024 * 1024];
		try {
			String zipStr = DataBean.newVerisonNumber+".zip";
			zipOutputStream = new ZipOutputStream(new FileOutputStream(target
					+ zipStr));
			String[] filelist = new File(target+DataBean.newVerisonNumber+"\\").list();
			for (String string : filelist) {
				if (string.endsWith(".zip")) {
					continue;
				}
				mPackingProgressInterface.packingProgress("正在压缩  "+string);
				System.out.println("zipping : " + string);
				if(string.contains("\\")){
					System.out.println("no support for folder.");
					continue;
				}
				zipOutputStream.putNextEntry(new ZipEntry(string));

				is = new FileInputStream(target +DataBean.newVerisonNumber+"\\"+ string);
				for (int length = 0; (length = is.read(buffer)) > 0;) {
					zipOutputStream.write(buffer, 0, length);
				}
				closeInputStream(is);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeOutputStream(zipOutputStream);
			closeInputStream(is);
		}
		return true;
	}
	
	/**
	 * 获取编译输出目录的根路径
	 * @param projectPath
	 * @return
	 */
	public String getCompileOutPath(String projectPath) {
		String result = null;
		String path=projectPath+"\\out\\target\\product";
		File file=new File(path);
		if(!file.exists()){
			return null;
		}
		File[] fileList=file.listFiles();
		if(fileList==null || fileList.length<=0){
			return null;
		}
		for(int index=0;index<fileList.length;index++){
			File temp=fileList[index];
			if(!temp.isDirectory()){
				continue;
			}
			File[] list=temp.listFiles();
			if(list==null || list.length<=0){
				continue;
			}
			
			for(int i=0;i<list.length;i++){
				File file2=list[i];
				String path1=file2.getPath();
				if(path1.contains("MT6735M_Android_scatter.txt") ||path1.contains("MT6735_Android_scatter.txt")){
					result=temp.getPath();
					break;
				}
			}
		}
		return result;
	}
	
}

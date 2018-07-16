package com.itecheasy.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class FileUtils {
	public static String readFileByLines(String fileName) {
		BufferedReader reader = null;
		FileInputStream fr = null;
		StringBuilder sb = new StringBuilder();
		try {
			fr = new FileInputStream(fileName);
			InputStreamReader is = new InputStreamReader(fr, "GBK");
			reader = new BufferedReader(is);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb.toString();
	}

	public static void writerFile(String fileName, String content,
			boolean isAppend) {
		FileOutputStream stream = null; // provides file access
		OutputStreamWriter writer = null; // writes to the file
		try {
			stream = new FileOutputStream(fileName, isAppend);
			writer = new OutputStreamWriter(stream, "GBK");
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/***
	 * 
	 * @param sourceFileName 文件完整路径
	 * @param targetFilePath 
	 */
	public static void moveFile(String sourceFileName, String targetFilePath) {
		 File sourceFile = new File(sourceFileName);
		 File targetFile = new File(targetFilePath);
		 if(!sourceFile.exists())
			 throw new RuntimeException(sourceFileName + "文件不存在");
		 if(!targetFile.exists())    
			 targetFile.mkdirs();
		 File newfile = new File(targetFilePath +File.separator+ sourceFile.getName());     
		 sourceFile.renameTo(newfile);
		 if(sourceFile.exists())
			 sourceFile.delete();
	}

	public static void deleteFile(String sourceFileName) {
		 File sourceFile = new File(sourceFileName);
		 if(sourceFile.exists())
			 sourceFile.delete();
	}
	
	/***
	 * 获取目录下所有文件,按文件名时间排序
	 * @param sourcePath
	 * @param type
	 * @return
	 */
	/*public static List<File> getAllFile(String sourcePath ,final String extension) {
		File file = new File(sourcePath);
		File[] files = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				String fn = pathname.getName();
				if (fn.lastIndexOf(".") > 0)
					return extension.equalsIgnoreCase(fn.substring(fn.lastIndexOf("."), fn.length()).toLowerCase());
				else
					return false;
			}
		});
		
		if(files == null ) return new ArrayList<File>();
		
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				String f1 = o1.getName();
				String f2 = o2.getName();
				long d1 = UuidHelp.getDateFromUuid(f1.substring(0,f1.lastIndexOf("."))).getTime();
				long d2 = UuidHelp.getDateFromUuid(f2.substring(0,f1.lastIndexOf("."))).getTime();
				return d1 > d2 ? 1 : 0;
			}
		});
		
		List<File> fileList = Arrays.asList(files);
		return fileList;
	}*/
	
	

}

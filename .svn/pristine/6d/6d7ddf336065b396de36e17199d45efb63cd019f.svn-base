package com.itecheasy.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * @author taozihao
 * @date 2018年8月29日 下午1:18:14
 * @description	自动更新osms_am服务，请不要擅自调用
 */
public class UpdateServiceUtils {
	
	private static final Logger LOGGER = Logger.getLogger(UpdateServiceUtils.class);
	
	/**
	 * 创建线程自动更新osms_am服务，请不要擅自调用
	 * 
	 * @throws IOException
	 */
	public static void updateService() throws IOException{
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					LOGGER.info("start to update service");
					Process pro = Runtime.getRuntime().exec("cmd /c call " + DeployProperties.getInstance().getProperty("batchFilePath"));
					pro.getOutputStream().close();
					BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream(), "UTF-8")); 
					while (br.readLine() != null) {}
			        br.close();
				}catch(Exception e){
					LOGGER.info(e.getMessage());
					e.printStackTrace();
				}
			}
		}).start();
	}

}

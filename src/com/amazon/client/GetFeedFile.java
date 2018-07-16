/**
 * 
 */
package com.amazon.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Administrator
 *
 */
public class GetFeedFile {

    public static Boolean downLoadUrlFile(String url, String filePath) {
        try {
            URLConnection connection = new URL(url).openConnection();
            InputStream input = connection.getInputStream();
            OutputStream output = new FileOutputStream(new File(filePath));
            try {
                byte[] buffer = new byte[1024];
                int i = 0;
                while ((i = input.read(buffer)) != -1) {
                    output.write(buffer, 0, i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            	if(null!=output)
                  output.flush();
            	if(null!=output)
                   output.close();
            	if(null!=input)
                   input.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static InputStream getFileAsStream(String url, String filePath) {
        try {
            URLConnection connection = new URL(url).openConnection();
            InputStream input = connection.getInputStream();
            OutputStream output = new FileOutputStream(new File(filePath));
            try {
                byte[] buffer = new byte[1024];
                int i = 0;
                while ((i = input.read(buffer)) != -1) {
                    output.write(buffer, 0, i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            	if(null!=output)
                  output.flush();
            	if(null!=output)
                   output.close();
            	if(null!=input)
                   input.close();
            }
            return input;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

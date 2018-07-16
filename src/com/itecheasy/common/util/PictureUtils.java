package com.itecheasy.common.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class PictureUtils {
	private static Logger logger = Logger.getLogger(PictureUtils.class);

	/**
	 * 等比压缩图片
	 * 
	 * @param pictureFile
	 * @param destFile
	 * @param width
	 * @param height
	 */
	public static void resizePicture(File pictureFile, String destFile,
			int width, int height) {
		try {

			String origFile = pictureFile.getAbsolutePath();
			int pos = origFile.lastIndexOf(".");
			String fileType = origFile.substring(pos + 1);
			BufferedImage srcImage = ImageIO.read(pictureFile); // 读入文件
			Integer srcWidth = srcImage.getWidth(null);
			Integer srcHeight = srcImage.getHeight(null);

			if (srcWidth < width && srcWidth < width) {
				width = srcWidth;
				height = srcHeight;
			} else if ((float) srcWidth / srcHeight > (float) width / height) {
				height = Math.round((float) width * srcHeight / srcWidth);
			} else {
				width = Math.round((float) height * srcWidth / srcHeight);
			}
			Image image = srcImage.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);

			BufferedImage bi = new BufferedImage(width, height,
					BufferedImage.TYPE_3BYTE_BGR);
			Graphics g = bi.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(bi, fileType.toLowerCase(), new File(destFile));// 输出到文件流
		} catch (IOException e) {
			// throw new RuntimeException(e);
			logger.error(pictureFile.getName() + " does not exist!");
		}
	}

	//
	// public static void resizePicture2(File pictureFile, String destFile, int
	// width, int height) {
	// try {
	// String origFile = pictureFile.getAbsolutePath();
	// int pos = origFile.lastIndexOf(".");
	// String fileType = origFile.substring(pos + 1);
	// // BufferedImage srcImage = ImageIO.read(pictureFile); // 读入文件
	// // Image image = srcImage.getScaledInstance(width, height,
	// Image.SCALE_DEFAULT);
	// Image image = ImageIO.read(pictureFile);
	// BufferedImage bi = new BufferedImage(width, height,
	// BufferedImage.TYPE_3BYTE_BGR);
	// Graphics g = bi.getGraphics();
	// g.drawImage(image, 0, 0, width, height, null); // 绘制缩小后的图
	// g.dispose();
	//
	// // ImageIO.write(bi, fileType.toLowerCase(), new File(destFile));//
	// 输出到文件流
	//
	// FileOutputStream deskImage = new FileOutputStream(destFile); // 输出到文件流
	// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
	// encoder.encode(bi); // 近JPEG编码
	// deskImage.close();
	// } catch (IOException e) {
	// throw new RuntimeException(e);
	// }
	// }
	//
	// public static void resizePicture3(String srcURL, String deskURL, double
	// comBase, double scale) {
	// try {
	// /* srcURl 原图地址；deskURL 缩略图地址；comBase 压缩基数；scale 压缩限制(宽/高)比例 */
	// java.io.File srcFile = new java.io.File(srcURL);
	// Image image = ImageIO.read(srcFile);
	// int srcHeight = image.getHeight(null);
	// int srcWidth = image.getWidth(null);
	// int deskHeight = 0;// 缩略图高
	// int deskWidth = 0;// 缩略图宽
	// double srcScale = (double) srcHeight / srcWidth;
	// if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
	// if (srcScale >= scale || 1 / srcScale > scale) {
	// if (srcScale >= scale) {
	// deskHeight = (int) comBase;
	// deskWidth = srcWidth * deskHeight / srcHeight;
	// } else {
	// deskWidth = (int) comBase;
	// deskHeight = srcHeight * deskWidth / srcWidth;
	// }
	// } else {
	// if ((double) srcHeight > comBase) {
	// deskHeight = (int) comBase;
	// deskWidth = srcWidth * deskHeight / srcHeight;
	// } else {
	// deskWidth = (int) comBase;
	// deskHeight = srcHeight * deskWidth / srcWidth;
	// }
	// }
	// } else {
	// deskHeight = srcHeight;
	// deskWidth = srcWidth;
	// }
	// BufferedImage bi = new BufferedImage(deskWidth, deskHeight,
	// BufferedImage.TYPE_3BYTE_BGR);
	// bi.getGraphics().drawImage(image, 0, 0, deskWidth, deskHeight, null); //
	// 绘制缩小后的图
	// FileOutputStream deskImage = new FileOutputStream(deskURL); // 输出到文件流
	// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
	// encoder.encode(bi); // 近JPEG编码
	// deskImage.close();
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ImageFormatException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	/** */
	/**
	 * 给图片添加水印
	 * 
	 * @param filePath
	 *            需要添加水印的图片的路径
	 * @param markContent
	 *            水印的文字
	 * @param markContentColor
	 *            水印文字的颜色
	 * @param qualNum
	 *            图片质量
	 * @return
	 */
	public static byte[] createMark(File picFile, String markContent,
			Color markContentColor, float qualNum) {
		ImageIcon imgIcon = new ImageIcon(picFile.getAbsolutePath());
		Image theImg = imgIcon.getImage();
		int width = theImg.getWidth(null);
		int height = theImg.getHeight(null);
		if (width == -1 && height == -1) {
			return null;
		}
		BufferedImage bimage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bimage.createGraphics();
		g.setColor(markContentColor);
		g.setBackground(Color.white);
		g.drawImage(theImg, 0, 0, null);
		Font font = new Font("Arial", Font.ITALIC, 30);
		g.setFont(font);
		g.drawString(markContent, (int) Math.ceil(width / 2.5),
				(int) Math.ceil(height / 2)); // 添加水印的文字和设置水印文字出现的内容
		g.dispose();
		ByteArrayOutputStream out;
		try {
			out = new ByteArrayOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
			param.setQuality(qualNum, true);
			encoder.encode(bimage, param);
			out.close();
		} catch (Exception e) {
			throw new RuntimeException("加水印出错");
		}
		return out.toByteArray();
	}

	/**
	 * 给图片添加水印
	 * 
	 * @param filePath
	 *            需要添加水印的图片的路径
	 * @param markpath
	 *            水印的图片路径
	 * @param alpha
	 *            透明度
	 * @return
	 */
	public static byte[] createMark(File picFile, String markpath, float alpha) {
		if (picFile == null) {
			return null;
		}
		ImageIcon imgIcon = new ImageIcon(picFile.getAbsolutePath());
		Image theImg = imgIcon.getImage();
		int width = theImg.getWidth(null);
		int height = theImg.getHeight(null);
		if (width == -1 && height == -1) {
			FileInputStream fs;
			try {
				fs = new FileInputStream(picFile.getAbsolutePath());
			} catch (FileNotFoundException x) {
				return null;
			}
			theImg = BitmapUtils.read(fs);
			width = theImg.getWidth(null);
			height = theImg.getHeight(null);
			if (width == -1 && height == -1) {
				return null;
			}
		}

		ImageIcon wmImgIcon = new ImageIcon(markpath);
		Image wmTheImg = wmImgIcon.getImage();
		int wmWith = wmTheImg.getWidth(null);
		int wmHeight = wmTheImg.getHeight(null);

		/*
		 * if (width / 2 < wmWith) { float w = width / 2f; float h = w / wmWith
		 * * wmHeight; wmTheImg = resize(wmImgIcon, (int) h, (int) w); wmWith =
		 * wmTheImg.getWidth(null); wmHeight = wmTheImg.getHeight(null); }
		 */

		/*
		 * if(width!=wmWith || height!=wmHeight){ float size =
		 * width>height?width:height; float w = size; float h =size; wmTheImg =
		 * resize(wmImgIcon, (int) h, (int) w); wmWith =
		 * wmTheImg.getWidth(null); wmHeight = wmTheImg.getHeight(null); }
		 */

		BufferedImage bimage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bimage.createGraphics();
		g.drawImage(theImg, 0, 0, null);

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				alpha));

		Point point = getPosition(theImg, wmWith, wmHeight);
		g.drawImage(wmTheImg, point.x, point.y, wmWith, wmHeight, null); // 添加水印的文字和设置水印文字出现的内容

		g.dispose();
		ByteArrayOutputStream out;
		try {
			out = new ByteArrayOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(bimage);
			out.close();
		} catch (Exception e) {
			throw new RuntimeException("加水印出错");
		}
		return out.toByteArray();
	}

	public static Image resize(ImageIcon imageIcon, int height, int width) {
		if (height < 1) {
			height = 1;
		}
		if (width < 1) {
			width = 1;
		}
		BufferedImage thumb = new BufferedImage(width, height,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = thumb.getGraphics();
		g.drawImage(imageIcon.getImage(), 0, 0, width, height,
				imageIcon.getImageObserver()); // 绘制缩小后的图

		g.dispose();
		return thumb;
	}

	public static Point getPosition(Image image, int width, int height) {
		// 原始图片的大小
		float w = image.getWidth(null);
		float h = image.getHeight(null);
		// 水印偏移位置
		return new Point((int) (w - width) / 2, (int) (h - height) / 2);

	}

	public static String downLoadPic(String imgUrl)
			throws MalformedURLException, IOException {
		String temp_dir = DeployProperties.getInstance()
				.getProperty("temp_dir");
		File tempDirFile = new File(temp_dir);
		if (!tempDirFile.exists()) {
			tempDirFile.mkdirs();
		}
		// 创建流
		BufferedInputStream in = new BufferedInputStream(
				new URL(imgUrl).openStream());
		// 生成图片名
		int index = imgUrl.lastIndexOf("/");
		String sName = imgUrl.substring(index + 1, imgUrl.length());
		System.out.println(sName);
		if (!temp_dir.endsWith("/")) {
			temp_dir = temp_dir + "/";
		}
		// 存放地址
		File img = new File(temp_dir + sName);
		// 生成图片
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(img));
		byte[] buf = new byte[2048];
		int length = in.read(buf);
		while (length != -1) {
			out.write(buf, 0, length);
			length = in.read(buf);
		}
		in.close();
		out.close();
		return temp_dir + sName;
	}

	public static File createTempFile(byte[] bfile, String fileName) {
		String temp_dir = DeployProperties.getInstance()
				.getProperty("temp_dir");
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(temp_dir);
			// 判断文件目录是否存在
			if (!dir.exists()) {
				dir.mkdirs();
			}
			file = new File(temp_dir + "\\" + fileName);

			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;

	}

	// 图片到byte数组
	public static byte[] image2byte(String path) {
		byte[] data = null;
		FileImageInputStream input = null;
		try {
			input = new FileImageInputStream(new File(path));
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int numBytesRead = 0;
			while ((numBytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, numBytesRead);
			}
			data = output.toByteArray();
			output.close();
			input.close();
		} catch (FileNotFoundException ex1) {
			ex1.printStackTrace();
		} catch (IOException ex1) {
			ex1.printStackTrace();
		}
		return data;
	}

	// byte数组到图片
	public static void byte2image(byte[] data, String path) {
		if (data.length < 3 || path.equals(""))
			return;
		try {
			FileImageOutputStream imageOutput = new FileImageOutputStream(
					new File(path));
			imageOutput.write(data, 0, data.length);
			imageOutput.close();
			System.out.println("Make Picture success,Please find image in "
					+ path);
		} catch (Exception ex) {
			System.out.println("Exception: " + ex);
			ex.printStackTrace();
		}
	}

	// byte数组到16进制字符串
	public static String byte2string(byte[] data) {
		if (data == null || data.length <= 1)
			return "0x";
		if (data.length > 200000)
			return "0x";
		StringBuffer sb = new StringBuffer();
		int buf[] = new int[data.length];
		// byte数组转化成十进制
		for (int k = 0; k < data.length; k++) {
			buf[k] = data[k] < 0 ? (data[k] + 256) : (data[k]);
		}
		// 十进制转化成十六进制
		for (int k = 0; k < buf.length; k++) {
			if (buf[k] < 16)
				sb.append("0" + Integer.toHexString(buf[k]));
			else
				sb.append(Integer.toHexString(buf[k]));
		}
		return "0x" + sb.toString().toUpperCase();
	}

}

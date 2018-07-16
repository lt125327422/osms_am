package com.amazon.client;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

public class Xls2Csv {

    private static final String EXTENSION_XLS = "xls";
    private static final String EXTENSION_XLSX = "xlsx";
    private static final String EXTENSION_XLSM = "xlsm";
//    private static final String FILE_PATH="C:\\Users\\Administrator\\my-app\\Flat.File.Home.xlsm";
	/**
	 * @param args
	 */
//	public static void main(String[] args) throws Exception{
//		// TODO Auto-generated method stub
//		//parse(new File("C:\\Users\\Administrator\\my-app\\Flat.File.Home.xlsm"));
////		test.preReadCheck(FILE_PATH);
////		test.getWorkbook(FILE_PATH);
////		test.readExcel(FILE_PATH);
//	}
    
    /***
     * <pre>
     * 取得Workbook对象(xls和xlsx对象不同,不过都是Workbook的实现类)
     *   xls:HSSFWorkbook
     *   xlsx：XSSFWorkbook
     * @param filePath
     * @return
     * @throws IOException
     * </pre>
     */
	public static Workbook getWorkbook(String filePath) throws IOException {
        Workbook workbook = null;
        InputStream is = new FileInputStream(filePath);
        if (filePath.endsWith(EXTENSION_XLS)) {
            workbook = new HSSFWorkbook(is);
        } else if (filePath.endsWith(EXTENSION_XLSX)||filePath.endsWith(EXTENSION_XLSM)) {
            workbook = new XSSFWorkbook(is);
        }
        return workbook;
    }

    /**
     * 文件检查
     * @param filePath
     * @throws FileNotFoundException
     * @throws FileFormatException
     */
	public static void preReadCheck(String filePath) throws FileNotFoundException, Exception {
        // 常规检查
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("传入的文件不存在：" + filePath);
        }

        if (!(filePath.endsWith(EXTENSION_XLS) || filePath.endsWith(EXTENSION_XLSX)|| filePath.endsWith(EXTENSION_XLSM))) {
            throw new Exception("传入的文件不是excel");
        }
    }

    /**
     * 读取excel文件内容
     * @param filePath
     * @throws FileNotFoundException
     * @throws FileFormatException
     */
    public static void readExcel(String filePath,String outFile,int sheetIndex) throws FileNotFoundException, Exception {
        // 检查
        preReadCheck(filePath);
        // 获取workbook对象
        Workbook workbook = null;
        BufferedWriter writer  = null;
        FileUtils.touch(new File(outFile));
		try {
			writer = new BufferedWriter(new FileWriter(outFile));
			workbook = getWorkbook(filePath);
			// 读文件 一个sheet一个sheet地读取
			// workbook.getNumberOfSheets()
			// for (int numSheet = 0; numSheet <1; numSheet++) {
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			if (sheet == null) {
				return;
			}
			// System.out.println("=======================" +
			// sheet.getSheetName() + "=========================");

			int firstRowIndex = sheet.getFirstRowNum();
			int lastRowIndex = sheet.getLastRowNum();

			/*
			 * // 读取首行 即,表头 Row firstRow = sheet.getRow(firstRowIndex); for (int
			 * i = firstRow.getFirstCellNum(); i <= firstRow.getLastCellNum();
			 * i++) { Cell cell = firstRow.getCell(i); String cellValue =
			 * this.getCellValue(cell, true); System.out.print(cellValue +
			 * "\t"); }
			 */
			// System.out.println("");

			// 读取数据行
			for (int rowIndex = firstRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
				Row currentRow = (Row) sheet.getRow(rowIndex);// 当前行
				if (null != currentRow) {
					int firstColumnIndex = ((org.apache.poi.ss.usermodel.Row) currentRow).getFirstCellNum(); // 首列
					int lastColumnIndex = ((org.apache.poi.ss.usermodel.Row) currentRow).getLastCellNum();// 最后一列
					for (int columnIndex = firstColumnIndex; columnIndex <= lastColumnIndex; columnIndex++) {
						Cell currentCell = ((org.apache.poi.ss.usermodel.Row) currentRow).getCell(columnIndex);// 当前单元格
						String currentCellValue = getCellValue(currentCell, true);// 当前单元格的值
						if (currentCellValue.contains("<br>")) {
							currentCellValue="\""+currentCellValue+"\"";
						}
//						System.out.print(currentCellValue + "\r\n");
						writer.write(currentCellValue + "\t");

					}
				}
				writer.write("\n");
				// System.out.print("\r\n");
			}
			// System.out.println("======================================================");
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.flush();
			writer.close();
			/*
			 * if (workbook != null) { try { workbook.close(); } catch
			 * (IOException e) { e.printStackTrace(); } }
			 */
		}
    }

    /**
     * 取单元格的值
     * @param cell 单元格对象
     * @param treatAsStr 为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
     * @return
     */
    public static String getCellValue(Cell cell, boolean treatAsStr) {
        if (cell == null) {
            return "";
        }

        if (treatAsStr) {
            // 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
            // 加上下面这句，临时把它当做文本来读取
//            cell.setCellType(Cell.CELL_TYPE_STRING);
        }

        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				double d = cell.getNumericCellValue();
				Date date = HSSFDateUtil.getJavaDate(d);
				SimpleDateFormat dformat = new SimpleDateFormat("yyyy/MM/dd");
//				System.out.println("=========date=" + dformat.format(date));
				return dformat.format(date);
			} else {
				NumberFormat nf = NumberFormat.getInstance();
				nf.setGroupingUsed(false);// true时的格式：1,234,567,890
//				System.out.println("===CELL_TYPE_NUMERIC" + cell.getNumericCellValue()+
//						"=="+nf.format(cell.getNumericCellValue())
//						+"==="+new DecimalFormat("0").format(cell.getNumericCellValue()));
				String val=nf.format(cell.getNumericCellValue());
				if (val.contains(".")&&val.length()>4) {
					val=new BigDecimal(val).setScale(2,4).toString();
//					System.out.println("==========2===="+val);
				}
				return val;
			}
        }  else {
            return String.valueOf(cell.getStringCellValue());
        }
    }
    
    public static void main(String[] args) {
    	try {
			Xls2Csv.readExcel("C:\\Users\\senxin-kf\\Desktop\\test1.xlsx", "C:\\Users\\senxin-kf\\Desktop\\111.txt",3);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
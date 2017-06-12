package com.focus3d.export.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

/**
 * 
 * *
 * @author lihaijun
 *
 */
public abstract class AbstractExport<T> {
	private static final Logger log = LoggerFactory.getLogger(AbstractExport.class);
	private String sheetName;
	private String[] clomunName;
	private String outputDir;
	private List<T> data;
	private Class cls;
	
	public AbstractExport(String sheetName, String[] clomunName, String outputDir, List<T> data){
		this.sheetName = sheetName;
		this.clomunName = clomunName;
		this.outputDir = outputDir;
		this.data = data;
		Type genType = getClass().getGenericSuperclass();  
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
		cls = (Class) params[0];  
	}
	/**
	 * 
	 * *
	 */
	public void export(){
		final List<Method> methods = getMethodList();
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			sheet.setColumnWidth((short)1, (short) (10 * 256));
			sheet.setColumnWidth((short)2, (short) (30 * 256));
			sheet.setColumnWidth((short)3, (short) (30 * 256));
			sheet.setColumnWidth((short)4, (short) (30 * 256));
			sheet.setColumnWidth((short)5, (short) (30 * 256));
			workbook.setSheetName(0, sheetName, HSSFWorkbook.ENCODING_UTF_16);
			HSSFRow row = sheet.createRow((short) 0);
			row.setHeight((short) (2 * 256));
			HSSFCell cell = null;
			HSSFCellStyle cellStyle = null;
			/*HSSFFont font = workbook.createFont();
			font.setColor(HSSFFont.COLOR_NORMAL);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); */

			int nColumn = clomunName.length;
			log.info("一共导出数据条数：" + data.size());
			log.info("显示列数：" + nColumn);
			// 写入各个字段的名称
			for (int i = 0; i < nColumn; i++) {
				cell = row.createCell((short) i);
				cellStyle = cell.getCellStyle();
				//cellStyle.setFont(font);
				cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				String cn = clomunName[i];
				cell.setCellValue(cn);
				log.info("列名称：" + cn);
			}
			for(int r = 0; r < data.size(); r ++){
				// 写入各条记录，每条记录对应Excel中的一行
				Object object = data.get(r);
				row = sheet.createRow((short) (r + 1));
				for (int c = 0; c < clomunName.length; c++) {
					/*
					row.setHeight((short) (1 * 256));
					cellStyle = cell.getCellStyle();
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					*/
					cell = row.createCell((short) c);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					Method method = methods.get(c);
					String text = "";
					if(method.getReturnType().getName().indexOf("Date") != -1){
						Date addTime = (Date)method.invoke(object);
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						text = dateFormat.format(addTime);
					} else {
						text = String.valueOf(method.invoke(object));
					}
					cell.setCellValue(text);
				}
			}
			ByteArrayOutputStream fOut = new ByteArrayOutputStream();
			try {
				workbook.write(fOut);
				fOut.flush();
				fOut.close();
				byte[] byteArray = fOut.toByteArray();
				writeToLocalFile(byteArray);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(fOut != null){
					fOut.close();
				}
			}
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * *
	 * @return
	 */
	private List<Method> getMethodList() {
		final List<Method> methods = new ArrayList<Method>();
		ReflectionUtils.doWithFields(cls, new ReflectionUtils.FieldCallback() {
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				try {
					Method method = cls.getMethod("get" + captureName(field.getName()));
					methods.add(method);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		});
		return methods;
	}
	/**
	 * *
	 * @param byteArray
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void writeToLocalFile(byte[] byteArray) throws FileNotFoundException, IOException {
		DateFormat dateFormat = new SimpleDateFormat(sheetName + "_yyyyMMddHHmmss");
		String dateTime = dateFormat.format(new Date());
		try {
			File file = new File(outputDir + File.separator + dateTime + ".xls");
			log.info("导出路径：" + file.getPath());
			log.info("文件大小：" + byteArray.length / 1024 + "字节");
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			try {
				fileOutputStream.write(byteArray);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(fileOutputStream != null){
					fileOutputStream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String captureName(String name) {
		char[] cs=name.toCharArray();
		cs[0]-=32;
		return String.valueOf(cs);
	}
}

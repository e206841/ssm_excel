package com.ln.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 处理excel
 * @author Administrator
 *
 */
public class ExcelUtils {
   private static final String fileUploadPath="D:\\ssm_fileUpload";//windows上传目录
   private static final String fileDownPath="D:\\ssm_fileDown";//windows下载目录
   private static final String uploadPath="var/uploaded";//linux上传目录
   
	/**
	 * 生成workbook对象
	 */
	public static Workbook getExcelInfo(InputStream is,boolean isExcel2003){
			Workbook wb = null;
			
	       try{
	           /** 根据版本选择创建Workbook的方式 */
	           //当excel是2003时
	           if(isExcel2003){
	               wb = new HSSFWorkbook(is); 
	           }
	           else{//当excel是2007时
	               wb = new XSSFWorkbook(is); 
	           }
	       }
	       catch (IOException e)  {  
	           e.printStackTrace();  
	       }  
	       return wb;
	  }
	/**
	 * 生成workbook对象
	 */
	public static Workbook getWorkbook(String fileName,MultipartFile Mfile){
		 //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
	       CommonsMultipartFile cf= (CommonsMultipartFile)Mfile; //获取本地存储路径
	       
	       //判断系统 处理目录
			String os = System.getProperty("os.name");  
			File file1 =null;
			if(os.toLowerCase().startsWith("win")){  //window
				 File file = new  File(fileUploadPath);
				 //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
			       if (!file.exists()) file.mkdirs();
			       //新建一个文件
			        file1 = new File(fileUploadPath +"\\"+fileName+"_"+ new Date().getTime() + ".xlsx");
			        
			}else{									//linux
				File file = new  File(uploadPath);
				 //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
			       if (!file.exists()) file.mkdirs();
			       //新建一个文件
			        file1 = new File(uploadPath +"/"+fileName+"_"+ new Date().getTime() + ".xlsx");
			}
	      
	       InputStream is = null;  
	       Workbook wb = null;
	       try {
	    	   //将上传的文件写入新建的文件中
	           cf.getFileItem().write(file1);
	           //验证文件名是否合格
	           if(!validateExcel(fileName)){
	               return null;
	           }
	           //根据文件名判断文件是2003版本还是2007版本
	           boolean isExcel2003 = true; 
	           if(WDWUtil.isExcel2007(fileName)){
	               isExcel2003 = false;  
	           }
	           //根据新建的文件实例化输入流
	           is = new FileInputStream(file1);
	           //获取excle对象
	           wb = getExcelInfo(is, isExcel2003);
	          
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       return  wb;
	}
	
	 /**
	   * 验证EXCEL文件
	   * @param filePath
	   * @return
	   */
	  public static boolean validateExcel(String filePath){
	        if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))){  
	            return false;  
	        }  
	        return true;
	  }
	  /**
	   * 处理单元格内容格式，统一改成string
	   */
	  public static String getCellToString(Cell cell){
		  if (null != cell) {     
              switch (cell.getCellType()) {     
              case Cell.CELL_TYPE_NUMERIC: // 数字     
            	  DecimalFormat df = new DecimalFormat("0");  
            	 return df.format(cell.getNumericCellValue());  
              case Cell.CELL_TYPE_STRING: // 字符串     
                  String stringCellValue = cell.getStringCellValue();
                  return stringCellValue+"";
              default:     
                  return "";
              }     
          }
		  return "";
	  }
}

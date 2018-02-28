package com.ln.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.ln.core.utils.ApplicationUtils;
import com.ln.core.utils.ExcelUtils;
import com.ln.core.utils.ReadExcel;
import com.ln.core.utils.WDWUtil;

@Controller
@RequestMapping(value="/excel")
public class ExcelController {
	private Logger logger=Logger.getLogger(ExcelController.class);
	/**
	 * 
	 * @param file1 表单文件
	 * @param file2 快递文件
	 * @param response
	 * @param file1SheetAt 表单文件中的第几个工作簿
	 * @param file2SheetAt 快递单位件中的第几个工作簿
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/upload")
	@ResponseBody
	public String test(@RequestParam MultipartFile file1,@RequestParam MultipartFile file2,
			String file1SheetName,String file2SheetName,
			HttpServletResponse response) throws Exception{
		logger.info("upload file begin");
		Integer file1RowCelNum=7;//每一行的第几个单元格，默认是7  ，获取明细文件
		Integer file2RowCelNum=3;//每一行的第几个单元格，默认是3  ，获取快递单文件
		List<String> file1NumberList=new ArrayList<String>();
		List<String> file2NumberList=new ArrayList<String>();
		//验证文件
		if(file1==null){
			return null;
		}
		if(file2==null){
			return null;
		}
		//验证工作表
		if(file1SheetName.trim().equals("")||file1SheetName.trim().length()<=0){
			throw new Exception("明细文件工作表名称为必填项");
		}
		if(file2SheetName.trim().equals("")||file2SheetName.trim().length()<=0){
			throw new Exception("快递单文件工作表名称为必填项");
		}
		
		
		String file1Name = file1.getOriginalFilename();
		String file2Name = file2.getOriginalFilename();
		/**
		 * 把两个文件转换成excel对象
		 */
		//file1
		Workbook workbook1 = ExcelUtils.getWorkbook(file1Name, file1);
		//file2
		Workbook workbook2 = ExcelUtils.getWorkbook(file2Name, file2);
		
		
		/**
		 * 获取file文件中的单号list
		 */
		
		file1NumberList=getNumberList(workbook1,file1SheetName,file1RowCelNum);
		file2NumberList=getNumberList(workbook2,file2SheetName,file2RowCelNum);
		
		/**
		 * 处理file文件
		 * 生成file1文件的同时，循环去file2NumberList判断是否有里面的快递单号，如果此行有加底纹
		 * 生成file2文件的同时，循环去file1NumberList判断是否有里面的快递单号，如果有此单元格字体标红
		 */
		
		
		
		
		
		
		
		
		return "处理完成，处理完成的文件副本已存放到D盘ssm_fileDown文件夹下";
	}
	/**
	 * 获取excel中的快递单号数组
	 * @return
	 * @throws Exception 
	 */
	private List<String> getNumberList(Workbook workbook1,String file1SheetName,Integer fileRowCelNum) throws Exception {
				List<String> file1NumberList=new ArrayList<String>();
				//得到Excel工作表对象    
				int sheetIndex = workbook1.getSheetIndex(file1SheetName.trim());//根据名字获取工作簿下标
				Sheet file1Sheet = workbook1.getSheetAt(sheetIndex);  
				String sheetName = file1Sheet.getSheetName();
				logger.info("工作表名称:"+sheetName);
				
				//最后一行行数
				int lastRowNum = file1Sheet.getLastRowNum();
				Row row = null;
				//从第三行数据开始
				for(int i=1;i<lastRowNum;i++){
					//循坏获取每一行
					row = file1Sheet.getRow(i);
					if(row==null){
						continue;
					}
					//获取每一行的第几个单元格
					Cell cell = row.getCell(fileRowCelNum-1);
					if(null==cell){
//						throw new Exception("第"+fileRowCelNum+"列输入错误，没有数据或不是快递单号列，请检查重新输入");
						continue;
					}
					
					
					//获取内容（快递单号）
					String trackNumber = ExcelUtils.getCellToString(cell);
					//如果单号为空
					if(trackNumber.trim().equals("")||trackNumber.trim().length()<=0){
						
						continue;
					}else{
						//放入数组用于比对
						file1NumberList.add(trackNumber);
						/**
						 * 与file2中的单号对比
						 * 如果有相等的，这一行样式底纹为黄色
						 * 如果不相等，保存此单号，方便处理file2。
						 */
					}
				}
				return file1NumberList;
	}
}

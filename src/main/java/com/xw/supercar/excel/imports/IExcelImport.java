package com.xw.supercar.excel.imports;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.LoggerFactory;

/**
 * Excel 导入接口
 * @author wsz 2017-07-30
 */
public abstract class IExcelImport {
	protected final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 导入方法
	 * @author wsz 2017-07-30
	 */
	@SuppressWarnings("resource")
	public Boolean imports(String importFilePath) {
		Boolean rs = true;
		//创建要读入的文件的输入流 
         
		// 创建工作薄Workbook  
        Workbook workBook = null;
		try {
			InputStream in = new FileInputStream(importFilePath); 
			workBook = new HSSFWorkbook(in);
		} catch (IOException e) {
			logger.error("excel导入-imports() 导入路径【" + importFilePath + "】错误，文件不存在！ exception...", e);
			logger.error("");
			rs = false;
			return rs;
		}  
          
        //获取excel第一页，Sheet是从0开始索引的 
        Sheet sheet = workBook.getSheetAt(0); 
		//导入数据
		rs = importData(sheet);
        
        return rs;
	}
	
	/**
	 * 导入表格的数据
	 * @param sheet
	 * @return
	 *
	 * @author wsz 2017-09-20
	 */
	protected abstract Boolean importData(Sheet sheet);
	
	/**
	 * 根据excel单元格的数据类型，获取相应的数据
	 * @param cell
	 * @return
	 *
	 * @author wsz 2017-09-20
	 */
	protected Object getCellValue(Cell cell) {
		if(CellType.NUMERIC == cell.getCellTypeEnum())
			return cell.getNumericCellValue()+"";
		else if(CellType.STRING == cell.getCellTypeEnum())
			return cell.getStringCellValue();
		else if(CellType.BOOLEAN == cell.getCellTypeEnum())
			return cell.getBooleanCellValue();
		else 
			return cell.getStringCellValue();
	}
}

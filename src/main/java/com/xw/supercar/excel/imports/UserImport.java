package com.xw.supercar.excel.imports;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.xw.supercar.entity.User;

public class UserImport implements IExcelImport{

	@Override
	public void imports(String importFilePath) {
		//创建要读入的文件的输入流 
         
     // 创建工作薄Workbook  
        Workbook workBook = null;
		try {
			InputStream in = new FileInputStream(importFilePath); 
			workBook = new HSSFWorkbook(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
          
        //获取excel第一页，Sheet是从0开始索引的 
        Sheet sheet = workBook.getSheetAt(0); 
		
		List<User> users = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            if (i % 500 == 0) {
                //在这里 处理 提取出来的数据
//                recordPoiMapper.batchInsert(recordPois);
                System.out.println("Excel解析出 size:" + users.size() + "recordPois:" + users.toString());
                users = null;
                users = new ArrayList<>();
            }
            Row row = sheet.getRow(i);
            User user = new User();

            user.setUsername(row.getCell(0).getStringCellValue());
            user.setFullname(row.getCell(1).getStringCellValue());
            user.setPassword(row.getCell(2).getStringCellValue());
            user.setEmail(row.getCell(3).getStringCellValue());
            user.setMobile(row.getCell(4).getStringCellValue());
            //角色和公司属于外键，需要进行特殊处理，此处暂时略过
//          user.setRole(row.getCell(5).getStringCellValue());
//          user.setCompany(row.getCell(6).getStringCellValue());
            user.setDescription(row.getCell(7).getStringCellValue());
            
            users.add(user);
        }
        if (users != null && users.size() > 0) {
            //在这里 处理 500除余,提取出来的数据
//            recordPoiMapper.batchInsert(recordPois);
            System.out.println("Excel解析出 size:" + users.size() + "recordPois:" + users.toString());
            users = null;
        }
	}

	public static void main(String[] args) {
		UserImport userImport = new UserImport();
		userImport.imports("D://导出记录.xls");
	}
}

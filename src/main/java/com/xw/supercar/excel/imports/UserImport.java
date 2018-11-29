package com.xw.supercar.excel.imports;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.xw.supercar.constant.CommonConstant;
import com.xw.supercar.entity.Company;
import com.xw.supercar.entity.Lookup;
import com.xw.supercar.entity.User;
import com.xw.supercar.service.CompanyService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.service.UserService;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;

public class UserImport extends IExcelImport{

	@Override
	protected Boolean importData(Sheet sheet) {
		Boolean rs = true;
		
		List<User> users = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
        	//集合超过５００条，则先导入一批数据后清空集合
            if (i % 500 == 0) {
            	logger.info("Excel解析出 size:" + users.size() + "开始导入……" );
            	//导入excel中的数据
                if (users != null && users.size() > 0) {
                	SpringContextHolder.getBean(UserService.class).add(users);
                	users = null;
                }
                users = null;
                users = new ArrayList<>();
            }
            
            Row row = sheet.getRow(i);
            User user = new User();
            
            user.setUsername((String)getCellValue(row.getCell(0)));
            user.setFullname((String)getCellValue(row.getCell(1)));
            user.setPassword((String)getCellValue(row.getCell(2)));
            user.setEmail((String)getCellValue(row.getCell(3)));
            user.setMobile((String)getCellValue(row.getCell(4)));
            user.setDescription((String)getCellValue(row.getCell(7)));
            
            //通过角色名称，获取角色id
            String roleName = (String)getCellValue(row.getCell(5));
            Searchable searchable = Searchable.newSearchable()
            		.addSearchFilter(Lookup.DP.value.name(), SearchOperator.eq, roleName);
            List<Lookup> lookups = SpringContextHolder.getBean(LookupService.class)
            		.searchByDefineCode(CommonConstant.LOOKUPDF_USER_ROLE, searchable);
            if(lookups != null && lookups.size() == 1) {
            	String roleId = lookups.get(0).getId();
            	user.setRole(roleId);
            }
            
            //通过公司名称，获取公司id
            String companyName = (String)getCellValue(row.getCell(6));
            searchable = Searchable.newSearchable().addSearchFilter(Company.DP.name.name(), SearchOperator.eq, companyName);
            Company company = SpringContextHolder.getBean(CompanyService.class).getBy(searchable, true, true);
            if(company != null)
            	user.setCompany(company.getId());
            
            users.add(user);
            
        }
        
        logger.info("Excel解析出 size:" + users.size() + "开始导入……" );
    	//导入excel中的数据
        if (users != null && users.size() > 0) {
        	SpringContextHolder.getBean(UserService.class).add(users);
        	users = null;
        }
        
		return rs;
	}
	
}

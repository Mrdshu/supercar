package com.xw.supercar.excel.exports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.xw.supercar.entity.Company;
import com.xw.supercar.entity.Lookup;
import com.xw.supercar.entity.Client;
import com.xw.supercar.service.CompanyService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.spring.util.SpringContextHolder;

/**
 * 用户导出配置类
 *
 * @author wsz 2017-09-20
 */
public class ClientExport extends IExcelExport<Client>{
	/**导出到excel中的数据*/
	List<Client> Clients = new ArrayList<>();
    /**excel的名称*/
    private String title = "客户信息.xls";
    /**excel标题行*/
    String[] headers = {"车牌号", "车品牌", "车型", "车架号", "车身颜色", "发动机号", "保险公司","保险到期时间",
    		"上牌日期", "客户姓名","客户性别","身份证", "客户类别", "客户级别", "邮箱", "手机", "地址", "备注","所属门店"};
    /**excel标题对应的Client中的属性*/
    String[] fields = {"Clientname", "fullname", "password", "email", "mobile", "role", "company","description"};

	@Override
	public String[] getHeader() {
		return headers;
	}

	@Override
	public int getHeaderSize() {
        return headers.length;
    }
	
	@Override
	public String[] getFields() {
		return fields;
	}

	@Override
    public String getTitle() {
        if (StringUtils.isEmpty(title)) {
        	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            return dateFormat.format(new Date()) + "-导出记录.xls";
        } else {
            return title;
        }
    }

	@Override
	public List<Client> getPoiList() {
		return this.Clients;
	}

	@Override
	public void setPoiList(List<Client> data) {
		this.Clients = data;
	}
	
	@Override
	public boolean containSpecialField(String filedName) {
		if(Client.DP.company.name().equals(filedName))
			return true;
		return false;
    }

	@Override
	public String getSpecialFieldValue(String filedName, Object filedValue) {
		if(Client.DP.company.name().equals(filedName)) {
			String companyId = filedValue+"";
			Company company = SpringContextHolder.getBean(CompanyService.class).getById(companyId);
			if(company != null)
				return company.getName();
		}
		else if(Client.DP.carBrand.name().equals(filedName)) {
			String roleId = filedValue+"";
			Lookup role = SpringContextHolder.getBean(LookupService.class).getById(roleId);
			if(role != null) return role.getValue();
		}
        return "";
    }
}

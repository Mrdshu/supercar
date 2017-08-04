package com.xw.supercar.excel.exports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.xw.supercar.entity.User;

public class UserExport implements IExcelExport<User>{
	List<User> users = new ArrayList<>();
    private String title = "用户信息.xls";
    String[] headers = {"用户名", "全名", "密码", "邮箱", "手机", "角色", "公司","备注"};

	@Override
	public String[] getHeader() {
		return headers;
	}

	public int getHeaderSize() {
        return headers.length;
    }

    public String getTitle() {
        if (StringUtils.isEmpty(title)) {
            return formatDate(new Date(), "yyyy-MM-dd_HH-mm-ss") + "-导出记录.xls";
        } else {
            return title;
        }
    }

    private String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public boolean containSpecialField(String filedName) {
        return false;
    }

    public String getSpecialFieldValue(String filedName) {
        return null;
    }

	@Override
	public List<User> getPoiList() {
		return this.users;
	}

	@Override
	public void setPoiList(List<User> data) {
		this.users = data;
	}



}

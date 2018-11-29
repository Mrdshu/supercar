package com.xw.supercar.controller;

import com.xw.supercar.annotation.SearchableDefaults;
import com.xw.supercar.entity.Client;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.excel.exports.ClientExport;
import com.xw.supercar.excel.imports.ClientImport;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.ClientService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.search.Searchable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 客户相关操作controller
 * @author wsz
 */
@Controller
@RequestMapping("/client")
@Api(tags = "客户相关操作")
public class ClientController extends BaseController<Client>{
	@Autowired
	private ClientService baseService;

	@Override
	protected BaseService<Client> getSevice() {
		return baseService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void afterReturn(ResponseResult result) {
		Map<String, Object> data = result.getData();
		//将数据字典对应的实体放入data
		addAttributesToData(data, new String[]{Client.DP.level.name(),Client.DP.carBrand.name(),Client.DP.type.name()}
		, new Class[]{LookupService.class,LookupService.class,LookupService.class});
	}

	/**
	 * 将所有用户信息导出为excel
	 * 
	 * @param searchable 筛选用户的过滤条件
	 * @param exportFilePath 导出路径
	 *
	 * @author wsz 2017-09-20
	 */
	@RequestMapping(value = "/export",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
    @ApiOperation(httpMethod = "POST", value = "将所有用户信息导出为excel" ,notes = "根据学生的name，查询学生对象的信息。")
    @ApiImplicitParam(name = "searchable", value = "搜索对象", dataType = "Searchable")
    public ResponseResult export(@SearchableDefaults(needPage = false) Searchable searchable, String exportFilePath) {
		ResponseResult result = ResponseResult.generateResponse();
		//校验导出路径地址是否正确合法
		try {
			File file = new File(exportFilePath);
			if(!file.exists()) file.createNewFile();
		} catch (Exception e) {
			logger.error("用户信息导出为excel-export() exception...", e);
			return ResponseResult.generateErrorResponse("导出路径【"+exportFilePath+"】错误，无法正常导出", "");
		}
		//获取数据库用户表中所有的用户
		List<Client> clients = SpringContextHolder.getBean(ClientService.class).findBy(Searchable.newSearchable(), true);
		ClientExport clientExport = new ClientExport();
		clientExport.setPoiList(clients);
		
		//将所有用户导出成excel
		Boolean exportRs = clientExport.export(exportFilePath, null);
		if(!exportRs)
			return ResponseResult.generateErrorResponse("导出失败！", "");
		return result;
	}
	
	/**
	 * 将excel表中数据导入。
	 * 
	 * @param importFilePath　导入文件路径
	 * @return
	 *
	 * @author wsz 2017-09-20
	 */
	@RequestMapping(value = "/imports",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
    @ApiOperation(httpMethod = "GET", value = "将excel表中数据导入")
	public ResponseResult imports(String importFilePath) {
		ResponseResult result = ResponseResult.generateResponse();
		
		ClientImport clientImport = new ClientImport();
		Boolean importRs = clientImport.imports(importFilePath);
		
		if(!importRs)
			return ResponseResult.generateErrorResponse("导入失败！", "");
		return result;
	}
}

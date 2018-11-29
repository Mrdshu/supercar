package com.xw.supercar.controller;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xw.supercar.entity.Client;
import com.xw.supercar.entity.ClientCoupon;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.ClientCouponService;
import com.xw.supercar.service.ClientService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;

/**
 * <p>
 * 客户优惠券controller层
 * </p>
 * 
 * @author wangsz
 * @date 2017-09-13 16:12:12
 */
@Controller
@RequestMapping("/clientCoupon")
@Api(tags = "客户优惠券相关操作")
public class ClientCouponController extends BaseController<ClientCoupon>{

	@Autowired
	private ClientCouponService baseService;
	
	@Autowired
	public PlatformTransactionManager transactionManager;
	
	@Override
	protected BaseService<ClientCoupon> getSevice() {
		return baseService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void afterReturn(ResponseResult result) {
		Map<String, Object> data = result.getData();
		//将数据字典对应的实体放入data
		addAttributesToData(data, new String[]{ClientCoupon.DP.clientId.name(),ClientCoupon.DP.couponId.name()}
		, new Class[]{ClientService.class,LookupService.class});
	}
	
	/**
	 * 增加用户绑定的优惠券
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/addCoupon",method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult addCoupon(ClientCoupon addClientCoupon){
		ResponseResult result = ResponseResult.generateResponse();
		if(addClientCoupon.getNum() <= 0 )
			return ResponseResult.generateErrorResponse("", "新增失败，用户绑定优惠券【"+addClientCoupon.getCouponId()+"】数目不能小于或等于0");
		
		/*
		 * 查询该用户需要绑定的优惠券是否存在
		 */
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(ClientCoupon.DP.clientId.name(), SearchOperator.eq, addClientCoupon.getClientId())
				.addSearchFilter(ClientCoupon.DP.couponId.name(), SearchOperator.eq, addClientCoupon.getCouponId());
		ClientCoupon clientCoupon = getSevice().getBy(searchable, true, false);
		
		//如果存在，则更新该用户绑定的优惠券数目
		if(clientCoupon != null) {
			clientCoupon.setNum(clientCoupon.getNum() + addClientCoupon.getNum());
			getSevice().modify(clientCoupon);
			result.setErrorMsg("新增成功！");
			return result;
		}
		
		//如果不存在，在数据库表中新增一条绑定关系
		ClientCoupon afterInsertEntity = getSevice().add(addClientCoupon);
		
		if(afterInsertEntity == null)
			return ResponseResult.generateErrorResponse("", "新增失败");
		
		result.setErrorMsg("新增成功！");
		return result;
	}
	
	/**
	 * 增加多个用户绑定的优惠券
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/addCoupons",method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult addCoupons(@RequestBody List<ClientCoupon> addClientCoupons){
		ResponseResult result = ResponseResult.generateResponse();
		//定义一个默认事务，事务隔离、传播等都是默认
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
		
		for (ClientCoupon clientCoupon : addClientCoupons) {
			result = addCoupon(clientCoupon);
			//如果一个增加失败，则事务回滚
			if(!result.getSuccess()) {
				transactionManager.rollback(status);
				return result;
			}
		}
		//全部增加成功后提交事务
		transactionManager.commit(status);
		
		result.setErrorMsg("新增成功！");
		return result;
	}

	/**
	 * 减少用户绑定的优惠券
	 * @param entity
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/reduceCoupon",method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult reduceCoupon(ClientCoupon reduceClientCoupon){
		ResponseResult result = ResponseResult.generateResponse();
		/*
		 * 查询该用户需要绑定的优惠券是否存在
		 */
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(ClientCoupon.DP.clientId.name(), SearchOperator.eq, reduceClientCoupon.getClientId())
				.addSearchFilter(ClientCoupon.DP.couponId.name(), SearchOperator.eq, reduceClientCoupon.getCouponId());
		ClientCoupon clientCoupon = getSevice().getBy(searchable, true, false);
		
		/*
		 * 如果不存在，则减少用户绑定的优惠券失败
		 */
		if(clientCoupon == null) {
			return ResponseResult.generateErrorResponse("", "减少优惠券失败，该用户不存在绑定的优惠券");
		}
		
		/*
		 * 如果存在，则更新用户绑定的优惠券
		 */
		int oldNum = clientCoupon.getNum();
		int reduceNum = reduceClientCoupon.getNum();
		//优惠券减少的数目大于存在的数目，减少失败
		if(oldNum < reduceNum) {
			return ResponseResult.generateErrorResponse("", "减少优惠券失败，该用户绑定的优惠券数目不足");
		}
		//优惠券减少的数目等于存在的数目，删除数据库中绑定关系
		else if(oldNum == reduceNum) {
			getSevice().remove(clientCoupon);
		}
		//优惠券减少的数目小于存在的数目，更新用户绑定的优惠券
		else {
			clientCoupon.setNum(oldNum - reduceNum);
			getSevice().modify(clientCoupon);
		}
		
		result.setErrorMsg("减少优惠券成功");
		
		return result;
	}
	
	/**
	 * 减少多个用户绑定的优惠券
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/reduceCoupons",method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult reduceCoupons(@RequestBody List<ClientCoupon> reduceClientCoupons){
		ResponseResult result = ResponseResult.generateResponse();
		//定义一个默认事务，事务隔离、传播等都是默认
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
		
		for (ClientCoupon clientCoupon : reduceClientCoupons) {
			result = reduceCoupon(clientCoupon);
			//如果一个减少失败，则事务回滚
			if(!result.getSuccess()) {
				transactionManager.rollback(status);
				return result;
			}
		}
		//全部增加成功后提交事务
		transactionManager.commit(status);
		
		result.setErrorMsg("减少优惠券成功！");
		return result;
	}
	
}
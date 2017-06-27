package com.xw.supercar.util;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.xw.supercar.entity.Client;
import com.xw.supercar.entity.InPart;
import com.xw.supercar.entity.InPartInfo;
import com.xw.supercar.entity.Inventory;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.entity.composite.InPartComposite;

public class GenerateRapJson {
	
	@Test
	public void generateSingle() throws Exception {
		Object object = getClassInstance(Client.class);
		getJsonAndKV(object);
		System.out.println("返回报文：");
		System.out.println(GsonUtil.transObjectToJson(ResponseResult.generateResponse()));
	}
	
	@Test
	public void generateComplex() throws Exception {
		InPart inPart = getClassInstance(InPart.class);
		InPartInfo inPartInfo =  getClassInstance(InPartInfo.class);
		InPartInfo inPartInfo2 = getClassInstance(InPartInfo.class);
		List<InPartInfo> list = new ArrayList<>();
		list.add(inPartInfo);
		list.add(inPartInfo2);
		
		InPartComposite inPartComposite = new InPartComposite(inPart,list);
		System.out.println("======================");
		System.out.println(GsonUtil.transObjectToJson(inPartComposite));
	}
	
	public static <E> E getClassInstance(Class<E> clazz){
		E object = null;
		try {
			object = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				
				if("data".equals(field.getName()))
					continue;
				field.setAccessible(true);
				if(field.getName().endsWith("LK")){
					field.set(object, "1");
				}
				else if(field.getType() == String.class){
					field.set(object, field.getName());
				}
				else if(field.getType() == Boolean.class){
					field.set(object, false);
				}
				else if(field.getType() == Integer.class){
					field.set(object, 1);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return object;
	}
	
	public static void getJsonAndKV(Object object) {
		String json = GsonUtil.transObjectToJson(object);
		System.out.println(json);
		System.out.println("===========================");
		String kv = json.replaceAll("\"", "").replaceAll("\\{", "").replaceAll("\\}", "").replaceAll(",", "&").replaceAll(":", "=");
		System.out.println(kv);
	}
}

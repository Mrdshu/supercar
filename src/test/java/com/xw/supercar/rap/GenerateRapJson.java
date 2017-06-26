package com.xw.supercar.rap;

import java.lang.reflect.Field;

import com.xw.supercar.entity.Inventory;
import com.xw.supercar.entity.Part;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.util.GsonUtil;

public class GenerateRapJson {
	
	public static void main(String[] args) {
		Object object = getClassInstance(Inventory.class);
		getJsonAndKV(object);
		System.out.println("返回报文：");
		System.out.println(GsonUtil.transObjectToJson(ResponseResult.generateResponse()));
	}
	
	public static Object getClassInstance(Class<?> clazz){
		Object object = null;
		try {
			object = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				
				if("data".equals(field.getName()))
					continue;
				field.setAccessible(true);
				if(field.getName().contains("Lookup")){
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

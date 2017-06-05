package com.xw.supercar.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *  Gson序列化工具类
 *  @author Bob
 */
public class GsonUtil {
	private static Gson gson;

	static {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Class.class, new ClassTypeAdapter());
		gson = gsonBuilder.create();
	}
	
	/**
	 * 将json字符串转换为对象
	 * @author wangsz  3:18:40 PM
	 */
	public static <T> T transJsonToObject(String jsonString,Class<T> type) {
		return gson.fromJson(jsonString, type);
	}
	
	/**
	 * 将json字符串转换为对象,针对特殊类型，如List
	 * @author wangsz  Apr 28, 2017 3:18:41 PM
	 */
	public static <T> T transJsonToObject(String jsonString,Type type) {
		return gson.fromJson(jsonString, type);
	}
	
	/**
	 * 将对象转换为Json字符串
	 * @author wangsz  3:20:42 PM
	 */
	public static String transObjectToJson(Object object){
		return gson.toJson(object);
	}

	/**
	 * 将class数组转换为json字符串
	 * @author Bob
	 */
	public static String Classes2Json(Class<?>[] parameterTypes) {
		return gson.toJson(parameterTypes, parameterTypes.getClass());
	}

	/**
	 * 将对象数组转换为json字符串
	 * @author Bob
	 */
	public static String RequestParameter2Json(Object[] args) {
		Object[] transferArgs = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				transferArgs[i] = "";
			} else if (((args[i] instanceof HttpServletRequest))
					|| ((args[i] instanceof HttpServletResponse))) {
				transferArgs[i] = "";
			} else {
				transferArgs[i] = args[i];
			}
		}

		return gson.toJson(transferArgs);
	}

	
	/**
	 * 将json字符串转换为对象数组
	 * @author Bob
	 */
//	public static Object[] Json2RequestParameter(String jsonContent, Class<?>[] parameterTypes) {
//		try {
//			Object[] args = new Object[parameterTypes.length];
//			JSONArray jsonArray = JSONArray.fromObject(jsonContent);
//			for (int i = 0; i < parameterTypes.length; i++) {
//				String jsonString = jsonArray.get(i).toString();
//				//特殊entity无法转换，手动创建对象
//				if ((Model.class.isAssignableFrom(parameterTypes[i]))
//						|| (BindingResult.class.isAssignableFrom(parameterTypes[i]))) {
//					if (!jsonString.isEmpty()){
//						args[i] = readObjectFromByte(Base64.decodeBase64(jsonString.getBytes()));
//					}else{
//						
//						if(Model.class.isAssignableFrom(parameterTypes[i])){
//							args[i] = new BindingAwareModelMap();
//						}else{
//							args[i] =new BeanPropertyBindingResult(null,"unused");
//						}						
//					}					
//				}
//				//如果为字符串，不需要进行json转换
//				else if(String.class.isAssignableFrom(parameterTypes[i])){
//					if(jsonString.length() == 0)
//						args[i] = null;
//					else {
//						args[i] = jsonString;
//					}
//				}			
//				else {
//					args[i] = gson.fromJson(jsonString, parameterTypes[i]);
//				}
//			}
//			return args;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	/**
	 * 获取json字符串（实体类数组）中的指定class的实体
	 * @param jsonContent 实体类数组json字符串
	 * @param parameterTypes  实体类数组的class数组
	 * @param type  要获取的class
	 * @return
	 *
	 * @author wangsz
	 */
//	public static<T> T Json2RequestParameter(String jsonContent,Class<?>[] parameterTypes, Class<T> type) {
//		try {
//			T rs = null;
//			JSONArray jsonArray = JSONArray.fromObject(jsonContent);
//			for (int i = 0; i < parameterTypes.length; i++) {
//				if(type.isAssignableFrom(parameterTypes[i])){
//					rs = gson.fromJson(jsonArray.get(i).toString(), type);
//					break;
//				}
//			}	
//			
//			return rs;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	/**
	 * 针对特殊类型（如List）,反序列化指定token、index的实体
	 * @param jsonContent
	 * @param parameterTypes
	 * @param index
	 * @param type
	 * @return
	 *
	 * @author wangsz  Apr 28, 2017 3:51:52 PM
	 */
//	public static<T> T Json2RequestParameter(String jsonContent,Class<?>[] parameterTypes, int index, Type type) {
//		try {
//			T rs = null;
//			JSONArray jsonArray = JSONArray.fromObject(jsonContent);
//			rs = gson.fromJson(jsonArray.get(index).toString(), type);
//			
//			return rs;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	/**
	 * 将json字符串转换为class数组
	 * @author Bob
	 */
	public static Class<?>[] Json2Classes(String entitysClass){
		return  (Class[])gson.fromJson(entitysClass, new Class[0].getClass());
	}
	
	private static byte[] getByteArrayForObject(Serializable object) {
		ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream outStream = new ObjectOutputStream(byteOutStream);
			outStream.writeObject(object);
			outStream.flush();
		} catch (IOException e) {
		}
		return null;
	}

	private static Object readObjectFromByte(byte[] byteArray) {
		ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
		try {
			ObjectInputStream ois = new ObjectInputStream(in);
			return ois.readObject();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		
	}
}

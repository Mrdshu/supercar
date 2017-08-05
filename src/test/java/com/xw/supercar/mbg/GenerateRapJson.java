package com.xw.supercar.mbg;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.xw.supercar.entity.Client;
import com.xw.supercar.entity.InPart;
import com.xw.supercar.entity.InPartInfo;
import com.xw.supercar.entity.OutPart;
import com.xw.supercar.entity.OutPartInfo;
import com.xw.supercar.entity.RepairWorkorder;
import com.xw.supercar.entity.RepairWorkorderItem;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.entity.composite.InPartComposite;
import com.xw.supercar.entity.composite.OutPartComposite;
import com.xw.supercar.entity.composite.RepairWorkOrderComposite;
import com.xw.supercar.util.GsonUtil;

public class GenerateRapJson {
	Object object = getClassInstance(Client.class);
	
	@Test
	public void generateSingle() throws Exception {
		getJsonAndKV(object);
		System.out.println("返回报文：");
		System.out.println(GsonUtil.transObjectToJson(ResponseResult.generateResponse()));
	}
	
	@Test
	public void generateComplex() throws Exception {
		OutPart outPart = getClassInstance(OutPart.class);
		OutPartInfo outPartInfo =  getClassInstance(OutPartInfo.class);
		OutPartInfo outPartInfo2 = getClassInstance(OutPartInfo.class);
		List<OutPartInfo> list = new ArrayList<>();
		list.add(outPartInfo);
		list.add(outPartInfo2);
//		
//		OutPartComposite inPartComposite = new OutPartComposite(outPart,list);
		
		InPart inPart = getClassInstance(InPart.class);
		InPartInfo inPartInfo =  getClassInstance(InPartInfo.class);
		InPartInfo inPartInfo2 = getClassInstance(InPartInfo.class);
		List<InPartInfo> li2st = new ArrayList<>();
		li2st.add(inPartInfo);
		li2st.add(inPartInfo2);
//		
//		OutPartComposite inPartComposite = new OutPartComposite(outPart,list);
		
//		InPart outPart = getClassInstance(InPart.class);
//		InPartInfo outPartInfo =  getClassInstance(InPartInfo.class);
//		InPartInfo outPartInfo2 = getClassInstance(InPartInfo.class);
//		List<InPartInfo> list = new ArrayList<>();
//		list.add(outPartInfo);
//		list.add(outPartInfo2);
		OutPartComposite outPartComposite = new OutPartComposite(outPart,list);
		Client client = (Client) object;
		
		RepairWorkorder repairWorkorder = getClassInstance(RepairWorkorder.class);
		
		RepairWorkorderItem repairWorkorderItem = getClassInstance(RepairWorkorderItem.class);
		repairWorkorderItem.setWorkorderId("1");
		repairWorkorderItem.setItemId("1");
		RepairWorkorderItem repairWorkorderItem2 = getClassInstance(RepairWorkorderItem.class);
		repairWorkorderItem2.setWorkorderId("1");
		repairWorkorderItem2.setItemId("1");
		List<RepairWorkorderItem> items = new ArrayList<>();
		items.add(repairWorkorderItem);
		items.add(repairWorkorderItem2);
		
		RepairWorkOrderComposite repairWorkOrderComposite = new RepairWorkOrderComposite();
		repairWorkOrderComposite.setOutPartComposite(outPartComposite);
		repairWorkOrderComposite.setRepairWorkorder(repairWorkorder);
		repairWorkOrderComposite.setRepairWorkorderItems(items);
		repairWorkOrderComposite.setClient(client);
		InPartComposite inPartComposite = new InPartComposite(inPart,li2st);
		System.out.println("======================");
		System.out.println(GsonUtil.transObjectToJson(repairWorkOrderComposite));
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
				else if(field.getType() == BigDecimal.class){
					field.set(object, new BigDecimal(11));
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

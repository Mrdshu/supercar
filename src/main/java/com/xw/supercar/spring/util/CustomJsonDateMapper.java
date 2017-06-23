package com.xw.supercar.spring.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * jackson序列化时，扩展自定义日期的序列化格式
 * 
 * @author wsz 2017-06-23
 */
public class CustomJsonDateMapper extends ObjectMapper {

	private static final long serialVersionUID = 1L;

	/**
	 * 在构造方法中加入，自定义json序列器
	 */
	public CustomJsonDateMapper() {
		SimpleModule sm = new SimpleModule();
		sm.addSerializer(Date.class, new JsonSerializer<Date>() {
			@Override
			public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider provider)
					throws IOException {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jsonGenerator.writeString(sdf.format(value));
			}
		});
		this.registerModule(sm);
	}
}

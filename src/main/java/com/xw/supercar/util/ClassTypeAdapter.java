package com.xw.supercar.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * 自定义gson序列化配置
 *
 * @author wsz 2017-09-20
 */
public class ClassTypeAdapter extends TypeAdapter<Class<?>> {
	
	public void write(JsonWriter jsonWriter, Class<?> clazz) throws IOException {
		if (clazz == null) {
			jsonWriter.nullValue();
			return;
		}
		jsonWriter.value(clazz.getName());
	}

	public Class<?> read(JsonReader jsonReader) throws IOException {
		if (jsonReader.peek() == JsonToken.NULL) {
			jsonReader.nextNull();
			return null;
		}
		Class<?> clazz = null;
		String classname = jsonReader.nextString();
		try {
			clazz = Class.forName(classname);
		} catch (ClassNotFoundException exception) {
			clazz = primitiveMap.get(classname);
			if (clazz == null)
				throw new IOException(exception);
		}
		return clazz;
	}

	static Map<String, Class<?>> primitiveMap = new HashMap<String, Class<?>>();
	static {

		primitiveMap.put(boolean.class.getName(), boolean.class);
		primitiveMap.put(byte.class.getName(), byte.class);
		primitiveMap.put(char.class.getName(), char.class);
		primitiveMap.put(short.class.getName(), short.class);
		primitiveMap.put(int.class.getName(), int.class);
		primitiveMap.put(long.class.getName(), long.class);
		primitiveMap.put(float.class.getName(), float.class);
		primitiveMap.put(double.class.getName(), double.class);
		primitiveMap.put(void.class.getName(), void.class);
	}
}
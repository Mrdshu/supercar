package com.xw.supercar.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	
	private String[] encryptPropNames = { "jdbc.username", "jdbc.password" };

	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if (isEncryptProp(propertyName)) {
			String decryptValue = DESUtils.getDecryptString(propertyValue);
			return decryptValue;
		} else {
			return propertyValue;
		}
	}

	/**
	 * 判断是否是加密的属性名
	 * @param propertyName
	 * @return
	 */
	private boolean isEncryptProp(String propertyName) {
		for (String encryptPropName : encryptPropNames) {
			if (encryptPropName.equals(propertyName)) {
				return true;
			}
		}
		return false;
	}
}

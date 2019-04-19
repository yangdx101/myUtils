package my.yangdx.ssmtest.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * .properties文件处理工具类
 * 
 * @author yangdexu
 */
public class UtilProperty {

	/**
	 * properties文件Map
	 */
	private static Map<String, Properties> propertyMap;

	static {
		propertyMap = new HashMap<String, Properties>();
	}

	/**
	 * 从文件中读取配置值
	 */
	public static String readNewProperty(String fileName, String key) {
		InputStream fis = null;
		try {
			fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			Properties property = new Properties();
			property.load(fis);
			// 记录本次所读文件,下次读该文件,直接从property获取
			propertyMap.put(fileName, property);
			return getValue(property.getProperty(key));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				System.out.println("InputStream close failed");
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 当前加载文件是要读取的文件时，直接读取；否则从文件中读取配置值
	 */
	public static String getValue(String fileName, String key) {
		try {
			if (fileName == null || fileName.isEmpty()) {
				return "";
			} else if (propertyMap.get(fileName) != null) {
				return getValue(propertyMap.get(fileName).getProperty(key));
			} else {
				return readNewProperty(fileName, key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String getValue(String str) {
		try {
			return new String(str.getBytes(FinalValue.ISO88591), FinalValue.UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
}

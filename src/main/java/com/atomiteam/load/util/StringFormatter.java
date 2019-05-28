package com.atomiteam.load.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;

public class StringFormatter {

	private static final String fieldStart = "\\$\\{";
	private static final String fieldEnd = "\\}";

	private static final String regex = fieldStart + "([^}]+)" + fieldEnd;
	private static final Pattern pattern = Pattern.compile(regex);

	public static String format(String format, Object context) {
		Matcher m = pattern.matcher(format);
		String result = format;
		while (m.find()) {
			String path = m.group(1);
			Object val;
			try {
				val = PropertyUtils.getNestedProperty(context, path);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			result = result.replaceFirst(regex, val == null ? "" : val.toString());
		}
		return result;
	}


}

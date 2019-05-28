package com.atomiteam.load.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.atomiteam.load.util.StringFormatter;

public class StringFormatterTests {

	@Test
	public void test() {

		ComplexClass d = new ComplexClass();
		d.name = "Sky";
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("d", d);
		Assert.assertEquals("My name is Sky.", StringFormatter.format("My name is ${d.name}.", context));
	}

}

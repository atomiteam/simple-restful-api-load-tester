package com.atomiteam.load.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.atomiteam.load.model.executions.Configuration;

public class ConfigReaderTests {

	@Test
	public void test() {

		Configuration configuration = ConfigReader.getTests();
		Assert.assertEquals(2, configuration.getTests().size());
		Assert.assertEquals("https://somehost/", configuration.getVariables().get("host"));
		Assert.assertEquals("POST", configuration.getTests().get(0).getMethod());
		Assert.assertEquals("application/json", configuration.getTests().get(0).getHeaders().get("content-type"));
		
	}

}

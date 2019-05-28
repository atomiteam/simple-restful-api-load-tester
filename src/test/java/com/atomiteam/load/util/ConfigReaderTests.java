package com.atomiteam.load.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.atomiteam.load.model.executions.Tests;

public class ConfigReaderTests {

	@Test
	public void test() {

		Tests tests = ConfigReader.getTests();
		Assert.assertEquals(2, tests.getTests().size());
		Assert.assertEquals("POST", tests.getTests().get(0).getMethod());
		Assert.assertEquals("application/json", tests.getTests().get(0).getHeaders().get("content-type"));
		
	}

}

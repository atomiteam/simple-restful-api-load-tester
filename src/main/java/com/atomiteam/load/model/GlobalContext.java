package com.atomiteam.load.model;

import java.util.HashMap;
import java.util.Map;

import com.atomiteam.load.model.executions.Tests;

public class GlobalContext {

	Map<String, Object> props = new HashMap<>();
	Tests tests;

	public void set(String key, Object value) {
		props.put(key, value);
	}

	public Object get(String key) {
		return props.get(key);
	}

	public Tests getTests() {
		return tests;
	}

	public void setTests(Tests tests) {
		this.tests = tests;
	}

}

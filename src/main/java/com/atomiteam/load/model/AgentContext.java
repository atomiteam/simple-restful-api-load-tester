package com.atomiteam.load.model;

import java.util.HashMap;
import java.util.Map;

public class AgentContext {

	Map<String, Object> executions = new HashMap<>();

	public Map<String, Object> getExecutions() {
		return executions;
	}

	public void setExecutions(Map<String, Object> executions) {
		this.executions = executions;
	}

}

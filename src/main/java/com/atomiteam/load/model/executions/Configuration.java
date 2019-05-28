package com.atomiteam.load.model.executions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration {

	Map<String, Object> variables = new HashMap<>();
	List<Test> tests;
	Integer agents;
	Integer wait;

	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	public Integer getAgents() {
		return agents;
	}

	public void setAgents(Integer agents) {
		this.agents = agents;
	}

	public Integer getWait() {
		return wait;
	}

	public void setWait(Integer wait) {
		this.wait = wait;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

}

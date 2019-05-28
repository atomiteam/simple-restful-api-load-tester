package com.atomiteam.load.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.atomiteam.load.model.executions.Tests;

public class ConfigReader {

	public static Tests getTests() {
		try (InputStream is = new FileInputStream(new File("./Tests.yml"))) {
			Yaml yaml = new Yaml(new Constructor(Tests.class));
			Tests test = yaml.load(is);
			return test;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

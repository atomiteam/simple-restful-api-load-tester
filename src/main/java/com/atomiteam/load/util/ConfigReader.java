package com.atomiteam.load.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.atomiteam.load.model.executions.Configuration;

public class ConfigReader {

	public static Configuration getTests() {
		try (InputStream is = new FileInputStream(new File("./Tests.yml"))) {
			Yaml yaml = new Yaml(new Constructor(Configuration.class));
			Configuration test = yaml.load(is);
			return test;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

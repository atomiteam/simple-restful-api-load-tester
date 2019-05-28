package com.atomiteam.load.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atomiteam.load.model.GlobalContext;
import com.atomiteam.load.model.executions.Tests;
import com.atomiteam.load.util.ConfigReader;

/**
 * 
 * Put Tests.yml into the current folder
 * 
 * Execute
 * 		java -jar simple-restful-api-load-tester-1.0-jar-with-dependencies.jar
 * 
 * @author Sami Altundag
 *
 */
public class Application {
	static final Logger STAT = LoggerFactory.getLogger("STAT");
	static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		Tests tests = ConfigReader.getTests();
		GlobalContext gc = new GlobalContext();
		gc.setTests(tests);
		STAT.debug(String.format("%4s %6s %6s %6s %4s %2s", "TID", "TOTAL", "I", "TAKEN", "CODE", "URL"));

		for (int i = 0; i < tests.getParalellism(); i++) {
			LOGGER.debug("Starting agent {}", i);
			new Thread(new Agent(gc)).start();
			try {
				Thread.sleep(tests.getWait());
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

}

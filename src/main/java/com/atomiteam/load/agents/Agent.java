package com.atomiteam.load.agents;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atomiteam.load.model.AgentContext;
import com.atomiteam.load.model.GlobalContext;
import com.atomiteam.load.model.executions.Test;
import com.atomiteam.load.util.StringFormatter;

public class Agent implements Runnable {

	GlobalContext globalContext;
	AgentContext agentContext;
	Map<String, Object> context;
	static final int MAX_ERROR = 3;
	static final Logger LOGGER = LoggerFactory.getLogger(Agent.class);
	static final Logger STAT = LoggerFactory.getLogger("STAT");

	public Agent(GlobalContext globalContext) {
		super();
		this.globalContext = globalContext;
		this.agentContext = new AgentContext();
		this.context = new HashMap<String, Object>();
		this.context.put("globalContext", globalContext);
		this.context.put("agentContext", agentContext);
	}

	long total = 0;

	@Override
	public void run() {
		Random random = new Random();

		for (int testIndex = 0; testIndex < globalContext.getConfiguration().getTests().size(); testIndex++) {

			Test test = globalContext.getConfiguration().getTests().get(testIndex);

			for (int iteration = 0; iteration < test.getIterations(); iteration++) {
				if (LOGGER.isDebugEnabled())
					LOGGER.debug(String.format("Agent %s Test: %d Iteration: %d ", Thread.currentThread().getId(),
							testIndex, iteration));
				int errorCount = 0;
				int successCount = 0;
				while (successCount == 0) {
					try {

						Long start = System.currentTimeMillis();
						String url = StringFormatter.format(test.getUrl(), this.context);

						HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

						test.getHeaders().forEach((k, v) -> {
							con.setRequestProperty(k, StringFormatter.format(v, this.context));
							if (LOGGER.isDebugEnabled())
								LOGGER.debug(String.format("Header %s=%s", k, StringFormatter.format(v, this.context)));
						});

						if (test.getBody() != null) {
							con.setDoInput(true);
							con.setDoOutput(true);
							String body = StringFormatter.format(test.getBody(), this.context);
							if (LOGGER.isDebugEnabled())
								LOGGER.debug(String.format("Body %s", body));
							OutputStream op = con.getOutputStream();
							op.write(body.getBytes());
						}

						int responseCode = con.getResponseCode();
						InputStream in = con.getInputStream();
						JSONObject jsonObject = new JSONObject(new JSONTokener(in));
						this.agentContext.getExecutions().put(test.getId(), jsonObject.toMap());

						Long taken = (System.currentTimeMillis() - start);
						this.total += taken;

						successCount++;
						STAT.warn(String.format("%4s %6d %6d %6d %4d %s", Thread.currentThread().getId(), this.total,
								iteration, taken, responseCode, test.getUrl()));

						int randomWait = Math.abs(random.nextInt(test.getWait())) + test.getWait();
						Thread.sleep(randomWait);
					} catch (Exception e) {
						errorCount++;
						LOGGER.error(String.format("Agent %d Test %s failed %d times with error. %s",
								Thread.currentThread().getId(), test.getUrl(), errorCount, e.getMessage()), e);
						if (errorCount == MAX_ERROR) {
							throw new RuntimeException(e);
						}
					}
				}

			}

		}
	}

}

package cn.rongcapital.sisyphus.base;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author nianjun
 *
 */

public abstract class BaseStep {

	protected final Log logger = LogFactory.getLog(this.getClass());

	private static ThreadLocal<HashMap<String, Object>> storyContext = new ThreadLocal<>();

	protected void setParameter(String name, Object value) {
		HashMap<String, Object> context = storyContext.get();
		if (context == null) {
			context = new HashMap<>();
			storyContext.set(context);
		}
		context.put(name, value);
	}

	protected Object getParameter(String name) {
		HashMap<String, Object> context = storyContext.get();
		if (context != null)
			return context.get(name);
		else
			return null;
	}
}

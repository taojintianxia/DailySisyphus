package jbehave.base.ftl;

import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import org.jbehave.core.io.StoryNameResolver;
import org.jbehave.core.io.UnderscoredToCapitalized;
import org.jbehave.core.reporters.FreemarkerProcessor;
import org.jbehave.core.reporters.TemplateableViewGenerator;

import freemarker.template.Configuration;

public class FreemarkerViewGenerator extends TemplateableViewGenerator {

	private static final FreemarkerViewGenerator INSTANCE = new FreemarkerViewGenerator();

	public static FreemarkerViewGenerator getInstance() {
		return INSTANCE;
	}

	private FreemarkerViewGenerator() {
		this(new UnderscoredToCapitalized());
	}

	private FreemarkerViewGenerator(StoryNameResolver nameResolver) {
		super(nameResolver, new FreemarkerProcessor(FreemarkerViewGenerator.class) {

			private Configuration configuration;

			public void process(String resource, Map<String, Object> dataModel, Writer writer) {
				if (null == configuration) {
					configuration = configuration();
					configuration.setTemplateUpdateDelay(1200);
				}

				try {
					configuration.getTemplate(resource).process(dataModel, writer);
				} catch (Exception e) {
					throw new FreemarkerProcessingFailed(configuration, resource, dataModel, e);
				}
			}
		});
	}

	public Properties defaultViewProperties() {
		Properties properties = new Properties();
		properties.setProperty("views", "ftl/jbehave-views.ftl");
		properties.setProperty("maps", "ftl/jbehave-maps.ftl");
		properties.setProperty("navigator", "ftl/jbehave-navigator.ftl");
		properties.setProperty("reports", "ftl/od-jbehave-reports.ftl");
		properties.setProperty("decorated", "ftl/jbehave-report-decorated.ftl");
		properties.setProperty("nonDecorated", "ftl/jbehave-report-non-decorated.ftl");
		properties.setProperty("decorateNonHtml", "true");
		properties.setProperty("defaultFormats", "stats");
		properties.setProperty("viewDirectory", "view");
		return properties;
	}

}

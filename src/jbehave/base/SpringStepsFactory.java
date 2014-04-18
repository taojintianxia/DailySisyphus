package jbehave.base;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.AbstractStepsFactory;
import org.springframework.context.ApplicationContext;

public class SpringStepsFactory extends AbstractStepsFactory {

    private final ApplicationContext context;
    
    private ThreadLocal<HashMap<Class<?>, Object>> stepCache = new ThreadLocal<HashMap<Class<?>, Object>>();
    
    private HashMap<Class<? extends BaseStep>, BaseStep> systemSteps = new HashMap<Class<? extends BaseStep>, BaseStep>();
    
    public SpringStepsFactory(Configuration configuration, ApplicationContext context, Collection<BaseStep> systemSteps) {
        super(configuration);
        this.context = context;
        for (BaseStep b : systemSteps){
        	this.systemSteps.put(b.getClass(), b);
        }
    }

    @Override
    protected List<Class<?>> stepsTypes() {
        List<Class<?>> types = new ArrayList<Class<?>>();
        for (String name : context.getBeanDefinitionNames()) {
            Class<?> type = context.getType(name);
            if (isAllowed(type) && hasAnnotatedMethods(type)) {
                types.add(type);
            }
        }
        types.addAll(systemSteps.keySet());
        return types;
    }

    protected boolean isAllowed(Class<?> type) {
    	boolean isODSteps = false;
    	for (Class<?> c = type; !c.equals(Object.class); c = c.getSuperclass()){
    		if (c.equals(BaseStep.class)){
    			isODSteps = true;
    			break;
    		}
    	}
        return type != null && !Modifier.isAbstract(type.getModifiers()) && isODSteps;
    }

    public Object createInstanceOfType(Class<?> type) {
    	if (systemSteps.get(type) != null) return systemSteps.get(type);
    	
    	if (stepCache.get() != null) {
    		Object cachedbean = stepCache.get().get(type);
    		if (cachedbean != null) return cachedbean;
    	}

        for (String name : context.getBeanDefinitionNames()) {
            Class<?> beanType = context.getType(name);
            if (type.equals(beanType)) {
            	Object o = context.getBean(name);
            	if (stepCache.get() == null) stepCache.set(new HashMap<Class<?>, Object>());
            	if (null == stepCache.get().get(type)) stepCache.get().put(type, o);
            	return o;
            }
        }
        throw new StepsInstanceNotFound(type, this);
    }

    public void clearThreadCache(){
    	stepCache.set(null);
    }
}

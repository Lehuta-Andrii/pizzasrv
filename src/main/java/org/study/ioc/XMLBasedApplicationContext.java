package org.study.ioc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Just tried to mimic spring behavior will be removed in further commits
 * 
 * @author Lehuta Andrii
 *
 */
public final class XMLBasedApplicationContext implements ApplicationContext {

    private Map<String, Class<?>> beanTypes;
    private Map<Class<?>, List<String>> constructorParameters;

    private static XMLBasedApplicationContext instance;

    private XMLBasedApplicationContext() {
	beanTypes = new HashMap<String, Class<?>>();
	constructorParameters = new HashMap<Class<?>, List<String>>();
    }

    public ApplicationContext loadConfigFromXML(String config) throws Exception {
	new XMLConfigReader().readConfig(config);
	return this;
    }

    private class XMLConfigReader {

	private boolean beansFlag;
	private boolean beanFlag;

	private Class<?> currentClass;

	public void readConfig(String config) throws Exception {
	    InputStream in = new FileInputStream(config);

	    XMLInputFactory factory = XMLInputFactory.newInstance();
	    XMLStreamReader parser = factory.createXMLStreamReader(in);

	    while (parser.hasNext()) {
		int event = parser.next();
		if (event == XMLStreamConstants.START_ELEMENT) {
		    switch (parser.getLocalName()) {
		    case "beans":
			if (beansFlag) {
			    throw new XMLStreamException();
			} else {
			    beansFlag = true;
			}
			break;
		    case "bean":
			if (beansFlag) {
			    beanFlag = true;
			    String name = parser.getAttributeValue(null, "name");
			    Class<?> beanClass = Class.forName(parser.getAttributeValue(null, "class"));
			    currentClass = beanClass;
			    beanTypes.put(name, beanClass);
			} else {
			    throw new XMLStreamException();
			}
			break;
		    case "constructor-arg":
			if (beansFlag && beanFlag) {
			    String ref = parser.getAttributeValue(null, "ref");
			    List<String> values = constructorParameters.putIfAbsent(currentClass,
				    new ArrayList<String>(Collections.singletonList(ref)));
			    if (values != null) {
				values.add(ref);
			    }
			}
			break;
		    default:
			throw new XMLStreamException();
		    }
		} else if (event == XMLStreamConstants.END_ELEMENT) {
		    switch (parser.getLocalName()) {
		    case "beans":
			if (beansFlag) {
			    beansFlag = false;
			} else {
			    throw new XMLStreamException();
			}
			break;
		    case "bean":
			if (beansFlag) {
			    beanFlag = false;
			    currentClass = null;
			} else {
			    throw new XMLStreamException();
			}
			break;
		    case "constructor-arg":
			break;
		    default:
			throw new XMLStreamException();
		    }
		}
	    }
	}
    }

    public static XMLBasedApplicationContext getInstance() {
	if (instance == null) {
	    instance = new XMLBasedApplicationContext();
	}
	return instance;
    }

    @Override
    public Object getBean(String beanName) throws Exception {

	Class<?> beanType = beanTypes.get(beanName);
	List<String> argNames = constructorParameters.get(beanType);

	if (argNames == null) {
	    return beanType.newInstance();
	} else {
	    // Class<?>[] argClasses = new Class<?>[argNames.size()];
	    List<Object> args = new ArrayList<Object>();

	    for (int i = 0; i < argNames.size(); i++) {
		args.add(getBean(argNames.get(i)));
	    }

	    Class<?>[] argClasses = getParameterTypeForConstructor(args, beanType);
	    return beanType.getConstructor(argClasses).newInstance(args.toArray());
	}
    }

    private Class<?>[] getParameterTypeForConstructor(List<Object> args, Class<?> beanType) {
	Class<?>[] result = new Class<?>[args.size()];
	List<Constructor> constructors = getConstructorsWithSameParamNumder(args, beanType);

	for (Constructor constructor : constructors) {
	    for (int i = 0; i < result.length; i++) {
		Class<?> type = getParameterType(constructor.getParameterTypes()[i], args.get(i).getClass());
		if (type == null) {
		    break;
		} else {
		    result[i] = type;
		}
	    }
	}

	return result;
    }

    private List<Constructor> getConstructorsWithSameParamNumder(List<Object> args, Class<?> beanType) {
	List<Constructor> constructors = new ArrayList<Constructor>();

	for (Constructor constructor : beanType.getConstructors()) {
	    if (constructor.getParameters().length == args.size()) {
		constructors.add(constructor);
	    }
	}
	return constructors;
    }

    private Class<?> getParameterType(Class<?> constructorParameterType, Class<?> argumentType) {
	if (constructorParameterType.equals(argumentType)) {
	    return argumentType;
	}

	if (constructorParameterType.equals(argumentType.getSuperclass())) {
	    return argumentType.getSuperclass();
	}

	for (Class<?> interfac : argumentType.getInterfaces()) {
	    if (constructorParameterType.equals(interfac)) {
		return interfac;
	    }
	}

	return null;
    }

}

package org.study.ioc;

public interface ApplicationContext {

    public Object getBean(String beanName) throws Exception;
    
}

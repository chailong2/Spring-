package springframework.common;

import springframework.BeansException;
import springframework.PropertyValue;
import springframework.PropertyValues;
import springframework.beans.factory.ConfigurableListableBeanFactory;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.BeanFactoryPostProcessor;
import springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.Properties;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition=beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues=beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("company","改为：字节跳动"));
    }
}
package springframework.beans.factory;

import jdk.management.resource.ResourceType;
import org.springframework.util.StringValueResolver;
import springframework.BeansException;
import springframework.PropertyValue;
import springframework.PropertyValues;
import springframework.beans.core.io.DefaultResourceLoader;
import springframework.beans.core.io.Resource;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.BeanFactoryPostProcessor;
import java.io.IOException;
import java.util.Properties;

public class PropertyPlaceholderConfigurer  implements BeanFactoryPostProcessor {
    public static final String DEFAULT_PLACEHOLDER_PREFIX="${";
    public static final String DEFAULT_PLACEHOLDER_SUFFIX="}";
    private String location;

    protected String resolvePlaceholder(String placeholder, Properties props) {
        return props.getProperty(placeholder);
    }
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //加载属性文件
        try{
            DefaultResourceLoader resourceLoader=new DefaultResourceLoader();
            //获取资源文件
            Resource resource=resourceLoader.getResource(location);
            Properties properties=new Properties();
            properties.load(resource.getInputStream());
            String[] beanDefinitionNames=beanFactory.getBeanDefinitionNames();
            for (String beanDefinitionName : beanDefinitionNames) {
                BeanDefinition beanDefinition=beanFactory.getBeanDefinition(beanDefinitionName);
                PropertyValues propertyValues=beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value=propertyValue.getValue();
                    if(!(value instanceof String))continue;
                    String strVal=(String)value;
                    StringBuilder buffer=new StringBuilder(strVal);
                    int startIdx=strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                    int stopIdx=strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                    if(startIdx !=-1 && stopIdx!=-1 && startIdx<stopIdx){
                        String propKey=strVal.substring(startIdx +2,stopIdx);
                        String propVal=properties.getProperty(propKey);
                        buffer.replace(startIdx,startIdx+1,propVal);
                        propertyValues.addPropertyValue(new  PropertyValue(propertyValue.getName(), buffer.toString()));
                    }
                }
            }
            //向容器中添加字符串解析器，以供解析@Value注解使用
            StringValueResolver valueResolver=new PlaceholderResolvingStringValueResolver(properties);
            beanFactory.addEmbeddedValueResolver(valueResolver);

        }catch (IOException e){
            throw new BeansException("Could not load propertied",e);
        }
    }
    public void setLocation(String location){
        this.location=location;
    }
    private class PlaceholderResolvingStringValueResolver implements StringValueResolver{
        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties){
            this.properties=properties;
        }

        @Override
        public String resolveStringValue(String s) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(s,properties);
        }
    }
}

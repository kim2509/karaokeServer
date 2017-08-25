package com.tessoft.karaoke;

import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

public class CustomPropertySourcesPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer implements InitializingBean{

	protected static Logger logger = Logger.getLogger(CustomPropertySourcesPlaceholderConfigurer.class.getName());
	
    public void afterPropertiesSet(){
        try{
            Properties loadedProperties = this.mergeProperties();
            for(Entry<Object, Object> singleProperty : loadedProperties.entrySet()){
                logger.info("LoadedProperty: "+singleProperty.getKey()+"="+singleProperty.getValue());
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
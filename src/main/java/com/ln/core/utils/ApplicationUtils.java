package com.ln.core.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;


/**
 * ApplicationUtils : 程序工具类，提供大量的便捷方法
 *
 * @author LiWenbin
 */
@Component
public class ApplicationUtils implements ApplicationContextAware{
	
	public static ApplicationContext applicationContext;
	
	public static final String uploadDir="/var/uploaded";//服务器文件存放位置
	
	public static String downloadURIRoot="/ldxk/download/tempfile/";
	
	public static final String AES_PASSWORD="@simple#aes$pass";
	
	public static final String winUploadDir="D:\\ssm_excel";//windows 文件存放位置
	
	static{
		File dir = new File(uploadDir);
		if(!dir.exists()){
			dir.mkdirs();
		}
	}
	
	public void setApplicationContext(ApplicationContext context)
			   throws BeansException {
		applicationContext=context;
	}

    /**
     * 产生一个36个字符的UUID
     *
     * @return UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * md5加密
     *
     * @param value 要加密的值
     * @return md5加密后的值
     */
    public static String md5Hex(String value) {
        return DigestUtils.md5Hex(value);
    }

    /**
     * sha1加密
     *
     * @param value 要加密的值
     * @return sha1加密后的值
     */
    public static String sha1Hex(String value) {
        return DigestUtils.sha1Hex(value);
    }

    /**
     * sha256加密
     *
     * @param value 要加密的值
     * @return sha256加密后的值
     */
    public static String sha256Hex(String value) {
        return DigestUtils.sha256Hex(value);
    }
    
    /**
     * 获取spring上下文中的实例
     * @param cls
     * @return
     */
    public static <T> T getBean(Class<T> cls){
    	T bean = null;
    	try{
    		bean = applicationContext.getBean(cls);
    	}catch(Exception e){
    		bean = getBeanFromWebContext(cls);
    	}
    	return bean;
    }
    
    /**
     * 从spring-mvc的上下文获取bean;
     * @param cls 类
     * @return
     */
    public static <T> T getBeanFromWebContext(Class<T> cls){
    	HttpServletRequest shs = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    	return RequestContextUtils.getWebApplicationContext(shs).getBean(cls);
    }
    
    /**
     * 获取国际化文件中的文本
     * @author LiWenbin
     * @param key
     */
    public static String getMessage(String key){
    	HttpServletRequest shs = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    	ReloadableResourceBundleMessageSource rrbm = RequestContextUtils.getWebApplicationContext(shs).getBean(ReloadableResourceBundleMessageSource.class);
		String message = rrbm.getMessage(key,
				new Object[0], Locale.CHINA);
		return message;
    }
    
    /**
     * 获取国际化文件中的文本
     * @author LiWenbin
     * @param key
     * @param objs
     */
    public static String getMessage(String key,Object[] objs){
    	HttpServletRequest shs = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    	ReloadableResourceBundleMessageSource rrbm = RequestContextUtils.getWebApplicationContext(shs).getBean(ReloadableResourceBundleMessageSource.class);
		String message = rrbm.getMessage(key,
				objs, Locale.CHINA);
		return message;
    }
    
    /**
     * 获取classes目录中文件的绝对路径
     * @param fileClassPath 文件在classes目录中的路径
     * @return
     * @throws IOException
     */
    public static String getWebFileAbsoluteClassPath(String fileClassPath) throws IOException{
    	Resource res = new ClassPathResource(fileClassPath);
    	URL classpath = res.getURL();
    	return classpath.getPath();
    }
    
    /**
     * 创建临时落地文件对象
     * @return
     */
    public static File createUploadTmpFile(){
    	String filePath = uploadDir+"/"+UUID.randomUUID();
    	return new File(filePath);
    }
    /**
     * 创建window临时落地文件对象
     * @return
     */
    public static File createWinUploadTmpFile(){
    	String filePath = winUploadDir+"\\"+UUID.randomUUID();
    	return new File(filePath);
    }
}

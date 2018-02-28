package com.ln.web.controller.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
/**
 * 
 * 不需要框架，手动链接数据库，获取流程引擎ProcessEngine对象
 * 
 * @author Administrator
 *
 */
public class ActivitiTest4 {

	@Test
	public void createTable(){
		//获取ProcessEngineConfiguration对象
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		//设计数据库链接属性
		configuration.setJdbcDriver("com.mysql.jdbc.Driver");
		configuration.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/ssm?useUnicode=true&characterEncoding=utf-8");
		configuration.setJdbcUsername("root");
		configuration.setJdbcPassword("1234");
		
		/**
		 *  public static final String DB_SCHEMA_UPDATE_FALSE = "false"; 如果数据库中没有表，抛出异常
		 *  public static final String DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop";如果数据库中有表，删除表在创建，存在就直接使用
		 *   public static final String DB_SCHEMA_UPDATE_TRUE = "true";如果数据库中没有表，新建
		 */
		
		
		//设置数据库建表策略
		configuration.setDatabaseSchemaUpdate(configuration.DB_SCHEMA_UPDATE_TRUE);
		//创建流程引擎
		ProcessEngine processEngine = configuration.buildProcessEngine();
	}
}

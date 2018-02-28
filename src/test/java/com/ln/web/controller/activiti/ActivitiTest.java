package com.ln.web.controller.activiti;

import java.util.List;

import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ln.web.controller.TestBase;

public class ActivitiTest extends TestBase{
	@Test
	public void test1() {
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

		// 获取流程储存服务组件
		RepositoryService rs = engine.getRepositoryService();
		// 获取运行时服务组件
		RuntimeService rse = engine.getRuntimeService();
		// 获取流程中的任务TASK组件
		TaskService ts = engine.getTaskService();
		// 部署流程，只要是符合BPMN2规范的XML文件，理论上都可以被ACTIVITI部署
		rs.createDeployment()
				.addClasspathResource("bpmn/MyProcess.bpmn")
				.deploy();
		// 开启流程，myprocess是流程的ID
		rse.startProcessInstanceByKey("myProcess");
		// 查询历史表中的Task
		List<Task> task = ts.createTaskQuery().list();
		Task task1 = task.get(task.size() - 1);
		System.out.println("test1第一环节：" + task1);
				ts.complete(task1.getId());
		task1 = ts.createTaskQuery().executionId(task1.getExecutionId())
				.singleResult();
		System.out.println("test1第二环节：" + task1);

	}
	
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	
	@RequestMapping(value = "/test2")
	@ResponseBody
	public String test2(){
		StringBuffer sb = new StringBuffer();
		// 部署流程，只要是符合BPMN2规范的XML文件，理论上都可以被ACTIVITI部署
		repositoryService.createDeployment().addClasspathResource("bpmn/MyProcess.bpmn").deploy();
		// 开启流程，myprocess是流程的ID
		runtimeService.startProcessInstanceByKey("myProcess");
		// 查询历史表中的Task
		List<Task> task = taskService.createTaskQuery().list();
		Task task1 = task.get(task.size()-1);
		sb.append("test2第一环节："+task1 +"<br/>");
		sb.append("test2推动流程到下一环节："+task1+"<br/>");
		taskService.complete(task1.getId());
		task1 = taskService.createTaskQuery().executionId(task1.getExecutionId()).singleResult();
		sb.append("test2第二环节：" + task1+"<br/>");
		return sb.toString();
	}
}

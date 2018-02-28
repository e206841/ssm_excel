package com.ln.web.activiti;

import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Activiti服务封装接口
 * @author linan
 *
 */
public class ActivitiInter {

	@Autowired
	RepositoryService repositoryService;//获得activiti服务
	@Autowired
	RuntimeService runtimeService;//用于管理运行时流程实例
	@Autowired
	TaskService taskService;//用于管理运行时任务
	@Autowired
	FormService formService;//表单管理
	@Autowired
	HistoryService historyService;//历史信息管理
	
	
	/**
	 * activiti服务列表
	 * @return
	 */
	public List<ProcessDefinition>  activitiListByprocesskey(String process_key){
		
		List<ProcessDefinition> procDefList = repositoryService.createProcessDefinitionQuery()
			.processDefinitionKey(process_key)
			.orderByProcessDefinitionVersion()
			.desc()
			.list();
		
		return procDefList;
	}
	
	/**
	 * 流程实例列表
	 * @return
	 */
	public List<ProcessInstance> listProcInstByPdfid(String pdfid){
		
		List<ProcessInstance> procInstList = runtimeService.createProcessInstanceQuery()
			.processDefinitionId(pdfid)
			.list();
		
		return procInstList;
	}
	
	/**
	 * 当前任务的执行情况
	 * @return
	 */
	public List<Execution> listExecutionByProId(String processInstanceId) {
		
		List<Execution> exectionList = runtimeService.createExecutionQuery()
			.processInstanceId(processInstanceId)
			.list();
		
		return exectionList;
	}
	
	/**
	 * 部署流程
	 * @return
	 */
	public Deployment deployProcDefByPath( String xmlPath){
		
		Deployment deploy = repositoryService.createDeployment()
        	.addClasspathResource(xmlPath)
			.deploy();
		
		return deploy;
	}
	
	/**
	 * 删除部署的流程
	 * @param process_key
	 */
	public void deleteProcDefByprocesskey(String process_key){
		List<ProcessDefinition> procDefList = this.activitiListByprocesskey(process_key);
		for(ProcessDefinition task : procDefList){
			List<Task> tasks = taskService.createTaskQuery().processDefinitionId(task.getId()).list();
			for(Task t : tasks){
				taskService.deleteTask(t.getId());
			}			
			repositoryService.deleteDeployment(task.getDeploymentId());
		}
	}
	
	/**
	 * 启动流程
	 * @param key
	 * @return
	 */
	public ProcessInstance startProcessInstanceByKey(String key){
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
		return processInstance;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public FormService getFormService() {
		return formService;
	}

	public void setFormService(FormService formService) {
		this.formService = formService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
}

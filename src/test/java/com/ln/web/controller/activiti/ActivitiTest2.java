package com.ln.web.controller.activiti;

import org.activiti.engine.repository.Deployment;
import org.junit.Test;

import com.ln.web.activiti.ActivitiInter;
import com.ln.web.controller.TestBase;

public class ActivitiTest2 extends TestBase{
	
	@Test
	public void eployment(){
		ActivitiInter act=new ActivitiInter();
		act.deployProcDefByPath("bpmn/MyProcess.bpmn");
		act.startProcessInstanceByKey("myProcess");
	}
	
	
	
	

}

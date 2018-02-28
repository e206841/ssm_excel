package com.ln.web.controller.activiti;

import java.util.ArrayList;
import java.util.List;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ProcessValidator;
import org.activiti.validation.ProcessValidatorFactory;
import org.activiti.validation.ValidationError;

/**
 * 动态生成流程图文件xml
 * @author linan
 *
 */
public class ActivitiTest3 {
	
	public static BpmnModel getBpmnModel(){
		//实例化BpmnModel对象  
		BpmnModel bpmnModel=new BpmnModel();  
		//开始节点的属性  
		StartEvent startEvent=new StartEvent();  
		startEvent.setId("start1shareniu");  
		startEvent.setName("start1shareniu");  
		//普通的UserTask节点  
		UserTask userTask=new UserTask();  
		userTask.setId("userTask1shareniu");  
		userTask.setName("userTask1shareniu");  
		//结束节点属性  
		EndEvent endEvent=new EndEvent();  
		endEvent.setId("endEventshareniu");  
		endEvent.setName("endEventshareniu");  
		//连线信息  
		List<SequenceFlow> sequenceFlows=new ArrayList<SequenceFlow>();  
		List<SequenceFlow> toEnd=new ArrayList<SequenceFlow>();  
		SequenceFlow s1=new SequenceFlow();  
		s1.setId("starttouserTask");  
		s1.setName("starttouserTask");  
		s1.setSourceRef("start1shareniu");  
		s1.setTargetRef("userTask1shareniu");  
		sequenceFlows.add(s1);  
		SequenceFlow s2=new SequenceFlow();  
		s2.setId("userTasktoend");  
		s2.setName("userTasktoend");  
		s2.setSourceRef("userTask1shareniu");  
		s2.setTargetRef("endEventshareniu");  
		toEnd.add(s2);  
		startEvent.setOutgoingFlows(sequenceFlows);  
		userTask.setOutgoingFlows(toEnd);  
		userTask.setIncomingFlows(sequenceFlows);  
		endEvent.setIncomingFlows(toEnd);  
		//Process对象  
		Process process=new Process();  
		process.setId("process1");  
		process.addFlowElement(startEvent);  
		process.addFlowElement(s1);  
		process.addFlowElement(userTask);  
		process.addFlowElement(s2);  
		process.addFlowElement(endEvent);  
		bpmnModel.addProcess(process);  
		return bpmnModel;
	}
	
	public static void main(String[] args) {
		BpmnModel bpmnModel = getBpmnModel();
		
		//验证bpmnModel是否有错误
		ProcessValidatorFactory processValidatorFactory=new ProcessValidatorFactory();
		ProcessValidator defaultProcessValidator = processValidatorFactory.createDefaultProcessValidator();
		//验证失败信息的封装ValidationError
		List<ValidationError> validate = defaultProcessValidator.validate(bpmnModel);
		System.out.println(validate.size());
		
		//如果validate长度是0，说明没有错误，如果大于0说明有错误。
		//ValidationError存放的是验证错误的信息
		if(0==validate.size()){
			BpmnXMLConverter bpmnXMLConverter=new BpmnXMLConverter();
			byte[] convertToXML = bpmnXMLConverter.convertToXML(bpmnModel);
			
			String xml=new String(convertToXML);
			System.out.println(xml);
		}
		
	
	}

}

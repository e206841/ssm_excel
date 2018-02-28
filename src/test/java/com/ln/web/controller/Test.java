package com.ln.web.controller;

public class Test {
	public static void main(String[] args) {
		//判断系统
		String os = System.getProperty("os.name");  
		if(os.toLowerCase().startsWith("win")){  //window
		  System.out.println(os+":window");  
		}else{									//linux
			System.out.println(os+":linux"); 
		}
	}
}

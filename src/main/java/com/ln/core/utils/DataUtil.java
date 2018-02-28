package com.ln.core.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数据处理工具类
 * @author linan
 *
 */
public class DataUtil {
	/**
	 * 判断字符串内容是否是数字（不包括负数）
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumeric(String str){
		  for (int i = 0; i < str.length(); i++){
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
		 }
	/**
	 * 每周的阿拉伯数字转换成中文
	 * @param week
	 * @return weekChinese
	 */
	public static String weekNumberToChinese(Integer week){
		String weekChinese=null;
		if(null!=week){
			if(week<=7){
				String [] chinese=new String[] {"一","二","三","四","五","六","日"};
				int index=week-1;
				weekChinese=chinese[index];
			}
		}
		return weekChinese;
	}
	
	/**
	 * list去重复值
	 * @param list
	 * @return List
	 */
	public static List listDistinct(List<Integer> list){
		 Set set = new  HashSet(); 
	        List<Integer> newList = new  ArrayList<Integer>(); 
	        for (Integer cd:list) {
	           if(set.add(cd)){
	               newList.add(cd);
	           }
	       }
	       return newList;
	}
	
	public static void main(String[] args) {
	    List<Integer> list  =   new  ArrayList<Integer>(); 
//        list.add(1);
//        list.add(2);
//        list.add(2);
//        list.add(3);
//        list.add(3);
        List listDistinct = listDistinct(list);
       System.out.println(listDistinct);
//		System.out.println(weekNumberToChinese(1));
//		System.out.println(weekNumberToChinese(2));
//		System.out.println(weekNumberToChinese(3));
//		System.out.println(weekNumberToChinese(4));
//		System.out.println(weekNumberToChinese(5));
//		System.out.println(weekNumberToChinese(6));
//		System.out.println(weekNumberToChinese(7));
//		System.out.println(weekNumberToChinese(8));
//		System.out.println(weekNumberToChinese(null));
	}
}

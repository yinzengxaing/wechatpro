package com.ssm.wechatpro.util;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public class ToolUtil {
	/**
	 * MD5加密技术
	 * @param str
	 * @return
	 */
	public static String MD5(String str) throws Exception {
		byte[] bt = str.getBytes();
		StringBuffer sbf = null;
		MessageDigest md = null;
		md = MessageDigest.getInstance("MD5");
		byte[] bt1 = md.digest(bt);
		sbf = new StringBuffer();
		for (int i = 0; i < bt1.length; i++) {
			int val = ((int) bt1[i]) & 0xff;
			if (val < 16)
				sbf.append("0");
			sbf.append(Integer.toHexString(val));
		}
		return sbf.toString();
	}
	
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);  
            dest = m.replaceAll("");
        }
        return dest;
    }
	
	public static <T> int contains( T[] array, T v ) {
		int k = -1;
	    for ( T e : array ){
	    	k++;
	        if ( e == v || v != null && v.equals( e ) )
	            return k;
	    }
	    return k;
	}
	
	public static boolean contains(Map<String,Object> map, String[] key, String[] returnMessage, InputObject inputObject, OutputObject outputObject) {
		for(int i = 0;i<key.length;i++){
			if(map.containsKey(key[i].toString())){
				if(JudgeUtil.isNull(map.get(key[i].toString()).toString())){
					outputObject.setreturnMessage(returnMessage[i]);
					return false;
				}
			}else{
				outputObject.setreturnMessage(key[i]+"键不存在");
				return false;
			}
		}
	    return true;
	}
	
	/**
	 * 获取当前日期(2016-12-29 11:23:09)
	 * 
	 * @return
	 */
	public static String getTimeAndToString() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = df.format(dt);
		return nowTime;
	}
	
	/**
	 * 获取当前时间（11:23）
	 * @return
	 */
	public static String getNowTimeAndToString(){
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("HH:mm");
		String nowTime = df.format(dt);
		return nowTime;
	}
	
	/**
	 * 判断是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str){
		if(str==null||str.equals("")||str.equals("null")){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 将数据转化为菜单形式
	 * @param beans
	 * @return
	 */
	public static List<Map<String, Object>> createDataToMenu(List<Map<String, Object>> beans) {
		List<Map<String, Object>> treeAncestors = new ArrayList<>();
		for(Map<String, Object> bean : beans){
			if(bean.get("type").toString().equals(Constants.CITY_First)){//一级类别
				treeAncestors.add(bean);
			}
		}
		for(Map<String, Object> bean : treeAncestors){
			List<Map<String, Object>> treeParent = new ArrayList<>();
			for(Map<String, Object> item : beans){
				if(item.get("pId").toString().equals(bean.get("id").toString())){
					treeParent.add(item);
				}
			}
			bean.put("cityList", treeParent);
			bean.put("size", treeParent.size());
		}
		return treeAncestors;
	}  
}

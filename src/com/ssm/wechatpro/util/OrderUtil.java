package com.ssm.wechatpro.util;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class OrderUtil {
	
	/**
	 * sha1加密
	 * @param str
	 * @return
	 */
	 public static String sha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];      
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
	
	/** 
     * 生成签名sign 
     * 第一步：非空参数值的参数按照参数名ASCII码从小到大排序，按照键值对的形式。生成字符串StringA 
     * stringA="appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA"; 
     * 第二部：拼接API密钥，这里的秘钥是微信商户平台的秘钥，是自己设置的，不是公众号的秘钥 
     * stringSignTemp="stringA&key=192006250b4c09247ec02edce69f6a2d" 
     * 第三部：MD5加密 
     * sign=MD5(stringSignTemp).toUpperCase()="9A0A8659F005D6984697E2CA0A9CF3B7" 
     *  
     * @param map 不包含空字符串的map 
     * @return 
	 * @throws Exception 
     */  
    public static String sign(Map<String, Object> map,String key) throws Exception {  
    	String bizString = FormatBizQueryParaMap(map, false);
        return MD5.sign(bizString, key);
    }  
    
    public static String FormatBizQueryParaMap(Map<String, Object> paraMap, boolean urlencode) throws Exception {
        String buff = "";
        try {
            List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(paraMap.entrySet());
            Collections.sort(infoIds,
                    new Comparator<Map.Entry<String, Object>>() {
                        public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                            return (o1.getKey()).toString().compareTo(o2.getKey());
                        }
                    });
            for (int i = 0; i < infoIds.size(); i++) {
                Map.Entry<String, Object> item = infoIds.get(i);
                //System.out.println(item.getKey());
                if (item.getKey() != "") {
                    String key = item.getKey();
                    String val = item.getValue().toString();
                    if (urlencode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    buff += key + "=" + val + "&";
                }
            }
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return buff;
    }
    
    /** 
     * 返回key的值 
     * @param map 
     * @param key 
     * @return 
     */  
	private static String getParamString(Map<String,Object> map, String key) {  
        String buf = "";  
        if (map.get(key) instanceof String[]) {  
            buf = ((String[]) map.get(key))[0];  
        } else {  
            buf = (String) map.get(key);  
        }  
        return buf;  
    } 
    
    /** 
     * 字符串列表从大到小排序 
     * @param data 
     * @return 
     */  
    @SuppressWarnings("unused")
	private static List<String> sort(List<String> data) {  
        Collections.sort(data, new Comparator<String>() {  
            public int compare(String obj1, String obj2) {  
                return obj1.compareTo(obj2);  
            }  
        });  
        return data;  
    }  
    
    /** 
     * Map转Xml 
     * @param arr 
     * @return 
     */  
    public static String MapToXml(Map<String, String> arr) {  
        String xml = "<xml>";  
        Iterator<Entry<String, String>> iter = arr.entrySet().iterator();  
        while (iter.hasNext()) {  
            Entry<String, String> entry = iter.next();  
            String key = entry.getKey();  
            String val = entry.getValue();  
            if (IsNumeric(val)) {  
                xml += "<" + key + ">" + val + "</" + key + ">";  
            } else  
                xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";  
        }  
        xml += "</xml>";  
        return xml;  
    }  
    
    private static boolean IsNumeric(String str) {  
        if (str.matches("\\d *")) {  
            return true;  
        } else {  
            return false;  
        }  
    }  
    
    public static String ArrayToXml(Map<String, Object> arr) {
        String xml = "<xml>";
        Iterator<Entry<String, Object>> iter = arr.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            String key = entry.getKey();
            String val = entry.getValue().toString();
            if (IsNumeric(val)) {
                xml += "<" + key + ">" + val + "</" + key + ">";
            } else
                xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
        }
        xml += "</xml>";
        return xml;
    }
    
    /** 
     * 对参数列表进行排序，并拼接key=value&key=value形式 
     * @param map 
     * @return 
     */  
    public static String sortParameters(Map<String, Object> map) {  
        Set<String> keys = map.keySet();  
        List<String> paramsBuf = new ArrayList<String>();  
        for (String k : keys) {  
            paramsBuf.add((k + "=" + getParamString(map, k)));  
        }  
        // 对参数排序  
        Collections.sort(paramsBuf);  
        String result="";  
        int count=paramsBuf.size();  
        for(int i=0;i<count;i++){  
            if(i<(count-1)){  
                result+=paramsBuf.get(i)+"&";  
            }else {  
                result+=paramsBuf.get(i);  
            }  
        }  
        return result;  
    }  

}

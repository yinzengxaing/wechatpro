package com.ssm.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.HttpsURLConnection;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ssm.wechatpro.util.Constants;

/**
 * 通用工具类
 * 
 * @author rory.wu
 * @version 1.0
 * @since 2015年08月05日
 */
public class CommonUtil {
   public static String appid = "";
    // 第三方用户唯一凭证密钥
    public static String appsecret = "";
    //商户ID
    public static String mch_id="";
    //获取openId
    public static String oauth2_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	private static Logger log = Logger.getLogger(CommonUtil.class);

	public static JSONObject httpsRequestToJsonObject(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			StringBuffer buffer = httpsRequest(requestUrl, requestMethod,
					outputStr);
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("连接超时：" + ce.getMessage());
		} catch (Exception e) {
			log.error("https请求异常：" + e.getMessage());
		}
		return jsonObject;
	}

	private static StringBuffer httpsRequest(String requestUrl,
			String requestMethod, String output)
			throws NoSuchAlgorithmException, NoSuchProviderException,
			KeyManagementException, MalformedURLException, IOException,
			ProtocolException, UnsupportedEncodingException {

		URL url = new URL(requestUrl);
		HttpsURLConnection connection = (HttpsURLConnection) url
				.openConnection();

		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod(requestMethod);
		if (null != output) {
			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(output.getBytes("UTF-8"));
			outputStream.close();
		}

		// 从输入流读取返回内容
		InputStream inputStream = connection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String str = null;
		StringBuffer buffer = new StringBuffer();
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}

		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();
		inputStream = null;
		connection.disconnect();
		return buffer;
	}
	
	/**
     * 获取用户的openId，并放入session
     * @param code 微信返回的code
     */
    public static String getOpenId(String code) {
        String url = oauth2_url.replace("APPID", Constants.APPID).replace("SECRET", Constants.APPSECRET).replace("CODE", code);
        String openId = null;
        log.info("oauth2_url:"+url);
        JSONObject jsonObject = CommonUtil.httpsRequestToJsonObject(oauth2_url, "POST", null);
        System.out.println(jsonObject);
        log.info("jsonObject:"+jsonObject);
        Object errorCode = jsonObject.get("errcode");
        if(errorCode != null) {
            log.info("code不合法");
        }else{
            openId = jsonObject.getString("openid");
            log.info("openId:"+openId);
            
        }
		return  openId;
    }
}

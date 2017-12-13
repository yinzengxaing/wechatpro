package com.ssm.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Test
{
    public static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";// 获取access
    public static final String APP_ID = "wxf5179dd713bc6e36";
    public static final String SECRET = "946bd10ae09c79733e7def85ff0a3f62";

    // 获取token
    @SuppressWarnings("deprecation")
	public static String getToken(String apiurl, String appid, String secret)
    {
        String turl = String.format(
                "%s?grant_type=client_credential&appid=%s&secret=%s", apiurl,
                appid, secret);
        @SuppressWarnings("deprecation")
		HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(turl);
        JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
        String result = null;
        try
        {
            HttpResponse res = client.execute(get);
            String responseContent = null; // 响应内容
            HttpEntity entity = res.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            JsonObject json = jsonparer.parse(responseContent)
                    .getAsJsonObject();
            // 将json字符串转换为json对象
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                if (json.get("errcode") != null)
                {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
                }
                else
                {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
                    result = json.get("access_token").getAsString();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
            return result;
        }
    }

    public static void main(String[] args) throws Exception
    {
        System.out.println("=========1获取token=========");
        String accessToken = getToken(GET_TOKEN_URL, APP_ID, SECRET);// 获取token
        System.out.println(accessToken);
        if (accessToken != null)
            System.out.println(accessToken);
    }

}
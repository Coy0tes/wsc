package com.jeeplus.wxpack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.UUID;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import com.jeeplus.common.config.Global;

public class WeChatUtils {
	
	 SendRedPack sendRedPack = new SendRedPack();
	
	public WeChatUtils(String re_openid, int total_amount){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(date);
       
        sendRedPack.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
        sendRedPack.setMch_id(Global.getConfig("MCHID"));
        sendRedPack.setMch_billno(Global.getConfig("MCHID") + str);
        sendRedPack.setWxappid(Global.getConfig("APPID"));
        sendRedPack.setSend_name(Global.getConfig("SENDNAME"));
        sendRedPack.setTotal_num(1);
        sendRedPack.setAct_name(Global.getConfig("ACTNAME"));
        sendRedPack.setWishing(Global.getConfig("WISHING"));
        sendRedPack.setRemark(Global.getConfig("REMARK"));
        sendRedPack.setClient_ip("8.8.8.8");
        sendRedPack.setRe_openid(re_openid);
        sendRedPack.setTotal_amount(total_amount);
    }
	
    public String createSendRedPackOrderSign(SendRedPack redPack){
        StringBuffer sign = new StringBuffer();
        sign.append("act_name=").append(redPack.getAct_name());
        sign.append("&client_ip=").append(redPack.getClient_ip());
        sign.append("&mch_billno=").append(redPack.getMch_billno());
        sign.append("&mch_id=").append(redPack.getMch_id());
        sign.append("&nonce_str=").append(redPack.getNonce_str());
        sign.append("&re_openid=").append(redPack.getRe_openid());
        sign.append("&remark=").append(redPack.getRemark());
        sign.append("&send_name=").append(redPack.getSend_name());
        sign.append("&total_amount=").append(redPack.getTotal_amount());
        sign.append("&total_num=").append(redPack.getTotal_num());
        sign.append("&wishing=").append(redPack.getWishing());
        sign.append("&wxappid=").append(redPack.getWxappid());
        sign.append("&key=").append(Global.getConfig("PAYKEY"));
        
        return DigestUtils.md5Hex(sign.toString()).toUpperCase();
    }
    
    /**
     * 发送请求
     * */
    private String ssl(String url,String data){
        StringBuffer message = new StringBuffer();
        try {
            String MCHID = Global.getConfig("MCHID");
            KeyStore keyStore  = KeyStore.getInstance("PKCS12");
            String certFilePath = "D:/certs/apiclient_cert.p12";
            FileInputStream instream = new FileInputStream(new File(certFilePath));
            keyStore.load(instream, MCHID.toCharArray());
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, MCHID.toCharArray()).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            HttpPost httpost = new HttpPost(url);
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(data, "UTF-8"));
            System.out.println("executing request" + httpost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        message.append(text);
                    }
                }
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } 
        return message.toString();
    }
    
    /**
     * 发送现金红包 4
     * @throws
     */
    public Map<String, String> sendRedPack() throws Exception {
        String sign = createSendRedPackOrderSign(sendRedPack);
        sendRedPack.setSign(sign);
        
        XMLUtil xmlUtil= new XMLUtil();
        xmlUtil.xstream().alias("xml", sendRedPack.getClass());
        String xml = xmlUtil.xstream().toXML(sendRedPack);
        System.out.println(xml);
        String response = ssl(Global.getConfig("SENDEEDPACKURL"), xml);
        Map<String, String> map = xmlUtil.parseXml(response);
        return map;
    }
    
    public static void main(String[] args) {
    	WeChatUtils wcu = new WeChatUtils("oe_YM0kiXHThVzo1px5Iy1Fn6yZQ",1);
    	try {
    		
    		Map<String, String> map = wcu.sendRedPack();
			System.out.println(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

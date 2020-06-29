package com.newsblogsite.sms.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * *@author 83614
 * *@date 2020/4/10
 **/

@Component
public class SendSms {


    @Autowired
    private Environment environment;

    /**
     * 发送验证码
     * @param mobile
     * @param code
     */
    public void sendCode(String mobile, String code) {
        String accessKeyId=environment.getProperty("aliyun.sms.accessKeyId");
        String accessSecret=environment.getProperty("aliyun.sms.accessSecret");
        String signName=environment.getProperty("aliyun.sms.signName");
        String templateCode=environment.getProperty("aliyun.sms.templateCode");
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers",mobile);
        //需要json
        request.putQueryParameter("TemplateParam","{\"code\":\"" + code + "\"}");
        request.putQueryParameter("SignName",signName);
        request.putQueryParameter("TemplateCode",templateCode);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }


}
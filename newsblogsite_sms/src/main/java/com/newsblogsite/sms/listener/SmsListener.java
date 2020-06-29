package com.newsblogsite.sms.listener;

import com.newsblogsite.sms.utils.SendSms;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * *@author 83614
 * *@date 2020/4/10
 **/
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SendSms sendSms;

    @RabbitHandler
    public void executeSms(Map<String,String> map) {
        System.out.println("手机号"+map.get("mobile"));
        System.out.println("验证码"+map.get("checkCode"));
        sendSms.sendCode(map.get("mobile"),map.get("checkCode"));
    }

}

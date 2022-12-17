package cn.itcast.travel.util;

import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;

/**
 * 短信测试工具类
 */
public class SMSTestUtil {

    public static void sendSms(String mobile,String code){
        try{

        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId("LTAI5t7mYq3p2McigeDoNJko")
                // 您的AccessKey Secret
                .setAccessKeySecret("YNxTD1SJzeq2BZPZ7EpoezNlyGAn77");
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        //创建的客户端
        com.aliyun.dysmsapi20170525.Client client = new com.aliyun.dysmsapi20170525.Client(config);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("阿里云短信测试")
                .setTemplateCode("SMS_154950909")
                .setPhoneNumbers(mobile)  //只能是测试绑定的手机号
                .setTemplateParam("{\"code\":"+code+"}");
        RuntimeOptions runtime = new RuntimeOptions();
        // 复制代码运行请自行打印 API 的返回值
        SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
        System.out.println("发短发送状态:"+sendSmsResponse.getBody().getCode());
//        System.out.println("短信验证码:"+code);

        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("短信发送失败");
        }
    }

    public static void main(String[] args){
        //验证码
        SMSTestUtil.sendSms("19936773784","1234");

    }
}

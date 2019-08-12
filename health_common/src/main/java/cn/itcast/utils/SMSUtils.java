package cn.itcast.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 阿里云短信服务
 */
public class SMSUtils {

    public static final String VALIDATE_CODE = "SMS_171858993";//发送短信验证码

    public static final String ORDER_NOTICE = "SMS_172005085";
    /**
     * 发送短信验证码
     * @param phoneNumber
     * @param param
     */
    public static void sendShortMesseage(String templateCode,String phoneNumber, String param) {
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIIDIruP7oLVSv", "RFD0vFWnDi3y7UPEDjH4RfvMZthnRP");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "传智健康");
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\""+param+"\"}");
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


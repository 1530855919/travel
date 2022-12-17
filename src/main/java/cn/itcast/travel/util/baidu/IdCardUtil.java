package cn.itcast.travel.util.baidu;

import cn.itcast.travel.util.JedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;

import java.net.URLEncoder;
import java.util.Map;

public class IdCardUtil {

    public static final String BAIDU_ACCESS_TOKEN = "BAIDU_ACCESS_TOKEN";

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String idcard(String filePath, String flag) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        try {
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "id_card_side=" + flag + "&detect_risk=true" + "&image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAccessToken();

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getAccessToken() {
        Jedis jedis = JedisUtil.getJedis();
        Boolean exists = jedis.exists(BAIDU_ACCESS_TOKEN);
        if (exists) {
            String baidu_access_token = jedis.get(BAIDU_ACCESS_TOKEN);
            return baidu_access_token;
        }
        String auth = AuthServiceUtil.getAuth();
        jedis.setex(BAIDU_ACCESS_TOKEN, 2562000, auth);
        JedisUtil.close(jedis);
        return auth;
    }


    public static void main(String[] args) {
        String filePath = "D:\\img\\Snipaste20456.png";
        String front = IdCardUtil.idcard(filePath, "front");
        Map map = JSON.parseObject(front, Map.class);
        System.out.println(map);
        JSONObject words_result = (JSONObject) map.get("words_result");
        String string1 = words_result.getJSONObject("姓名").getString("words");
        String string2 = words_result.getJSONObject("民族").getString("words");
        String string3 = words_result.getJSONObject("住址").getString("words");
        String string4 = words_result.getJSONObject("公民身份号码").getString("words");
        String string5 = words_result.getJSONObject("出生").getString("words");
        String string6 = words_result.getJSONObject("性别").getString("words");

        String risk_type = (String) map.get("risk_type");
        String image_status = (String) map.get("image_status");
        Integer idcard_number_type = (Integer) map.get("idcard_number_type");
    }


}

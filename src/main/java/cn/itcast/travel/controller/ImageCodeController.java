package cn.itcast.travel.controller;

import cn.itcast.travel.util.ImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class ImageCodeController {

    @GetMapping(value = "/imageCheckCode")
    public void imageCheckCode(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        //服务器通知浏览器不要缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");

        //生成图片验证码
        String checkCode = ImageUtil.getCheckCode();
        //生成图片
        BufferedImage image = ImageUtil.createImage(80, 35, BufferedImage.TYPE_3BYTE_BGR, checkCode);

        //将验证码保存到session中
        session.setAttribute("CHECK_CODE", checkCode);

        //将图片写到response的数据流中
        ImageIO.write(image, "png", response.getOutputStream());
    }

}

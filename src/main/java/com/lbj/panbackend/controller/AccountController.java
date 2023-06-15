package com.lbj.panbackend.controller;

import com.lbj.panbackend.entity.constants.Constant;
import com.lbj.panbackend.utils.CreateImageCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户信息controller
 *
 */
@RestController("userinfoController")
public class AccountController extends BaseController{

    @GetMapping("/checkcode")
    public void checkCode(HttpServletResponse response, HttpSession session, Integer type) throws IOException {
        CreateImageCode vCode = new CreateImageCode(130, 38, 5, 10);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        String code = vCode.getCode();
        if (type == null || type == 0) {
            session.setAttribute(Constant.CHECK_CODE_KEY, code);
        } else {
            session.setAttribute(Constant.CHECK_CODE_KEY_EMAIL, code);
        }
        vCode.write(response.getOutputStream());
    }

}

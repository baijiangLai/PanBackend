package com.lbj.panbackend.controller;

import com.lbj.panbackend.annotation.GlobalInterceptor;
import com.lbj.panbackend.annotation.VerifyParam;
import com.lbj.panbackend.entity.constants.Constant;
import com.lbj.panbackend.entity.enums.VerifyRegexEnum;
import com.lbj.panbackend.entity.vo.ResponseVO;
import com.lbj.panbackend.exception.BusinessException;
import com.lbj.panbackend.service.EmailCodeService;
import com.lbj.panbackend.utils.CreateImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户信息controller
 *
 */
@RestController("userinfoController")
public class AccountController extends BaseController {

    @Autowired
    EmailCodeService emailCodeService;

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

    @RequestMapping("/sendEmailCode")
    @GlobalInterceptor(checkLogin = false, checkParams = true)
    public ResponseVO sendEmailCode(HttpSession session,
                                    @VerifyParam(required = true, regex = VerifyRegexEnum.EMAIL, max = 150) String email,
                                    @VerifyParam(required = true) String checkCode,
                                    @VerifyParam(required = true) Integer type) {
        try {
        // 验证用户输入的图片验证码(checkCode)与会话中的验证码是否相等
        if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constant.CHECK_CODE_KEY_EMAIL))) {
            throw new BusinessException("图片验证码不正确");
        }
        emailCodeService.sendEmailCode(email, type);
        return getSuccessResponseVO(null);
    } finally
    {
        session.removeAttribute(Constant.CHECK_CODE_KEY_EMAIL);
    }
}
}

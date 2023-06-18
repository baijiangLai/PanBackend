package com.lbj.panbackend.service;

public interface EmailCodeService {
    /**
     *  向邮箱发送验证码
      */

    void sendEmailCode(String email, Integer type);
}

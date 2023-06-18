package com.lbj.panbackend.service.impl;

import com.lbj.panbackend.entity.constants.Constant;
import com.lbj.panbackend.entity.po.EmailCode;
import com.lbj.panbackend.entity.po.UserInfo;
import com.lbj.panbackend.entity.query.EmailCodeQuery;
import com.lbj.panbackend.entity.query.UserInfoQuery;
import com.lbj.panbackend.exception.BusinessException;
import com.lbj.panbackend.mappers.EmailCodeMapper;
import com.lbj.panbackend.mappers.UserInfoMapper;
import com.lbj.panbackend.service.EmailCodeService;
import com.lbj.panbackend.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class EmailCodeServiceImpl implements EmailCodeService {

    @Resource
    private EmailCodeMapper<EmailCode, EmailCodeQuery> emailCodeMapper;
    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;

    @Override
    public void sendEmailCode(String email, Integer type) {

        // 1.若是注册过程，则先校验一下邮箱是否已经注册过了
        if(type.equals(Constant.ZERO)){
            UserInfoQuery query =new UserInfoQuery();
            query.setEmail(email);
            List<UserInfo> userInfos = userInfoMapper.selectList(query);
            if (null!=userInfos){
                throw new BusinessException("邮箱已经存在");
            }
        }
        // 2. 生成随机的验证码
        String randomNumber = StringTools.getRandomNumber(Constant.LENGTH_5);

        // 3. 发送验证码
        sendEmailCode(email, randomNumber);

        // 4. 将email_code中email已有的验证码失效
        EmailCode emailCode = new EmailCode();
        emailCode.setEmail(email);
        emailCodeMapper.disableEmailCode(emailCode);

        // 5， 将新的验证码与邮箱存到email_code里面去
        emailCode.setCode(randomNumber);
        emailCode.setStatus(0);
        emailCode.setStatus(Constant.ZERO);
        emailCode.setCreateTime(new Date());
        emailCodeMapper.insert(emailCode);
    }

    public void sendEmailCode(String toEmail, String randomNumber){}

}

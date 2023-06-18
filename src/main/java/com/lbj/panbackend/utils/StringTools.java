package com.lbj.panbackend.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class StringTools {
    /**
     * 生成随机数，count为随机数的长度
     */
    public static final String getRandomNumber(Integer count) {
        return RandomStringUtils.random(count, false, true);
    }
}

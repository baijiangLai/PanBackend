package com.lbj.panbackend.mappers;

public interface EmailCodeMapper<T, P> extends BaseMapper<T, P> {

    void disableEmailCode(T emailCode);

}

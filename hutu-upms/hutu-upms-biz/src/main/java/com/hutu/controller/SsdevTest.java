package com.hutu.controller;

import cn.hutool.setting.dialect.Props;
import lombok.Data;

import java.io.Serializable;

@Data
public class SsdevTest implements Serializable {
    String appName;
    String appId;
    Props props;
}

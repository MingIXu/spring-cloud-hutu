package com.hutu.cloud.passport.authorization;

import com.hutu.cloud.core.entity.LoginUser;

import java.util.Map;

public interface Authorizer {

	LoginUser grant(Map<String, String> params);

}

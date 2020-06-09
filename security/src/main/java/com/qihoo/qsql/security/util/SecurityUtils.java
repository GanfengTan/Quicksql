package com.qihoo.qsql.security.util;

import com.google.common.io.CharStreams;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qihoo.qsql.security.SecurityContext;
import com.qihoo.qsql.security.api.PresetSecurityStrategy;
import com.qihoo.qsql.security.api.SecurityStrategy;
import com.qihoo.qsql.security.common.CommonConfiguration;
import com.qihoo.qsql.security.model.ResultBean;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 安全验证工具模块.
 */
public class SecurityUtils {

    public static ResultBean getSecurityCheck(String sql) {
        InputStream in = PresetSecurityStrategy.class.getClassLoader()
            .getResourceAsStream(CommonConfiguration.security_config_file.getValue());
        try {
            String json = CharStreams.toString(new InputStreamReader(in, StandardCharsets.UTF_8));
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
            Class clazz = Class.forName(jsonObject.get("clazz").getAsString());
            SecurityStrategy securityStrategy = (SecurityStrategy) clazz.getConstructor().newInstance();
            return new SecurityContext(securityStrategy).checkSecurityStrategy(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultBean<>("0", "unknown error!");
    }

}

package com.qihoo.qsql.security.api;

import com.google.common.io.CharStreams;
import com.qihoo.qsql.security.common.CommonConfiguration;
import com.qihoo.qsql.security.common.ErrorCode;
import com.qihoo.qsql.security.engine.CommonExecutor;
import com.qihoo.qsql.security.model.ResultBean;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class PresetSecurityStrategy implements SecurityStrategy {

    @Override
    public ResultBean checkSecurityStrategy(String sql) {
        InputStream in = PresetSecurityStrategy.class.getClassLoader()
            .getResourceAsStream(CommonConfiguration.security_config_file.getValue());
        try {
            String json = CharStreams.toString(new InputStreamReader(in, StandardCharsets.UTF_8));
            return new CommonExecutor().getExecutorResult(sql, json);
        } catch (IOException e) {
        }
        return new ResultBean<>(ErrorCode.ERROR.code, "unknown error!");
    }

    @Override
    public ResultBean checkSecurityStrategy(String sql, String clientType) {
        return null;
    }
}

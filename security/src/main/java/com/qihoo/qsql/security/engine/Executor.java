package com.qihoo.qsql.security.engine;

import com.qihoo.qsql.security.model.ResultBean;

public interface Executor {

    ResultBean getExecutorResult(String sql, String json);

}

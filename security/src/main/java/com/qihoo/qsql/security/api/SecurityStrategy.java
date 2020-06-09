package com.qihoo.qsql.security.api;

import com.qihoo.qsql.security.model.ResultBean;

/**
 * security strategy.
 */
public interface SecurityStrategy {

    //check sql
    ResultBean checkSecurityStrategy(String sql);

    //integration of qsql driver,reward result
    ResultBean checkSecurityStrategy(String sql, String clientType);

}

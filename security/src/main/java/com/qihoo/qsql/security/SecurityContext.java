package com.qihoo.qsql.security;

import com.qihoo.qsql.security.api.SecurityStrategy;
import com.qihoo.qsql.security.model.ResultBean;

public class SecurityContext {

    private SecurityStrategy securityStrategy;

    public SecurityContext(SecurityStrategy securityStrategy){
        this.securityStrategy = securityStrategy;
    }

    public ResultBean checkSecurityStrategy(String sql){
        return securityStrategy.checkSecurityStrategy(sql);
    }

    public ResultBean checkSecurityStrategy(String sql,String clientType){
        return securityStrategy.checkSecurityStrategy(sql,clientType);
    }

}

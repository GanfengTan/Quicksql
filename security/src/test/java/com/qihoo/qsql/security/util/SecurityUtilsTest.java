package com.qihoo.qsql.security.util;

import org.junit.Test;

public class SecurityUtilsTest {

    @Test
    public void getSecurityCheck() {
        String sql = "select * from tables1 -- where 1=1";
        System.out.println(SecurityUtils.getSecurityCheck(sql).getCode());
        System.out.println(SecurityUtils.getSecurityCheck(sql).getData());
        System.out.println(SecurityUtils.getSecurityCheck(sql).getMessage());
    }
}
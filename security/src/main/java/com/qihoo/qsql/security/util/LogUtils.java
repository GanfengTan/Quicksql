package com.qihoo.qsql.security.util;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qihoo.qsql.security.api.PresetSecurityStrategy;
import com.qihoo.qsql.security.common.CommonConfiguration;
import com.qihoo.qsql.security.common.SecurityConfigCode;
import com.qihoo.qsql.security.model.LogBean;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;

/**
 * 日志采集工具模块，发送日志到kafka.
 */
public class LogUtils {

    public static String sendCollectLog(String topic, String dbName, String driverName,
        String dbIp, String dbPort, String serverIp, String userIp, String userCookie,
        String serverName, String sql, Integer status, String message, String queryIdentity,
        Integer terminal, Long sqlStartTime, Long sqlEndTime) {
        LogBean logBean = new LogBean(dbName, driverName, dbIp, dbPort, serverIp,
            userIp, userCookie, serverName, sql, status, message, queryIdentity,
            terminal, sqlStartTime, sqlEndTime);
        logBean.setSqlEvent(System.currentTimeMillis());
        logBean.setSqlZoneTime(TimeZone.getDefault().getID());
        if (Objects.nonNull(sql) && !"".equals(sql)) {
            logBean.setTableNames(String.join(",", ParserUtils.extractTableNames(sql)));
            logBean.setEventType(getBitSet(sql));
        }
        return new Gson().toJson(logBean);
        // new LogCollector().sendMessage(topic, new Gson().toJson(logBean));
    }

    private static String getBitSet(String sql) {
        InputStream in = PresetSecurityStrategy.class.getResourceAsStream(CommonConfiguration.security_config_file
            .getValue());
        List<Integer> list = new ArrayList<>();
        try {
            String json = CharStreams.toString(new InputStreamReader(in, StandardCharsets.UTF_8));
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject().get(SecurityConfigCode.KEYWORDS.code)
                .getAsJsonObject();
            if (1 == jsonObject.get(SecurityConfigCode.STATUS.code).getAsInt()) {
                JsonObject ddlJsonObject = jsonObject.get(SecurityConfigCode.DDL.code).getAsJsonObject();
                JsonObject dmlJsonObject = jsonObject.get(SecurityConfigCode.DML.code).getAsJsonObject();
                list.addAll(getList(sql, ddlJsonObject));
                list.addAll(getList(sql, dmlJsonObject));
            }

        } catch (IOException e) {
        }

        return list.toString().replace("[", "").replace("]", "").replaceAll(" ", "");
    }

    private static List<Integer> getList(String sql, JsonObject jsonObject) {
        List<Integer> list = new ArrayList<>();
        List<String> sqlList = new ArrayList<>();
        for (String str : Arrays.asList(sql.toLowerCase().trim().split(" "))) {
            sqlList.add(str.replaceAll("\\s*", "").replaceAll(" +", "").replaceAll(" ", ""));
        }
        if (jsonObject.get(SecurityConfigCode.STATUS.code).getAsInt() == 1) {
            Set<String> set = jsonObject.keySet();
            set.remove(SecurityConfigCode.STATUS.code);
            for (Iterator it2 = set.iterator(); it2.hasNext(); ) {
                String key = it2.next().toString();
                if (sqlList.contains(key)) {
                    JsonObject jsonObject1 = jsonObject.get(key).getAsJsonObject();
                    if (jsonObject1.get(SecurityConfigCode.STATUS.code).getAsInt() == 1) {
                        Set<String> set1 = jsonObject1.keySet();
                        set1.remove(SecurityConfigCode.STATUS.code);
                        for (Iterator it3 = set1.iterator(); it3.hasNext(); ) {
                            String key2 = it3.next().toString();
                            if (sqlList.contains(key2)) {
                                JsonObject resultJson = jsonObject1.get(key2).getAsJsonObject();
                                if (resultJson.get(SecurityConfigCode.STATUS.code).getAsInt() == 1) {
                                    list.add(resultJson.get(SecurityConfigCode.VALUE.code).getAsInt());
                                }

                            }
                        }

                    }
                }
            }
        }
        return list;
    }
}

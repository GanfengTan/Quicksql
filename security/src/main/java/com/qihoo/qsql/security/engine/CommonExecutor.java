package com.qihoo.qsql.security.engine;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.qihoo.qsql.security.common.ErrorCode;
import com.qihoo.qsql.security.common.SecurityConfigCode;
import com.qihoo.qsql.security.model.RegularBean;
import com.qihoo.qsql.security.model.RegularListBean;
import com.qihoo.qsql.security.model.ResultBean;
import com.qihoo.qsql.security.util.ParserUtils;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * this executor include keywords,rules and parser.
 */
public class CommonExecutor implements Executor {

    @Override
    public ResultBean getExecutorResult(String sql, String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        try {
            if (getRegularCheck(sql.toLowerCase(), jsonObject) && getSqlStandardCheck(sql.toLowerCase(), jsonObject)
                && getParserCheck(sql.toLowerCase(), jsonObject)) {
                return new ResultBean<>(ErrorCode.NO_ERROR);
            }
        } catch (Throwable tx) {
            return new ResultBean<>(ErrorCode.ERROR.code, tx.getMessage());
        }
        return new ResultBean<>(ErrorCode.ERROR.code, "unknown check error!");
    }

    private boolean getRegularCheck(String sql, JsonObject jsonObject) {
        Pattern pattern = Pattern.compile(jsonObject.get(SecurityConfigCode.SYMBOL.code)
            .getAsJsonObject().get(SecurityConfigCode.CONDITION.code)
            .getAsString());
        if (pattern.matcher(sql).find()) {
            throw new RuntimeException("SQL Injection");
        }
        RegularListBean regularListBean = new Gson()
            .fromJson(jsonObject
                    .get(SecurityConfigCode.RULES.code)
                    .getAsJsonObject()
                    .get(SecurityConfigCode.REGULAR.code)
                    .getAsJsonObject(),
                new TypeToken<RegularListBean>() {
                }.getType());
        boolean flag = false;
        if (1 == jsonObject.get(SecurityConfigCode.RULES.code).getAsJsonObject()
            .get(SecurityConfigCode.STATUS.code)
            .getAsInt()) {
            for (RegularBean regularBean : regularListBean.getRegulars()) {
                if (1 == regularBean.getStatus() && null != regularBean.getCondition()) {
                    if (0 == regularBean.getInclude()) {
                        if (Pattern.matches(regularBean.getCondition(), sql)) {
                            throw new RuntimeException(regularBean.getFunction());
                        }
                        flag = true;
                    }
                    if (1 == regularBean.getInclude()) {
                        if (Pattern.matches(regularBean.getCondition(), sql)) {
                            flag = true;
                        } else {
                            throw new RuntimeException(regularBean.getFunction());
                        }
                    }
                }

            }
        }
        return flag;
    }

    private boolean getSqlStandardCheck(String sql, JsonObject jsonObject) {
        jsonObject = jsonObject.get(SecurityConfigCode.RULES.code).getAsJsonObject();
        boolean flag = true;
        if (1 == jsonObject.get(SecurityConfigCode.STATUS.code).getAsInt()) {
            Set<String> set = jsonObject.keySet();
            set.remove(SecurityConfigCode.STATUS.code);
            for (Iterator it2 = set.iterator(); it2.hasNext(); ) {
                String key = it2.next().toString();
                if (sql.contains(key)) {
                    JsonObject jsonObjectMid = jsonObject.get(key).getAsJsonObject();
                    for (Iterator it3 = jsonObjectMid.keySet().iterator(); it3.hasNext(); ) {
                        String key2 = it3.next().toString();
                        if (key2.equals(SecurityConfigCode.STATUS.code)) {
                            continue;
                        }
                        JsonObject sqlStandard = jsonObjectMid.get(key2).getAsJsonObject();
                        String sqlReplace = sql.replaceAll(",", " ").replaceAll("\\(", " ").replaceAll("\\)",
                            " ");
                        List<String> inputStringList = Arrays.stream(sqlReplace.split(" "))
                            .filter(key2::equals)
                            .collect(Collectors.toList());
                        if (sqlStandard.keySet().contains(SecurityConfigCode.KEYNUMBER.code)) {
                            flag = (0 == sqlStandard.get(SecurityConfigCode.LEVEL.code).getAsInt()) &&
                                inputStringList.size() <= sqlStandard.get(SecurityConfigCode.KEYNUMBER.code).getAsInt();
                            if (!flag) {
                                throw new RuntimeException(inputStringList.toString() + "too many");
                            }
                            continue;
                        }

                        if (sqlStandard.keySet().contains(SecurityConfigCode.SUBQUERY.code)) {
                            flag = (0 == sqlStandard.get(SecurityConfigCode.LEVEL.code).getAsInt()) &&
                                inputStringList.size() <= sqlStandard.get(SecurityConfigCode.SUBQUERY.code).getAsInt();
                            if (!flag) {
                                throw new RuntimeException("subquery too many");
                            }
                            continue;
                        }

                        if (sql.contains(key2) && 1 == sqlStandard.get(SecurityConfigCode.STATUS.code).getAsInt()) {
                            if (0 == sqlStandard.get(SecurityConfigCode.INCLUDE.code).getAsInt() && !sql
                                .contains(sqlStandard.get(SecurityConfigCode.CONDITION.code).getAsString())) {
                                flag = true;
                            } else if (1 == sqlStandard.get(SecurityConfigCode.INCLUDE.code).getAsInt() && sql
                                .contains(sqlStandard.get(SecurityConfigCode.CONDITION.code).getAsString())) {
                                flag = true;
                            } else {
                                String message = (0 == sqlStandard.get(SecurityConfigCode.INCLUDE.code)
                                    .getAsInt()) ? "contains" : " not contains";
                                throw new RuntimeException(
                                    message + sqlStandard.get(SecurityConfigCode.CONDITION.code));
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }

    private boolean getParserCheck(String sql, JsonObject jsonObject) {
        try {
            //TODO this function is deficiency.
            if (ParserUtils.getParserJudgement(sql)) {
                return true;
            }
        } catch (Exception ex) {
            // ex.printStackTrace();
            System.out.println("SQL syntax has problems:" + ex.getMessage());
            return true;
        }
        return true;
    }

}

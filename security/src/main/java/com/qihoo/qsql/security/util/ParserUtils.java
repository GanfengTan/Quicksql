package com.qihoo.qsql.security.util;

import com.qihoo.qsql.org.apache.calcite.sql.SqlNode;
import com.qihoo.qsql.org.apache.calcite.sql.parser.SqlParseException;
import com.qihoo.qsql.org.apache.calcite.sql.parser.SqlParser;
import com.qihoo.qsql.org.apache.calcite.sql.parser.impl.SqlParserImpl;
import com.qihoo.qsql.org.apache.calcite.sql.validate.SqlConformanceEnum;
import com.qihoo.qsql.org.apache.calcite.tools.FrameworkConfig;
import com.qihoo.qsql.org.apache.calcite.tools.Frameworks;
import com.qihoo.qsql.security.parser.TableNameCollector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;


public class ParserUtils {

    private final static FrameworkConfig config;

    static {
        config = Frameworks.newConfigBuilder()
            .parserConfig(SqlParser.configBuilder()
                .setParserFactory(SqlParserImpl.FACTORY)
                .setCaseSensitive(false)
                .setQuoting(Quoting.BACK_TICK)
                .setQuotedCasing(Casing.TO_UPPER)
                .setUnquotedCasing(Casing.TO_UPPER)
                .setConformance(SqlConformanceEnum.ORACLE_12)
                .build())
            .build();
    }

    public static SqlNode getSqlNode(String sql) {
        SqlParser parser = SqlParser.create(sql, config.getParserConfig());
        SqlNode sqlNode = null;
        try {
            sqlNode = parser.parseStmt();
            System.out.println(sqlNode);
            return sqlNode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlNode;
    }

    public static boolean getParserJudgement(String sql) throws SqlParseException {
        SqlParser parser = SqlParser.create(sql, config.getParserConfig());
        SqlNode sqlNode = parser.parseStmt();
        return true;
    }

    public static List<String> getParseTableName(String sql) {
        TableNameCollector collector = new TableNameCollector();
        try {
            List<String> list = new ArrayList<>();
            list.addAll(new LinkedHashSet<String>(new ArrayList<>(collector
                .parseTableName(sql,config)
                .tableNames)));
            return list;
        } catch (SqlParseException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static List<String> extractTableNames(String sql) {
        Set<String> tables = new HashSet<>();

        Matcher fromMatcher = Pattern.compile("\\s+from(\\s+)([.\\w]+)",
            Pattern.CASE_INSENSITIVE).matcher(sql);
        Matcher joinMatcher = Pattern.compile("\\s+join(\\s+)([.\\w]+)",
            Pattern.CASE_INSENSITIVE).matcher(sql);

        while (fromMatcher.find()) {
            tables.add(fromMatcher.group(2));
        }
        while (joinMatcher.find()) {
            tables.add(joinMatcher.group(2));
        }

        if (tables.isEmpty()) {
            Matcher descMatcher = Pattern.compile("\\s*(desc|describe|table)(\\s+)([.\\w]+)",
                Pattern.CASE_INSENSITIVE).matcher(sql);
            while (descMatcher.find()) {
                tables.add(descMatcher.group(3));
            }
            return new ArrayList<>(tables);
        }

        return new ArrayList<>(tables);
    }
}

package com.qihoo.qsql.security.model;

public class LogBean {

    private String logName;
    private String dbName;
    private String driverName;
    private String dbIp;
    private String dbPort;
    private String userIp;
    private String serverIp;
    private String serverName;
    private String eventType;
    private String sqlInfo;
    private Integer status;
    private String message;
    private String userCookie;
    private String tableNames;
    private Integer terminal;
    private String queryIdentity;
    private Long sqlStartTime;
    private Long sqlEndTime;
    private Long sqlEvent;
    private String sqlZoneTime;

    public LogBean(String dbName, String driverName, String dbIp, String dbPort,
        String serverIp, String userIP, String userCookie,
        String serverName, String sql, Integer status, String message, String queryIdentity,
        Integer terminal, Long sqlStartTime, Long sqlEndTime) {
        this.logName = serverName + " " + serverIp;
        this.dbName = dbName;
        this.driverName = driverName;
        this.dbIp = dbIp;
        this.dbPort = dbPort;
        this.serverIp = serverIp;
        this.serverName = serverName;
        this.userIp = userIP;
        this.userCookie = userCookie;
        this.sqlInfo = sql;
        this.status = status;
        this.message = message;
        this.queryIdentity = queryIdentity;
        this.terminal = terminal;
        this.sqlStartTime = sqlStartTime;
        this.sqlEndTime = sqlEndTime;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDbIp() {
        return dbIp;
    }

    public void setDbIp(String dbIp) {
        this.dbIp = dbIp;
    }

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSqlInfo() {
        return sqlInfo;
    }

    public void setSqlInfo(String sqlInfo) {
        this.sqlInfo = sqlInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserCookie() {
        return userCookie;
    }

    public void setUserCookie(String userCookie) {
        this.userCookie = userCookie;
    }

    public String getTableNames() {
        return tableNames;
    }

    public void setTableNames(String tableNames) {
        this.tableNames = tableNames;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

    public String getQueryIdentity() {
        return queryIdentity;
    }

    public void setQueryIdentity(String queryIdentity) {
        this.queryIdentity = queryIdentity;
    }

    public Long getSqlStartTime() {
        return sqlStartTime;
    }

    public void setSqlStartTime(Long sqlStartTime) {
        this.sqlStartTime = sqlStartTime;
    }

    public Long getSqlEndTime() {
        return sqlEndTime;
    }

    public void setSqlEndTime(Long sqlEndTime) {
        this.sqlEndTime = sqlEndTime;
    }

    public Long getSqlEvent() {
        return sqlEvent;
    }

    public void setSqlEvent(Long sqlEvent) {
        this.sqlEvent = sqlEvent;
    }

    public String getSqlZoneTime() {
        return sqlZoneTime;
    }

    public void setSqlZoneTime(String sqlZoneTime) {
        this.sqlZoneTime = sqlZoneTime;
    }
}

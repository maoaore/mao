package cn.edu.ctbu.dp01.constant;

public enum REnum {
    UNKNOW_ERR( -999,  "未知错误"),
    COMMON_ERR( -10,  "一般性错误"),
    QUERY_ERR(-3,"error in query criteria"),
    LOGIN_ERR( -2,  "出错了，不正确的用户名密码!"),
    SUCCESS( 1, "成功")
            ;

    private Integer code;
    private String msg;

    REnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
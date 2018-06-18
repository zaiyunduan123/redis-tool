package com.jesper.result;

/**
 * Created by jiangyunxiong on 2018/6/18.
 */
public class CodeMsg {

    private int code;
    private String msg;

    //通用的错误码
    public static CodeMsg LIMIT_REACHED= new CodeMsg(500104, "访问太频繁！");


    private CodeMsg( ) {
    }

    private CodeMsg( int code,String msg ) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }


}

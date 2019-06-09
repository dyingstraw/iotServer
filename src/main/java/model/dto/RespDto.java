package model.dto;

import java.security.PublicKey;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-09 16:11
 **/
public class RespDto {
    private Integer code;
    private String msg;

    public RespDto(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static RespDto SUCCESS(){
        return new RespDto(200,"failed");
    }
    public static RespDto FAILED(String msg){
        return new RespDto(400,msg);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"classname\":\"RespDto\",");
        sb.append("\"code\":")
                .append(code);
        sb.append(",\"msg\":\"")
                .append(msg).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

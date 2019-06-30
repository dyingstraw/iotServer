package model.message;

import config.ErrorCode;
import lombok.Data;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-05-17 20:37
 **/
@Data
public class Message<T> {
    private Integer version;
    private Integer cmd;
    private Integer code;
    private T data;

    public Message() {
    }

    public Message(Integer version, Integer cmd, T data) {
        this.version = version;
        this.cmd = cmd;
        this.data = data;
    }

    public Message(Integer cmd, T data) {
        this.version = 1;
        this.cmd = cmd;
        this.data = data;
    }
    public Message(Integer cmd,Integer version,Integer code, T data) {
        this.version = 1;
        this.cmd = MessageType.RESP.getKey();
        this.data = data;
        this.code = code;
    }

    public static Message success() {
        return new Message<Boolean>(MessageType.RESP.getKey(), 1, ErrorCode.SUCCESS.getCode(),true);
    }

    public static Message failed() {
        return new Message<Boolean>(MessageType.RESP.getKey(), 1, ErrorCode.FAILED.getCode(),false);
    }
    public static Message failed(String cause) {
        return new Message(MessageType.RESP.getKey(), 1, ErrorCode.FAILED.getCode(),cause);
    }

}

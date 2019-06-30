package model.message;

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

    public static Message<Boolean> success() {
        return new Message<Boolean>(MessageType.RESP.getKey(), new Boolean(true));
    }

    public static Message<Boolean> failed() {
        return new Message<Boolean>(MessageType.RESP.getKey(), new Boolean(false));
    }

}

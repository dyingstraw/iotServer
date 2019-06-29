package model.message;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-05-17 20:37
 **/
public class Message<T> {
    private Integer version;
    private Integer cmd;
    private String from;
    private String to;
    private T data;

    public Message() {
    }

    public Message(Integer version, Integer cmd, T data) {
        this.version = version;
        this.cmd = cmd;
        this.data = data;
    }

    public Message(Integer cmd, T data) {
        this.cmd = cmd;
        this.data = data;
    }

    public static Message<Boolean> success(){
        return new Message<Boolean>(MessageType.RESP.getKey(),new Boolean(true));
    }

    public static Message<Boolean> failed(){
        return new Message<Boolean>(MessageType.RESP.getKey(),new Boolean(false));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"classname\":\"Message\",");
        sb.append("\"version\":")
                .append(version);
        sb.append(",\"cmd\":")
                .append(cmd);
        sb.append(",\"from\":\"")
                .append(from).append('\"');
        sb.append(",\"to\":\"")
                .append(to).append('\"');
        sb.append(",\"data\":")
                .append(data);
        sb.append('}');
        return sb.toString();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCmd() {
        return cmd;
    }

    public void setCmd(Integer cmd) {
        this.cmd = cmd;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

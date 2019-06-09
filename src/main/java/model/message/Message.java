package model.message;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-05-17 20:37
 **/
public class Message<T> {
    private byte version='1';
    private byte cmd='2';
    private String from;
    private String to;
    private T data;

    public Message() {
    }

    public Message(byte version, byte cmd, T data) {
        this.version = version;
        this.cmd = cmd;
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"classname\":\"Message\",");
        sb.append("\"version\":")
                .append(version);
        sb.append(",\"cmd\":")
                .append(cmd);
        sb.append(",\"data\":\"")
                .append(data).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getCmd() {
        return cmd;
    }

    public void setCmd(byte cmd) {
        this.cmd = cmd;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

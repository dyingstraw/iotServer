package model.dto;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-08 14:31
 **/
public class AuthDTO extends BaseDTO {
    private Long devId;
    private String devKey;
    private Boolean flag;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"classname\":\"AuthDTO\",");
        sb.append("\"devId\":")
                .append(devId);
        sb.append(",\"devKey\":\"")
                .append(devKey).append('\"');
        sb.append(",\"flag\":")
                .append(flag);
        sb.append(",\"id\":")
                .append(id);
        sb.append(",\"createTime\":\"")
                .append(createTime).append('\"');
        sb.append(",\"createUser\":")
                .append(createUser);
        sb.append('}');
        return sb.toString();
    }

    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
    }

    public String getDevKey() {
        return devKey;
    }

    public void setDevKey(String devKey) {
        this.devKey = devKey;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}

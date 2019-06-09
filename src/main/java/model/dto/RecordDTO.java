package model.dto;

import java.util.List;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-08 14:22
 **/
public class RecordDTO extends BaseDTO {
    private Long devId;
    private List<Double> values;
    private Integer type;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"classname\":\"RecordDTO\",");
        sb.append("\"devId\":")
                .append(devId);
        sb.append(",\"values\":")
                .append(values);
        sb.append(",\"type\":")
                .append(type);
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

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

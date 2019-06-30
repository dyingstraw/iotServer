package model.dto;

import lombok.Data;

import java.util.List;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-08 14:22
 **/
@Data
public class RecordDTO extends BaseDTO {
    /**
     * 设备id
     */
    private Long devId;
    /**
     * 数值列表
     */
    private List<Double> values;
    /**
     * 消息类型
     */
    private Integer type;

}

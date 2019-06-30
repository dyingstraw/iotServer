package model.dto;

import lombok.Data;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-30 21:49
 **/
@Data
public class HeartDTO {
    /**
     * 设备id
     */
    private Long devId;
    /**
     * 序列号
     */
    private Long sid;
}

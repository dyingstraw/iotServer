package model.dto;

import lombok.Data;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-08 14:31
 **/
@Data
public class AuthDTO extends BaseDTO {
    /**
     * 设备id
     */
    private Long devId;
    /**
     * 设备认证key
     */
    private String devKey;
    /**
     * 是否超级设备
     */
    private Boolean flag;

}

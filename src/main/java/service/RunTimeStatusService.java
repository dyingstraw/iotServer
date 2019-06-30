package service;

import model.RunTimeStatus;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-30 15:39
 **/
public interface RunTimeStatusService {
    /**
     * 获取服务状态
     * @return
     */
    RunTimeStatus getRunTimeStatus();
}

package service;

import exception.CustomException;
import model.RunTimeStatus;

import java.util.List;

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

    /**
     * 从所有的服务列表中找到负载最小的服务器的名字
     * @param
     * @return
     */
    String judgeBestServer() throws NoSuchFieldException, IllegalAccessException, CustomException;
}

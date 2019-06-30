package service.impl;

import com.sun.management.OperatingSystemMXBean;
import model.RunTimeStatus;
import service.RunTimeStatusService;
import util.RuntimeUtil;

import java.lang.management.ManagementFactory;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-30 15:40
 **/
public class RunTimeStatusServiceImpl implements RunTimeStatusService {
    @Override
    public RunTimeStatus getRunTimeStatus() {
        return RuntimeUtil.getRunTimeStatus();
    }
}

package service.impl;

import com.sun.management.OperatingSystemMXBean;
import exception.CustomException;
import model.RunTimeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import service.RunTimeStatusService;
import util.*;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-30 15:40
 **/
public class RunTimeStatusServiceImpl implements RunTimeStatusService {


    private static RunTimeStatusService INSTANCE = new RunTimeStatusServiceImpl();


    public static RunTimeStatusService getINSTANCE() {
        return INSTANCE;
    }

    private RunTimeStatusServiceImpl() {
    }

    @Override
    public RunTimeStatus getRunTimeStatus() {
        return RuntimeUtil.getRunTimeStatus();
    }
    @Override
    public String judgeBestServer() throws CustomException, NoSuchFieldException, IllegalAccessException {
        ArrayList<RunTimeStatus> allServerStatus = new ArrayList<RunTimeStatus>();
        for (int i = 0; i < ZookeeperUtil.getChildren().size(); i++) {
            // 获得当前服务的状态
            Map<String, String> result = RedisUtil.getJedis().hgetAll(ConfigUtil.get("app.name"));
            RunTimeStatus status = CommonUtil.mapToBean(result, RunTimeStatus.class);
            allServerStatus.add(status);
        }
        allServerStatus.sort(new Comparator<RunTimeStatus>() {
            @Override
            public int compare(RunTimeStatus o1, RunTimeStatus o2) {
                return (int) (o1.getActive() - o2.getActive());
            }
        });
        return allServerStatus.get(0).getAddr();
    }
}

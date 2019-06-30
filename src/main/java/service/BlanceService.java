package service;

import util.RedisUtil;

import java.util.List;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-30 15:19
 **/
public class BlanceService {

    private static BlanceService instance = new BlanceService();

    private BlanceService() {
    }

    public static BlanceService getInstance() {
        return instance;
    }



    public String getPrettyNode(List<String> children){
        children.stream().forEach(e->{
            String devNumber = RedisUtil.getJedis().hget("blance", e);

        });
        return null;

    }
}

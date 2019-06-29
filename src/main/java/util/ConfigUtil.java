package util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-23 22:19
 **/
@Slf4j
public class ConfigUtil {


    private static ConfigUtil INSTANCE = new ConfigUtil();
    private  ResourceBundle resourceBundle;


    public static ConfigUtil getINSTANCE() {
        return INSTANCE;
    }

    private ConfigUtil() {
        try {
            resourceBundle = init1();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ResourceBundle init1() throws IOException {
        Properties properties = new Properties();
        String filePath =  Thread.currentThread().getContextClassLoader().getResource("config").getPath();
        log.info("load config.properities from " + filePath);
        resourceBundle = ResourceBundle.getBundle("config");
        return resourceBundle;
    }
    public static String get(String key){
       return getINSTANCE().resourceBundle.getString(key);
    }

}

package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-29 15:57
 **/
public class CommonUtil {

    /**
     * bean2map
     * @param o
     * @return
     */
    public static Map<String, String> beanToMap(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        Map<String, String> map = new HashMap<>();
        Arrays.asList(fields).stream().forEach(e -> {
            try {
                map.put(e.getName(), (String) e.get(e.getName()));
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
        return map;
    }
}

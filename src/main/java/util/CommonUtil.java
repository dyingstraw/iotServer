package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import model.RunTimeStatus;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-29 15:57
 **/
@Slf4j
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
                e.setAccessible(true);
                map.put(e.getName(), (e.get(o)).toString());
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
        return map;
    }

    /**
     * mapTobean 仅支持基础类型转换
     * @param map
     * @param clazz
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws CustomException
     */
    public static RunTimeStatus mapToBean(Map<String,String> map,Class<?extends RunTimeStatus> clazz) throws NoSuchFieldException, IllegalAccessException, CustomException {
        RunTimeStatus status = new RunTimeStatus();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, String> temp = it.next();
            String k = temp.getKey();
            String v = temp.getValue();

            Field field = clazz.getDeclaredField(k);
            field.setAccessible(true);
            if (Long.class.equals(field.getType())|| long.class.equals(field.getType())){
                field.set(status,Long.valueOf(v));
            }else if (String.class.equals(field.getType())) {
                field.set(status,v);
            }else if (Double.class.equals(field.getType())||double.class.equals(field.getType())){
                field.set(status,Double.valueOf(v));
            }else if (Integer.class.equals(field.getType())||int.class.equals(field.getType())){
                field.set(status,Integer.valueOf(v));
            }else {
                throw new CustomException("不支持的数据类型:"+ field.getType());
            }
        }
        return status;
    }

    public static void main(String[] args) throws IllegalAccessException, CustomException, NoSuchFieldException {
        RunTimeStatus status = RuntimeUtil.getRunTimeStatus();
        Map<String, String> map = beanToMap(status);
        log.info(map.toString());


        RunTimeStatus s = mapToBean(map, RunTimeStatus.class);
        log.info(s.toString());

    }
}

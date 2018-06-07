package com.example.robotdemo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.robotdemo.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ning
 * 创建于 2017年10月19日下午3:31:25
 * //TODO json工具类
 */
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private JsonUtil() {
    }

    public static void main(String[] args) {
        List<UserDTO> userList = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            UserDTO user = new UserDTO();
            user.setUsername("张三");
            user.setAge(12 + i);
            user.setCreateTime(new Date());
            userList.add(user);
            System.out.println(toJsonFromObj(user));
        }
        System.out.println(fromJson(toJsonFromObj(userList.get(0)),UserDTO.class));

        System.out.println(toJsonFromArry(userList));

        System.out.println(fromListJson(toJsonFromArry(userList), UserDTO.class));

    }

    //将json转成对象
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return JSON.parseObject(json,clazz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("JsonUtil fromJson Type:" + clazz.getName() + ", Json:" + json, e);
            return null;
        }
    }

    //将json转成对象集合
    public static <T> List<T> fromListJson(String json, Class<T> clazz) {
        try {
            return JSON.parseArray(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("JsonUtil fromJson Type:List<" + clazz.getName() + ">, Json:" + json, e);
            return null;
        }
    }

    //将对象转成json
    public static String toJsonFromObj(Object obj) {
        return JSONObject.toJSONString(obj);
    }

    //将对象集合转成json
    public static <T> String toJsonFromArry(List<T> list) {
        String pojoJson = JSONArray.toJSONString(list);
        return pojoJson;
    }
}

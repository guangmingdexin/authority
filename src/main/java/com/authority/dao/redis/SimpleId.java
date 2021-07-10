package com.authority.dao.redis;

import com.authority.common.utils.annotation.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.UUID;

/**
 * @ClassName SimpleId
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/4 10:55
 * @Version 1.0
 **/
@Component
public class SimpleId implements GenerateId {

    private String prefix;

    private String value;

    private String fieldName;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String generateId(Class clazz) {

        return UUID.randomUUID().toString().substring(0,5);

        // 获取到 注解属性 名称
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field field : fields) {
//            Annotation[] annotations = field.getAnnotations();
//            for (Annotation annotation : annotations) {
//                // 暂时不考虑多个 Id 的情况
//                if(annotation instanceof Id && ((Id) annotation).isPrimary()) {
//                    fieldName = field.getName();
//                    System.out.println(fieldName);
//                    return deal(clazz, fieldName);
//                }
//            }
//        }
//        // 没有 Id 属性
//        fieldName = "Id";
//        System.out.println(fieldName);
//        return deal(clazz, fieldName);
    }

    private synchronized String deal(Class clzz, String s) {
        // 判断 redis 当中有无 这个 类型 的注解
        String key = clzz.getSimpleName().toUpperCase() + ":" + s;

        if(redisTemplate == null) {
            throw new NullPointerException("redis 数据库连接失败！");
        }
        ValueOperations<String, String> op = redisTemplate.opsForValue();

        // 获取最新的主键值
        String value =  op.get(key);
        System.out.println("获取到的 value: " + value);
        String result;
        if(value == null) {
            String prefix = key.substring(0, 1).toUpperCase();
            result = prefix + "0002";
        }else {
            // 进行字符串 加一操作
            result = addOne(value);
        }
        // 更新redis
        System.out.println("更新之前的： result " + result);
        op.set(key, result);
        return result;
    }

    private synchronized String addOne(String s){
        int len = s.length();
        char[] nums = s.toCharArray();
        boolean isOverflow = false;
        int cor = 1;
        // 第一位为 字母
        for (int i = len - 1; i >= 1; i--) {

            int sum =  nums[i] - '0'  + cor;

            if(sum >= 10) {
                if(i == 1) {
                    isOverflow = true;
                }
                sum -= 10;
                cor = 1;
                nums[i] = (char) (sum + '0');

            }else {
                nums[i] = (char) (sum + '0');
                break;
            }
        }

        StringBuilder builder = new StringBuilder();

        builder.append(nums[0]);

        if(isOverflow) {
            builder.append("1");
        }

        for (int i = 1; i < len; i++) {
            builder.append(nums[i]);
        }

        return builder.toString();
    }

}

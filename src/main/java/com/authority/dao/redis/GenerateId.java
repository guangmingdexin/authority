package com.authority.dao.redis;

/**
 * 自动生成主键 ID 接口
 *
 * @Param
 * @Author guangmingdexin
 * @See
 **/
public interface GenerateId {

    /**
     * 传入一个 class 对象，通过反射获取自定义的注解
     * 通过注解判断是否 为 主键
     * 获取到 主键后，将实体类类型 以及主键作为 key 存入 redis中
     * 每次 插入 让该主键 自增 1
     * 删除 更新 操作 不会更改 主键
     *
     * @param clazz 生成 主键 ID的实体类 类型
     * @return
     */
    String generateId(Class clazz);
}

package com.authority.dao.mapper.admin;

import com.authority.pojo.po.Admin;
import com.authority.pojo.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Param
 * @Author guangmingdexin
 * @See
 **/

@Mapper
public interface AdminUserMapper {

    Admin loadUserByUsername(String userName);

    List<Role> getRoles(String userId);
}

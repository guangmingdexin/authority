package com.authority.dao.mapper.account;

import com.authority.pojo.po.Account;
import com.authority.pojo.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

/**
 * @Param
 * @Author guangmingdexin
 * @See
 **/
@Mapper
public interface AccountMapper {


    /**
     * 添加账户
     *
     * @param account 账户
     */
    void createAccount (Account account) throws DuplicateKeyException;

    /**
     * 关联 账户 与 角色的关系
     *
     * @param accountId 账户 id
     * @param rIds 角色 Id
     */
    void batchInsertAccountRole(@Param("accountId") String accountId, @Param("rIds") List<String> rIds);

    List<Account> getAllAccount();

    void delAccount(List<String> accountId);

    void updateAccount(Account account);

    Account loadUserByUsername(String userName);

    List<Role> getRoles(String userId);

    Account getAccount(String accountId);
}

package com.authority.service.user.account;

import com.authority.dao.mapper.account.AccountMapper;
import com.authority.dao.redis.GenerateId;
import com.authority.pojo.bo.JwtUser;
import com.authority.pojo.po.Account;
import com.authority.pojo.po.Role;
import com.authority.pojo.vo.VoAccountRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName AccountUserService
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/5 9:41
 * @Version 1.0
 **/
@Service
@Qualifier("accountUsrService")
public class AccountUserService implements UserDetailsService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private GenerateId id;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        // 通过用户名获取到 用户信息
        Account user = accountMapper.loadUserByUsername(userName);
        System.out.println("user: " + user);
        if(user == null) {
            return new JwtUser();
        }

        // 通过查找到 的 roleId 获取 用户
        List<Role> roles = accountMapper.getRoles(user.getUserId());

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        // 不可更改
        authorities = Collections.unmodifiableList(authorities);
        return new JwtUser(user, authorities);
    }


    @NonNull
    @Transactional(noRollbackFor = DuplicateKeyException.class, rollbackFor = Exception.class)
    public void createAccount(Account account) {
        // 首先判断 id 是否存在
        // 如果不存在，则自动生成 id
        if(account.getUserId() == null) {
            account.setUserId(id.generateId(Account.class));
        }

        // 判断头像是否为空
        if(account.getAvatar() == null) {
            // 设置默认的头像
            account.setAvatar("default");
        }
        try {
            account.setPassword(encoder.encode(account.getPassword()));
            accountMapper.createAccount(account);
        }catch (DuplicateKeyException key) {
            // 自动生成的主键重复 更新策略
            account.setUserId(UUID.randomUUID().toString());
            accountMapper.createAccount(account);
        }
    }

    @Transactional(noRollbackFor = DuplicateKeyException.class, rollbackFor = Exception.class)
    public void insertBatchAR(String accountId, List<String> rIds) {
        if(rIds.size() < 1) {
            return;
        }
        accountMapper.batchInsertAccountRole(accountId, rIds);
    }

    public List<Account> getAllAccount() {
        return accountMapper.getAllAccount();
    }

    @NonNull
    public void updAccount(Account account) {
        if(account.getUserId() == null) {
            throw new NullPointerException("更新的 user_id 不能为零！");
        }

        accountMapper.updateAccount(account);
    }

    public void delAccount(List<String> accountIds) {

        accountMapper.delAccount(accountIds);
    }

    public VoAccountRole getAccountRole(String accountId) {
        Account account = accountMapper.getAccount(accountId);
        account.setPassword(null);
        List<Role> roles = accountMapper.getRoles(accountId);
        return new VoAccountRole(account, roles);

    }
}

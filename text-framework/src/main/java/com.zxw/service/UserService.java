package com.zxw.service;

import com.zxw.auth.entity.UserInfo;
import com.zxw.mapper.BaseMapper;
import com.zxw.mapper.UserMapper;
import com.zxw.pojo.User;
import org.smart4j.framework.aop.annotation.Transactional;
import org.smart4j.framework.beans.factory.annotation.Autowire;
import org.smart4j.framework.stereotype.Service;

@Service
public class UserService {
    @Autowire
    UserMapper userMapper;

    @Transactional
    public String register() {
        User user = new User();
        user.setUsername("a");
        user.setPassword("123");
        user.setVersion(1);
        user.setStatus(1);
        user.setAge("1");
        user.setBirthday("1");
        user.setGender("1");
        user.setRegion("1");
        userMapper.insert(user);
        System.out.println("userService:注册成功");
        return "";
    }
	/*public String login(String username, String password, JwtProperties properties) {
		User user = userMapper.login(username, password);
		UserInfo info = new UserInfo();
		info.setId(Long.valueOf(user.getId()));
		info.setUsername(user.getUsername());
		info.setQx(String.valueOf(user.getStatus()));
		try {
			String token = JwtUtils.generateToken(info, properties.getPrivateKey(), properties.getExpire());
			return token;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	/*@Transaction
	public User loginUser(String username, String password) {
		User user = userMapper.login(username, password);
		return user;
	}

	public void register(User user) {
		userMapper.insert(user);
	}

	public User findById(Long id) {
		User user = userMapper.selectById(String.valueOf(id));
		return user;
	}*/

}

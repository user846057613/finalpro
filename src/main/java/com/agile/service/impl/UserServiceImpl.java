package com.agile.service.impl;

import com.agile.dao.UserDao;
import com.agile.pojo.User;
import com.agile.pojo.example.UserExample;
import com.agile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao = null;

    @Override
    public List<User> list() {
        UserExample example = new UserExample();
        return  userDao.selectByExample(example);
    }

    @Override
    public void updatePassword(int id, String password) {
        User user = get(id);
        user.setPassword(password);
        userDao.updateByPrimaryKey(user);
    }

    @Override
    public User get(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public User get(String name, String password) {
        UserExample example = new UserExample();
        example.or().andNameEqualTo(name).andPasswordEqualTo(password);
        List<User> users = userDao.selectByExample(example);
        if(users.isEmpty())
            return null;
        return users.get(0);
    }

    @Override
    public boolean isExist(String name) {
        UserExample example = new UserExample();
        example.or().andNameEqualTo(name);
        List<User> users = userDao.selectByExample(example);
        return !users.isEmpty();
    }

    @Override
    public void add(User user) {
        userDao.insert(user);
    }
}

package com.racoonberus.hotelHero.service;

import com.racoonberus.hotelHero.dao.UserDao;
import com.racoonberus.hotelHero.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User get(String username) {
        return userDao.findBy(username);
    }

    public String hash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(password.getBytes());
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }

    public void add(User user) {
        userDao.add(user);
    }

    public void edit(User user) {
        userDao.edit(user);
    }

}

package com.racoonberus.hotelHero.dao;

import com.racoonberus.hotelHero.domain.User;

public interface UserDao {

    User find(Long id);

    void add(User user);

    void edit(User user);

    void delete(User user);

    void deleteById(Long id);

    User findBy(String username);

}

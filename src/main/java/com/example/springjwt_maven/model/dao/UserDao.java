package com.example.springjwt_maven.model.dao;

import com.example.springjwt_maven.model.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDao {
    boolean existsByUsername(String username);

    User findByUsername(String username);

    void registUser(User user);
}

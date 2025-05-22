package com.example.springjwt_maven.repository;

import com.example.springjwt_maven.dto.in.RegistRequestDto;
import com.example.springjwt_maven.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDao {
    boolean existsByUsername(String username);

    UserEntity findByUsername(String username);
    UserEntity findByUserId(int userid);
    UserEntity findByUserEmail(String userEmail);

    void registUser(UserEntity userEntity);
}

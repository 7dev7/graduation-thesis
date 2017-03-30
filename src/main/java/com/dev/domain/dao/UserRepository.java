package com.dev.domain.dao;

import com.dev.domain.model.users.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {
    User findOneByLogin(String login);

    @Override
    List<User> findAll();
}

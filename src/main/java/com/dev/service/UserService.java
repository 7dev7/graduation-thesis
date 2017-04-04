package com.dev.service;

import com.dev.domain.dao.RoleRepository;
import com.dev.domain.dao.UserRepository;
import com.dev.domain.model.user.Role;
import com.dev.domain.model.user.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleManager roleManager;

    @Value("${password.size}")
    private int passwordSize;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, RoleManager roleManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleManager = roleManager;
    }

    private String getCurrentUserLogin() {
        User currentUser = getCurrentUser();
        return currentUser != null ? currentUser.getLogin() : null;
    }

    public User getUser(String login) {
        return userRepository.findOneByLogin(login);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return userRepository.findOneByLogin(authentication.getName());
        }
        return null;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public boolean addUser(String login, String pass, List<String> rolesName, String email) {
        if (userRepository.findOneByLogin(login) != null) {
            return false;
        }
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setEnabled(true);
        user.setPassword(new BCryptPasswordEncoder().encode(pass));
        user.setRoles(getRolesByAuthorityNames(rolesName));
        userRepository.save(user);
        return true;
    }

    private List<Role> getRolesByAuthorityNames(List<String> authorityNames) {
        List<Role> rolesResult = new ArrayList<>();
        if (authorityNames == null) {
            return rolesResult;
        }
        for (String authority : authorityNames) {
            Role role = roleRepository.findByAuthority(authority);
            if (role != null) {
                rolesResult.add(role);
            }
        }
        return rolesResult;
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphabetic(passwordSize);
    }

    private String generateLogin(String name, String lastName) {
        Calendar calendar = Calendar.getInstance();
        return name.trim().substring(0, 2) + lastName.trim().substring(0, 2) + calendar.get(Calendar.DAY_OF_MONTH) + calendar.get(Calendar.YEAR);
    }
}

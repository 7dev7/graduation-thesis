package com.dev.service.auth;

import com.dev.domain.model.doctor.Doctor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private Doctor doctor;

    public UserDetailsImpl(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return doctor.getRoles();
    }

    @Override
    public String getPassword() {
        return doctor.getPassword();
    }

    @Override
    public String getUsername() {
        return doctor.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return doctor.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return doctor.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return doctor.isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return doctor.isEnabled();
    }
}

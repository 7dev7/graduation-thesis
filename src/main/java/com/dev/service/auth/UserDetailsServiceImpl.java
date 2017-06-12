package com.dev.service.auth;

import com.dev.domain.model.doctor.Doctor;
import com.dev.domain.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final DoctorRepository doctorRepository;

    @Autowired
    public UserDetailsServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Doctor doctor = doctorRepository.findOneByLogin(login);
        if (doctor == null) {
            throw new UsernameNotFoundException("Doctor Not Found");
        }
        return new UserDetailsImpl(doctor);
    }
}

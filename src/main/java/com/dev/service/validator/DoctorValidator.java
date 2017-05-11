package com.dev.service.validator;

import com.dev.domain.model.doctor.Doctor;
import com.dev.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorValidator {
    public static final String SUCCESSFUL_CODE = "OK";
    private final DoctorService doctorService;

    @Autowired
    public DoctorValidator(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public String validate(Doctor doctor) {
        String msg = SUCCESSFUL_CODE;
        if (doctor.getLogin().length() < 4 || doctor.getLogin().length() > 32) {
            msg = "Длина логина должна быть от 4 до 32 символов";
        }
        if (doctorService.findByLogin(doctor.getLogin()) != null) {
            msg = "Пользователь с таким логином уже существует";
        }
        if (doctor.getPassword().length() < 6 || doctor.getPassword().length() > 32) {
            msg = "Длина пароля должна быть от 6 до 32 символов";
        }
        if (!doctor.getPasswordConfirm().equals(doctor.getPassword())) {
            msg = "Подтверждение пароля не соответствует паролю";
        }

        return msg;
    }
}

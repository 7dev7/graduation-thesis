package com.dev.service.validator;

import com.dev.domain.model.doctor.Doctor;
import com.dev.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class DoctorValidator implements Validator {

    private final DoctorService doctorService;

    @Autowired
    public DoctorValidator(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Doctor.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Doctor doctor = (Doctor) object;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "NotEmpty");
        if (doctor.getLogin().length() < 4 || doctor.getLogin().length() > 32) {
            errors.rejectValue("login", "Size");
        }
        if (doctorService.findByLogin(doctor.getLogin()) != null) {
            errors.rejectValue("login", "Duplicate");
        }
        //TODO uncomment it
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
//        if (doctor.getPassword().length() < 6 || doctor.getPassword().length() > 32) {
//            errors.rejectValue("password", "Size");
//        }

        if (!doctor.getPasswordConfirm().equals(doctor.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff");
        }
    }
}

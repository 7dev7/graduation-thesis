package com.dev.web.filter;

import com.dev.domain.model.doctor.Doctor;
import com.dev.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class CurrentUserFilter implements Filter {

    private final DoctorService doctorService;

    @Autowired
    public CurrentUserFilter(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        Doctor currentDoctor = doctorService.getCurrentDoctor();
        if (currentDoctor != null) {
            servletRequest.setAttribute("doctorLogin", currentDoctor.getLogin());
            servletRequest.setAttribute("doctorId", currentDoctor.getId());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}

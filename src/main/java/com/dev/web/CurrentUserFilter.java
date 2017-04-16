package com.dev.web;

import com.dev.domain.model.user.Doctor;
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
        Doctor currentDoctor = doctorService.getCurrentUser();
        if (currentDoctor != null) {
            servletRequest.setAttribute("doctorLogin", currentDoctor.getLogin());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}

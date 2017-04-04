package com.dev.web;

import com.dev.domain.model.user.User;
import com.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class CurrentUserFilter implements Filter {

    private final UserService userService;

    @Autowired
    public CurrentUserFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            servletRequest.setAttribute("user", currentUser.getLogin());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}

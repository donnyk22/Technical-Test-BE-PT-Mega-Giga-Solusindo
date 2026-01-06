package com.github.donnyk22.project.services.auth;

import com.github.donnyk22.project.models.dtos.UsersDto;
import com.github.donnyk22.project.models.forms.UserLoginForm;
import com.github.donnyk22.project.models.forms.UserRegisterForm;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    UsersDto register(UserRegisterForm form);
    UsersDto login(UserLoginForm form);
    Boolean logout(HttpServletRequest request);
}

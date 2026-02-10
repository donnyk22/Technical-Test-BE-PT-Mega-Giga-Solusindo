package com.github.donnyk22.project.models.mappers;

import java.util.stream.Collectors;

import com.github.donnyk22.project.models.dtos.UsersDto;
import com.github.donnyk22.project.models.entities.Users;
import com.github.donnyk22.project.models.constants.UserRoles;
import com.github.donnyk22.project.models.forms.UserRegisterForm;

public class UsersMapper {
    public static UsersDto toBaseDto(Users model){
        UsersDto dto = new UsersDto()
            .setId(model.getId())
            .setName(model.getName())
            .setEmail(model.getEmail())
            .setRole(model.getRole())
            .setCreatedAt(model.getCreatedAt());
        return dto;
    }

    public static UsersDto toDetailDto(Users model){
        UsersDto dto = toBaseDto(model);
        dto.setOrderList(model.getOrders()
            .stream()
            .map(OrdersMapper::toBaseDto)
            .collect(Collectors.toList()));
        return dto;
    }

    public static Users toEntity(UserRegisterForm form, String encryptedPassword){
        Users user = new Users()
            .setName(form.getName())
            .setEmail(form.getEmail())
            .setRole(UserRoles.USER)
            .setPassword(encryptedPassword);
        return user;
    }
}

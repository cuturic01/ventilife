package com.ftn.sbnz.service.mapper;

import com.ftn.sbnz.service.dto.UserDTO;
import com.ftn.sbnz.service.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToEntity(UserDTO userDTO);

    UserDTO entityToDto(User user);

}


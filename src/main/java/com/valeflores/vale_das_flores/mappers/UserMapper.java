package com.valeflores.vale_das_flores.mappers;

import com.valeflores.vale_das_flores.dto.UserCreateDTO;
import com.valeflores.vale_das_flores.entities.User;
import com.valeflores.vale_das_flores.entities.enums.UserType;

public class UserMapper {

	public static User toEntity(UserCreateDTO dto) {
		User user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setRole(UserType.USER);
		return user;
	}

}
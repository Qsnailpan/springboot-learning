package com.snail.convert;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.snail.commons.AbstractConverter;
import com.snail.dto.UserDto;
import com.snail.model.User;


@Component
public class UserConverter extends AbstractConverter<User, UserDto> {

	@Override
	public void copyProperties(User model, UserDto dto) {
		model.setAge(dto.getAge());
		model.setName(dto.getName());
	}
	@Override
	public UserDto toDto(User model) {
		if (Objects.isNull(model)){
			return null;
		}
		UserDto dto = new UserDto();
		dto.setName(model.getName());
		dto.setId(model.getId());
		dto.setAge(model.getAge());

		return dto;
	}

}

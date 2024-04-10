package com.ropulva.sidecars.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BaseService {
	private final ModelMapper modelMapper;

	BaseService(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public <T> T convertModelTo(Object source, Class<T> destination) {
		T result = modelMapper.map(source, destination);
		return result;

	}

}

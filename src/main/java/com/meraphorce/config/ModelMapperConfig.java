package com.meraphorce.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.meraphorce.dtos.UserRequest;
import com.meraphorce.dtos.UserResponse;
import com.meraphorce.models.User;

@Configuration
public class ModelMapperConfig {
	
    @Bean
    ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<UserRequest, User>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setEmail(source.getEmail());
            }
        });

        modelMapper.addMappings(new PropertyMap<User, UserResponse>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setEmail(source.getEmail());
            }
        });

        return modelMapper;
    }
    
}

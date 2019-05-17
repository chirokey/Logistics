package com.logistics.service;

import com.logistics.dao.interfaces.UserDAO;
import com.logistics.dto.user.AdminDTO;
import com.logistics.dto.user.UserDTO;
import com.logistics.entity.user.Admin;
import com.logistics.entity.user.User;
import com.logistics.entity.user.UserRole;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegisterService {
    private UserDAO userDAO;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(UserDAO userDAO, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        modelMapperSettings();
    }

    private void modelMapperSettings() {
        Converter<String, String> encodePassword = ctx -> passwordEncoder.encode(ctx.getSource());
        TypeMap<AdminDTO, Admin> typeMap = modelMapper.createTypeMap(AdminDTO.class, Admin.class);
        typeMap.addMappings(mapper -> mapper.using(encodePassword).map(UserDTO::getPassword, User::setPassword));
    }

    @Transactional
    public void register(AdminDTO adminDTO) {
        Admin user = modelMapper.map(adminDTO, Admin.class);
        user.setRole(UserRole.ADMIN);
        userDAO.create(user);
    }
}

package com.logistics.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterServiceTest {

    @Test
    void register() {
        /*ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("1@1.ru");
        userDTO.setFirstName("fName");
        userDTO.setLastName("lName");
        userDTO.setUsername("login");
        userDTO.setPassword("123");
        userDTO.setMatchingPassword("123");
        User user = modelMapper.map(userDTO, User.class);
        assertEquals("1@1.ru", user.getEmail());
        assertEquals("login", user.getUsername());
        assertEquals("fName", user.getFirstName());
        assertEquals("lName", user.getLastName());
        assertEquals("123", user.getPassword());
        System.out.println(user.getId());*/
    }

    @Test
    void register2() {
        /*ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = new User();
        user.setEmail("1@1.ru");
        user.setFirstName("fName");
        user.setLastName("lName");
        user.setUsername("login");
        user.setPassword("123");
        user.setId(1);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        assertEquals("1@1.ru", userDTO.getEmail());
        assertEquals("login", userDTO.getUsername());
        assertEquals("fName", userDTO.getFirstName());
        assertEquals("lName", userDTO.getLastName());
        assertEquals("123", userDTO.getPassword());
        System.out.println(userDTO.getMatchingPassword());*/
    }
}

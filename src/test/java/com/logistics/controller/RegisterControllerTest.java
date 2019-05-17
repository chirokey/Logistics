package com.logistics.controller;

import com.logistics.service.RegisterService;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class RegisterControllerTest {

    //@Test
    public void shouldShowRegistration() throws Exception {
        RegisterService mockRegisterService = mock(RegisterService.class);
        RegisterController controller = new RegisterController(mockRegisterService);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/register"))
                .andExpect(view().name("registerForm"));
    }

    //@Test
    public void shouldProcessRegistration() throws Exception {
        RegisterService mockRegisterService = mock(RegisterService.class);
        RegisterController controller =
                new RegisterController(mockRegisterService);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/register")
                .param("firsName", "Name")
                .param("lastName", "Surname")
                .param("login", "test")
                .param("password", "testpass")
                .param("email", "test@email.com"))
                .andExpect(redirectedUrl("/user/test"));
    }
}

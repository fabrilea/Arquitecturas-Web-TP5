package micro.example.gateway.test;

import micro.example.gateway.entity.User;
import micro.example.gateway.repository.AuthorityRepository;
import micro.example.gateway.repository.UserRepository;
import micro.example.gateway.service.UserService;
import micro.example.gateway.service.dto.user.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthorityRepository authorityRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testSaveUser() {
        // Datos de prueba
        String username = "testUser";
        String password = "password";
        UserDTO userDTO = new UserDTO(username, password, List.of("ROLE_USER"));
        User user = new User(username);
        user.setPassword("encodedPassword");

        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        long userId = userService.saveUser(userDTO);

        verify(userRepository).save(any(User.class));
        assertEquals(user.getId(), userId);
    }
}
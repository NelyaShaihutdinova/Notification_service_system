package junit;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import ru.ifmo.authapi.controllers.AuthenticationController;
import ru.ifmo.authapi.services.AuthenticationService;
import ru.ifmo.common.responses.UserInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authenticationService;


    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testConfirmPersonAccountSuccess() throws JWTVerificationException {
        String token = "testToken";

        doNothing().when(authenticationService).confirmAccount(anyString());

        String viewName = authenticationController.confirmPersonAccount(token, model);

        assertEquals("confirmAccount/success", viewName);
    }

    @Test
    public void testConfirmPersonAccountEmailNotFound() throws JWTVerificationException {
        String token = "testToken";
        String email = "test@example.com";

        doThrow(new UsernameNotFoundException(email)).when(authenticationService).confirmAccount(anyString());

        String viewName = authenticationController.confirmPersonAccount(token, model);

        assertEquals("confirmAccount/failed", viewName);
        verify(model, times(1)).addAttribute("emailNotFound", email);
    }



    @Test
    public void testDeleteUserSuccess() {
        when(authenticationService.deleteUser()).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        ResponseEntity<Void> responseEntity = authenticationController.deleteUser();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testAuthenticatePersonSuccess() {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("test@example.com");
        userInfo.setRole("ROLE_USER");

        when(authenticationService.authenticatePerson()).thenReturn(new ResponseEntity<>(userInfo, HttpStatus.OK));

        ResponseEntity<UserInfo> responseEntity = authenticationController.authenticatePerson();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("test@example.com", responseEntity.getBody().getEmail());
        assertEquals("ROLE_USER", responseEntity.getBody().getRole());
    }
}

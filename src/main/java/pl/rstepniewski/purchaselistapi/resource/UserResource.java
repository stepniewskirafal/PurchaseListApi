package pl.rstepniewski.purchaselistapi.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.rstepniewski.purchaselistapi.domain.User;
import pl.rstepniewski.purchaselistapi.security.jwt.JwtTokenService;
import pl.rstepniewski.purchaselistapi.services.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final UserService userService;
    private final JwtTokenService tokenService;

    public UserResource(UserService userService, JwtTokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap){
        User processedUser = userService.processUserRequestBody(userMap);
        userService.validateUser(processedUser);

        return new ResponseEntity<>(tokenService.generateJWToken(processedUser), HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap){
        User processedUser = userService.processUserRequestBody(userMap);
        userService.registerUser(processedUser);

        return new ResponseEntity<>(tokenService.generateJWToken(processedUser), HttpStatus.OK);
    }





}

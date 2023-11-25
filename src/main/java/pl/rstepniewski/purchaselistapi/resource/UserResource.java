package pl.rstepniewski.purchaselistapi.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.rstepniewski.purchaselistapi.domain.User;
import pl.rstepniewski.purchaselistapi.services.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap){
        User processedUser = userService.processUserRequestBody(userMap);
        userService.validateUser(processedUser);

        Map<String, String> responce = new HashMap<>();
        responce.put("Message", "LoggedIn successfully");
        return new ResponseEntity<>(responce, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap){
        User processedUser = userService.processUserRequestBody(userMap);
        userService.registerUser(processedUser);

        Map<String, String> responce = new HashMap<>();
        responce.put("Message", "Registered successfully");
        return new ResponseEntity<>(responce, HttpStatus.OK);
    }


}
package com.solution.hacktrail.controller;

import com.solution.hacktrail.repositories.UserRepository;
import com.solution.hacktrail.security.auth.AuthenticationService;
import com.solution.hacktrail.security.request.LoginRequest;
import com.solution.hacktrail.security.request.RegisterRequest;
import com.solution.hacktrail.security.response.MassageResponse;
import com.solution.hacktrail.security.response.RegisterResponse;
import com.solution.hacktrail.security.response.UserInfoResponse;
import com.solution.hacktrail.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody RegisterRequest request){
//
        if(userRepository.existsByUserName(request.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MassageResponse("Error: Username is already taken!"));
        }

        if(userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MassageResponse("Error: Email is already taken!"));
        }

        RegisterResponse registerResponse = authenticationService.register(request);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, registerResponse.getJwtCookie().toString())
                .body(registerResponse.getUserInfo());

    }

    @PostMapping("/signin")
    public ResponseEntity<UserInfoResponse>  authenticate(@RequestBody LoginRequest request){

        return authenticationService.authenticate(request);
    }

    @GetMapping("/username")
    public String username(Authentication authentication){
        if(authentication == null){
            return authentication.getName();
        }
        return "Not Found User";
    }

    @GetMapping("/user")
    public ResponseEntity<UserInfoResponse> currentUser(Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(item -> item.getAuthority())
                .toList();

        UserInfoResponse userInfoResponse = new UserInfoResponse(userDetails.getUserId(),userDetails.getUsername(),userDetails.getEmail(),roles);

        return ResponseEntity.ok().body(userInfoResponse);
    }


    @PostMapping("/signout")
    public ResponseEntity<String> signout(){
        return authenticationService.signOut();
    }
}

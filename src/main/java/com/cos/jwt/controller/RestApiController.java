package com.cos.jwt.controller;

import com.cos.jwt.auth.PrincipalDetails;
import com.cos.jwt.repository.UserRepository;
import com.cos.jwt.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestApiController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @GetMapping("home")
    public String home(){
        return "<h1>home</h1>";
    }

    @PostMapping("token")
    public String token(){
        return "<h1>token</h1>";
    }

    @PostMapping("join")
    public String join(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return "회원가입완료";
    }

    //user , manager, admin 권한 접근가능
    @GetMapping("/api/v1/user")
    public String user(Authentication authentication) {
        return "user";
    }

    //manager, admin 권한 접근가능
    @GetMapping("/api/v1/manager")
    public String manager(Authentication authentication) {
        return "manager";
    }

    //admin 권한 접근가능
    @GetMapping("/api/v1/admin")
    public String admin(Authentication authentication) {
        return "admin";
    }
}

package com.example.moonkey.controller;

import com.example.moonkey.dto.AccountDto;
import com.example.moonkey.service.AccountService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequestMapping("/api")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/helloAccount")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("helloAccount");
    }

    @PostMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/account");
    }

    // AccountDto를 인자로 받아서 UserService의 signup 메소드를 호출
    @PostMapping("/accountSignup")
    public ResponseEntity<AccountDto> signup(
            @Valid @RequestBody AccountDto accountDto
    ){
        return ResponseEntity.ok(accountService.signup(accountDto));
    }


    // @PreAuthorize를 통해 USER, ADMIN 두가지 권한을 모두 허용했고
    @GetMapping("/account")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<AccountDto> getMyUserInfo(HttpServletRequest request){
        return ResponseEntity.ok(accountService.getMyUserWithAuthorities());
    }

    // 해당 메소드는 ADMIN 권한만 호출할 수 있다.
    @GetMapping("/account/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<AccountDto> getUserInfo(@PathVariable String username){
        return ResponseEntity.ok(accountService.getUserWithAuthorities(username));
    }

}

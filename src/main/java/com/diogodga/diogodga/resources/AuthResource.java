package com.diogodga.diogodga.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.diogodga.diogodga.dto.EmailDTO;
import com.diogodga.diogodga.security.JWTUtil;
import com.diogodga.diogodga.security.UserSS;
import com.diogodga.diogodga.services.AuthService;
import com.diogodga.diogodga.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @ApiOperation(value="Atualiza token")
    @PostMapping(value = "/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }
    @ApiOperation(value="Retorna senha por email")
    @PostMapping(value = "/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
        service.sendNewPassword(objDto.getEmail());
        return ResponseEntity.noContent().build();
    }
}
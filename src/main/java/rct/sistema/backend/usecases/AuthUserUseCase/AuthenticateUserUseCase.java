package rct.sistema.backend.usecases.AuthUserUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rct.sistema.backend.controllers.dto.request.LoginRequestDTO;
import rct.sistema.backend.controllers.dto.response.JwtAuthResponse;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.gateways.AuthUserGateway;
import rct.sistema.backend.security.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthenticateUserUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthUserGateway authUserGateway;

    public JwtAuthResponse execute(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        AuthUser user = authUserGateway.findByUsername(loginRequest.getUsername());

        return JwtAuthResponse.builder()
                .token(jwt)
                .type("Bearer")
                .username(user.getUsername())
                .email(user.getEmail())
                .fullname(user.getFullname())
                .roles(user.getRoles())
                .build();
    }
}

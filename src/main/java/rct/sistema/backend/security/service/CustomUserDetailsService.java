package rct.sistema.backend.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.gateways.AuthUserGateway;
import rct.sistema.backend.security.jwt.UserDetailsImpl;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserGateway authUserGateway;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user;
        try {
            user = authUserGateway.findByUsername(username);
            log.info("Carregando usuário: {}", user.getUsername());

            return new UserDetailsImpl(
                    user.getUsername(),
                    user.getPassword(),
                    user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.name()))
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            log.error("Erro ao carregar usuário: {}", username, e);
            throw new UsernameNotFoundException("Usuário não encontrado com o username: " + username);
        }
    }
}

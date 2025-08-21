package rct.sistema.backend.gateways;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.exceptions.UserNotFoundException;
import rct.sistema.backend.gateways.mapper.AuthUserMapper;
import rct.sistema.backend.repositories.AuthUserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserGateway {

    private final AuthUserRepository authUserRepository;
    private final AuthUserMapper authUserMapper;

    public AuthUser save (final AuthUser authUser){
        return authUserMapper.toDomain(authUserRepository.save(authUserMapper.toEntity(authUser)));
    }

    public AuthUser findById (final Long id){
        return authUserMapper.toDomain(authUserRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    public AuthUser findByUsername(final String username) {
        return authUserMapper.toDomain(authUserRepository.findByUsername(username).orElseThrow(UserNotFoundException::new));
    }

    public AuthUser findByEmail(final String email) {
        return authUserMapper.toDomain(authUserRepository.findByEmail(email).orElseThrow(UserNotFoundException::new));
    }

    public List<AuthUser> findAll () {
        return authUserRepository.findAll().stream().map(authUserMapper::toDomain).toList();
    }

    public void deleteById(final Long id) {
        authUserRepository.deleteById(id);
    }

    public boolean existsByUsername(final String username) {
        return authUserRepository.existsByUsername(username);
    }

    public boolean existsByEmail(final String email) {
        return authUserRepository.existsByEmail(email);
    }
}

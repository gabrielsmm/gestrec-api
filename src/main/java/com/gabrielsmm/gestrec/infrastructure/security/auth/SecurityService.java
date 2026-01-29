package com.gabrielsmm.gestrec.infrastructure.security.auth;

import com.gabrielsmm.gestrec.application.port.repository.ReservaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("securityService")
@RequiredArgsConstructor
public class SecurityService {

    private final ReservaRepositoryPort reservaRepository;

    public boolean isOwner(Long reservaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null || !(auth.getPrincipal() instanceof UserDetailsImpl)) {
            return false;
        }
        Long userId = ((UserDetailsImpl) auth.getPrincipal()).getId();
        return reservaRepository.existePorIdEUsuarioId(reservaId, userId);
    }

    public boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null || !(auth.getPrincipal() instanceof UserDetailsImpl)) {
            return false;
        }
        return ((UserDetailsImpl) auth.getPrincipal()).getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
    }

}

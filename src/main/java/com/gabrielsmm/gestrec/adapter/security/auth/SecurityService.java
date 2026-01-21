package com.gabrielsmm.gestrec.adapter.security.auth;

import com.gabrielsmm.gestrec.domain.port.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("securityService")
@RequiredArgsConstructor
public class SecurityService {

    private final ReservaRepository reservaRepository;

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

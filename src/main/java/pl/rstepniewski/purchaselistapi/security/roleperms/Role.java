package pl.rstepniewski.purchaselistapi.security.roleperms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    USER (Collections.emptySet()),
    ADMIN( Set.of(
            Permissions.ADMIN_CREATE,
            Permissions.ADMIN_UPDATE,
            Permissions.ADMIN_READ,
            Permissions.ADMIN_DELETE,
            Permissions.MANAGER_CREATE,
            Permissions.MANAGER_UPDATE,
            Permissions.MANAGER_READ,
            Permissions.MANAGER_DELETE  ) ),
    MANAGER( Set.of(
            Permissions.MANAGER_CREATE,
            Permissions.MANAGER_UPDATE,
            Permissions.MANAGER_READ,
            Permissions.MANAGER_DELETE ) ),
    ;

    @Getter
    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;

    }
}

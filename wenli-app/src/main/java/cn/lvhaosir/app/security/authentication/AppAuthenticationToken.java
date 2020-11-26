package cn.lvhaosir.app.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by lvhaosir on 2018/10/12.
 */
public class AppAuthenticationToken extends AbstractAuthenticationToken {


    private final Object principal;

    private Object credentials;
    private Object type;


    public AppAuthenticationToken(Object principal, Object credentials, Object type) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.type = type;
        setAuthenticated(false);
    }
    public AppAuthenticationToken(Object principal,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }
    public AppAuthenticationToken(Object principal, Object credentials, Object type,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.type = type;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }
    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public Object getType() {
        return type;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

}

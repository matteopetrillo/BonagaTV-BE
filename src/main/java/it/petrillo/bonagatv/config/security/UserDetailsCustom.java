package it.petrillo.bonagatv.config.security;

import it.petrillo.bonagatv.models.UtenteAbbonato;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsCustom implements UserDetails {

    private UtenteAbbonato utente;

    public UserDetailsCustom(UtenteAbbonato utente) {
        this.utente = utente;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return utente.getPassword();
    }

    @Override
    public String getUsername() {
        return utente.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UtenteAbbonato getUtente() {
        return utente;
    }
}

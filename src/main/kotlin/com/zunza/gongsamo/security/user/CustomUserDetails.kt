package com.zunza.gongsamo.security.user

import com.zunza.gongsamo.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val user: User
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        TODO("Not yet implemented")
    }

    override fun getPassword(): String? {
        TODO("Not yet implemented")
    }

    override fun getUsername(): String? {
        TODO("Not yet implemented")
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}

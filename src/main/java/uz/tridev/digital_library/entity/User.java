package uz.tridev.digital_library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private String fullName;

    private String email;

    private String phone;

    @ElementCollection
    @CollectionTable(name = "user_borrowed_books", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "book_id")
    private Set<Integer> borrowedBookIds = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

}
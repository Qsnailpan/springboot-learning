package com.snail.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "user_")
public class User implements Serializable {
    private static final long serialVersionUID = 9058110335651086815L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 用户名
     */
    @Column(name = "username_", unique = true, length = 50)
    @NotBlank
    private String username;
    /**
     * 密码
     */
    @Column(name = "password_", length = 100)
    @NotBlank
    private String password;
    /**
     * 全名
     */
    @Column(name = "fullname_", length = 50)
    private String fullname;

    /**
     * 联系方式
     */
    @Column(name = "mobile_", length = 20)
    private String mobile;


    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "bf_user_role_", joinColumns = {
            @JoinColumn(name = "user_", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_", referencedColumnName = "id")})
    private List<Role> roles;


}

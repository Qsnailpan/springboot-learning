package com.snail.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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
	@NotNull
	private String username;
	/**
	 * 密码
	 */
	@Column(name = "password_", length = 100)
	@NotNull
	private String password;
	/**
	 * 全名
	 */
	@Column(name = "fullname_", length = 50)
	private String fullname;

}

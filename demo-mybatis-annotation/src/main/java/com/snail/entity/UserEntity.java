package com.snail.entity;

import java.io.Serializable;

import com.snail.enums.UserSexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String userName;
    private String passWord;
    private UserSexEnum userSex;
    private String nickName;

    public UserEntity(String userName, String passWord, UserSexEnum userSex) {
        this.passWord = passWord;
        this.userName = userName;
        this.userSex = userSex;
    }


}
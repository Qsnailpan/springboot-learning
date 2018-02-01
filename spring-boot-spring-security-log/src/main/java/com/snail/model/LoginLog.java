package com.snail.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "login_log_")
@Getter
@Setter
public class LoginLog implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 用户
     */
    @Column(name = "user_", length = 50)
    private String user;
    /**
     * 操作IP
     */
    @Column(name = "ip_", length = 100)
    private String ip;
    /**
     * 操作时间
     */
    @Column(name = "time_")
    private Date time;
    /**
     * 操作类型
     */
    @Column(name = "type_", length = 50)
    private String type;

}

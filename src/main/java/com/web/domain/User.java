package com.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;
    @Column
    private LocalDateTime createDate;

    @Column
    private LocalDateTime updateDate;

    @Builder
    public User(String name, String password, String email, LocalDateTime createDate, LocalDateTime updateDate) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}


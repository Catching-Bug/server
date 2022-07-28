package com.catchbug.server.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    private Long id;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;


}

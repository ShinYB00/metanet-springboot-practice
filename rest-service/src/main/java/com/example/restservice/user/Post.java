package com.example.restservice.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

//    @Id
//    @GeneratedValue
    private Integer id;

    private String description;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonIgnore
//    private User user;
    // 해당 유저는 jpa로 사용하는 것이므로 oracle 사용 때는 필요가 없음

    private int user_id;
}

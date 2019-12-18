package com.ga.com.eureka.eurekacenter.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private String username;
    private String userTel;
    private Integer age;
    private Long id;
}

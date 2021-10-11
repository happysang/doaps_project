package kgu.doaps.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {
    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}

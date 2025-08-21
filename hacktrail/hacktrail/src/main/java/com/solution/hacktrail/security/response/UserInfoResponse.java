package com.solution.hacktrail.security.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private Integer userId;
    private String username;
    private String email;
    private List<String> roles;
//    private String jwtToken;


}

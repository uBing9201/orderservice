package com.playdata.orderservice.user.dto;

import com.playdata.orderservice.common.entity.Address;
import com.playdata.orderservice.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResDto {

    private Long id;
    private String email;
    private String name;
    private Role role;
    private Address address;

}

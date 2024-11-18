package com.shop.gateway.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ApiCall {
    private String userId;
    private String url;
}
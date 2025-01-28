package com.teamhide.template.clients.pgclient;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApproveRequest {
    private String productId;
    private String price;
}

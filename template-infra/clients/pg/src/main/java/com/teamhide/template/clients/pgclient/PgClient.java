package com.teamhide.template.clients.pgclient;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface PgClient {
    @GetExchange("/{transactionId}")
    GetDetailResponse getDetail(@PathVariable("transactionId") final String transactionId);

    @PostExchange("/approve")
    ApproveResponse approve(@RequestBody final ApproveRequest request);
}

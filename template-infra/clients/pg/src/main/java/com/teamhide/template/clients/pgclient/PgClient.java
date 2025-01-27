package com.teamhide.template.clients.pgclient;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface PgClient {
    @GetExchange("/{transactionId}")
    String getDetail(@PathVariable("transactionId") final String transactionId);

    @PostExchange("/approve")
    String approve(final ApproveRequest request);
}

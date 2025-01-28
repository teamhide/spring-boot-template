package com.teamhide.template.clients.pgclient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.teamhide.template.clients.WireMockSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PgClientFactory.class)
class PgClientTest extends WireMockSupport {
    @Autowired private PgClient pgClient;

    @Test
    void testGetDetail() {
        // Given, When
        final GetDetailResponse response = pgClient.getDetail("1");

        // Then
        assertThat(response.getTransactionId()).isEqualTo("1234");
    }

    @Test
    void testApprove() {
        // Given
        final String price = "12000";
        final String productId = "P1234";
        final ApproveRequest request =
                ApproveRequest.builder().price(price).productId(productId).build();

        // When
        final ApproveResponse response = pgClient.approve(request);

        // Then
        assertThat(response.getTransactionId()).isEqualTo("1234");
    }
}

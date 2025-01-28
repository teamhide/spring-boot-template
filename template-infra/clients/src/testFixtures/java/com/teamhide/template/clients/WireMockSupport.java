package com.teamhide.template.clients;

import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

@AutoConfigureWireMock(port = 0, stubs = "classpath:mappings")
@ActiveProfiles("client-test")
public abstract class WireMockSupport {}

package com.duitsev.apigateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
public class ApiGatewayTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void shouldCallSavingsAccountService() {
        stubFor(get(urlEqualTo("/balance"))
                .willReturn(aResponse()
                        .withBody("{\"balance\":\"100\"}")));

        webClient
                .get().uri("/savings/a/balance")
                .headers(headers -> headers.setBasicAuth("user", "pass"))
                .exchange()
                .expectStatus().isOk();

        webClient
                .get().uri("/savings/b/balance")
                .headers(headers -> headers.setBasicAuth("user", "pass"))
                .exchange()
                .expectStatus().isOk();

        webClient
                .get().uri("/savings/c/balance")
                .headers(headers -> headers.setBasicAuth("user", "pass"))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void shouldReturn504IfTimeoutIsReached() {
        stubFor(get(urlEqualTo("/balance"))
                .willReturn(aResponse()
                        .withFixedDelay(1100)
                        .withBody("{\"balance\":\"100\"}")));

        webClient
                .get().uri("/savings/a/balance")
                .headers(headers -> headers.setBasicAuth("user", "pass"))
                .exchange()
                .expectStatus().isEqualTo(504);
    }

    @Test
    public void shouldReturn401IfBasicAuthNotProvided() {
        webClient
                .get().uri("/savings/b/balance")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void shouldReturn401IfCredentialAreIncorrect() {
        webClient
                .get().uri("/savings/b/balance")
                .headers(headers -> headers.setBasicAuth("user", "incorrect_pass"))
                .exchange()
                .expectStatus().isUnauthorized();
    }
}

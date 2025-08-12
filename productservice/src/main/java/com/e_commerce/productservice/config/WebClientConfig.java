//package com.e_commerce.productservice.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.reactive.function.client.ClientRequest;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Configuration
//public class WebClientConfig {
//
//    @Bean
//    public WebClient userServiceWebClient(
//            WebClient.Builder builder,
//            @Value("${userservice.base-url}") String baseUrl) {
//
//        return builder
//                .baseUrl(baseUrl)
//                .filter((request, next) -> {
//                    var attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//                    String auth = (attrs != null) ? attrs.getRequest().getHeader("Authorization") : null;
//
//                    ClientRequest mutated = ClientRequest.from(request)
//                            .headers(h -> { if (auth != null && !auth.isBlank()) h.add("Authorization", auth); })
//                            .build();
//
//                    return next.exchange(mutated);
//                })
//                .build();
//    }
//}

package com.e_commerce.productservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(req -> {
            System.out.println("[WebClient →] " + req.method() + " " + req.url());
            System.out.println("[WebClient →] Authorization present? " + (req.headers().getFirst("Authorization") != null));
            return Mono.just(req); // <-- was CompletableFuture.completedFuture(req)
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(res -> {
            System.out.println("[WebClient ←] status: " + res.statusCode());
            return Mono.just(res); // <-- was CompletableFuture.completedFuture(res)
        });
    }

    @Bean
    public WebClient userServiceWebClient(WebClient.Builder builder,
                                          @Value("${userservice.base-url}") String baseUrl) {

        return builder
                .baseUrl(baseUrl)
                // simple request/response log to debug 403s
                .filter(logRequest())
                .filter(logResponse())
                .filter((request, next) -> {
                    var attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                    String headerCandidate = null;
                    if (attrs != null) {
                        Object fromAttr = attrs.getRequest().getAttribute("AUTH_HEADER");
                        if (fromAttr != null) headerCandidate = fromAttr.toString();

                        if (headerCandidate == null || headerCandidate.isBlank()) {
                            headerCandidate = attrs.getRequest().getHeader("Authorization");
                        }
                    }

                    if (headerCandidate == null || headerCandidate.isBlank()) {
                        var a = SecurityContextHolder.getContext().getAuthentication();
                        if (a instanceof JwtAuthenticationToken jwtAuth) {
                            headerCandidate = "Bearer " + jwtAuth.getToken().getTokenValue();
                        }
                    }

                    final String authHeaderFinal = headerCandidate; // <-- effectively final for lambda

                    ClientRequest mutated = ClientRequest.from(request)
                            .headers(h -> {
                                if (authHeaderFinal != null && !authHeaderFinal.isBlank()) {
                                    h.set("Authorization", authHeaderFinal);
                                }
                            })
                            .build();

                    return next.exchange(mutated);
                })


                .build();
    }
}

package micro.example.gateway.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import micro.example.gateway.security.jwt.JwtFilter;
import micro.example.gateway.security.jwt.TokenProvider;
import micro.example.gateway.service.dto.login.LoginDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/authenticate")
@RequiredArgsConstructor
public class JwtController {

    private final TokenProvider tokenProvider;
    private final ReactiveAuthenticationManager reactiveAuthenticationManager;

    @PostMapping
    public Mono<ResponseEntity<JWTToken>> authorize(@Valid @RequestBody LoginDTO request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        return reactiveAuthenticationManager
                .authenticate(authenticationToken)
                .flatMap(authentication -> {
                    ReactiveSecurityContextHolder.getContext()
                            .map(securityContext -> {
                                securityContext.setAuthentication(authentication);
                                return securityContext;
                            });

                    String jwt = tokenProvider.createToken(authentication);
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

                    return Mono.just(new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK));
                });
    }

    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        public String getIdToken() {
            return idToken;
        }

        public void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}

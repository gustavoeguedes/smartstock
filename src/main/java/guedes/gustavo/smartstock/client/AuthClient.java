package guedes.gustavo.smartstock.client;

import guedes.gustavo.smartstock.client.dto.AuthRequest;
import guedes.gustavo.smartstock.client.dto.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AuthClient", url = "${api.auth-url}")
public interface AuthClient {
    @PostMapping(path = "/api/token")
    ResponseEntity<AuthResponse> authentidate(@RequestBody AuthRequest authRequest);
}

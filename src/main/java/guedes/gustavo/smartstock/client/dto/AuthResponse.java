package guedes.gustavo.smartstock.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponse(@JsonProperty("access_token") String accessToken,
                           @JsonProperty("expires_in") Integer expiresIn) {
}

package co.istad.ebanking.dto;

import lombok.Builder;
import lombok.Data;


@Builder
public record AuthenticationResponse(
       Boolean isAuthenticated
) {
}

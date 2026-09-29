package co.istad.pipeline.client.dto;

import lombok.Builder;

@Builder
public record UserResponse(
        Integer id,
        String name,
        String username,
        String email
) {
}

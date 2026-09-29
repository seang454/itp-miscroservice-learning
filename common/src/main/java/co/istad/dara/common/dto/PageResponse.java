package co.istad.dara.common.dto;

import lombok.Builder;

@Builder
public record PageResponse(
        Object data,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
}

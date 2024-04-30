package by.bsuir.dorm.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public record PageResponse<T>(
        long totalElements,
        long totalPages,
        List<T>  content
) {
    public static <T> PageResponse<T> create(Page<T> page) {
        return new PageResponse<>(page.getTotalElements(),
                page.getTotalPages(),
                page.getContent());
    }

    public static <E, T> PageResponse<T> create(Page<E> page, Function<E, T> contentElementMapper) {
        return new PageResponse<>(page.getTotalElements(),
                page.getTotalPages(),
                page.get()
                        .map(contentElementMapper)
                        .collect(Collectors.toList()));
    }
}

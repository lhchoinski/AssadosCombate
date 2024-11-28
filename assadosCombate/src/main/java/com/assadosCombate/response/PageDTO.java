package com.assadosCombate.response;

import com.assadosCombate.dtos.groups.AppGroup;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public class PageDTO<E, DTO> {

    @JsonView({AppGroup.ResponsePage.class})
    private int itemsPerPage;

    @JsonView({AppGroup.ResponsePage.class})
    private int currentPage;

    @JsonView({AppGroup.ResponsePage.class})
    private int totalPages;

    @JsonView({AppGroup.ResponsePage.class})
    private Long totalItems;

    @JsonView({AppGroup.ResponsePage.class})
    private List<DTO> data;

    public PageDTO(Page<E> page, Function<E, DTO> mapper){
        this.itemsPerPage = page.getSize();
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.totalItems = page.getTotalElements();
        this.data = page.getContent()
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}

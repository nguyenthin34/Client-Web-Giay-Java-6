package com.assignment.DTO;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizesDTO {
    private Integer sizeId;
    @NotEmpty
    private String name;
    @NotNull
    private Boolean status;
}

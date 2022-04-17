package com.assignment.DTO;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorsDTO implements Serializable {
    private Integer colorId;
    @NotEmpty
    private String name;
    @NotNull
    private Boolean status;
}

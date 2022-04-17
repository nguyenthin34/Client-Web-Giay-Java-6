package com.assignment.DTO;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesDTO implements Serializable {
    private Integer categoryId;
    @NotEmpty
    @Length(min = 4)
    private String name;
    @NotNull
    private Boolean status;
    private Boolean isEdit;
}

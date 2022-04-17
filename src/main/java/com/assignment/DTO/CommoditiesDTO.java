package com.assignment.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommoditiesDTO {
    private Integer commodityId;
    private String name;
    private MultipartFile imageFile;
    private String image;
    private Integer category_id;
    private Boolean status;

}

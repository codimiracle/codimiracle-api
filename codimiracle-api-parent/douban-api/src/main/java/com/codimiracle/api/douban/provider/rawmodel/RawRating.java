package com.codimiracle.api.douban.provider.rawmodel;

import lombok.Data;

@Data
public class RawRating {
    private Integer count;
    private String ratingInfo;
    private Double starCount;
    private Double value;
}

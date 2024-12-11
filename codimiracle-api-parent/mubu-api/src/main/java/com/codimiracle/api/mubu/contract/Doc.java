package com.codimiracle.api.mubu.contract;

import lombok.Data;

@Data
public class Doc {
    private String id;
    private String folderId;
    private String name;
    private Integer userId;
    private Long createTime;
    private Long updateTime;
    private Integer itemCount;
    private Integer deleted;
    private Integer deleteTime;
    private String shareId;
    private String shareRememberId;
    private Integer switched;
    private Integer encrypted;
    private Integer stared;
    private Integer starIndex;
    private Integer viewCount;
    private Long version;
    private String commitClient;
    private Integer syncState;
    private Integer type;
    private Integer praiseCount;
    private Integer commentCount;
    private Integer rewardCount;
    private Integer collectCount;
}

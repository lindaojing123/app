package com.code.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "yx_video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id
    private String id;

    private String title;

    private String description;

    @Column(name = "video_path")
    private String videoPath;

    @Column(name = "cover_path")
    private String coverPath;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_time")
    private Date createTime;

    private User user;
    private Category category;

    @Column(name = "group_id")
    private String groupId;

    @Transient
    private Integer playCount;  //播放次数

    @Transient
    private Integer likeCount; //点赞次数

}
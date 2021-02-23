package com.code.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "yx_play")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Play {
    @Id
    private String id;

    @Column(name = "video_id")
    private String videoId;

    @Column(name = "play_count")
    private Integer playCount;


}
package com.nastudy.stubox.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoxSearchCond {
    private Long memberId;
    private Long teamId;
}

package com.nastudy.stubox.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TestCond {
    private Long memberId;
    private Long selectBoxId;
    private int level;
}

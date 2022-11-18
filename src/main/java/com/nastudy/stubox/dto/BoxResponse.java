package com.nastudy.stubox.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class BoxResponse {
    private List<BoxDto> boxes = new ArrayList<>();
    public Long selectBoxId;
}

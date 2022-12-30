package com.nastudy.stubox.dto;

import com.nastudy.stubox.domain.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class TagDto {
    private Long id;
    private String name;

    public static TagDto of(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }
}

package com.nastudy.stubox.domain.entity;

import com.nastudy.stubox.domain.Category;
import com.nastudy.stubox.domain.CategorySetConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.EnumSet;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Team extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Convert(converter = CategorySetConverter.class)
    EnumSet<Category> categories;

    @Builder
    public Team(Long id, String name, EnumSet<Category> categories) {
        this.id = id;
        this.name = name;
        this.categories = categories;
    }
}

package com.nastudy.stubox.controller.form;

import com.nastudy.stubox.domain.Category;
import com.nastudy.stubox.domain.entity.Team;
import lombok.Data;

import java.util.EnumSet;

@Data
public class TeamSaveForm {
    private String name;
    private EnumSet<Category> categories;

    public Team toTeam() {
        return Team.builder().name(this.name).build();
    }
}

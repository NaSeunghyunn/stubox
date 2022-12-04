package com.nastudy.stubox.controller.form;

import com.nastudy.stubox.domain.Category;
import com.nastudy.stubox.domain.entity.Team;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.EnumSet;

@Data
public class TeamSaveForm {
    @NotBlank
    private String name;
    @NotEmpty
    private EnumSet<Category> categories;

    public Team toTeam() {
        return Team.builder().name(this.name).build();
    }
}

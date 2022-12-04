package com.nastudy.stubox.controller.form;

import com.nastudy.stubox.domain.Category;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.EnumSet;

@Data
public class TeamUpdateForm {
    @NotEmpty
    private EnumSet<Category> categories;
}

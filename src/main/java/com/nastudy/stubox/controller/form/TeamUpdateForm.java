package com.nastudy.stubox.controller.form;

import com.nastudy.stubox.domain.Category;
import lombok.Data;

import java.util.EnumSet;

@Data
public class TeamUpdateForm {
    private EnumSet<Category> categories;
}

package com.nastudy.stubox.dto;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoxSearchCond {
    private Long memberId;
    private Long teamId;

    public static BoxSearchCond of(PrincipalDetail principal){
        return new BoxSearchCond(principal.getId(), principal.getTeamId());
    }
}

package chloe.movietalk.dto.response.user;

import chloe.movietalk.domain.SiteUser;
import chloe.movietalk.domain.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfo {

    private Long id;
    private String email;
    private String nickname;
    private UserRole role;

    @Builder
    public UserInfo(Long id, String email, String nickname, UserRole role) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
    }

    public static UserInfo fromEntity(SiteUser user) {
        return UserInfo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();
    }
}
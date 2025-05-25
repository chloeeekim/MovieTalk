package chloe.movietalk.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.index.Indexed;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@RedisHash(value = "refresh", timeToLive = 1209600) // 2주
public class Refresh {
    @Id
    private Long userId;

    @Indexed
    private String refreshToken;

    @Builder
    public Refresh(Long userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }
}

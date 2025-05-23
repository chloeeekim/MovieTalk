package chloe.movietalk.dto.request;

import chloe.movietalk.domain.Review;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateReviewRequest {

    @NotNull(message = "평점이 입력되지 않았습니다.")
    private Double rating;

    private String comment;

    @Builder
    public UpdateReviewRequest(Double rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public Review toEntity() {
        return Review.builder()
                .rating(this.rating)
                .comment(this.comment)
                .build();
    }
}

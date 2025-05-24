package chloe.movietalk.dto.response.review;

import chloe.movietalk.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewByMovieResponse {

    @Schema(description = "리뷰 ID")
    private Long id;

    @Schema(description = "평점")
    private Double rating;

    @Schema(description = "코멘트")
    private String comment;

    @Builder
    public ReviewByMovieResponse(Long id, Double rating, String comment) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
    }

    public static ReviewByMovieResponse fromEntity(Review review) {
        return ReviewByMovieResponse.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .build();
    }
}

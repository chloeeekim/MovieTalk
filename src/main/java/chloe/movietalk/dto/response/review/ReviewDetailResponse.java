package chloe.movietalk.dto.response.review;

import chloe.movietalk.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewDetailResponse {

    @Schema(description = "리뷰 ID")
    private Long id;

    @Schema(description = "평점")
    private Double rating;

    @Schema(description = "코멘트")
    private String comment;

    @Schema(description = "영화 ID")
    private Long movieId;

    @Builder
    public ReviewDetailResponse(Long id, Double rating, String comment, Long movieId) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.movieId = movieId;
    }

    public static ReviewDetailResponse fromEntity(Review review) {
        return ReviewDetailResponse.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .movieId(review.getMovie().getId())
                .build();
    }
}

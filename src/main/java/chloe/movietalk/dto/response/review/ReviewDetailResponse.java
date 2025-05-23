package chloe.movietalk.dto.response.review;

import chloe.movietalk.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewDetailResponse {

    private Long id;
    private Double rating;
    private String comment;
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

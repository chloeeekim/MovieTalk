package chloe.movietalk.dto.response.review;

import chloe.movietalk.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewByMovieResponse {

    private Long id;
    private Double rating;
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

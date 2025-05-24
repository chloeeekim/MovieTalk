package chloe.movietalk.dto.response.review;

import chloe.movietalk.domain.Review;
import chloe.movietalk.dto.response.movie.MovieInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewByUserResponse {

    @Schema(description = "리뷰 ID")
    private Long id;

    @Schema(description = "평점")
    private Double rating;

    @Schema(description = "코멘트")
    private String comment;

    @Schema(description = "영화 정보")
    private MovieInfo movieInfo;

    @Builder
    public ReviewByUserResponse(Long id, Double rating, String comment, MovieInfo movieInfo) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.movieInfo = movieInfo;
    }

    public static ReviewByUserResponse fromEntity(Review review) {
        return ReviewByUserResponse.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .movieInfo(MovieInfo.fromEntity(review.getMovie()))
                .build();
    }
}

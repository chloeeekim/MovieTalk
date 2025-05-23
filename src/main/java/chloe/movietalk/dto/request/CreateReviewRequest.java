package chloe.movietalk.dto.request;

import chloe.movietalk.domain.Movie;
import chloe.movietalk.domain.Review;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateReviewRequest {

    @NotNull(message = "평점이 입력되지 않았습니다.")
    private Double rating;

    private String comment;

    @NotNull(message = "영화가 입력되지 않았습니다.")
    private Long movieId;

    @Builder
    public CreateReviewRequest(Double rating, String comment, Long movieId) {
        this.rating = rating;
        this.comment = comment;
        this.movieId = movieId;
    }

    public Review toEntity(Movie movie) {
        return Review.builder()
                .rating(this.rating)
                .comment(this.comment)
                .movie(movie)
                .build();
    }
}

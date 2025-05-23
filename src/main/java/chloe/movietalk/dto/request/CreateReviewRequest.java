package chloe.movietalk.dto.request;

import chloe.movietalk.common.HalfPointStep;
import chloe.movietalk.domain.Movie;
import chloe.movietalk.domain.Review;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateReviewRequest {

    @NotNull(message = "평점이 입력되지 않았습니다.")
    @DecimalMin(value = "0.5", message = "평점은 0.5점 이상이어야 합니다.")
    @DecimalMax(value = "5.0", message = "평점은 5.0점 이하여야 합니다.")
    @Digits(integer = 1, fraction = 1, message = "평점은 소수점 아래 한자리수여야 합니다.")
    @HalfPointStep
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

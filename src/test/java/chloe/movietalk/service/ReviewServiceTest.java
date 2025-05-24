package chloe.movietalk.service;

import chloe.movietalk.domain.Movie;
import chloe.movietalk.domain.Review;
import chloe.movietalk.dto.request.CreateReviewRequest;
import chloe.movietalk.dto.request.UpdateReviewRequest;
import chloe.movietalk.repository.MovieRepository;
import chloe.movietalk.repository.ReviewRepository;
import chloe.movietalk.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    ReviewService reviewService;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    MovieRepository movieRepository;

    @BeforeEach
    public void beforeEach() {
        reviewService = new ReviewServiceImpl(reviewRepository, movieRepository);
    }

    @Test
    @DisplayName("리뷰 등록 시 영화 평점 업데이트 확인")
    public void updateMovieRatingWhenReviewIsCreated() {
        // given
        Movie movie = Movie.builder()
                .title("테스트용 영화")
                .codeFIMS("123123")
                .build();

        Review review = Review.builder()
                .rating(3.5)
                .comment("좋은 영화입니다.")
                .movie(movie)
                .build();

        given(movieRepository.findById(1L))
                .willReturn(Optional.of(movie));

        given(reviewRepository.save(any(Review.class)))
                .willReturn(review);

        // when
        reviewService.createReview(new CreateReviewRequest(3.5, "좋은 영화입니다.", 1L));

        // then
        assertThat(movie.getTotalRating()).isEqualTo(3.5);
        assertThat(movie.getAverageRating()).isEqualTo(3.5);
        assertThat(movie.getReviewCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("리뷰 업데이트 시 영화 평점 업데이트 확인")
    public void updateMovieRatingWhenReviewIsUpdated() {
        // given
        Movie movie = Movie.builder()
                .title("테스트용 영화")
                .codeFIMS("123123")
                .totalRating(3.5)
                .reviewCount(1)
                .build();

        Review review = Review.builder()
                .rating(3.5)
                .comment("좋은 영화입니다.")
                .movie(movie)
                .build();

        given(reviewRepository.findById(1L))
                .willReturn(Optional.of(review));

        // when
        reviewService.updateReview(1L, new UpdateReviewRequest(5.0, "훌륭한 영화입니다."));

        // then
        assertThat(movie.getTotalRating()).isEqualTo(5.0);
        assertThat(movie.getAverageRating()).isEqualTo(5.0);
        assertThat(movie.getReviewCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("리뷰 삭제 시 영화 평점 업데이트 확인")
    public void updateMovieRatingWhenReviewIsDeleted() {
        // given
        Movie movie = Movie.builder()
                .title("테스트용 영화")
                .codeFIMS("123123")
                .totalRating(3.5)
                .reviewCount(1)
                .build();

        Review review = Review.builder()
                .rating(3.5)
                .comment("좋은 영화입니다.")
                .movie(movie)
                .build();

        given(reviewRepository.findById(1L))
                .willReturn(Optional.of(review));

        // when
        reviewService.deleteReview(1L);

        // then
        assertThat(movie.getTotalRating()).isEqualTo(0.0);
        assertThat(movie.getAverageRating()).isEqualTo(0.0);
        assertThat(movie.getReviewCount()).isEqualTo(0);
    }
}

package chloe.movietalk.repository;

import chloe.movietalk.domain.Movie;
import chloe.movietalk.domain.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MovieRepository movieRepository;

    @Test
    @DisplayName("리뷰 등록")
    public void createReview() {
        // given
        Movie movie = Movie.builder()
                .title("테스트용 영화")
                .codeFIMS("123123")
                .build();
        movieRepository.save(movie);

        Review review = Review.builder()
                .rating(3.5)
                .comment("좋은 영화입니다!")
                .movie(movie)
                .build();

        // when
        Review save = reviewRepository.save(review);

        // then
        assertThat(save).isEqualTo(review);
    }

    @Test
    @DisplayName("리뷰 목록 불러오기 : 영화 기준")
    public void reviewListByMovie() {
        // given
        Movie movie = Movie.builder()
                .title("테스트용 영화")
                .codeFIMS("123123")
                .build();
        movieRepository.save(movie);

        Review review = Review.builder()
                .rating(3.5)
                .comment("좋은 영화입니다!")
                .movie(movie)
                .build();
        reviewRepository.save(review);

        // when
        List<Review> reviewList = reviewRepository.findByMovieId(movie.getId());

        // then
        assertThat(reviewList).containsOnly(review);
    }
}

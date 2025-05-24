package chloe.movietalk.repository;

import chloe.movietalk.domain.Movie;
import chloe.movietalk.domain.Review;
import chloe.movietalk.domain.SiteUser;
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

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("리뷰 등록")
    public void createReview() {
        // given
        Movie movie = Movie.builder()
                .title("테스트용 영화")
                .codeFIMS("123123")
                .build();
        movieRepository.save(movie);
        SiteUser user = SiteUser.builder()
                .email("test@movietalk.com")
                .passwordHash("1234")
                .nickname("test")
                .build();
        userRepository.save(user);

        Review review = Review.builder()
                .rating(3.5)
                .comment("좋은 영화입니다!")
                .movie(movie)
                .user(user)
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
        SiteUser user = SiteUser.builder()
                .email("test@movietalk.com")
                .passwordHash("1234")
                .nickname("test")
                .build();
        userRepository.save(user);

        Review review = Review.builder()
                .rating(3.5)
                .comment("좋은 영화입니다!")
                .movie(movie)
                .user(user)
                .build();
        reviewRepository.save(review);

        // when
        List<Review> reviewList = reviewRepository.findByMovieId(movie.getId());

        // then
        assertThat(reviewList).containsOnly(review);
    }

    @Test
    @DisplayName("리뷰 목록 불러오기 : 사용자 기준")
    public void reviewListByUser() {
        // given
        Movie movie = Movie.builder()
                .title("테스트용 영화")
                .codeFIMS("123123")
                .build();
        movieRepository.save(movie);
        SiteUser user = SiteUser.builder()
                .email("test@movietalk.com")
                .passwordHash("1234")
                .nickname("test")
                .build();
        userRepository.save(user);

        Review review = Review.builder()
                .rating(3.5)
                .comment("좋은 영화입니다!")
                .movie(movie)
                .user(user)
                .build();
        reviewRepository.save(review);

        // when
        List<Review> reviewList = reviewRepository.findByUserId(user.getId());

        // then
        assertThat(reviewList).containsOnly(review);
    }
}

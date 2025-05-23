package chloe.movietalk.controller;

import chloe.movietalk.domain.Movie;
import chloe.movietalk.domain.Review;
import chloe.movietalk.dto.request.CreateReviewRequest;
import chloe.movietalk.dto.request.UpdateReviewRequest;
import chloe.movietalk.exception.global.GlobalErrorCode;
import chloe.movietalk.exception.movie.MovieErrorCode;
import chloe.movietalk.exception.review.ReviewErrorCode;
import chloe.movietalk.repository.MovieRepository;
import chloe.movietalk.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ReviewControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MovieRepository movieRepository;

    @Test
    @DisplayName("리뷰 등록")
    public void createReview() throws Exception {
        // given
        Movie movie = Movie.builder()
                .title("테스트 영화 제목")
                .codeFIMS("123123")
                .build();
        movieRepository.save(movie);

        CreateReviewRequest request = CreateReviewRequest.builder()
                .rating(3.5)
                .comment("좋은 영화입니다.")
                .movieId(movie.getId())
                .build();

        // when
        ResultActions resultActions = mvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("data.rating").value(request.getRating()))
                .andExpect(jsonPath("data.comment").value(request.getComment()));
    }

    @Test
    @DisplayName("리뷰 등록 실패 : 평점 미입력")
    public void createReviewFailure1() throws Exception {
        // given
        Movie movie = Movie.builder()
                .title("테스트 영화 제목")
                .codeFIMS("123123")
                .build();
        movieRepository.save(movie);

        CreateReviewRequest request = CreateReviewRequest.builder()
                .comment("좋은 영화입니다.")
                .movieId(movie.getId())
                .build();

        // when
        ResultActions resultActions = mvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        GlobalErrorCode errorCode = GlobalErrorCode.INVALID_FIELD_VALUE;
        resultActions
                .andExpect(status().is(errorCode.getStatus()))
                .andExpect(jsonPath("code").value(errorCode.getCode()));
    }

    @Test
    @DisplayName("리뷰 등록 실패 : 영화 아이디 미입력")
    public void createReviewFailure2() throws Exception {
        // given
        CreateReviewRequest request = CreateReviewRequest.builder()
                .rating(3.5)
                .comment("좋은 영화입니다.")
                .build();

        // when
        ResultActions resultActions = mvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        GlobalErrorCode errorCode = GlobalErrorCode.INVALID_FIELD_VALUE;
        resultActions
                .andExpect(status().is(errorCode.getStatus()))
                .andExpect(jsonPath("code").value(errorCode.getCode()));
    }

    @Test
    @DisplayName("리뷰 등록 실패 : 존재하지 않는 영화")
    public void createReviewFailure3() throws Exception {
        // given
        CreateReviewRequest request = CreateReviewRequest.builder()
                .rating(3.5)
                .comment("좋은 영화입니다.")
                .movieId(1L)
                .build();

        // when
        ResultActions resultActions = mvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        MovieErrorCode errorCode = MovieErrorCode.MOVIE_NOT_FOUND;
        resultActions
                .andExpect(status().is(errorCode.getStatus()))
                .andExpect(jsonPath("code").value(errorCode.getCode()));
    }

    @Test
    @DisplayName("리뷰 수정")
    public void updateReview() throws Exception {
        // given
        Movie movie = Movie.builder()
                .title("테스트 영화 제목")
                .codeFIMS("123123")
                .build();
        movieRepository.save(movie);
        Review review = Review.builder()
                .rating(3.5)
                .comment("좋은 영화입니다.")
                .movie(movie)
                .build();
        reviewRepository.save(review);

        UpdateReviewRequest request = UpdateReviewRequest.builder()
                .rating(4.5)
                .comment("대단한 영화입니다.")
                .build();

        // when
        ResultActions resultActions = mvc.perform(put("/api/reviews/{id}", review.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.rating").value(request.getRating()))
                .andExpect(jsonPath("data.comment").value(request.getComment()));
    }

    @Test
    @DisplayName("리뷰 수정 실패 : 존재하지 않는 리뷰")
    public void updateReviewFailure1() throws Exception {
        // given
        UpdateReviewRequest request = UpdateReviewRequest.builder()
                .rating(4.5)
                .comment("대단한 영화입니다.")
                .build();

        // when
        ResultActions resultActions = mvc.perform(put("/api/reviews/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        ReviewErrorCode errorCode = ReviewErrorCode.REVIEW_NOT_FOUND;
        resultActions
                .andExpect(status().is(errorCode.getStatus()))
                .andExpect(jsonPath("code").value(errorCode.getCode()));
    }

    @Test
    @DisplayName("리뷰 수정 실패 : 평점 미입력")
    public void updateReviewFailure2() throws Exception {
        // given
        Movie movie = Movie.builder()
                .title("테스트 영화 제목")
                .codeFIMS("123123")
                .build();
        movieRepository.save(movie);
        Review review = Review.builder()
                .rating(3.5)
                .comment("좋은 영화입니다.")
                .movie(movie)
                .build();
        reviewRepository.save(review);

        UpdateReviewRequest request = UpdateReviewRequest.builder()
                .comment("대단한 영화입니다.")
                .build();

        // when
        ResultActions resultActions = mvc.perform(put("/api/reviews/{id}", review.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        GlobalErrorCode errorCode = GlobalErrorCode.INVALID_FIELD_VALUE;
        resultActions
                .andExpect(status().is(errorCode.getStatus()))
                .andExpect(jsonPath("code").value(errorCode.getCode()));
    }

    @Test
    @DisplayName("리뷰 삭제")
    public void deleteReview() throws Exception {
        // given
        Movie movie = Movie.builder()
                .title("테스트 영화 제목")
                .codeFIMS("123123")
                .build();
        movieRepository.save(movie);

        Review review = Review.builder()
                .rating(3.5)
                .comment("좋은 영화입니다.")
                .movie(movie)
                .build();
        reviewRepository.save(review);

        // when
        ResultActions resultActions = mvc.perform(delete("/api/reviews/{id}", review.getId()));

        // then
        resultActions
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("리뷰 삭제 실패 : 존재하지 않는 리뷰")
    public void deleteReviewFailure1() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(delete("/api/reviews/{id}", 1L));

        // then
        ReviewErrorCode errorCode = ReviewErrorCode.REVIEW_NOT_FOUND;
        resultActions
                .andExpect(status().is(errorCode.getStatus()))
                .andExpect(jsonPath("code").value(errorCode.getCode()));
    }

    @Test
    @DisplayName("리뷰 목록 불러오기 : 영화 기준")
    public void getReviewsByMovie() throws Exception {
        // given
        Movie movie = Movie.builder()
                .title("테스트 영화 제목")
                .codeFIMS("123123")
                .build();
        movieRepository.save(movie);
        Review review = Review.builder()
                .rating(3.5)
                .comment("좋은 영화입니다.")
                .movie(movie)
                .build();
        reviewRepository.save(review);

        // when
        ResultActions resultActions = mvc.perform(get("/api/reviews/movies/{id}", movie.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(1)))
                .andExpect(jsonPath("data[0].rating").value(review.getRating()))
                .andExpect(jsonPath("data[0].comment").value(review.getComment()));
    }

    @Test
    @DisplayName("영화 기준 리뷰 목록 불러오기 실패 : 존재하지 않는 영화")
    public void getReviewsByMovieFailure() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/api/reviews/movies/{id}", 1L));

        // then
        MovieErrorCode errorCode = MovieErrorCode.MOVIE_NOT_FOUND;
        resultActions
                .andExpect(status().is(errorCode.getStatus()))
                .andExpect(jsonPath("code").value(errorCode.getCode()));
    }
}

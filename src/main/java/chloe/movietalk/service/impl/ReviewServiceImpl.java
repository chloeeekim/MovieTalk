package chloe.movietalk.service.impl;

import chloe.movietalk.domain.Movie;
import chloe.movietalk.domain.Review;
import chloe.movietalk.domain.ReviewLike;
import chloe.movietalk.domain.SiteUser;
import chloe.movietalk.dto.request.CreateReviewRequest;
import chloe.movietalk.dto.request.UpdateReviewRequest;
import chloe.movietalk.dto.response.review.ReviewByMovieResponse;
import chloe.movietalk.dto.response.review.ReviewByUserResponse;
import chloe.movietalk.dto.response.review.ReviewDetailResponse;
import chloe.movietalk.exception.auth.UserNotFoundException;
import chloe.movietalk.exception.movie.MovieNotFoundException;
import chloe.movietalk.exception.review.AlreadyLikedReviewException;
import chloe.movietalk.exception.review.ReviewNotFoundException;
import chloe.movietalk.exception.review.ReviewlikeNotFoundException;
import chloe.movietalk.repository.MovieRepository;
import chloe.movietalk.repository.ReviewLikeRepository;
import chloe.movietalk.repository.ReviewRepository;
import chloe.movietalk.repository.UserRepository;
import chloe.movietalk.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    @Override
    public List<ReviewByMovieResponse> getAllReviewsByMovie(Long movieId) {
        movieRepository.findById(movieId)
                .orElseThrow(() -> MovieNotFoundException.EXCEPTION);

        return reviewRepository.findByMovieId(movieId).stream()
                .map(ReviewByMovieResponse::fromEntity)
                .toList();
    }

    @Override
    public List<ReviewByUserResponse> getAllReviewsByUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        return reviewRepository.findByUserId(userId).stream()
                .map(ReviewByUserResponse::fromEntity)
                .toList();
    }

    @Override
    public ReviewDetailResponse createReview(CreateReviewRequest request) {
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> MovieNotFoundException.EXCEPTION);
        SiteUser user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        Review save = reviewRepository.save(request.toEntity(movie, user));

        movie.updateTotalRating(movie.getTotalRating() + request.getRating());
        movie.updateReviewCount(movie.getReviewCount() + 1);

        return ReviewDetailResponse.fromEntity(save);
    }

    @Override
    public ReviewDetailResponse updateReview(Long id, UpdateReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> ReviewNotFoundException.EXCEPTION);

        Double oldRating = review.getRating();

        review.updateReview(request.toEntity());

        Movie movie = review.getMovie();
        movie.updateTotalRating(movie.getTotalRating() - oldRating + request.getRating());

        return ReviewDetailResponse.fromEntity(review);
    }

    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> ReviewNotFoundException.EXCEPTION);

        Movie movie = review.getMovie();
        movie.updateTotalRating(movie.getTotalRating() - review.getRating());
        movie.updateReviewCount(movie.getReviewCount() - 1);

        reviewRepository.deleteById(id);
    }

    @Override
    public void likeReview(Long userId, Long reviewId) {
        if (reviewLikeRepository.existsByUserIdAndReviewId(userId, reviewId)) {
            throw AlreadyLikedReviewException.EXCEPTION;
        }

        SiteUser user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> ReviewNotFoundException.EXCEPTION);

        ReviewLike like = ReviewLike.builder()
                .user(user)
                .review(review)
                .build();

        review.updateTotalLikes(review.getLikes() + 1);

        reviewLikeRepository.save(like);
    }

    @Override
    public void unlikeReview(Long userId, Long reviewId) {
        ReviewLike like = reviewLikeRepository.findByUserIdAndReviewId(userId, reviewId)
                .orElseThrow(() -> ReviewlikeNotFoundException.EXCEPTION);

        Review review = like.getReview();
        review.updateTotalLikes(review.getLikes() - 1);

        reviewLikeRepository.delete(like);
    }
}

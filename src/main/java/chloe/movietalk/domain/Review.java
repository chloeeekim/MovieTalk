package chloe.movietalk.domain;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false, updatable = false)
    private Movie movie;

    @Builder
    public Review(Double rating, String comment, Movie movie) {
        this.rating = rating;
        this.comment = comment;
        this.movie = movie;
    }

    public void updateReview(Review review) {
        this.rating = review.getRating();
        this.comment = review.getComment();
    }
}

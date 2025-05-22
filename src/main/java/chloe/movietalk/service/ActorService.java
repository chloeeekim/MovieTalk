package chloe.movietalk.service;

import chloe.movietalk.dto.request.ActorRequest;
import chloe.movietalk.dto.response.actor.ActorDetailResponse;
import chloe.movietalk.dto.response.actor.ActorInfoResponse;

import java.util.List;

public interface ActorService {

    public List<ActorInfoResponse> getAllActors();

    public ActorDetailResponse getActorById(Long id);

    public List<ActorInfoResponse> searchActor(String keyword);

    public ActorInfoResponse createActor(ActorRequest request);

    public ActorInfoResponse updateActor(Long id, ActorRequest request);

    public void deleteActor(Long id);

    public ActorDetailResponse updateFilmographyToActor(Long id, List<Long> filmography);
}

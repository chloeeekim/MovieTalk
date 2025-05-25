package chloe.movietalk.service;

import chloe.movietalk.dto.request.ActorRequest;
import chloe.movietalk.dto.response.actor.ActorDetailResponse;
import chloe.movietalk.dto.response.actor.ActorInfoResponse;

import java.util.List;
import java.util.UUID;

public interface ActorService {

    public List<ActorInfoResponse> getAllActors();

    public ActorDetailResponse getActorById(UUID id);

    public List<ActorInfoResponse> searchActor(String keyword);

    public ActorInfoResponse createActor(ActorRequest request);

    public ActorInfoResponse updateActor(UUID id, ActorRequest request);

    public void deleteActor(UUID id);

    public ActorDetailResponse updateActorFilmography(UUID id, List<UUID> filmography);
}

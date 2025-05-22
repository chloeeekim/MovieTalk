package chloe.movietalk.service;

import chloe.movietalk.domain.Actor;
import chloe.movietalk.dto.request.ActorRequest;
import chloe.movietalk.dto.response.ActorDetailResponse;
import chloe.movietalk.dto.response.ActorInfoResponse;
import chloe.movietalk.exception.actor.ActorNotFoundException;
import chloe.movietalk.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    @Override
    public List<ActorInfoResponse> getAllActors() {
        return actorRepository.findAll().stream()
                .map(ActorInfoResponse::fromEntity)
                .toList();
    }

    @Override
    public ActorDetailResponse getActorById(Long id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> ActorNotFoundException.EXCEPTION);
        return ActorDetailResponse.fromEntity(actor);
    }

    @Override
    public List<ActorInfoResponse> searchActor(String keyword) {
        return actorRepository.findByNameContaining(keyword).stream()
                .map(ActorInfoResponse::fromEntity)
                .toList();
    }

    @Override
    public ActorInfoResponse createActor(ActorRequest request) {
        Actor actor = actorRepository.save(request.toEntity());
        return ActorInfoResponse.fromEntity(actor);
    }

    @Override
    public ActorInfoResponse updateActor(Long id, ActorRequest request) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> ActorNotFoundException.EXCEPTION);

        actor.updateActor(request.toEntity());
        return ActorInfoResponse.fromEntity(actor);
    }

    @Override
    public void deleteActor(Long id) {
        actorRepository.findById(id)
                .orElseThrow(() -> ActorNotFoundException.EXCEPTION);
        actorRepository.deleteById(id);
    }
}

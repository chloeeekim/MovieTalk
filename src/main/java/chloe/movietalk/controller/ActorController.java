package chloe.movietalk.controller;

import chloe.movietalk.dto.request.ActorRequest;
import chloe.movietalk.dto.response.actor.ActorDetailResponse;
import chloe.movietalk.dto.response.actor.ActorInfoResponse;
import chloe.movietalk.service.ActorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/actors")
public class ActorController {

    private final ActorService actorService;

    @GetMapping
    public List<ActorInfoResponse> getAllActors() {
        return actorService.getAllActors();
    }

    @GetMapping("/{id}")
    public ActorDetailResponse getActorById(@PathVariable Long id) {
        return actorService.getActorById(id);
    }

    @GetMapping("/search")
    public List<ActorInfoResponse> searchActors(@RequestParam String keyword) {
        return actorService.searchActor(keyword);
    }

    @PostMapping
    public ResponseEntity<ActorInfoResponse> createActor(@RequestBody @Valid ActorRequest request) {
        ActorInfoResponse actor = actorService.createActor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(actor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActorInfoResponse> updateActor(@PathVariable Long id, @RequestBody @Valid ActorRequest request) {
        ActorInfoResponse actor = actorService.updateActor(id, request);
        return ResponseEntity.ok().body(actor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
        return ResponseEntity.noContent().build();
    }
}

package chloe.movietalk.controller;

import chloe.movietalk.dto.request.DirectorRequest;
import chloe.movietalk.dto.response.director.DirectorDetailResponse;
import chloe.movietalk.dto.response.director.DirectorInfoResponse;
import chloe.movietalk.service.DirectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/directors")
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping
    public List<DirectorInfoResponse> getAllDirectors() {
        return directorService.getAllDirectors();
    }

    @GetMapping("/{id}")
    public DirectorDetailResponse getDirectorById(@PathVariable Long id) {
        return directorService.getDirectorById(id);
    }

    @GetMapping("/search")
    public List<DirectorInfoResponse> searchDirectors(@RequestParam String keyword) {
        return directorService.searchDirector(keyword);
    }

    @PostMapping
    public ResponseEntity<DirectorInfoResponse> createDirector(@RequestBody @Valid DirectorRequest request) {
        DirectorInfoResponse director = directorService.createDirector(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(director);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectorInfoResponse> updateDirector(@PathVariable Long id, @RequestBody @Valid DirectorRequest request) {
        DirectorInfoResponse director = directorService.updateDirector(id, request);
        return ResponseEntity.ok().body(director);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable Long id) {
        directorService.deleteDirector(id);
        return ResponseEntity.noContent().build();
    }
}

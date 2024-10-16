package br.com.fiap.app.controller;

import br.com.fiap.app.mapper.CollectMapper;
import br.com.fiap.app.request.CollectPostRequest;
import br.com.fiap.app.request.CollectPutRequest;
import br.com.fiap.app.response.CollectGetResponse;
import br.com.fiap.app.response.CollectPostResponse;
import br.com.fiap.app.service.CollectService;
import br.com.fiap.app.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/collects")
public class CollectController {

    private final CollectService collectService;

    private final EmailService emailService;

    private final CollectMapper collectMapper;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CollectGetResponse>> listCollects(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(collectService.listCollects(userId));
    }

    @PostMapping
    public ResponseEntity<CollectPostResponse> save(@RequestBody CollectPostRequest request) {
        var collectSaved = collectService.save(request);
        var response = collectMapper.toPostResponse(collectSaved);
        emailService.sendEmailNotificationCollectCreated(response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        var collect = collectService.delete(id);
        emailService.sendEmailNotificationCollectDeleted(collect);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody CollectPutRequest request) {
        collectService.update(request);
        emailService.sendEmailNotificationCollectUpdated(request.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("Collect updated successfully");
    }

}

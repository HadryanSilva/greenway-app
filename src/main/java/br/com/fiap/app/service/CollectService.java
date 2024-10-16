package br.com.fiap.app.service;

import br.com.fiap.app.exception.NotFoundException;
import br.com.fiap.app.mapper.CollectMapper;
import br.com.fiap.app.model.Collect;
import br.com.fiap.app.model.WasteType;
import br.com.fiap.app.repository.CollectRepository;
import br.com.fiap.app.repository.UserRepository;
import br.com.fiap.app.request.CollectPostRequest;
import br.com.fiap.app.request.CollectPutRequest;
import br.com.fiap.app.response.CollectGetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class CollectService {

    private final CollectRepository collectRepository;

    private final UserRepository userRepository;

    private final CollectMapper collectMapper;

    public Collect save(CollectPostRequest collect) {
        var collectConverted = collectMapper.postToCollect(collect);
        var user = userRepository.findById(collect.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        collectConverted.setUser(user);
        collectConverted.setCollectionDate(LocalDate.now().plusDays(3));
        log.info("Saving collect");
        return collectRepository.save(collectConverted);
    }

    public Collect delete(Long id) {
        var collect = collectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Collect not found"));
        log.info("Deleting collect");
        collectRepository.deleteById(id);
        return collect;
    }

    public void update(CollectPutRequest collect) {
        var collectToUpdate = collectRepository.findById(collect.getId())
                .orElseThrow(() -> new NotFoundException("Collect not found"));
        collectToUpdate.setWasteType(WasteType.valueOf(collect.getWasteType()));
        collectRepository.save(collectToUpdate);
    }

    public List<CollectGetResponse> listCollects(Long userId) {
        log.info("Finding collects for user with id {}", userId);
        var collects = collectRepository.findByUserId(userId);

        if (collects.isEmpty()) {
            throw new NotFoundException("No collects found for user with id " + userId);
        }

        return collectMapper.toGetResponse(collects);
    }

}

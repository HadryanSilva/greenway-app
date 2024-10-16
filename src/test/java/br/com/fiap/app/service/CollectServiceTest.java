package br.com.fiap.app.service;

import br.com.fiap.app.exception.NotFoundException;
import br.com.fiap.app.mapper.CollectMapper;
import br.com.fiap.app.model.Collect;
import br.com.fiap.app.model.User;
import br.com.fiap.app.model.WasteType;
import br.com.fiap.app.repository.CollectRepository;
import br.com.fiap.app.repository.UserRepository;
import br.com.fiap.app.request.CollectPostRequest;
import br.com.fiap.app.response.CollectGetResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CollectServiceTest {

    @InjectMocks
    private CollectService collectService;
    @Mock
    private CollectRepository collectRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CollectMapper collectMapper;

    @Test
    void findCollectsByUserIdShouldReturnCollectList() {
        Mockito.when(collectRepository.findByUserId(1L)).thenReturn(getCollectList());
        Mockito.when(collectMapper.toGetResponse(Mockito.anyList())).thenReturn(getCollectResponseList());

        var collects = collectService.listCollects(1L);
        Assertions.assertThat(collects).isNotEmpty();
        Assertions.assertThat(collects).hasSize(3);
    }

    @Test
    void findCollectsByUserIdShouldReturnNotFoundException() {
        Mockito.when(collectRepository.findByUserId(2L)).thenReturn(List.of());

        Assertions.assertThatThrownBy(() -> collectService.listCollects(2L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("404 NOT_FOUND \"No collects found for user with id 2\"");
    }

    @Test
    void saveCollectShouldReturnCollect() {

        var collect = getCollectList().get(0);

        var collectPostRequest = new CollectPostRequest();

        Mockito.when(collectMapper.postToCollect(collectPostRequest)).thenReturn(collect);
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(collect.getUser()));
        Mockito.when(collectRepository.save(collect)).thenReturn(collect);

        var collectSaved = collectService.save(collectPostRequest);
        Assertions.assertThat(collectSaved).isNotNull();
    }

    @Test
    void deleteShouldReturnNotFoundException() {
        Mockito.when(collectRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> collectService.delete(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("404 NOT_FOUND \"Collect not found\"");
    }

    private List<Collect> getCollectList() {
        var user = new User();
        user.setId(1L);
        user.setUsername("User");
        user.setPassword("Password");
        user.setEmail("user@gmail.com");
        user.setRole("USER");

        var collect1 = new Collect();
        collect1.setId(1L);
        collect1.setCollectionDate(LocalDate.now().plusDays(3));
        collect1.setWasteType(WasteType.ORGANIC);
        collect1.setUser(user);

        var collect2 = new Collect();
        collect1.setId(2L);
        collect1.setCollectionDate(LocalDate.now().plusDays(3));
        collect1.setWasteType(WasteType.NON_RECYCLABLE);
        collect1.setUser(user);

        var collect3 = new Collect();
        collect1.setId(3L);
        collect1.setCollectionDate(LocalDate.now().plusDays(3));
        collect1.setWasteType(WasteType.RECYCLABLE);
        collect1.setUser(user);

        return List.of(collect1, collect2, collect3);
    }

    private List<CollectGetResponse> getCollectResponseList() {
        var collect1 = new CollectGetResponse();
        collect1.setId(1L);
        collect1.setCollectionDate(LocalDate.now().plusDays(3));
        collect1.setWasteType(WasteType.ORGANIC.name());

        var collect2 = new CollectGetResponse();
        collect1.setId(2L);
        collect1.setCollectionDate(LocalDate.now().plusDays(3));
        collect1.setWasteType(WasteType.NON_RECYCLABLE.name());

        var collect3 = new CollectGetResponse();
        collect1.setId(3L);
        collect1.setCollectionDate(LocalDate.now().plusDays(3));
        collect1.setWasteType(WasteType.RECYCLABLE.name());

        return List.of(collect1, collect2, collect3);
    }

}
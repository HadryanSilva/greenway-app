package br.com.fiap.app.mapper;

import br.com.fiap.app.model.Collect;
import br.com.fiap.app.request.CollectPostRequest;
import br.com.fiap.app.response.CollectGetResponse;
import br.com.fiap.app.response.CollectPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CollectMapper {

    Collect postToCollect(CollectPostRequest collectPostRequest);

    @Mapping(target = "user.password", ignore = true)
    CollectPostResponse toPostResponse(Collect collect);

    List<CollectPostResponse> toPostResponse(List<Collect> collects);

    CollectGetResponse toGetResponse(Collect collect);

    List<CollectGetResponse> toGetResponse(List<Collect> collects);

}

package br.com.fiap.app.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectPutRequest {

    private Long id;
    private Long userId;
    private String wasteType;

}

package br.com.fiap.app.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectPostRequest {

    private Long userId;
    private String wasteType;

}

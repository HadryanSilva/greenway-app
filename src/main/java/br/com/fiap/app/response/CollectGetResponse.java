package br.com.fiap.app.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CollectGetResponse {

    private Long id;
    private LocalDate collectionDate;
    private String wasteType;

}

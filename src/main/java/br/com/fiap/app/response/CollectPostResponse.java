package br.com.fiap.app.response;

import br.com.fiap.app.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CollectPostResponse {

    private Long id;
    private LocalDate collectionDate;
    private String wasteType;
    private User user;

}

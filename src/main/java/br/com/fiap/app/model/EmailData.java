package br.com.fiap.app.model;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class EmailData {

    private String from;
    private String to;
    private String subject;
    private String body;

}

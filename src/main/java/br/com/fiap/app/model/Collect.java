package br.com.fiap.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "collects")
public class Collect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate collectionDate;

    @Enumerated(EnumType.STRING)
    private WasteType wasteType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

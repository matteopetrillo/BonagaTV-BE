package it.petrillo.bonagatv.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UtenteDto {

    private Long id;
    private String email;
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dataDiRegistrazione;

    public UtenteDto(Long id, String email, String password, LocalDateTime dataDiRegistrazione) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.dataDiRegistrazione = dataDiRegistrazione != null ? dataDiRegistrazione.plusHours(1) : null;
    }
}

package it.petrillo.bonagatv.models.dto;

import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoDto {
    private Long id;
    private String nomeEvento;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private Long idCanale;
    private String nomeCanale;
    private String logoEvento;

    // Private constructor to enforce the use of the builder
    private EventoDto(Builder builder) {
        this.id = builder.id;
        this.nomeEvento = builder.nomeEvento;
        this.dataInizio = builder.dataInizio;
        this.dataFine = builder.dataFine;
        this.idCanale = builder.idCanale;
        this.nomeCanale = builder.nomeCanale;
        this.logoEvento = builder.logoEvento;
    }

    // Static inner Builder class
    public static class Builder {
        private Long id;
        private String nomeEvento;
        private LocalDate dataInizio;
        private LocalDate dataFine;
        private Long idCanale;
        private String nomeCanale;
        private String logoEvento;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder nomeEvento(String nomeEvento) {
            this.nomeEvento = nomeEvento;
            return this;
        }

        public Builder dataInizio(LocalDate dataInizio) {
            this.dataInizio = dataInizio;
            return this;
        }

        public Builder dataFine(LocalDate dataFine) {
            this.dataFine = dataFine;
            return this;
        }

        public Builder idCanale(Long idCanale) {
            this.idCanale = idCanale;
            return this;
        }

        public Builder nomeCanale(String nomeCanale) {
            this.nomeCanale = nomeCanale;
            return this;
        }

        public Builder logoEvento(String logoEvento) {
            this.logoEvento = logoEvento;
            return this;
        }

        public EventoDto build() {
            return new EventoDto(this);
        }
    }

    // Static method to start the builder
    public static Builder builder() {
        return new Builder();
    }
}

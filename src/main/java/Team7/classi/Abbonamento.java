package Team7.classi;

import Team7.superclassi.Biglietto;
import Team7.superclassi.Emissione_Biglietti;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("abbonamento")
public class Abbonamento extends Biglietto {

    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;

    @OneToOne
    private Tessera tessera;

    public Abbonamento(LocalDate data, Emissione_Biglietti emissioneBiglietti, Periodicita periodicita, Tessera tessera) {
        super(data, emissioneBiglietti);
        this.periodicita = periodicita;
        this.tessera = tessera;
    }
}

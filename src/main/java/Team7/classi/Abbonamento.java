package Team7.classi;

import Team7.superclassi.Biglietto;
import Team7.superclassi.Emissione_Biglietti;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DiscriminatorValue("abbonamento")
public class Abbonamento extends Biglietto {

    @ManyToOne
    @JoinColumn(name = "emissione_id", nullable = false)
    private Emissione_Biglietti emissione;

    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;

    @OneToOne
    private Tessera tessera;
}

package Team7.classi;

import Team7.superclassi.Emissione_Biglietti;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Distributore extends Emissione_Biglietti {
    @Enumerated(EnumType.STRING)
    private Stato stato;

    public Distributore( Stato stato) {
        this.stato = stato;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }
}

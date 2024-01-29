package Team7.classi;

import Team7.superclassi.Emissione_Biglietti;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DiscriminatorValue("biglietto")
public class Biglietto extends Emissione_Biglietti {

    @ManyToOne
    @JoinColumn(name = "emissione_id", nullable = false)
    private Emissione_Biglietti emissione;

    private LocalDate dataAcquisto;

    private LocalDate dataTimbro;
}

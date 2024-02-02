package Team7.superclassi;

import Team7.classi.Abbonamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_emissione")
public abstract class Emissione_Biglietti {

    @Id
    @Column(name = "emissione_id")
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "emissioneBiglietti")
    private List<Biglietto> bigliettiEmessi;

    @OneToMany(mappedBy = "emissioneBiglietti")
    private List<Abbonamento> abbonamentiEmessi;

}

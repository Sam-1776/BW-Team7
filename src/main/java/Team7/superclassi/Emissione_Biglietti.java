package Team7.superclassi;

import Team7.classi.Abbonamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_emissione")
public abstract class Emissione_Biglietti {

    @Id
    @Column(name = "emissione_id")
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "emissione")
    private List<Biglietto> bigliettiEmessi;

    @OneToMany(mappedBy = "emissione")
    private List<Abbonamento> abbonamentiEmessi;

}

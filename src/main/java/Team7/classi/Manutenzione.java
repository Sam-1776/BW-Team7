package Team7.classi;

import Team7.superclassi.Mezzo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NamedQuery(name = "Manutenzione_attuale", query = "from Manutenzione where dataFine is null")
public class Manutenzione {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate dataInizio;

    private LocalDate dataFine;

    @OneToOne
    private Mezzo mezzo;

}

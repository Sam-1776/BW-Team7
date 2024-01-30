package Team7.superclassi;


import Team7.classi.Servizio;
import Team7.classi.Tratta;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "mezzi")
public abstract class Mezzo {
    @Id
    @GeneratedValue
    private long id;
    private LocalDate periodo;

    @Enumerated(EnumType.STRING)
    private Servizio servizio;

    @OneToOne
    @JoinColumn(name = "tratta_id")
    private Tratta tratta;


    @OneToMany(mappedBy = "mezzo")
    private List<Biglietto> listaBiglietto;

    private LocalDateTime partenza;

    private LocalDateTime arrivo;


    protected Mezzo() {
    }

    public Mezzo(LocalDate periodo, Servizio servizio, Tratta tratta, LocalDateTime partenza, LocalDateTime arrivo) {
        this.periodo = periodo;
        this.servizio = servizio;
        this.tratta = tratta;
        this.partenza = partenza;
        this.arrivo = arrivo;
    }





    @Override
    public String toString() {
        return "Mezzo{" +
                "periodo=" + periodo +
                ", servizio=" + servizio +
                ", tratta=" + tratta +
                ", bigliettiTratta=" +
                ", partenza=" + partenza +
                ", arrivo=" + arrivo +
                '}';
    }


}

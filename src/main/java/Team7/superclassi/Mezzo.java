package Team7.superclassi;


import Team7.classi.Servizio;
import Team7.classi.Tratta;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
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


    @OneToMany
    private List<Biglietto> bigliettiTratta;

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

    public LocalDate getPeriodo() {
        return periodo;
    }

    public void setPeriodo(LocalDate periodo) {
        this.periodo = periodo;
    }

    public Servizio getServizio() {
        return servizio;
    }

    public void setServizio(Servizio servizio) {
        this.servizio = servizio;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }



    public LocalDateTime getPartenza() {
        return partenza;
    }

    public void setPartenza(LocalDateTime partenza) {
        this.partenza = partenza;
    }

    public LocalDateTime getArrivo() {
        return arrivo;
    }

    public void setArrivo(LocalDateTime arrivo) {
        this.arrivo = arrivo;
    }

    @Override
    public String toString() {
        return "Mezzo{" +
                "periodo=" + periodo +
                ", servizio=" + servizio +
                ", tratta=" + tratta +
                ", bigliettiTratta=" + bigliettiTratta +
                ", partenza=" + partenza +
                ", arrivo=" + arrivo +
                '}';
    }
}

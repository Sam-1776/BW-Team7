package Team7.classi;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Tappa {
    @Id
    @GeneratedValue
    private long id;

    private String nome;
    @ManyToOne
    @JoinColumn(name = "tratta_id")
    private Tratta tratta;


    public Tappa() {
    }

    public Tappa(String nome, Tratta tratta) {
        this.nome = nome;
        this.tratta = tratta;
    }


}

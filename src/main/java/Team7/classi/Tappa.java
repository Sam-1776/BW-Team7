package Team7.classi;

import javax.persistence.*;

@Entity
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }
}

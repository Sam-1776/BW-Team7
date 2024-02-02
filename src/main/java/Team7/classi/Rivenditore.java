package Team7.classi;

import Team7.superclassi.Emissione_Biglietti;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Rivenditore extends Emissione_Biglietti {

    private String nome;
    private String Luogo;

    public Rivenditore(String nome, String luogo) {
        this.nome = nome;
        Luogo = luogo;
    }

    @Override
    public String toString() {
        return "Rivenditore{" +
                "nome='" + nome + '\'' +
                ", Luogo='" + Luogo + '\'' +
                '}';
    }
}

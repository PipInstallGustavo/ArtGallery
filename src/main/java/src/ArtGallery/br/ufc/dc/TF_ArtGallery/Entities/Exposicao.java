package src.ArtGallery.br.ufc.dc.TF_ArtGallery.Entities;

import java.util.Vector;

public class Exposicao {
    private String nome;
    private Vector<Obra> obras;

    public Exposicao(String nome, Vector<Obra> obras){
        this.nome = nome;
        this.obras = new Vector<>();
    }

    public void adicionarObra(Obra obra){
        obras.add(obra);
    }

    public Vector<Obra> listarObras() {
        return obras;
    }



    // getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

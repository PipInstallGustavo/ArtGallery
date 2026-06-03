package src.ArtGallery.br.ufc.dc.TF_ArtGallery.Entities;

import java.util.Vector;


abstract public class Obra {
    private String titulo;
    private String autor;
    private boolean ativa;
    private Vector<Avaliacao> avaliacoes;

    public Obra(String titulo, String autor){
        this.titulo = titulo;
        this.autor = autor;
        this.ativa = true;
        this.avaliacoes = new Vector<Avaliacao>();
    }

    public void adicionarAvaliacao(Avaliacao avaliacao){
        avaliacoes.add(avaliacao);
    }

    public double mediaAvaliacoes(){
        if (avaliacoes.isEmpty()) {
            return 0;
        }

        double soma = 0;

        for (Avaliacao avaliacao : avaliacoes) {
            soma += avaliacao.getNota();
        }

        return (soma / avaliacoes.size());
    }
    public abstract String exibirDetalhes();

    // getters e setters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Vector<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

}

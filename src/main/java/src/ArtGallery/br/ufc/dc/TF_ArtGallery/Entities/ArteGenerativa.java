package src.ArtGallery.br.ufc.dc.TF_ArtGallery.Entities;

public class ArteGenerativa extends Obra {
    private String algoritmo;
    private long seed;

    public ArteGenerativa(String titulo, String autor, String algoritmo, long seed){
        super(titulo, autor);
        this.algoritmo= algoritmo;
        this.seed = seed;
    }

    @Override
    public String exibirDetalhes(){
        return "Título: " + getTitulo() + "\n" +
                "Autor: " + getAutor() + "\n" +
                "Tipo: Arte Generativa\n" +
                "Algoritmo: " + algoritmo + "\n" +
                "Seed: " + seed;
    }

    //getters e setters
    public String getAlgoritmo() {
        return algoritmo;
    }
    public long getSeed() {
        return seed;
    }
    public void setAlgoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
    }
    public void setSeed(long seed) {
        this.seed = seed;
    }
}

package src.ArtGallery.br.ufc.dc.TF_ArtGallery.Entities;

public class PinturaDigital extends Obra {
    private String resolucao;
    private String softwareUtilizado;

    public PinturaDigital(String titulo, String autor, String resolucao, String softwareUtilizado) {

        super(titulo, autor);
        this.resolucao = resolucao;
        this.softwareUtilizado = softwareUtilizado;
    }

    @Override
    public String exibirDetalhes() {
        return "Título: " + getTitulo() + "\n" +
                "Autor: " + getAutor() + "\n" +
                "Tipo: Pintura Digital\n" +
                "Resolução: " + resolucao + "\n" +
                "Software: " + softwareUtilizado;
    }

    // getters e setters
    public String getResolucao() {
        return resolucao;
    }
    public String getSoftwareUtilizado() {
        return softwareUtilizado;
    }
    public void setResolucao(String resolucao) {
        this.resolucao = resolucao;
    }
    public void setSoftwareUtilizado(String softwareUtilizado) {
        this.softwareUtilizado = softwareUtilizado;
    }
}
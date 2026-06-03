package src.ArtGallery.br.ufc.dc.TF_ArtGallery.ArtGalleryApp;

import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Entities.*;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Interfaces.IRepositorioObra;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.PersistenciaArquivos.Persistencia;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Repository.RepositorioObra;

import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        IRepositorioObra repositorio = new RepositorioObra();
        ArtGallery artGallery = new ArtGallery(repositorio);

        try {
            // Inicialização
            Persistencia.inicializarArquivos();

            // Carregar dados salvos
            Vector<Obra> obrasCarregadas = Persistencia.carregarObras();
            Persistencia.carregarAvaliacoes(obrasCarregadas);

            for (Obra obra : obrasCarregadas) {
                repositorio.cadastrar(obra);
            }

            // Obras teste
            Obra obra1 = new PinturaDigital("Aurora Neon", "Alice", "4K", "Photoshop");
            Obra obra2 = new Modelagem3D("Cyber Dragon", "Bruno", 50000, "Blender");
            Obra obra3 = new ArteGenerativa("Chaos Waves", "Carla", "Perlin Noise", 98231);

            artGallery.publicarObra(obra1);
            artGallery.publicarObra(obra2);
            artGallery.publicarObra(obra3);

            artGallery.avaliarObra("Aurora Neon", new Avaliacao("João", 10, "Excelente"));
            artGallery.avaliarObra("Cyber Dragon", new Avaliacao("Maria", 8, "Muito bom"));

            // Exposições
            Vector<Exposicao> exposicoes = new Vector<>();

            Exposicao exp1 = new Exposicao("Exposição Digital", artGallery.listarObras());
            Exposicao exp2 = new Exposicao("Exposição Experimental", artGallery.listarObras());

            // exemplo: separando tudo na primeira
            for (Obra o : artGallery.listarObras()) {
                exp1.adicionarObra(o);
            }

            // segunda exposição só com obras ativas
            for (Obra o : artGallery.listarObras()) {
                if (o.isAtiva()) {
                    exp2.adicionarObra(o);
                }
            }

            exposicoes.add(exp1);
            exposicoes.add(exp2);

            // Prints
            System.out.println("===== OBRAS ATIVAS =====");
            for (Obra obra : artGallery.listarObras()) {
                System.out.println(obra.exibirDetalhes());
                System.out.println("Média: " + obra.mediaAvaliacoes());
                System.out.println();
            }

            System.out.println("===== TOP OBRAS MAIS BEM AVALIADAS =====");
            for (Obra obra : artGallery.topObras()) {
                System.out.println(obra.getTitulo() + " -> " + obra.mediaAvaliacoes());
            }

            // Persistência (CSV)
            Persistencia.salvarObras(artGallery.listarObras());
            Persistencia.salvarAvaliacoes(artGallery.listarObras());

            System.out.println("\nDados salvos com sucesso em CSV.");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
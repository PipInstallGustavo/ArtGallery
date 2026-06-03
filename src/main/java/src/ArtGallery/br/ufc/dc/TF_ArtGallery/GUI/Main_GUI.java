package src.ArtGallery.br.ufc.dc.TF_ArtGallery.GUI;

import src.ArtGallery.br.ufc.dc.TF_ArtGallery.ArtGalleryApp.ArtGallery;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Interfaces.IRepositorioObra;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Repository.RepositorioObra;

public class Main_GUI {

    public static void main(String[] args) {
        IRepositorioObra repositorio = new RepositorioObra();
        ArtGallery sistema = new ArtGallery(repositorio);

        new TelaPrincipal(sistema);
    }
}
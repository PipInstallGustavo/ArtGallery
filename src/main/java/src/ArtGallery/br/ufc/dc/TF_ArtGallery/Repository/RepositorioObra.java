package src.ArtGallery.br.ufc.dc.TF_ArtGallery.Repository;

import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Exception.ObraJaCadastradaException;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Exception.ObraNaoEncontradaException;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Interfaces.IRepositorioObra;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Entities.Obra;

import java.util.Vector;

public class RepositorioObra implements IRepositorioObra {
    private Vector<Obra> obras;
    public RepositorioObra() {
        obras = new Vector<>();
    }

    @Override
    public void cadastrar(Obra obra)
            throws ObraJaCadastradaException {
        for (Obra o : obras) {
            if (o.getTitulo().equalsIgnoreCase(obra.getTitulo())
                    && o.getAutor().equalsIgnoreCase(obra.getAutor())) {

                throw new ObraJaCadastradaException("Obra já cadastrada.");
            }
        }

        obras.add(obra);
    }

    @Override
    public Obra buscar(String titulo) {
        for (Obra obra : obras) {
            if (obra.getTitulo().equalsIgnoreCase(titulo)) {
                return obra;
            }
        }

        return null;
    }

    @Override
    public void atualizar(Obra obra)
            throws ObraNaoEncontradaException {

        for (int i = 0; i < obras.size(); i++) {
            Obra atual = obras.get(i);
            if (atual.getTitulo().equalsIgnoreCase(
                    obra.getTitulo())) {

                obras.set(i, obra);
                return;
            }
        }

        throw new ObraNaoEncontradaException("Obra não encontrada.");
    }

    @Override
    public void remover(String titulo) {
        Obra obra = buscar(titulo);
        if (obra != null) {
            obra.setAtiva(false);
        }
    }

    @Override
    public Vector<Obra> listar() {
        return obras;
    }
}
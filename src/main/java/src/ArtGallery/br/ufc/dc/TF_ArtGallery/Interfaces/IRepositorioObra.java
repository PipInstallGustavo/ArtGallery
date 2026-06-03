package src.ArtGallery.br.ufc.dc.TF_ArtGallery.Interfaces;

import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Exception.ObraJaCadastradaException;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Exception.ObraNaoEncontradaException;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Entities.Obra;

import java.util.Vector;


public interface IRepositorioObra {

    void cadastrar(Obra obra) throws ObraJaCadastradaException;
    Obra buscar(String titulo);
    void atualizar(Obra obra) throws ObraNaoEncontradaException;
    void remover(String titulo);
    Vector<Obra> listar();
}
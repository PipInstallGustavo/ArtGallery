package src.ArtGallery.br.ufc.dc.TF_ArtGallery.Interfaces;

import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Entities.Avaliacao;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Entities.Obra;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Exception.NotaInvalidaException;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Exception.ObraJaCadastradaException;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Exception.ObraNaoEncontradaException;

import java.util.Vector;

public interface IArtGallery {
    void publicarObra(Obra obra) throws ObraJaCadastradaException;
    void avaliarObra(String titulo, Avaliacao avaliacao) throws ObraNaoEncontradaException, NotaInvalidaException;
    void removerObra(String titulo);
    Vector<Obra> listarObras();
    void atualizarObra(String titulo, Obra novaDados) throws ObraNaoEncontradaException;
    Vector<Obra> buscarPorAutor(String autor);
    Vector<Obra> topObras();
    Vector<Obra> obrasExpostas(String nomeExposicao);
}
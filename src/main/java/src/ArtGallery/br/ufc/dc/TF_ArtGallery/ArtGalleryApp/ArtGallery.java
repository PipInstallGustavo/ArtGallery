package src.ArtGallery.br.ufc.dc.TF_ArtGallery.ArtGalleryApp;

import java.util.Vector;

import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Entities.*;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Exception.*;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Interfaces.IArtGallery;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Interfaces.IRepositorioObra;

public class ArtGallery implements IArtGallery {
    private IRepositorioObra repositorio;
    private Vector<Exposicao> exposicoes;

    public ArtGallery(IRepositorioObra repositorio) {
        this.repositorio = repositorio;
        this.exposicoes = new Vector<>();
    }

    // PUBLICAR OBRA
    @Override
    public void publicarObra(Obra obra) throws ObraJaCadastradaException {
        // valida duplicidade
        for (Obra o : repositorio.listar()) {
            if (o.getTitulo().equalsIgnoreCase(obra.getTitulo())) {
                throw new ObraJaCadastradaException("Já existe uma obra com o título: " + obra.getTitulo());
            }
        }

        repositorio.cadastrar(obra);
    }

    // REMOVER (DESATIVAR)
    @Override
    public void removerObra(String titulo) {
        Obra obra = repositorio.buscar(titulo);
        if (obra == null) {
            return;
        }
        if (!obra.isAtiva()) {
            return;
        }

        obra.setAtiva(false);
    }

    // AVALIAR OBRA
    @Override
    public void avaliarObra(String titulo, Avaliacao avaliacao) throws ObraNaoEncontradaException, NotaInvalidaException {
        if (avaliacao.getNota() < 0 || avaliacao.getNota() > 10) {
            throw new NotaInvalidaException("Nota deve estar entre 0 e 10");
        }

        Obra obra = repositorio.buscar(titulo);

        if (obra == null) {
            throw new ObraNaoEncontradaException("Obra: " + titulo);
        }
        if (!obra.isAtiva()) {
            throw new ObraNaoEncontradaException("Obra desativada: " + titulo);
        }

        obra.adicionarAvaliacao(avaliacao);
    }

    // SETAR OBRAS
    public void atualizarObra(String titulo, Obra novaDados) throws ObraNaoEncontradaException {

        Obra antiga = repositorio.buscar(titulo);

        if (antiga == null || !antiga.isAtiva()) {
            throw new ObraNaoEncontradaException("Obra não encontrada: " + titulo);
        }

        // atualiza campos comuns
        antiga.setAutor(novaDados.getAutor());

        // atualiza campos específicos via instanceof
        if (antiga instanceof PinturaDigital p && novaDados instanceof PinturaDigital np) {
            p.setResolucao(np.getResolucao());
            p.setSoftwareUtilizado(np.getSoftwareUtilizado());
        }

        else if (antiga instanceof Modelagem3D m && novaDados instanceof Modelagem3D nm) {
            m.setNumeroPoligonos(nm.getNumeroPoligonos());
            m.setEngine(nm.getEngine());
        }

        else if (antiga instanceof ArteGenerativa a && novaDados instanceof ArteGenerativa na) {
            a.setAlgoritmo(na.getAlgoritmo());
            a.setSeed(na.getSeed());
        }
    }

    // LISTAR
    @Override
    public Vector<Obra> listarObras() {
        Vector<Obra> resultado = new Vector<>();
        for (Obra obra : repositorio.listar()) {
            if (obra.isAtiva()) {
                resultado.add(obra);
            }
        }

        return resultado;
    }

    // BUSCAR POR AUTOR
    @Override
    public Vector<Obra> buscarPorAutor(String autor) {
        Vector<Obra> resultado = new Vector<>();
        for (Obra obra : repositorio.listar()) {
            if (obra.getAutor().equalsIgnoreCase(autor)) {
                resultado.add(obra);
            }
        }

        return resultado;
    }

    // TOP OBRAS POR AVALIAÇÃO
    @Override
    public Vector<Obra> topObras() {
        Vector<Obra> resultado = new Vector<>(repositorio.listar());

        resultado.sort((o1, o2) -> Double.compare(o2.mediaAvaliacoes(), o1.mediaAvaliacoes()));
        return resultado;
    }

    // EXPOSIÇÕES
    @Override
    public Vector<Obra> obrasExpostas(String nomeExposicao) {
        for (Exposicao e : exposicoes) {
            if (e.getNome().equalsIgnoreCase(nomeExposicao)) {
                return e.listarObras();
            }
        }

        return new Vector<>();
    }

    public void adicionarExposicao(Exposicao exposicao) {
        exposicoes.add(exposicao);
    }
}
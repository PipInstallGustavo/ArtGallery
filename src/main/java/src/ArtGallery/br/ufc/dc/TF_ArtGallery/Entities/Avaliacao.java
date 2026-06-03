package src.ArtGallery.br.ufc.dc.TF_ArtGallery.Entities;

import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Exception.NotaInvalidaException;

public class Avaliacao {
    private String usuario;
    private int nota;
    private String comentario;

    public Avaliacao(String usuario, int nota, String comentario)
            throws NotaInvalidaException {

        if (nota < 0 || nota > 10) {
            throw new NotaInvalidaException("A nota deve estar entre 0 e 10.");
        }

        this.usuario = usuario;
        this.nota = nota;
        this.comentario = comentario;
    }



    // getters e setters
    public String getUsuario() {
        return usuario;
    }

    public int getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setNota(int nota) throws NotaInvalidaException {
        if (nota < 0 || nota > 10) {
            throw new NotaInvalidaException("A nota deve estar entre 0 e 10.");
        }
        this.nota = nota;
    }
}
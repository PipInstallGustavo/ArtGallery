package src.ArtGallery.br.ufc.dc.TF_ArtGallery.PersistenciaArquivos;

import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Entities.Obra;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Entities.*;

import java.io.*;
import java.util.Vector;

public class Persistencia {
    private static final String OBRAS = "dados/obras.csv";
    private static final String AVALIACOES = "dados/avaliacoes.csv";

    public static void inicializarArquivos() {
        try {
            File pasta = new File("dados");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            File arquivo_obras = new File(OBRAS);
            File arquivo_avaliacoes = new File(AVALIACOES);

            if (!arquivo_obras.exists()) {
                arquivo_obras.createNewFile();
            }

            if (!arquivo_avaliacoes.exists()) {
                arquivo_avaliacoes.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void salvarObras(Vector<Obra> obras) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OBRAS))) {
            writer.write("tipo,titulo,autor,ativa,resolucao,software,poligonos,engine,algoritmo,seed");
            writer.newLine();

            for (Obra obra : obras) {
                if (obra instanceof PinturaDigital) {
                    PinturaDigital p = (PinturaDigital) obra;
                    writer.write(
                            "PINTURA," +
                                    p.getTitulo() + "," +
                                    p.getAutor() + "," +
                                    p.isAtiva() + "," +
                                    p.getResolucao() + "," +
                                    p.getSoftwareUtilizado() + "," +
                                    "null,null,null,null"
                    );
                }
                else if (obra instanceof Modelagem3D) {
                    Modelagem3D m = (Modelagem3D) obra;
                    writer.write(
                            "MODELAGEM," +
                                    m.getTitulo() + "," +
                                    m.getAutor() + "," +
                                    m.isAtiva() + "," +
                                    "null,null," +
                                    m.getNumeroPoligonos() + "," +
                                    m.getEngine() + "," +
                                    "null,null"
                    );
                }
                else if (obra instanceof ArteGenerativa) {
                    ArteGenerativa a = (ArteGenerativa) obra;
                    writer.write(
                            "GENERATIVA," +
                                    a.getTitulo() + "," +
                                    a.getAutor() + "," +
                                    a.isAtiva() + "," +
                                    "null,null,null,null," +
                                    a.getAlgoritmo() + "," +
                                    a.getSeed()
                    );
                }
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Vector<Obra> carregarObras() {
        Vector<Obra> obras = new Vector<>();

        File arquivo = new File(OBRAS);

        if (!arquivo.exists()) return obras;

        try (BufferedReader reader = new BufferedReader(new FileReader(OBRAS))) {
            String linha = reader.readLine(); // pular cabeçalho

            while ((linha = reader.readLine()) != null) {
                String[] d = linha.split(",");

                if (d.length < 4) continue;

                switch (d[0]) {
                    case "PINTURA":
                        if (d.length >= 6) {
                            PinturaDigital pintura = new PinturaDigital(
                                    d[1],  // titulo
                                    d[2],  // autor
                                    d[4],  // resolucao
                                    d[5]   // software
                            );
                            pintura.setAtiva(Boolean.parseBoolean(d[3]));
                            obras.add(pintura);
                        }
                        break;

                    case "MODELAGEM":
                        if (d.length >= 8) {
                            Modelagem3D modelagem = new Modelagem3D(
                                    d[1],  // titulo
                                    d[2],  // autor
                                    Integer.parseInt(d[6]),  // poligonos
                                    d[7]   // engine
                            );
                            modelagem.setAtiva(Boolean.parseBoolean(d[3]));
                            obras.add(modelagem);
                        }
                        break;

                    case "GENERATIVA":
                        if (d.length >= 10) {
                            ArteGenerativa generativa = new ArteGenerativa(
                                    d[1],  // titulo
                                    d[2],  // autor
                                    d[8],  // algoritmo
                                    Long.parseLong(d[9])  // seed
                            );
                            generativa.setAtiva(Boolean.parseBoolean(d[3]));
                            obras.add(generativa);
                        }
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obras;
    }


    public static void salvarAvaliacoes(Vector<Obra> obras) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AVALIACOES))) {
            writer.write("tituloObra,usuario,nota,comentario");
            writer.newLine();

            for (Obra obra : obras) {
                for (Avaliacao a : obra.getAvaliacoes()) {
                    writer.write(
                            obra.getTitulo() + "," +
                                    a.getUsuario() + "," +
                                    a.getNota() + "," +
                                    a.getComentario()
                    );
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void carregarAvaliacoes(Vector<Obra> obras) {
        File arquivo = new File(AVALIACOES);

        if (!arquivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(AVALIACOES))) {
            String linha = reader.readLine(); // pular cabeçalho

            while ((linha = reader.readLine()) != null) {
                String[] d = linha.split(",");
                if (d.length < 4) continue;

                String titulo = d[0];
                String usuario = d[1];
                int nota = Integer.parseInt(d[2]);
                String comentario = d[3];

                for (Obra obra : obras) {
                    if (obra.getTitulo().equalsIgnoreCase(titulo)) {
                        obra.adicionarAvaliacao(new Avaliacao(usuario, nota, comentario));
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
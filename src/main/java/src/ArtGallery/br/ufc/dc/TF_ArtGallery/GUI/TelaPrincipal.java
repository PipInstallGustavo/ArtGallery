package src.ArtGallery.br.ufc.dc.TF_ArtGallery.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

import src.ArtGallery.br.ufc.dc.TF_ArtGallery.ArtGalleryApp.ArtGallery;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Entities.*;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.PersistenciaArquivos.Persistencia;
import src.ArtGallery.br.ufc.dc.TF_ArtGallery.Exception.*;

public class TelaPrincipal extends JFrame {

    private ArtGallery sistema;

    private JButton btnCadastrar;
    private JButton btnListar;
    private JButton btnBuscarTitulo;
    private JButton btnBuscarAutor;
    private JButton btnAvaliar;
    private JButton btnTopObras;
    private JButton btnAtualizar;
    private JButton btnDesativar;
    private JButton btnRemover;

    private JTextArea areaTextoExibicao;

    public TelaPrincipal(ArtGallery sistema) {
        this.sistema = sistema;

        // CARREGAMENTO CSV
        carregarDoCSV();

        // UI
        setTitle("ArtGallery - Sistema de Exposição");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(12, 12, 12, 12));
        setContentPane(painel);

        JPanel menu = new JPanel(new GridLayout(9, 1, 5, 5));

        // botões
        btnCadastrar = new JButton("Cadastrar");
        btnListar = new JButton("Listar");
        btnBuscarTitulo = new JButton("Buscar Título");
        btnBuscarAutor = new JButton("Buscar Autor");
        btnAvaliar = new JButton("Avaliar");
        btnTopObras = new JButton("Top Obras");
        btnAtualizar = new JButton("Atualizar");
        btnDesativar = new JButton("Desativar");
        btnRemover = new JButton("Remover");

        menu.add(btnCadastrar);
        menu.add(btnListar);
        menu.add(btnBuscarTitulo);
        menu.add(btnBuscarAutor);
        menu.add(btnAvaliar);
        menu.add(btnTopObras);
        menu.add(btnAtualizar);
        menu.add(btnDesativar);
        menu.add(btnRemover);

        painel.add(menu, BorderLayout.WEST);

        areaTextoExibicao = new JTextArea();
        areaTextoExibicao.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(areaTextoExibicao);

        painel.add(scroll, BorderLayout.CENTER);

        configurarEventos();

        setVisible(true);
    }

    // CSV
    private void carregarDoCSV() {
        try{
            Vector<Obra> obras = Persistencia.carregarObras();
            Persistencia.carregarAvaliacoes(obras);

            for (Obra o : obras) {
                sistema.publicarObra(o);
            }
        }catch (ObraJaCadastradaException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Obra já cadastrada: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

        }
    }

    private void salvarEstado() {
        Persistencia.salvarObras(sistema.listarObras());
        Persistencia.salvarAvaliacoes(sistema.listarObras());
    }

    // EVENTOS
    private void configurarEventos() {

        btnCadastrar.addActionListener(e -> cadastrar());
        btnListar.addActionListener(e -> listar());
        btnBuscarTitulo.addActionListener(e -> buscarTitulo());
        btnBuscarAutor.addActionListener(e -> buscarAutor());
        btnAvaliar.addActionListener(e -> avaliar());
        btnTopObras.addActionListener(e -> top());
        btnAtualizar.addActionListener(e -> atualizar());
        btnDesativar.addActionListener(e -> desativar());
        btnRemover.addActionListener(e -> remover());
    }

    // CADASTRAR OBRA
    private void cadastrar() {
        try {
            String[] tipos = {"Pintura Digital", "Modelagem 3D", "Arte Generativa"};

            String tipo = (String) JOptionPane.showInputDialog(
                    this, "Tipo:", "Cadastro",
                    JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]
            );

            if (tipo == null) return;

            String titulo = JOptionPane.showInputDialog("Título:");
            String autor = JOptionPane.showInputDialog("Autor:");

            Obra obra;

            if (tipo.equals("Pintura Digital")) {
                obra = new PinturaDigital(
                        titulo, autor,
                        JOptionPane.showInputDialog("Resolução"),
                        JOptionPane.showInputDialog("Software")
                );
            }
            else if (tipo.equals("Modelagem 3D")) {
                obra = new Modelagem3D(
                        titulo, autor,
                        Integer.parseInt(JOptionPane.showInputDialog("Polígonos")),
                        JOptionPane.showInputDialog("Engine")
                );
            }
            else {
                obra = new ArteGenerativa(
                        titulo, autor,
                        JOptionPane.showInputDialog("Algoritmo"),
                        Long.parseLong(JOptionPane.showInputDialog("Seed"))
                );
            }

            sistema.publicarObra(obra);

            salvarEstado();
            listar();

        } catch (ObraJaCadastradaException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Obra já cadastrada: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro de formato numérico.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // LISTAR OBRAS
    private void listar() {
        StringBuilder sb = new StringBuilder();

        for (Obra o : sistema.listarObras()) {
            sb.append(o.exibirDetalhes()).append("\n");
            sb.append("Média: ").append(o.mediaAvaliacoes()).append("\n\n");
        }

        areaTextoExibicao.setText(sb.toString());
    }

    // BUSCAR OBRAS
    private void buscarTitulo() {
        try {
            String t = JOptionPane.showInputDialog("Título:");

            for (Obra o : sistema.listarObras()) {
                if (o.getTitulo().equalsIgnoreCase(t)) {
                    areaTextoExibicao.setText(o.exibirDetalhes());
                    return;
                }
            }

            throw new ObraNaoEncontradaException("Obra não encontrada: " + t);

        } catch (ObraNaoEncontradaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscarAutor() {
        String a = JOptionPane.showInputDialog("Autor:");
        StringBuilder sb = new StringBuilder();

        for (Obra o : sistema.buscarPorAutor(a)) {
            sb.append(o.exibirDetalhes()).append("\n");
        }

        areaTextoExibicao.setText(sb.toString());
    }

    // AVALIAR
    private void avaliar() {
        try {
            String titulo = JOptionPane.showInputDialog("Título:");
            String usuario = JOptionPane.showInputDialog("Usuário");

            int nota = Integer.parseInt(
                    JOptionPane.showInputDialog("Nota (0-10)")
            );

            String comentario = JOptionPane.showInputDialog("Comentário");

            sistema.avaliarObra(
                    titulo,
                    new Avaliacao(usuario, nota, comentario)
            );

            salvarEstado();
            listar();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nota inválida", "Erro", JOptionPane.ERROR_MESSAGE);

        } catch (NotaInvalidaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Nota inválida", JOptionPane.ERROR_MESSAGE);

        } catch (ObraNaoEncontradaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Não encontrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ATUALIZAR OBRAS
    private void atualizar() {
        try {
            String titulo = JOptionPane.showInputDialog("Título da obra:");
            if (titulo == null) return;

            Obra antiga = null;

            for (Obra o : sistema.listarObras()) {
                if (o.getTitulo().equalsIgnoreCase(titulo)) {
                    antiga = o;
                    break;
                }
            }

            if (antiga == null) {
                throw new ObraNaoEncontradaException("Obra não encontrada");
            }

            String novoAutor = JOptionPane.showInputDialog("Novo autor:", antiga.getAutor());

            Obra dadosNovos = null;

            if (antiga instanceof PinturaDigital) {
                dadosNovos = new PinturaDigital(
                        antiga.getTitulo(),
                        novoAutor,
                        JOptionPane.showInputDialog("Nova resolução"),
                        JOptionPane.showInputDialog("Novo software")
                );
            }

            else if (antiga instanceof Modelagem3D) {
                dadosNovos = new Modelagem3D(
                        antiga.getTitulo(),
                        novoAutor,
                        Integer.parseInt(JOptionPane.showInputDialog("Novos polígonos")),
                        JOptionPane.showInputDialog("Nova engine")
                );
            }

            else if (antiga instanceof ArteGenerativa) {
                dadosNovos = new ArteGenerativa(
                        antiga.getTitulo(),
                        novoAutor,
                        JOptionPane.showInputDialog("Novo algoritmo"),
                        Long.parseLong(JOptionPane.showInputDialog("Nova seed"))
                );
            }

            sistema.atualizarObra(titulo, dadosNovos);

            salvarEstado();
            listar();

            JOptionPane.showMessageDialog(this, "Obra atualizada com sucesso!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro numérico", "Erro", JOptionPane.ERROR_MESSAGE);

        } catch (ObraNaoEncontradaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // TOP OBRAS MAIS BEM AVALIADAS
    private void top() {
        StringBuilder sb = new StringBuilder();

        for (Obra o : sistema.topObras()) {
            sb.append(o.getTitulo())
                    .append(" -> ")
                    .append(o.mediaAvaliacoes())
                    .append("\n");
        }

        areaTextoExibicao.setText(sb.toString());
    }

    // DESATIVAR / REMOVER
    private void desativar() {
        String t = JOptionPane.showInputDialog("Título:");
        sistema.removerObra(t);
        salvarEstado();
        listar();
    }

    private void remover() {
        String t = JOptionPane.showInputDialog("Título:");

        for (Obra o : sistema.listarObras()) {
            if (o.getTitulo().equalsIgnoreCase(t)) {
                o.setAtiva(false);
            }
        }

        salvarEstado();
        listar();
    }
}
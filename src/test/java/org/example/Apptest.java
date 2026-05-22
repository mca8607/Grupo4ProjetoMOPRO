package org.example;

import org.example.exceptions.ClassificacaoDuplicadaException;
import org.example.exceptions.JaVistoException;
import org.example.exceptions.RecursoNaoVistoException;
import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private DB imdb;
    private Espectador ana;
    private Espectador pedro;
    private Admin admin;
    private Filme padrinho;
    private Filme inception;
    private Episodios ep1;

    @BeforeEach
    public void setUp() {
        imdb = new DB("www.imdb.com");

        // Utilizadores
        ana = new Espectador("ana@email.com", "ana", "abc");
        pedro = new Espectador("pedro@email.com", "pedro", "qwerty");
        admin = new Admin("admin@email.com", "admin", "admin");
        imdb.adicionarUtilizador(ana);
        imdb.adicionarUtilizador(pedro);
        imdb.adicionarUtilizador(admin);

        // Filmes
        List<Genero> generos1 = new ArrayList<>();
        generos1.add(Genero.DRAMA);
        generos1.add(Genero.THRILLER);
        padrinho = new Filme("O Padrinho", "Drama criminal", "1972", "175 min", generos1);

        List<Genero> generos2 = new ArrayList<>();
        generos2.add(Genero.SCI_FI);
        generos2.add(Genero.ACTION);
        inception = new Filme("Inception", "Thriller sci-fi", "2010", "148 min", generos2);

        imdb.adicionarRecurso(padrinho);
        imdb.adicionarRecurso(inception);

        // Episódio
        ep1 = new Episodios("Piloto");
    }

    // =========================================================
    // TESTES DE LOGIN
    // =========================================================

    /**
     * Testa que o login com credenciais corretas devolve o utilizador.
     */
    @Test
    public void testLoginCorreto() {
        UtilizadorRegistado resultado = imdb.login("ana", "abc");
        assertNotNull(resultado);
        assertEquals("ana", resultado.getNome());
    }

    /**
     * Testa que o login com password errada devolve null.
     */
    @Test
    public void testLoginPasswordErrada() {
        UtilizadorRegistado resultado = imdb.login("ana", "errada");
        assertNull(resultado);
    }

    /**
     * Testa que o login com username inexistente devolve null.
     */
    @Test
    public void testLoginUsernameInexistente() {
        UtilizadorRegistado resultado = imdb.login("naoexiste", "abc");
        assertNull(resultado);
    }

    /**
     * Testa que o admin é reconhecido como instância de Admin.
     */
    @Test
    public void testLoginAdmin() {
        UtilizadorRegistado resultado = imdb.login("admin", "admin");
        assertNotNull(resultado);
        assertInstanceOf(Admin.class, resultado);
    }

    // =========================================================
    // TESTES DE MARCAR COMO VISTO
    // =========================================================

    /**
     * Testa que marcar um filme como visto funciona corretamente.
     */
    @Test
    public void testMarcarFilmeComoVisto() throws Exception {
        padrinho.marcarComoVisto(ana);
        assertTrue(padrinho.isVisto(ana));
        assertTrue(ana.jaViu(padrinho));
    }

    /**
     * Testa que marcar o mesmo filme duas vezes lança JaVistoException.
     */
    @Test
    public void testMarcarFilmeComoVistoJaVisto() throws Exception {
        padrinho.marcarComoVisto(ana);
        assertThrows(JaVistoException.class, () -> padrinho.marcarComoVisto(ana));
    }

    /**
     * Testa que marcar um episódio como visto funciona corretamente.
     */
    @Test
    public void testMarcarEpisodioComoVisto() throws Exception {
        ep1.marcarComoVisto(ana);
        assertTrue(ep1.isVisto(ana));
        assertTrue(ana.jaViu(ep1));
    }

    /**
     * Testa que marcar o mesmo episódio duas vezes lança JaVistoException.
     */
    @Test
    public void testMarcarEpisodioComoVistoJaVisto() throws Exception {
        ep1.marcarComoVisto(ana);
        assertThrows(JaVistoException.class, () -> ep1.marcarComoVisto(ana));
    }

    /**
     * Testa que dois espectadores diferentes podem marcar o mesmo filme como visto.
     */
    @Test
    public void testDoisEspectadoresFilmeVisto() throws Exception {
        padrinho.marcarComoVisto(ana);
        padrinho.marcarComoVisto(pedro);
        assertTrue(padrinho.isVisto(ana));
        assertTrue(padrinho.isVisto(pedro));
    }

    // =========================================================
    // TESTES DE CLASSIFICAÇÃO
    // =========================================================

    /**
     * Testa que classificar um filme já visto funciona corretamente.
     */
    @Test
    public void testClassificarFilme() throws Exception {
        padrinho.marcarComoVisto(ana);
        ana.classificarFilme(padrinho, 9, "Obra-prima!");
        assertEquals(9.0, padrinho.getClassificacaoMedia(), 0.01);
    }

    /**
     * Testa que classificar um filme sem o ter visto lança RecursoNaoVistoException.
     */
    @Test
    public void testClassificarFilmeSemVerLanca() {
        assertThrows(RecursoNaoVistoException.class,
                () -> ana.classificarFilme(padrinho, 8, ""));
    }

    /**
     * Testa que classificar o mesmo filme duas vezes lança ClassificacaoDuplicadaException.
     */
    @Test
    public void testClassificarFilmeDuasVezesLanca() throws Exception {
        padrinho.marcarComoVisto(ana);
        ana.classificarFilme(padrinho, 9, "Ótimo");
        assertThrows(ClassificacaoDuplicadaException.class,
                () -> ana.classificarFilme(padrinho, 7, "Segunda vez"));
    }

    /**
     * Testa que a nota inválida (fora de 1-10) lança IllegalArgumentException.
     */
    @Test
    public void testClassificacaoNotaInvalida() throws Exception {
        padrinho.marcarComoVisto(ana);
        assertThrows(IllegalArgumentException.class,
                () -> ana.classificarFilme(padrinho, 11, ""));
    }

    /**
     * Testa que a média de classificações é calculada corretamente com vários espectadores.
     */
    @Test
    public void testClassificacaoMediaVariosEspectadores() throws Exception {
        padrinho.marcarComoVisto(ana);
        padrinho.marcarComoVisto(pedro);
        ana.classificarFilme(padrinho, 8, "");
        pedro.classificarFilme(padrinho, 6, "");
        assertEquals(7.0, padrinho.getClassificacaoMedia(), 0.01);
    }

    /**
     * Testa que classificar um episódio sem o ter visto lança RecursoNaoVistoException.
     */
    @Test
    public void testClassificarEpisodioSemVerLanca() {
        assertThrows(RecursoNaoVistoException.class,
                () -> ana.classificarEpisodio(ep1, 8, ""));
    }

    // =========================================================
    // TESTES DE LISTA PESSOAL
    // =========================================================

    /**
     * Testa que adicionar um filme à lista pessoal funciona.
     */
    @Test
    public void testAdicionarFilmeListaPessoal() {
        ana.adicionarFilmeListaPessoal(padrinho);
        assertTrue(ana.consultarListaPessoal().contains("O Padrinho"));
    }

    /**
     * Testa que o mesmo filme não é adicionado duas vezes à lista pessoal.
     */
    @Test
    public void testAdicionarFilmeDuplicadoListaPessoal() {
        ana.adicionarFilmeListaPessoal(padrinho);
        ana.adicionarFilmeListaPessoal(padrinho);
        // Conta apenas uma ocorrência
        String lista = ana.consultarListaPessoal();
        int count = lista.split("O Padrinho", -1).length - 1;
        assertEquals(1, count);
    }

    /**
     * Testa que remover um filme da lista pessoal funciona.
     */
    @Test
    public void testRemoverFilmeListaPessoal() {
        ana.adicionarFilmeListaPessoal(padrinho);
        ana.removerFilmeListaPessoal(padrinho);
        assertFalse(ana.consultarListaPessoal().contains("O Padrinho"));
    }

    /**
     * Testa que adicionar um episódio à lista pessoal funciona.
     */
    @Test
    public void testAdicionarEpisodioListaPessoal() {
        ana.adicionarEpisodioListaPessoal(ep1);
        assertTrue(ana.consultarListaPessoal().contains("Piloto"));
    }

    // =========================================================
    // TESTES DE FILMES VISTOS
    // =========================================================

    /**
     * Testa que o contador de filmes vistos é atualizado corretamente.
     */
    @Test
    public void testNumFilmesVistos() throws Exception {
        assertEquals(0, ana.getNumFilmesVistos());
        padrinho.marcarComoVisto(ana);
        assertEquals(1, ana.getNumFilmesVistos());
        inception.marcarComoVisto(ana);
        assertEquals(2, ana.getNumFilmesVistos());
    }

    // =========================================================
    // TESTES DE DUPLICADOS NA DB
    // =========================================================

    /**
     * Testa que não é possível adicionar dois filmes com o mesmo título e ano.
     */
    @Test
    public void testFilmeDuplicadoNaoAdicionado() {
        int antes = imdb.getLstRecursos().size();
        List<Genero> g = new ArrayList<>();
        g.add(Genero.DRAMA);
        imdb.adicionarRecurso(new Filme("O Padrinho", "Cópia", "1972", "100 min", g));
        assertEquals(antes, imdb.getLstRecursos().size());
    }

    /**
     * Testa que é possível adicionar dois filmes com o mesmo título mas anos diferentes.
     */
    @Test
    public void testFilmeMesmoTituloAnosDiferentes() {
        int antes = imdb.getLstRecursos().size();
        List<Genero> g = new ArrayList<>();
        g.add(Genero.DRAMA);
        imdb.adicionarRecurso(new Filme("O Padrinho", "Parte II", "1974", "200 min", g));
        assertEquals(antes + 1, imdb.getLstRecursos().size());
    }

    // =========================================================
    // TESTES DE PESQUISA
    // =========================================================

    /**
     * Testa que a pesquisa por título (parcial) devolve resultados corretos.
     */
    @Test
    public void testPesquisaFilmePorTitulo() {
        assertTrue(padrinho.correspondePesquisa("padrinho"));
        assertTrue(padrinho.correspondePesquisa("PADRINHO"));
        assertFalse(padrinho.correspondePesquisa("inception"));
    }

    /**
     * Testa que a pesquisa por nome de ator funciona corretamente.
     */
    @Test
    public void testPesquisaAtorPorNome() {
        org.example.utils.Data dataFicticia = new org.example.utils.Data(1977, 9, 15);
        Ator ator = new Ator("Tom Hardy", dataFicticia);

        assertTrue(ator.correspondePesquisa("tom"));
        assertTrue(ator.correspondePesquisa("TOM HARDY"));
        assertFalse(ator.correspondePesquisa("Pierce"));
    }
    // =========================================================
    // TESTES DE GÉNEROS
    // =========================================================

    /**
     * Testa que criar um recurso sem géneros lança IllegalArgumentException.
     */
    @Test
    public void testCriarFilmeSemGenerosLanca() {
        assertThrows(IllegalArgumentException.class,
                () -> new Filme("Sem Género", "desc", "2020", "90 min", new ArrayList<>()));
    }

    /**
     * Testa que criar um recurso com géneros funciona corretamente.
     */
    @Test
    public void testCriarFilmeComGeneros() {
        List<Genero> g = new ArrayList<>();
        g.add(Genero.ACTION);
        Filme f = new Filme("Ação Total", "desc", "2020", "90 min", g);
        assertEquals(1, f.getGeneros().size());
        assertEquals(Genero.ACTION, f.getGeneros().get(0));
    }

    // =========================================================
    // TESTES DE LISTAGENS ORDENADAS
    // =========================================================

    /**
     * Testa que a listagem de filmes por título está ordenada alfabeticamente.
     */
    @Test
    public void testListagemFilmesPorTitulo() throws Exception {
        String resultado = imdb.listarFilmesPorTitulo();
        int posI = resultado.indexOf("Inception");
        int posP = resultado.indexOf("O Padrinho");
        assertTrue(posI < posP);
    }

    /**
     * Testa que a listagem de filmes por classificação coloca o melhor em primeiro.
     */
    @Test
    public void testListagemFilmesPorClassificacao() throws Exception {
        padrinho.marcarComoVisto(ana);
        ana.classificarFilme(padrinho, 9, "");
        inception.marcarComoVisto(ana);
        ana.classificarFilme(inception, 5, "");

        String resultado = imdb.listarFilmesPorClassificacaoMedia();
        int posP = resultado.indexOf("O Padrinho");
        int posI = resultado.indexOf("Inception");
        assertTrue(posP < posI);
    }

    /**
     * Testa que a listagem de utilizadores por filmes vistos está ordenada corretamente.
     */
    @Test
    public void testListagemUtilizadoresPorFilmesVistos() throws Exception {
        padrinho.marcarComoVisto(pedro);
        inception.marcarComoVisto(pedro);
        padrinho.marcarComoVisto(ana);

        String resultado = imdb.listarUtilizadoresPorFilmesVistos();
        int posPedro = resultado.indexOf("pedro");
        int posAna = resultado.indexOf("ana");
        assertTrue(posPedro < posAna);
    }
}
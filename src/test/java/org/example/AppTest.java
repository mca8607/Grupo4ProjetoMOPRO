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

/**
 * Classe de testes unitários para validação global das regras de negócio do sistema IMDB,
 * cobrindo a gestão de utilizadores, recursos, listas pessoais, pesquisas e classificações.
 */
public class AppTest {

    private DB imdb;
    private Espectador ana;
    private Espectador pedro;
    private Admin admin;
    private Filme padrinho;
    private Filme inception;
    private Episodios ep1;

    /**
     * Configura o ambiente de testes antes da execução de cada método de teste.
     * Inicializa a base de dados, adiciona utilizadores (Admin e Espectadores),
     * géneros e recursos de teste.
     */
    @BeforeEach
    public void setUp() {
        imdb = new DB("www.imdb.com");

        ana = new Espectador("ana@email.com", "ana", "abc");
        pedro = new Espectador("pedro@email.com", "pedro", "qwerty");
        admin = new Admin("admin@email.com", "admin", "admin");
        imdb.adicionarUtilizador(ana);
        imdb.adicionarUtilizador(pedro);
        imdb.adicionarUtilizador(admin);

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

        ep1 = new Episodios("Piloto");
    }

    // =========================================================
    // TESTES DE LOGIN
    // =========================================================


    /**
     * Testa o procedimento de login efetuado com credenciais válidas.
     */
    @Test
    public void testLoginCorreto() {
        UtilizadorRegistado resultado = imdb.login("ana", "abc");
        assertNotNull(resultado);
        assertEquals("ana", resultado.getNome());
    }


    /**
     * Testa se o sistema rejeita o login quando a password fornecida está incorreta.
     */
    @Test
    public void testLoginPasswordErrada() {
        UtilizadorRegistado resultado = imdb.login("ana", "errada");
        assertNull(resultado);
    }


    /**
     * Testa se o sistema rejeita o login para um username que não existe no sistema.
     */
    @Test
    public void testLoginUsernameInexistente() {
        UtilizadorRegistado resultado = imdb.login("naoexiste", "abc");
        assertNull(resultado);
    }


    /**
     * Testa se um administrador consegue efetuar login com sucesso e se a sua
     * instância pertence de facto à classe Admin.
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
     * Testa a marcação de um filme como visto por um espectador.
     *
     * @throws Exception Caso ocorra algum erro inesperado durante o processo.
     */
    @Test
    public void testMarcarFilmeComoVisto() throws Exception {
        padrinho.marcarComoVisto(ana);
        assertTrue(padrinho.isVisto(ana));
        assertTrue(ana.jaViu(padrinho));
    }


    /**
     * Testa se o sistema lança a exceção adequada ao tentar marcar como visto
     * um filme que o espectador já visualizou anteriormente.
     */
    @Test
    public void testMarcarFilmeComoVistoJaVisto() throws Exception {
        padrinho.marcarComoVisto(ana);
        assertThrows(JaVistoException.class, () -> padrinho.marcarComoVisto(ana));
    }

    /**
     * Testa a marcação de um episódio como visto por um espectador.
     *
     * @throws Exception Caso ocorra algum erro inesperado durante o processo.
     */
    @Test
    public void testMarcarEpisodioComoVisto() throws Exception {
        ep1.marcarComoVisto(ana);
        assertTrue(ep1.isVisto(ana));
        assertTrue(ana.jaViu(ep1));
    }


    /**
     * Testa se o sistema lança a exceção adequada ao tentar marcar como visto
     * um episódio que o espectador já visualizou.
     */
    @Test
    public void testMarcarEpisodioComoVistoJaVisto() throws Exception {
        ep1.marcarComoVisto(ana);
        assertThrows(JaVistoException.class, () -> ep1.marcarComoVisto(ana));
    }


    /**
     * Testa o comportamento de visualização independente de um mesmo filme
     * por parte de dois espectadores distintos.
     *
     * @throws Exception Caso ocorra algum erro inesperado durante o processo.
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
     * Testa o fluxo padrão de classificação de um filme após este ter sido visto.
     *
     * @throws Exception Caso ocorra algum erro inesperado durante o processo.
     */
    @Test
    public void testClassificarFilme() throws Exception {
        padrinho.marcarComoVisto(ana);
        ana.classificarFilme(padrinho, 9, "Obra-prima!");
        assertEquals(9.0, padrinho.getClassificacaoMedia(), 0.01);
    }


    /**
     * Testa se o sistema impede um utilizador de classificar um filme que ainda não viu.
     */
    @Test
    public void testClassificarFilmeSemVerLanca() {
        assertThrows(RecursoNaoVistoException.class,
                () -> ana.classificarFilme(padrinho, 8, ""));
    }

    /**
     * Testa se o sistema impede a submissão de múltiplas classificações do mesmo utilizador
     * para o mesmo filme.
     *
     * @throws Exception Caso ocorra algum erro inesperado durante o processo.
     */
    @Test
    public void testClassificarFilmeDuasVezesLanca() throws Exception {
        padrinho.marcarComoVisto(ana);
        ana.classificarFilme(padrinho, 9, "Ótimo");
        assertThrows(ClassificacaoDuplicadaException.class,
                () -> ana.classificarFilme(padrinho, 7, "Segunda vez"));
    }


    /**
     * Testa a validação dos limites da nota inserida, garantindo que valores inválidos
     * disparam uma exceção apropriada.
     *
     * @throws Exception Caso ocorra algum erro inesperado durante o processo.
     */
    @Test
    public void testClassificacaoNotaInvalida() throws Exception {
        padrinho.marcarComoVisto(ana);
        assertThrows(IllegalArgumentException.class,
                () -> ana.classificarFilme(padrinho, 11, ""));
    }


    /**
     * Testa o cálculo do desvio e média ponderada das notas atribuídas por vários utilizadores
     * distintos ao mesmo filme.
     *
     * @throws Exception Caso ocorra algum erro inesperado durante o processo.
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
     * Testa se o sistema impede a classificação de um episódio sem que este tenha sido visto.
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
     * Testa a inserção com sucesso de um filme na lista pessoal do espectador.
     */
    @Test
    public void testAdicionarFilmeListaPessoal() {
        ana.adicionarFilmeListaPessoal(padrinho);
        assertTrue(ana.consultarListaPessoal().contains("O Padrinho"));
    }


    /**
     * Testa o comportamento de rejeição ou tratamento de duplicações ao tentar adicionar
     * o mesmo filme à lista pessoal mais do que uma vez.
     */
    @Test
    public void testAdicionarFilmeDuplicadoListaPessoal() {
        ana.adicionarFilmeListaPessoal(padrinho);
        ana.adicionarFilmeListaPessoal(padrinho);
        String lista = ana.consultarListaPessoal();
        int count = lista.split("O Padrinho", -1).length - 1;
        assertEquals(1, count);
    }


    /**
     * Testa a remoção com sucesso de um filme previamente inserido na lista pessoal do utilizador.
     */
    @Test
    public void testRemoverFilmeListaPessoal() {
        ana.adicionarFilmeListaPessoal(padrinho);
        ana.removerFilmeListaPessoal(padrinho);
        assertFalse(ana.consultarListaPessoal().contains("O Padrinho"));
    }


    /**
     * Testa a inserção de um episódio na lista pessoal do espectador.
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
     * Testa se o contador de filmes vistos incrementa devidamente à medida que o utilizador
     * assiste a novas produções.
     *
     * @throws Exception Caso ocorra algum erro inesperado durante o processo.
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
     * Testa se recursos duplicados ou cópias idênticas são barrados de entrar na listagem geral de recursos.
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
     * Testa o correto armazenamento e distinção de filmes com o mesmo título mas lançados
     * em anos cronológicos diferentes.
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
     * Testa o mecanismo de pesquisa de filmes por correspondência textual do título.
     */
    @Test
    public void testPesquisaFilmePorTitulo() {
        assertTrue(padrinho.correspondePesquisa("padrinho"));
        assertTrue(padrinho.correspondePesquisa("PADRINHO"));
        assertFalse(padrinho.correspondePesquisa("inception"));
    }


    /**
     * Testa a pesquisa e correspondência de atores pelo seu nome completo.
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
     * Testa se a criação de um filme sem qualquer género associado gera a devida exceção.
     */
    @Test
    public void testCriarFilmeSemGenerosLanca() {
        assertThrows(IllegalArgumentException.class,
                () -> new Filme("Sem Género", "desc", "2020", "90 min", new ArrayList<>()));
    }


    /**
     * Testa a correta instanciação e obtenção dos géneros associados a um filme criado validamente.
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
     * Testa o método de listagem textual de filmes ordenados por título.
     */
    @Test
    public void testListagemFilmesPorTitulo() throws Exception {
        String resultado = imdb.listarFilmesPorTitulo();
        int posI = resultado.indexOf("Inception");
        int posP = resultado.indexOf("O Padrinho");
        assertTrue(posI < posP);
    }


    /**
     * Testa o método de listagem dos filmes baseado no ranking de classificação média.
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
     * Testa o método de listagem de utilizadores com base na quantidade decrescente de filmes vistos.
     *
     * @throws Exception Caso ocorra algum erro inesperado durante o processo.
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
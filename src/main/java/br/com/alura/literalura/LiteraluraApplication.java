package br.com.alura.literalura;

import br.com.alura.literalura.model.*;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private AutorRepository autorRepository;

	private ConsumoApi consumoApi = new ConsumoApi();
	private ConverteDados converteDados = new ConverteDados();
	private Scanner leitura = new Scanner(System.in);
	private final String ENDERECO = "https://gutendex.com/books/";

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		exibeMenu();
	}

	private void exibeMenu() {
		var opcao = -1;
		while (opcao != 0) {
			var menu = """
					\n*** LITERALURA - CATÁLOGO DE LIVROS ***
					
					1 - Buscar livro pelo título
					2 - Listar livros registrados
					3 - Listar autores registrados
					4 - Listar autores vivos em determinado ano
					5 - Listar livros em determinado idioma
					6 - Estatísticas de downloads
					7 - Top 10 livros mais baixados
					8 - Buscar autor por nome
					9 - Estatísticas por idioma
					
					0 - Sair
					
					Escolha uma opção: """;

			System.out.print(menu);

			try {
				opcao = leitura.nextInt();
				leitura.nextLine(); // Consumir a quebra de linha

				switch (opcao) {
					case 1:
						buscarLivroPorTitulo();
						break;
					case 2:
						listarLivrosRegistrados();
						break;
					case 3:
						listarAutoresRegistrados();
						break;
					case 4:
						listarAutoresVivosEmAno();
						break;
					case 5:
						listarLivrosPorIdioma();
						break;
					case 6:
						exibirEstatisticasDownloads();
						break;
					case 7:
						exibirTop10Livros();
						break;
					case 8:
						buscarAutorPorNome();
						break;
					case 9:
						exibirEstatisticasPorIdioma();
						break;
					case 0:
						System.out.println("Encerrando a aplicação...");
						break;
					default:
						System.out.println("Opção inválida! Tente novamente.");
				}
			} catch (Exception e) {
				System.out.println("Entrada inválida! Por favor, digite um número.");
				leitura.nextLine(); // Limpar buffer
				opcao = -1;
			}
		}
	}

	private void buscarLivroPorTitulo() {
		System.out.print("Digite o título do livro que deseja buscar: ");
		var tituloLivro = leitura.nextLine();

		// Verificar se o livro já existe no banco
		Optional<Livro> livroExistente = livroRepository.findByTituloContainingIgnoreCase(tituloLivro);
		if (livroExistente.isPresent()) {
			System.out.println("Livro já está registrado no banco de dados:");
			System.out.println(livroExistente.get());
			return;
		}

		// Buscar na API
		var json = consumoApi.obterDados(ENDERECO + "?search=" + tituloLivro.replace(" ", "%20"));
		var dadosResposta = converteDados.obterDados(json, DadosResultado.class);

		if (dadosResposta.resultados().isEmpty()) {
			System.out.println("Livro não encontrado!");
			return;
		}

		// Pegar o primeiro resultado
		DadosLivro dadosLivro = dadosResposta.resultados().get(0);

		try {
			// Verificar se o autor já existe no banco
			Autor autorExistente = null;
			if (dadosLivro.autores() != null && !dadosLivro.autores().isEmpty()) {
				String nomeAutor = dadosLivro.autores().get(0).nome();
				Optional<Autor> autorOpt = autorRepository.findByNomeContainingIgnoreCase(nomeAutor);
				if (autorOpt.isPresent()) {
					autorExistente = autorOpt.get();
				}
			}

			Livro livro = new Livro(dadosLivro);

			// Se o autor já existe, usar o existente
			if (autorExistente != null) {
				livro.setAutor(autorExistente);
			}

			livroRepository.save(livro);
			System.out.println("Livro salvo com sucesso!");
			System.out.println(livro);
		} catch (Exception e) {
			System.out.println("Erro ao salvar o livro: " + e.getMessage());
		}
	}

	private void listarLivrosRegistrados() {
		List<Livro> livros = livroRepository.findAll();
		if (livros.isEmpty()) {
			System.out.println("Nenhum livro registrado no banco de dados.");
			return;
		}

		System.out.println("\n*** LIVROS REGISTRADOS ***");
		livros.forEach(System.out::println);
	}

	private void listarAutoresRegistrados() {
		List<Autor> autores = autorRepository.findAll();
		if (autores.isEmpty()) {
			System.out.println("Nenhum autor registrado no banco de dados.");
			return;
		}

		System.out.println("\n*** AUTORES REGISTRADOS ***");
		autores.forEach(autor -> {
			System.out.println(autor);
			System.out.println("Livros: " + autor.getLivros().size());
			autor.getLivros().forEach(livro -> System.out.println(" - " + livro.getTitulo()));
			System.out.println();
		});
	}

	private void listarAutoresVivosEmAno() {
		System.out.print("Digite o ano para verificar quais autores estavam vivos: ");
		try {
			var ano = leitura.nextInt();
			leitura.nextLine();

			List<Autor> autoresVivos = autorRepository.findAutoresVivosNoAno(ano);

			if (autoresVivos.isEmpty()) {
				System.out.println("Nenhum autor encontrado vivo no ano " + ano);
				return;
			}

			System.out.println("\n*** AUTORES VIVOS EM " + ano + " ***");
			autoresVivos.forEach(autor -> {
				System.out.println(autor);
				System.out.println();
			});
		} catch (Exception e) {
			System.out.println("Ano inválido!");
			leitura.nextLine();
		}
	}

	private void listarLivrosPorIdioma() {
		var menuIdiomas = """
				\nEscolha o idioma:
				1 - Português (pt)
				2 - Inglês (en)
				3 - Espanhol (es)
				4 - Francês (fr)
				5 - Outro idioma
				
				Digite a opção: """;

		System.out.print(menuIdiomas);

		try {
			var opcaoIdioma = leitura.nextInt();
			leitura.nextLine();

			String idioma = "";
			switch (opcaoIdioma) {
				case 1 -> idioma = "pt";
				case 2 -> idioma = "en";
				case 3 -> idioma = "es";
				case 4 -> idioma = "fr";
				case 5 -> {
					System.out.print("Digite o código do idioma (ex: de, it, ru): ");
					idioma = leitura.nextLine();
				}
				default -> {
					System.out.println("Opção inválida!");
					return;
				}
			}

			List<Livro> livrosPorIdioma = livroRepository.findByIdioma(idioma);

			if (livrosPorIdioma.isEmpty()) {
				System.out.println("Nenhum livro encontrado no idioma: " + idioma);
				return;
			}

			System.out.println("\n*** LIVROS EM " + idioma.toUpperCase() + " ***");
			livrosPorIdioma.forEach(System.out::println);
		} catch (Exception e) {
			System.out.println("Entrada inválida!");
			leitura.nextLine();
		}
	}

	private void exibirEstatisticasDownloads() {
		List<Livro> livros = livroRepository.findAll();
		if (livros.isEmpty()) {
			System.out.println("Nenhum livro registrado para calcular estatísticas.");
			return;
		}

		DoubleSummaryStatistics stats = livros.stream()
				.filter(l -> l.getNumeroDeDownloads() != null)
				.mapToDouble(Livro::getNumeroDeDownloads)
				.summaryStatistics();

		System.out.println("\n*** ESTATÍSTICAS DE DOWNLOADS ***");
		System.out.println("Quantidade de livros: " + stats.getCount());
		System.out.println("Média de downloads: " + String.format("%.2f", stats.getAverage()));
		System.out.println("Máximo de downloads: " + String.format("%.0f", stats.getMax()));
		System.out.println("Mínimo de downloads: " + String.format("%.0f", stats.getMin()));
		System.out.println("Total de downloads: " + String.format("%.0f", stats.getSum()));
	}

	private void exibirTop10Livros() {
		List<Livro> top10 = livroRepository.findTop10ByOrderByNumeroDeDownloadsDesc();

		if (top10.isEmpty()) {
			System.out.println("Nenhum livro registrado no banco de dados.");
			return;
		}

		System.out.println("\n*** TOP 10 LIVROS MAIS BAIXADOS ***");
		for (int i = 0; i < top10.size(); i++) {
			Livro livro = top10.get(i);
			System.out.printf("%d. %s - %.0f downloads\n",
				i + 1, livro.getTitulo(), livro.getNumeroDeDownloads());
		}
	}

	private void buscarAutorPorNome() {
		System.out.print("Digite o nome do autor: ");
		var nomeAutor = leitura.nextLine();

		Optional<Autor> autorEncontrado = autorRepository.findByNomeContainingIgnoreCase(nomeAutor);

		if (autorEncontrado.isEmpty()) {
			System.out.println("Autor não encontrado no banco de dados.");
			return;
		}

		Autor autor = autorEncontrado.get();
		System.out.println("\n*** AUTOR ENCONTRADO ***");
		System.out.println(autor);
		System.out.println("\nLivros do autor:");
		autor.getLivros().forEach(livro -> System.out.println("- " + livro.getTitulo()));
	}

	private void exibirEstatisticasPorIdioma() {
		var menuIdiomas = """
				\nEscolha o idioma para estatísticas:
				1 - Português (pt)
				2 - Inglês (en)
				3 - Espanhol (es)
				4 - Francês (fr)
				
				Digite a opção: """;

		System.out.print(menuIdiomas);

		try {
			var opcaoIdioma = leitura.nextInt();
			leitura.nextLine();

			String idioma = "";
			String nomeIdioma = "";

			switch (opcaoIdioma) {
				case 1 -> {
					idioma = "pt";
					nomeIdioma = "Português";
				}
				case 2 -> {
					idioma = "en";
					nomeIdioma = "Inglês";
				}
				case 3 -> {
					idioma = "es";
					nomeIdioma = "Espanhol";
				}
				case 4 -> {
					idioma = "fr";
					nomeIdioma = "Francês";
				}
				default -> {
					System.out.println("Opção inválida!");
					return;
				}
			}

			Long quantidadeLivros = livroRepository.countByIdioma(idioma);
			List<Livro> livrosIdioma = livroRepository.findByIdioma(idioma);

			System.out.printf("\n*** ESTATÍSTICAS PARA %s ***\n", nomeIdioma.toUpperCase());
			System.out.printf("Quantidade de livros em %s: %d\n", nomeIdioma, quantidadeLivros);

			if (!livrosIdioma.isEmpty()) {
				DoubleSummaryStatistics stats = livrosIdioma.stream()
						.filter(l -> l.getNumeroDeDownloads() != null)
						.mapToDouble(Livro::getNumeroDeDownloads)
						.summaryStatistics();

				System.out.printf("Média de downloads: %.2f\n", stats.getAverage());
				System.out.printf("Total de downloads: %.0f\n", stats.getSum());
			}
		} catch (Exception e) {
			System.out.println("Entrada inválida!");
			leitura.nextLine();
		}
	}
}

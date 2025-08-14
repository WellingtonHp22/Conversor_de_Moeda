package br.com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    private String idioma;
    private Double numeroDeDownloads;

    public Livro() {}

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();

        // Pega apenas o primeiro autor da lista
        if (dadosLivro.autores() != null && !dadosLivro.autores().isEmpty()) {
            this.autor = new Autor(dadosLivro.autores().get(0));
        }

        // Pega apenas o primeiro idioma da lista
        if (dadosLivro.idiomas() != null && !dadosLivro.idiomas().isEmpty()) {
            this.idioma = dadosLivro.idiomas().get(0);
        }

        this.numeroDeDownloads = dadosLivro.numeroDeDownloads();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDeDownloads() {
        return numeroDeDownloads;
    }

    public void setNumeroDeDownloads(Double numeroDeDownloads) {
        this.numeroDeDownloads = numeroDeDownloads;
    }

    @Override
    public String toString() {
        return String.format(
            "\n--- LIVRO ---" +
            "\nTítulo: %s" +
            "\nAutor: %s" +
            "\nIdioma: %s" +
            "\nNúmero de downloads: %.0f" +
            "\n-----------\n",
            titulo, autor != null ? autor.getNome() : "N/A", idioma, numeroDeDownloads
        );
    }
}

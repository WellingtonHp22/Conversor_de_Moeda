package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT a FROM Autor a WHERE (a.anoNascimento IS NULL OR a.anoNascimento <= :ano) AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    List<Autor> findAutoresVivosNoAno(@Param("ano") Integer ano);
}

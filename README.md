# 📚 LiterAlura - Catálogo de Livros

![Java](https://img.shields.io/badge/Java-17+-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-green.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16+-blue.svg)
![Maven](https://img.shields.io/badge/Maven-4.0+-red.svg)

## 📖 Sobre o Projeto

O **LiterAlura** é um catálogo de livros pessoal que funciona via console (terminal), desenvolvido como parte do desafio de programação da Alura. O programa se conecta à API **Gutendx** (Project Gutenberg), salva os dados em um banco PostgreSQL e permite interação através de um menu de opções.

## ✨ Funcionalidades

### 🎯 Funcionalidades Obrigatórias (Backlog do Projeto)
**✅ TODAS AS 5 FUNCIONALIDADES IMPLEMENTADAS:**

1. **Buscar livro pelo título** - Consulta a API externa e salva livro + autor no banco
2. **Listar livros registrados** - Exibe todos os livros salvos no banco de dados
3. **Listar autores registrados** - Exibe todos os autores salvos no banco de dados  
4. **Listar autores vivos em determinado ano** - Pesquisa autores vivos no ano informado
5. **Listar livros em determinado idioma** - Pesquisa por idioma específico (pt, en, es, fr)

### 🚀 Desafios Opcionais Implementados
**✅ TODOS OS DESAFIOS EXTRAS CONCLUÍDOS:**

6. **Gerar Estatísticas** - Utiliza `DoubleSummaryStatistics` para dados dos livros
7. **Top 10 Livros Mais Baixados** - Consulta dos livros mais populares
8. **Buscar Autor por Nome** - Busca de autores diretamente no banco de dados
9. **Consultas Avançadas de Autores** - Filtros por anos de nascimento/falecimento

## 🛠️ Tecnologias Utilizadas (Conforme Especificação)

- **Java 17** - Versão LTS mais recente
- **Spring Boot 3.2.3** - Framework principal (versão exata especificada)
- **Spring Data JPA** - Persistência de dados com repositórios
- **PostgreSQL 16+** - Banco de dados relacional
- **Maven 4+** - Gerenciamento de dependências
- **Jackson** - Mapeamento JSON → Objetos Java
- **Gutendx API** - API gratuita do Project Gutenberg (70mil+ livros)

## 📋 Roteiro de Desenvolvimento Seguido

### ✅ Passo 1: Consumindo a API Gutendx
- **HttpClient, HttpRequest, HttpResponse** implementados
- **URL base**: `https://gutendx.com/books/`
- **Sem chave de acesso** necessária

### ✅ Passo 2: Mapeamento JSON para Objetos Java  
- **Classes de dados**: `DadosLivro`, `DadosAutor`, `DadosResultado`
- **Anotações**: `@JsonAlias`, `@JsonIgnoreProperties`
- **Conversão automática** de JSON para objetos

### ✅ Passo 3: Persistência com PostgreSQL
- **Entidades JPA**: `Livro`, `Autor` com anotações corretas
- **Repositórios**: `LivroRepository`, `AutorRepository` extends `JpaRepository`
- **Regra de simplificação**: 1 autor e 1 idioma por livro (primeiro da lista)
- **Relacionamento**: Livro salvo com seu autor correspondente

### ✅ Passo 4: Interface com Usuário
- **CommandLineRunner** implementado na classe principal
- **Menu interativo** com laço `while` 
- **Scanner** para captura de entrada do usuário
- **Tratamento de erros** para entradas inválidas

## 🎮 Como Usar

Ao executar a aplicação, você verá um menu interativo:

```
*** LITERALURA - CATÁLOGO DE LIVROS ***

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

Escolha uma opção:
```

### Exemplos de Uso

1. **Buscar um livro**: Digite "1" e informe o título (ex: "Dom Casmurro")
2. **Ver estatísticas**: Digite "6" para ver estatísticas completas de downloads
3. **Filtrar por idioma**: Digite "5" e escolha o idioma desejado
4. **Autores vivos em 1900**: Digite "4" e informe o ano "1900"

## 📊 Funcionalidades Detalhadas

### Derived Queries
- `findByTituloContainingIgnoreCase()` - Busca livros por título
- `findByIdioma()` - Filtra livros por idioma
- `findAutoresVivosNoAno()` - Query customizada para autores vivos

### Estatísticas com Streams
- Uso de `DoubleSummaryStatistics` para análises completas
- Cálculo de médias, máximos, mínimos e totais
- Filtragem e mapeamento com Stream API

### Relacionamentos JPA
- **@ManyToOne**: Livro → Autor
- **@OneToMany**: Autor → Livros
- Cascade e Fetch configurados adequadamente

## 🔧 Configurações Avançadas

### Banco de Dados
- Auto-criação de tabelas com `spring.jpa.hibernate.ddl-auto=update`
- Logs SQL habilitados para debug
- Dialect PostgreSQL específico

### API Integration
- Cliente HTTP nativo do Java 11+
- Tratamento de erros e timeouts
- Conversão automática JSON → Objects

## 🧪 Testes e Validação

O projeto inclui validações para:
- Dados inválidos de entrada
- Duplicação de livros e autores
- Tratamento de exceções HTTP
- Validação de anos e idiomas

## 🎯 Desafios Implementados

✅ **Funcionalidades Obrigatórias**
- Busca e persistência de livros
- Listagem de livros e autores
- Consulta por idioma
- Autores vivos em determinado ano
- Estatísticas por idioma

✅ **Funcionalidades Opcionais**
- Estatísticas completas com `DoubleSummaryStatistics`
- Top 10 livros mais baixados
- Busca de autor por nome
- Interface de usuário rica e intuitiva

## 📝 API Utilizada

**Gutendx API**: https://gutendx.com/
- Mais de 70.000 livros do Project Gutenberg
- Dados em formato JSON
- Sem necessidade de chave de API
- Busca por título, autor e filtros diversos

## 🤝 Contribuição

Este projeto foi desenvolvido como parte do desafio **LiterAlura** da Alura, demonstrando:
- Consumo de APIs REST
- Persistência com Spring Data JPA
- Relacionamentos entre entidades
- Queries derivadas e customizadas
- Estatísticas com Streams
- Interface de linha de comando interativa

## 👨‍💻 Autor

Desenvolvido com ❤️ durante o desafio LiterAlura da Alura.

---

*"A leitura torna o homem completo; a conversa, ágil; e o escrever, preciso." - Francis Bacon*

## 🚀 Como Executar

### Pré-requisitos (Conforme Especificação)

1. **Java JDK 17+** - [Download Java LTS](https://www.oracle.com/java/technologies/javase-downloads.html)
2. **Maven 4+** - Para gerenciamento de dependências  
3. **PostgreSQL 16+** - [Download PostgreSQL](https://www.postgresql.org/download/)
4. **IDE** (Opcional) - IntelliJ IDEA ou similar

### Configuração do Projeto (Spring Initializr)

✅ **Configurações utilizadas:**
- **Project**: Maven
- **Language**: Java  
- **Spring Boot**: 3.2.3
- **Packaging**: Jar
- **Java**: 17+
- **Dependências**: Spring Data JPA, PostgreSQL Driver

### Setup do Banco de Dados

1. **Instale e inicie o PostgreSQL**
2. **Crie o banco de dados**:
```sql
CREATE DATABASE literalura;
```

3. **Configure as credenciais** em `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### Executando a Aplicação

1. **Clone/baixe** o projeto
2. **Navegue** até a pasta do projeto via terminal
3. **Execute** o comando:
```bash
mvn spring-boot:run
```

4. **Interaja** com o menu que aparecerá no console!

## 🎯 Status do Projeto - COMPLETO

### ✅ Checklist da Proposta Melhorada

**Objetivo Principal** ✅
- [x] Catálogo de livros via console
- [x] Conexão com API de livros  
- [x] Dados salvos em banco PostgreSQL
- [x] Menu interativo de opções

**5 Funcionalidades Obrigatórias** ✅ 
- [x] 1. Buscar livro pelo título
- [x] 2. Listar livros registrados  
- [x] 3. Listar autores registrados
- [x] 4. Listar autores vivos em determinado ano
- [x] 5. Listar livros em determinado idioma

**Tecnologias Especificadas** ✅
- [x] Java JDK 17+
- [x] Maven 4+
- [x] Spring Boot 3.2.3
- [x] PostgreSQL 16+
- [x] Spring Data JPA + PostgreSQL Driver

**Roteiro de Desenvolvimento** ✅
- [x] Passo 1: API Gutendx consumida
- [x] Passo 2: JSON mapeado para objetos Java
- [x] Passo 3: Persistência PostgreSQL implementada  
- [x] Passo 4: Interface com usuário funcional

**Desafios Opcionais** ✅ (TODOS IMPLEMENTADOS)
- [x] Estatísticas com DoubleSummaryStatistics
- [x] Top 10 livros mais baixados
- [x] Buscar autor por nome
- [x] Consultas avançadas de autores

**Documentação** ✅
- [x] README.md completo e detalhado

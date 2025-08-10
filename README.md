# 💱 Conversor de Moedas

Um conversor de moedas desenvolvido em Java que utiliza a ExchangeRate-API para obter taxas de câmbio em tempo real.

## 🚀 Funcionalidades

- ✅ Conversão entre 6 moedas principais
- ✅ Interface interativa via console
- ✅ Histórico das últimas conversões
- ✅ Logs com timestamp das operações
- ✅ Tratamento de erros e validações
- ✅ Consumo de API REST em tempo real

## 💰 Moedas Suportadas

- **USD** - Dólar americano
- **ARS** - Peso argentino
- **BOB** - Boliviano boliviano
- **BRL** - Real brasileiro
- **CLP** - Peso chileno
- **COP** - Peso colombiano

## 🛠️ Tecnologias Utilizadas

- **Java 11+** - Linguagem principal
- **Gson 2.10.1** - Manipulação de JSON
- **HttpClient** - Requisições HTTP
- **ExchangeRate-API** - Fonte das taxas de câmbio

## 📋 Pré-requisitos

- Java JDK 11 ou superior
- Biblioteca Gson 2.10.1+
- Chave da API do ExchangeRate-API (já configurada)

## 🎮 Como Usar

1. Execute a classe `Main.java`
2. Escolha uma opção do menu (1-7)
3. Digite o valor a ser convertido
4. Veja o resultado da conversão
5. Use a opção 6 para ver o histórico
6. Use a opção 7 para sair

## 📊 Exemplo de Uso

```
=====================================================
    BEM-VINDO AO CONVERSOR DE MOEDAS
=====================================================

1) Dólar (USD) => Real Brasileiro (BRL)
2) Real Brasileiro (BRL) => Dólar (USD)
3) Dólar (USD) => Peso Argentino (ARS)
4) Peso Argentino (ARS) => Dólar (USD)
5) Dólar (USD) => Peso Colombiano (COP)
6) Ver Histórico de Conversões
7) Sair

Digite a opção desejada: 1
Digite o valor que deseja converter: 100

Processando...

O valor de US$ 100,00 USD corresponde a R$ 542,25 BRL.
```

## 📁 Estrutura do Projeto

```
src/
├── Main.java                    # Classe principal com menu
├── CurrencyConverter.java       # Lógica de conversão
├── ExchangeRateService.java     # Cliente HTTP para API
├── ExchangeRateResponse.java    # Modelo de dados da API
└── ConversionRecord.java        # Registro de conversões
```

## 🔧 Configuração da API

O projeto utiliza a ExchangeRate-API com chave já configurada. Para usar sua própria chave:

1. Registre-se em: https://www.exchangerate-api.com/
2. Obtenha sua chave de API
3. Substitua em `ExchangeRateService.java`:

```java
private static final String API_KEY = "sua-chave-aqui";
```

## 🎨 Funcionalidades Extras

### Histórico de Conversões
- Registra automaticamente cada conversão
- Mostra as últimas 10 operações
- Inclui timestamp e taxa de câmbio

### Validação de Entrada
- Verifica valores numéricos
- Trata opções inválidas
- Gerencia erros de conexão

## 📄 Licença

Este projeto foi desenvolvido como parte do desafio da Alura e está disponível sob a licença MIT.

## 👨‍💻 Autor

Desenvolvido com ❤️ como parte do programa ONE - Oracle Next Education

---

⭐ **Gostou do projeto? Deixe uma estrela!**
## 🔧 Configuração da API

O projeto utiliza a ExchangeRate-API. Para usar sua própria chave:

1. Registre-se em: https://www.exchangerate-api.com/
2. Obtenha sua chave de API
3. Substitua em `ExchangeRateService.java`:

```java
private static final String API_KEY = "sua-chave-aqui";
```

## 🎨 Funcionalidades Extras

### Histórico de Conversões
- Registra automaticamente cada conversão
- Mostra as últimas 10 operações
- Inclui timestamp e taxa de câmbio

### Validação de Entrada
- Verifica valores numéricos
- Trata opções inválidas
- Gerencia erros de conexão

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

## 📄 Licença

Este projeto foi desenvolvido como parte do desafio da Alura e está disponível sob a licença MIT.

## 👨‍💻 Autor

Desenvolvido com ❤️ como parte do programa ONE - Oracle Next Education

---

⭐ **Gostou do projeto? Deixe uma estrela!**

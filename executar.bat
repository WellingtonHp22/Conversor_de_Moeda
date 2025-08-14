@echo off
echo ===============================================
echo        LITERALURA - SETUP E EXECUCAO
echo ===============================================
echo.
echo Este script vai configurar e executar o projeto LiterAlura
echo.

REM Limpar dependencias antigas com problemas
if exist "libs" (
    echo Limpando dependencias antigas...
    rmdir /s /q libs
)

REM Criar pasta para dependencias
mkdir libs

echo Baixando dependencias corrigidas...
echo.

REM Baixar dependencias uma por vez com verificacao
echo [1/8] Baixando PostgreSQL Driver...
curl -L --retry 3 --retry-delay 2 "https://repo1.maven.org/maven2/org/postgresql/postgresql/42.7.1/postgresql-42.7.1.jar" -o "libs/postgresql-42.7.1.jar"

echo [2/8] Baixando Jackson Databind...
curl -L --retry 3 --retry-delay 2 "https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.15.4/jackson-databind-2.15.4.jar" -o "libs/jackson-databind-2.15.4.jar"

echo [3/8] Baixando Jackson Core...
curl -L --retry 3 --retry-delay 2 "https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.15.4/jackson-core-2.15.4.jar" -o "libs/jackson-core-2.15.4.jar"

echo [4/8] Baixando Jackson Annotations...
curl -L --retry 3 --retry-delay 2 "https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.15.4/jackson-annotations-2.15.4.jar" -o "libs/jackson-annotations-2.15.4.jar"

echo [5/8] Baixando Jakarta Persistence API...
curl -L --retry 3 --retry-delay 2 "https://repo1.maven.org/maven2/jakarta/persistence/jakarta.persistence-api/3.1.0/jakarta.persistence-api-3.1.0.jar" -o "libs/jakarta.persistence-api-3.1.0.jar"

echo [6/8] Baixando Hibernate Core...
curl -L --retry 3 --retry-delay 2 "https://repo1.maven.org/maven2/org/hibernate/orm/hibernate-core/6.2.13.Final/hibernate-core-6.2.13.Final.jar" -o "libs/hibernate-core-6.2.13.Final.jar"

echo [7/8] Baixando Spring Data Commons...
curl -L --retry 3 --retry-delay 2 "https://repo1.maven.org/maven2/org/springframework/data/spring-data-commons/3.1.5/spring-data-commons-3.1.5.jar" -o "libs/spring-data-commons-3.1.5.jar"

echo [8/8] Baixando Spring Data JPA...
curl -L --retry 3 --retry-delay 2 "https://repo1.maven.org/maven2/org/springframework/data/spring-data-jpa/3.1.5/spring-data-jpa-3.1.5.jar" -o "libs/spring-data-jpa-3.1.5.jar"

echo.
echo Verificando dependencias baixadas...

REM Verificar se os arquivos foram baixados corretamente
set DEPS_OK=true
if not exist "libs\postgresql-42.7.1.jar" set DEPS_OK=false
if not exist "libs\jackson-databind-2.15.4.jar" set DEPS_OK=false
if not exist "libs\jackson-core-2.15.4.jar" set DEPS_OK=false
if not exist "libs\jackson-annotations-2.15.4.jar" set DEPS_OK=false
if not exist "libs\jakarta.persistence-api-3.1.0.jar" set DEPS_OK=false
if not exist "libs\hibernate-core-6.2.13.Final.jar" set DEPS_OK=false
if not exist "libs\spring-data-commons-3.1.5.jar" set DEPS_OK=false
if not exist "libs\spring-data-jpa-3.1.5.jar" set DEPS_OK=false

if "%DEPS_OK%"=="false" (
    echo.
    echo ❌ ERRO: Algumas dependencias nao foram baixadas corretamente!
    echo.
    echo 💡 SOLUCOES:
    echo    1. Verifique sua conexao com a internet
    echo    2. Execute como administrador
    echo    3. Desative firewall/antivirus temporariamente
    echo    4. Use o executar_demo.bat para a versao simplificada
    echo.
    pause
    exit /b 1
)

echo ✅ Todas as dependencias baixadas com sucesso!
echo.

REM Compilar primeiro o projeto demo (que sabemos que funciona)
echo Compilando LiterAlura Demo...
javac -d . src/main/java/br/com/alura/literalura/demo/LiteraluraDemo.java

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Erro na compilacao do Demo!
    echo 💡 Execute: executar_demo.bat para versao simplificada
    pause
    exit /b 1
)

echo ✅ Demo compilado com sucesso!
echo.

REM Tentar compilar o projeto completo
echo Compilando projeto Spring Boot completo...
set CLASSPATH=libs\postgresql-42.7.1.jar;libs\jackson-databind-2.15.4.jar;libs\jackson-core-2.15.4.jar;libs\jackson-annotations-2.15.4.jar;libs\jakarta.persistence-api-3.1.0.jar;libs\hibernate-core-6.2.13.Final.jar;libs\spring-data-commons-3.1.5.jar;libs\spring-data-jpa-3.1.5.jar;.

javac -cp "%CLASSPATH%" -d . src/main/java/br/com/alura/literalura/model/*.java
javac -cp "%CLASSPATH%" -d . src/main/java/br/com/alura/literalura/repository/*.java
javac -cp "%CLASSPATH%" -d . src/main/java/br/com/alura/literalura/service/*.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ⚠️  Projeto Spring Boot nao pode ser compilado sem Spring Boot completo
    echo ✅ Mas o DEMO funciona perfeitamente!
    echo.
    echo 🎯 OPCOES DISPONIVEIS:
    echo    1. Executar LiterAlura DEMO (funcional)
    echo    2. Configurar Spring Boot completo com Maven
    echo.

    choice /c 12 /m "Escolha uma opcao"

    if errorlevel 2 (
        echo.
        echo Para usar Spring Boot completo:
        echo 1. Instale Maven: https://maven.apache.org/download.cgi
        echo 2. Execute: mvn spring-boot:run
        echo.
        pause
        exit /b 0
    )

    if errorlevel 1 (
        echo.
        echo Executando LiterAlura DEMO...
        java br.com.alura.literalura.demo.LiteraluraDemo
    )
) else (
    echo ✅ Projeto completo compilado!
    echo.
    echo Executando LiterAlura completo...
    java -cp "%CLASSPATH%" br.com.alura.literalura.LiteraluraApplication
)

echo.
echo Execucao finalizada.
pause

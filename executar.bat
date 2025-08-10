@echo off
echo =============================================
echo  CONVERSOR DE MOEDAS - PROJETO ALURA
echo =============================================
echo.
echo Compilando o projeto...
javac -cp "lib\gson-2.10.1.jar" -d bin src\*.java src\models\*.java src\services\*.java src\ui\*.java

if %ERRORLEVEL% EQU 0 (
    echo Compilacao concluida com sucesso!
    echo.
    echo Executando o Conversor de Moedas...
    echo.
    java -cp "bin;lib\gson-2.10.1.jar" Main
) else (
    echo Erro na compilacao! Verifique o codigo.
    pause
)

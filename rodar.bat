@echo off
:: ============================================================
:: rodar.bat  —  Roda o ValidaSystem sem precisar de nada
:: Duplo clique ou execute no terminal: .\rodar.bat
:: ============================================================

set JAVA_HOME=C:\Users\nadab\.vscode\extensions\redhat.java-1.54.0-win32-x64\jre\21.0.10-win32-x86_64
set PATH=%JAVA_HOME%\bin;%PATH%
set MAVEN=%~dp0maven\bin\mvn.cmd

if not exist "%MAVEN%" (
    echo ERRO: Maven nao encontrado em .\maven\
    echo Execute o script compilar.ps1 primeiro.
    pause
    exit /b 1
)

echo Iniciando ValidaSystem...
call "%MAVEN%" -q clean javafx:run

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERRO ao iniciar. Tente rodar no terminal:
    echo   .\maven\bin\mvn.cmd clean javafx:run
    pause
)

# Rock n’ Roll Rentals

> Alugue equipamentos musicais de rock e metal direto do seu Android!

---

## 📖 Descrição

Rock n’ Roll Rentals é um aplicativo Android que permite alugar guitarras, amplificadores, pedais e pedaleiras.  
Tudo roda localmente com **SQLite** para dados e **SharedPreferences** para sessão de usuário.  

---

## 🚀 Funcionalidades

1. **Autenticação**  
   - Cadastro de usuário (nome, e-mail, senha SHA-256, data de nascimento)  
   - Login com sessão persistente  

2. **Explore Agora**  
   - Tela informativa com depoimentos e imagem da sede  

3. **Instrumentos**  
   - Lista de 13 instrumentos (4 guitarras, 4 amplificadores, 3 pedais, 2 pedaleiras)  
   - Cada item exibe imagem, nome e botão **Alugar**  

4. **Aluguel**  
   - Formulário de coleta de endereço  
   - Salva em SQLite: item, endereço, prazo (“30 dias”), valor, timestamp  
   - Após confirmar, redireciona à tela de pagamento fake  

5. **Pagamento Fake**  
   - Simula métodos PIX ou Cartão de Crédito  
   - Campos de entrada e confirmação via Toast  

6. **Meus Aluguéis**  
   - Lista dinâmica de aluguéis salvos  
   - Exibe: item, data do aluguel, dias restantes, endereço, valor  
   - Botão **Cancelar** para remover do banco e da lista  

7. **Navegação Condicional**  
   - Botões na tela principal: **Alugue um Instrumento** e **Meus Aluguéis**  
   - Se o usuário estiver logado, levam às respectivas telas; caso contrário, direcionam ao login  

---

## 📦 Estrutura de Pastas

```text
app/
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ com/example/projetointgrafael/
│  │  │     ├─ MainActivity.java
│  │  │     ├─ LoginActivity.java
│  │  │     ├─ CadastroActivity.java
│  │  │     ├─ ExploreActivity.java
│  │  │     ├─ AluguelActivity.java
│  │  │     ├─ PagamentoActivity.java
│  │  │     └─ MeusAlugueisActivity.java
│  │  ├─ java/
│  │  │  └─ com/example/projetointgrafael/database/
│  │  │     └─ DatabaseHelper.java
│  │  └─ res/
│  │     ├─ layout/
│  │     │  ├─ activity_main.xml
│  │     │  ├─ activity_login.xml
│  │     │  ├─ activity_cadastro.xml
│  │     │  ├─ activity_explore.xml
│  │     │  ├─ activity_aluguel.xml
│  │     │  ├─ activity_pagamento.xml
│  │     │  └─ activity_meus_alugueis.xml
│  │     ├─ drawable/
│  │     │  └─ (13 imagens .png)
│  │     ├─ font/
│  │     │  └─ (bebas_neue.ttf, rock_salt.ttf)
│  │     └─ values/
│  │        ├─ colors.xml
│  │        ├─ strings.xml
│  │        └─ styles.xml
└─ AndroidManifest.xml

```
## 🏦 Banco de Dados SQLite

```
-- Versão 3 do esquema (DATABASE_VERSION = 3)

CREATE TABLE Usuario (
  id            INTEGER PRIMARY KEY AUTOINCREMENT,
  nome          TEXT    NOT NULL,
  email         TEXT    NOT NULL UNIQUE,
  senha         TEXT    NOT NULL,
  nascimento    TEXT    NOT NULL
);

CREATE TABLE instrumentos (
  id            INTEGER PRIMARY KEY AUTOINCREMENT,
  categoria     TEXT    NOT NULL,
  marca         TEXT    NOT NULL,
  prazo         INTEGER NOT NULL,   -- sempre 30 dias
  valor         REAL    NOT NULL
);

CREATE TABLE alugueis (
  id             INTEGER PRIMARY KEY AUTOINCREMENT,
  item           TEXT    NOT NULL,
  endereco       TEXT    NOT NULL,
  prazo_aluguel  TEXT    NOT NULL,  -- "30 dias"
  valor_aluguel  TEXT    NOT NULL,  -- "R$ 300", etc.
  dataAluguel    INTEGER NOT NULL   -- timestamp em milissegundos
);
```

## 🚀 Como Rodar o Projeto

0. **Clone o repositório**  
 ```bash
   https://github.com/R4ffz/rocknrentalsmobile
   cd rocknrollrentals

```

1. **Abra no Android Studio**  

2. **Inicie o Android Studio**  

3. **File > Open** e selecione a pasta do projeto (`rocknrollrentals/app`)  

4. **Sincronize o Gradle**  
   - O Android Studio deve pedir para **Sync Now**  
   - Caso contrário, clique em **File > Sync Project with Gradle Files**  

5. **Verifique a versão do banco**  
   - Se você alterou o esquema (`DatabaseHelper`), incremente `DATABASE_VERSION` em `DatabaseHelper.java`

6. **Configure um emulador ou dispositivo físico**  
   - Abra o **AVD Manager** e inicie um virtual device  
   - Ou conecte seu dispositivo via USB e ative a depuração USB 

7. **Execute o app**  
   - Clique em **Run ▶️** ou pressione **Shift+F10**  
   - Selecione o dispositivo/emulador desejado  

8. **Testes rápidos**  
   - Cadastre um usuário em **Cadastro**  
   - Faça login e alugue um instrumento  
   - Verifique **Meus Aluguéis** e teste o cancelamento  

  





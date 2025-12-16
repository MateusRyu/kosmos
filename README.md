# 🌌 Kosmos Plataforma Criativa para Artistas
Kosmos é uma plataforma segura e colaborativa criada para artistas divulgarem, venderem ou alugarem suas obras, além de se conectarem com outros criadores ao redor do mundo.
---
## Visão Geral
 - Divulgue seu trabalho!
 - Uma área segura para artistas compartilharem seus projetos e itens para venda eou aluguel, fortalecendo conexões e ampliando oportunidades criativas.
## Funcionalidades
### ✨ Venda e Valorize sua Arte
 - Publique obras para venda ou aluguel com segurança
 - Gerencie seu portfólio artístico
 - Acompanhe transações e negociações em um só lugar
### 💬 Comunicação Facilitada
 - Envio de mensagens diretas
 - Participação em grupos de discussão
 - Compartilhamento de ideias em tempo real
### 🌐 Conecte-se com o Mundo
 - Interação entre artistas de diferentes estilos e culturas
 - Descoberta de novos talentos
 - Colaboração em projetos criativos
### 🌿 Amplie sua Visibilidade
 - Ambiente pensado para destacar o talento artístico
 - Maior alcance para suas produções
 - Conexão com apreciadores, compradores e parceiros
## Tecnologias Utilizadas
 - Backend 
   - Java
- Maven
- JDBC 
- DAO Pattern 
- Servidor HTTP customizado 
- Arquitetura em camadas (Routes, DAO, Database)
- Frontend 
  - HTML5 
- CSS3 
- JavaScript
- Banco de Dados
- Banco relacional (via JDBC)
- Gerenciamento de
- Usuários 
- ProdutosObras 
- Transações 
- Trocas
## Estrutura do Projeto
```
kosmos
├── .idea
├── src
│   └── main
│       ├── java
│       │   ├── database
│       │   │   ├── dao
│       │   │   │   ├── ProductDAO.java
│       │   │   │   ├── TradeDAO.java
│       │   │   │   └── UserDAO.java
│       │   │   ├── DatabaseConnection.java
│       │   │   ├── KosmosServer.java
│       │   │   ├── LoginRoute.java
│       │   │   └── Main.java
│       └── resources
│           ├── about
│           ├── error
│           ├── imagesHome
│           ├── logout
│           ├── main
│           ├── marketplace
│           ├── pit
│           ├── profile
│           ├── register
│           ├── home.css
│           ├── home.js
│           └── index.html
├── pom.xml
└── README.md
```
## Principais Componentes
### DAO (Data Access Object)
 - UserDAO – Gerenciamento de usuários 
 - ProductDAO – Obras, produtos e itens artísticos 
 - TradeDAO – Trocas, vendas e alugueis
### Rotas
 - Login 
 - Registro 
 - Marketplace 
 - Perfil do artista 
 - Logout
## Como Executar o Projeto
### Pré-requisitos
 - Java 17+ 
 - Maven 
 - Banco de dados configurado (JDBC)
### Passos
1. Clone o repositório
```shell
git clone httpsgithub.comLari07aaakosmos
```
2. Acesse o projeto
```shell
cd kosmos
```
3. Compile o projeto
```shell
mvn clean install
```
4. Execute
```shell
mvn execjava
```
5. Após iniciar, acesse no navegador httplocalhost8080
## Nosso Propósito
A Kosmos acredita que cada artista carrega seu próprio cosmos.
Nossa missão é oferecer visibilidade, suporte e conexão, criando um ambiente onde artistas possam
 - Compartilhar suas criações 
 - Se expressar livremente 
 - Expandir suas oportunidades profissionais
## Nossa Equipe
### Ítalo Gabriel
Estudante de Engenharia de Software, explorando diversas linguagens. Engenheiro em formação, gamer nas horas vagas e resiliente frente aos desafios da ansiedade e da rotina intensa.
### Gabriel Cavalcanti
Estudante de Engenharia de Software (19 anos), focado em Java. Futuro Engenheiro de PlataformaDevOps. Evoluindo constantemente em lógica e bancos de dados. Player de Valorant e frequentador assíduo da academia.
### Larissa Andrade
Desenvolvedora júnior full-stack. Designer de games formada em 2023. Estudante de Engenharia de Software e Designer Gráfico. Cosplayer e mestre de RPG nas horas vagas.
### Mário Neto
Desenvolvedor full-stack com foco em back-end. Ilustrador digital e técnico em Multimídia. Formado pelo Senac Recife em 2024. Pai do Zeus.
### Mateus Yamaguchi
Desenvolvedor full-stack com foco em back-end. Bacharel em Ciências e Tecnologias do Mar pela UNIFESP (2023).
## Contribuição
Contribuições são muito bem-vindas!
1. Faça um fork do projeto
2. Crie uma branch (featnova-feature)
```shell
git checkout -b featnova-feature
```
3. Commit suas alterações

4. Abra um Pull Request
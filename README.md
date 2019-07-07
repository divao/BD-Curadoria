# Projeto e Implementação de Banco de Dados

## Desenvolvedores
* Caio Ferreira, RA: 726502
* Leonardo Penna de Lima, RA: 726559

## Projeto: BD de Curadoria de Ensino

Em um banco de dados é necessário armazenar informações sobre usuários, materiais e áreas de conhecimento.
Para usuários são armazenados nome, email, id, idade, áreas de interesse e nível de experiência caso seja um curador.
É necessário para cada usuário determinar quais são suas áreas de interesse, podendo ser mais de uma.
O sistema deve armazenar dados de cada material da plataforma, como o nome do material, tipo de material, id, comentários, link, likes, dislikes e o score de ranqueamento.
Cada área consiste de 1 ou mais materiais.
Os usuários interagem com cada material desejado, seguir 0 ou mais áreas, e gerenciar as mesmas caso seja curador.

O projeto foi implementado utilizando a linguagem Java e o banco de dados relacional PostgreSQL.

## Roteiro

1. Instale o PostgreSQL:
  * Para facilitar, coloque __postgres__ no nome de usuário e senha (caso não queira, será necessário alterá-los no arquivo ConectaBD.java);
  * Também para facilitar, crie uma base de dados com o nome __Curadoria__ (caso não queira, será necessário alterá-lo no arquivo ConectaBD.java);
  * Rode o script __Curadoria.sql__.
2. Entre na pasta __/dist__ e execute o arquivo Curadoria.jar.
3. Aproveite e teste nosso banco de dados!
  * Para logar como um usuário, escolha o ID e clique em __Entrar__. Você pode acessar a página de Áreas, onde você pode seguir ou deixar de seguir as áreas, e a página de materiais, onde você visualiza os materiais das áreas em que o usuário está inscrito.
  * Para logar como administrador do banco de dados, clique no __ícone da engrenagem__. Você pode gerenciar usuários, áreas e materiais, adicionando, excluindo ou editando esses campos.
4. (Opcional) Abra o NetBeans IDE e importe o projeto (pasta __Curadoria__) para verificar o código. E, se você quiser, pode executar por lá também!
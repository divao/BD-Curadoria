/*
Alunos:
Caio Ferreira, RA: 726502
Leonardo Penna de Lima, RA: 726559

Mapeamento do DER para o Esquema Relacional:
usuario(pk(id), idade, nome , email)

usuarioAreasInt(pk(id, areasInteresse))
id ref usuário.id

curador(pk(id), nível)
id ref usuario.id

area(pk(idArea), nome, seguidores, numMateriais)

material(pk(id, idArea), tipo, dislikes, likes, nome, link, score)
idArea ref area.id

materialComentarios(pk(id, comentarios))
id ref material.id

interage(pk(idUsuario, idMaterial))
idUsuario ref usuario.id
idMaterial ref material.id

segue(pk(idUsuario, idArea))
idUsuario ref usuario.id
idArea ref area.id

gerencia(pk(idCurador, idArea))
idCurador ref curador.id
idArea ref area.id

contem(pk(idMaterial, idArea))
idMaterial ref material.id
idArea ref area.id
*/

DROP TABLE IF EXISTS contem; 
DROP TABLE IF EXISTS gerencia;
DROP TABLE IF EXISTS segue;
DROP TABLE IF EXISTS interage;
DROP TABLE IF EXISTS materialComentarios;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS area;
DROP TABLE IF EXISTS curador;
DROP TABLE IF EXISTS usuarioAreasInt;
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
	idUsuario serial primary key UNIQUE,
	nome varchar (100),
	idade varchar (3),
	email varchar (100)
);

CREATE TABLE usuarioAreasInt (
	idUsuario integer,
	areasInt varchar (100),
	constraint areasint_fk foreign key(idUsuario) references usuario(idUsuario) ON DELETE CASCADE,
	constraint areasint_pk primary key (idUsuario,areasInt)
);

CREATE TABLE curador (
	idCurador integer,
	nivel int not null,
	constraint curador_fk foreign key(idCurador) references	 usuario(idUsuario) ON DELETE CASCADE,
	constraint curador_pk primary key (idCurador)
);

CREATE TABLE area (
	idArea serial UNIQUE primary key,
	nome varchar (100) UNIQUE,
	seguidores integer DEFAULT 0,
	numMateriais integer DEFAULT 0
);

CREATE TABLE material (
	idMaterial serial UNIQUE,
	idArea integer,
	tipo varchar (30),
	likes varchar (6) DEFAULT 0,
	dislikes varchar (6) DEFAULT 0,
	nome varchar (100),
	link varchar (100),
	score varchar (10) DEFAULT 0,
	constraint material_fk foreign key (idArea) references area(idArea) ON DELETE CASCADE,
	constraint material_pk primary key (idMaterial, idArea)
);

CREATE TABLE materialComentarios (
	idMaterial integer,
	comentario varchar (140),
	constraint comentario_fk foreign key (idMaterial) references material(idMaterial) ON DELETE CASCADE,
	constraint comentario_pk primary key (idMaterial,comentario)
);

CREATE TABLE interage (
	idUsuario integer,
	idMaterial integer,
	constraint interage_fk1 foreign key (idUsuario) references usuario(idUsuario) ON DELETE CASCADE,
	constraint interage_fk2 foreign key (idMaterial) references material(idMaterial) ON DELETE CASCADE,
	constraint interage_pk primary key (idUsuario,idMaterial)
);

CREATE TABLE segue (
	idUsuario integer,
	idArea integer,
	constraint segue_fk1 foreign key(idUsuario) references usuario(idUsuario) ON DELETE CASCADE,
	constraint segue_fk2 foreign key(idArea) references area(idArea) ON DELETE CASCADE,
	constraint segue_pk primary key (idUsuario,idArea)
);

CREATE TABLE gerencia (
	idCurador integer,
	idArea integer,
	constraint gerencia_fk1 foreign key(idCurador) references curador(idCurador) ON DELETE CASCADE,
	constraint gerencia_fk2 foreign key(idArea) references area(idArea) ON DELETE CASCADE,
	constraint gerencia_pk primary key (idCurador,idArea)
);

CREATE TABLE contem (
	idArea integer,
	idMaterial integer,
	constraint contem_fk1 foreign key(idArea) references area(idArea) ON DELETE CASCADE,
	constraint contem_fk2 foreign key(idMaterial) references material(idMaterial) ON DELETE CASCADE,
	constraint contem_pk primary key (idArea,idMaterial)
);

-- Procedures
CREATE OR REPLACE PROCEDURE insertUser(_nome character varying, _idade integer, _email character varying)
LANGUAGE SQL
AS $$
	INSERT INTO usuario(nome, idade, email)
	VALUES(_nome, _idade, _email);
$$;

CREATE OR REPLACE PROCEDURE insertArea(_nome character varying)
LANGUAGE SQL
AS $$
        INSERT INTO area(nome)
        VALUES(_nome);
$$;

CREATE OR REPLACE PROCEDURE insertMaterial(_idarea integer, _tipo character varying, _nome character varying, _link character varying)
LANGUAGE SQL
AS $$
        INSERT INTO material(idarea, tipo, nome, link)
        VALUES(_idarea, _tipo, _nome, _link);
$$;

-- Funções
CREATE OR REPLACE FUNCTION insertContem() RETURNS TRIGGER AS $updateContem$
BEGIN
    INSERT INTO contem VALUES(NEW.idarea, NEW.idmaterial);
    RETURN NEW;
END;
$updateContem$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION calcMateriais() RETURNS TRIGGER AS $updateArea$
BEGIN
    UPDATE area
	SET numMateriais = subConsulta.count
	FROM (SELECT area.idArea, count(area.idArea)
		  FROM contem INNER JOIN area
	   	  ON contem.idarea = area.idarea
		  GROUP BY area.idArea) AS subConsulta
	WHERE area.idArea = subConsulta.idArea;
    RETURN NEW;
END;
$updateArea$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION calcSeguidores() RETURNS TRIGGER AS $updateAreaSeguidores$
BEGIN
    UPDATE area
	SET seguidores = subConsulta2.count
	FROM (SELECT area.idArea, count(area.idArea)
		  FROM segue INNER JOIN area
	   	  ON segue.idarea = area.idarea
		  GROUP BY area.idArea) AS subConsulta2
	WHERE area.idArea = subConsulta2.idArea;
    RETURN NEW;
END;
$updateAreaSeguidores$ LANGUAGE plpgsql;

-- Triggers
CREATE TRIGGER updateContem 
AFTER INSERT ON material
	FOR EACH ROW EXECUTE FUNCTION insertContem();

CREATE TRIGGER updateArea 
AFTER INSERT ON contem
	FOR EACH ROW EXECUTE FUNCTION calcMateriais();

CREATE TRIGGER updateAreaSeguidores
AFTER INSERT ON segue
	FOR EACH ROW EXECUTE FUNCTION calcSeguidores();

--Funções com o uso de cursores
CREATE OR REPLACE FUNCTION listarMateriais()
	RETURNS TEXT AS $$
DECLARE
	saida TEXT DEFAULT '';
	vMaterial  material%ROWTYPE;
	matCursor CURSOR FOR SELECT * FROM material;
BEGIN
	OPEN matCursor;
	LOOP
		FETCH matCursor INTO vMaterial;
		EXIT WHEN NOT FOUND;
		IF saida = '' THEN saida = vMaterial.nome;
		ELSE saida = saida || ', ' || vMaterial.nome;
		END IF;
	END LOOP;
	CLOSE matCursor;
	RETURN saida;
END; $$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION listarMateriaisPorArea(_idarea integer)
	RETURNS TEXT AS $$
DECLARE
	saida TEXT DEFAULT '';
	vMaterial2  material%ROWTYPE;
	matCursor2 CURSOR FOR SELECT * FROM material;
BEGIN
	OPEN matCursor2;
	LOOP
		FETCH matCursor2 INTO vMaterial2;
		EXIT WHEN NOT FOUND;
		IF vMaterial2.idArea = _idarea THEN
			IF saida = '' THEN saida = vMaterial2.nome;
			ELSE saida = saida || ', ' || vMaterial2.nome;
			END IF;
		END IF;
	END LOOP;
	CLOSE matCursor2;
	RETURN saida;
END; $$
LANGUAGE plpgsql;

-- Inserts para popular as tabelas
call insertUser('Caio Ferreira', '21', 'divao@gmail.com');
call insertUser('Leonardo Lima', '20', 'peolenna@doleo.com');
call insertUser('Jackson Victor', '22', 'jeversdon@hotmail.com');
call insertUser('Gabriel Bortolote', '18', 'borto@gmail.com');
call insertUser('Jhonattan Vieira', '25', 'flash@live.com');
call insertUser('Alisson Nunes', '21', 'alynva@alynva.com');
call insertUser('Matheus Bortoleto', '20', 'bortinho@gmail.com');
call insertUser('Gabriel Alves', '19', 'gabrielzinho@hotmail.com');
call insertUser('Gabriel Nardy', '19', 'nardy@uol.com');
call insertUser('Guilherme Franco', '22', 'gffranco@gmail.com');
call insertUser('Jean Araujo', '25', 'geao@outlook.com');
call insertUser('Otavio Cesar', '21', 'otavs@otavs.com');
call insertUser('Claucio Filho', '20', 'claucio2@gmail.com');
call insertUser('Igor Rozani', '27', 'ig_igor@hotmail.com');
call insertUser('Guilherme Leite', '16', 'leito@gmail.com');
call insertUser('William Eugenio', '19', 'andre_balada@outlook.com');
call insertUser('Marcelo Huffenbaecher', '21', 'huffenbacon@gmail.com');
call insertUser('Leonardo Felipe', '23', 'indiao@outlook.com');
call insertUser('Rene Ferrante', '17', 'rance@hotmail.com');
call insertUser('Eduardo Oliveira', '24', 'balota@gmail.com');

call insertArea('Banco de Dados');
call insertArea('Inteligencia Artificial');
call insertArea('Engenharia de Software');
call insertArea('Algoritmos');
call insertArea('Sistemas Distribuidos');
call insertArea('Compiladores');
call insertArea('Redes de Computadores');
call insertArea('Sistemas Operacionais');
call insertArea('Teoria dos Grafos');
call insertArea('Computação Gráfica');
call insertArea('Microeconomia');
call insertArea('Calculo');
call insertArea('Geometria Analitica');
call insertArea('Sociologia');
call insertArea('Direito');

call insertmaterial(1,'Livro','Introdução a Sistemas de Bancos de Dados','www.christopherjdate.com');
call insertmaterial(1,'Livro','Projeto de Bancos de Dados','www.carlosalbertoheuser.com');
call insertmaterial(2,'Livro','Artificial Intelligence - A Modern Approach','www.srussel.com');
call insertmaterial(9,'Apostila','Teoria dos Grafos - Notas de Aula','www.alexandrelevada.com');
call insertmaterial(12,'Livro','Um curso de calculo','www.guidorizzi.com');
call insertmaterial(12,'Livro','Calculo - Volume 1','www.jamesstewart.com');
call insertmaterial(3,'PDF','Comecando a utilizar o SonarQube','www.dc.ufscar.br/aurivicenzi');
call insertmaterial(13,'Livro','Geometria Analitica - Um Tratamento Vetorial','www.pauloboulos.com');
call insertmaterial(15,'Livro','O Caso dos Exploradores de Cavernas','www.fuller.com');
call insertmaterial(1,'Livro','SQL e Teoria Relacional','www.christopherjdate.com');
call insertmaterial(4,'Livro','Introduction To Algorithms','www.cormen.com');
call insertmaterial(14,'Livro','Sociologia do Corpo','www.anthonygiddens.com');
call insertmaterial(3,'Livro','Engenharia de software: uma
abordagem profissional','www.pressman.com');
call insertmaterial(10,'Livro','Computacao Grafica - Teoria e Pratica','www.eduardoazevedo.com');
call insertmaterial(11,'Livro','Manual de Economia','www.montorofilho.com');
call insertmaterial(15,'Livro','O Caso dos Denunciantes Invejosos','www.dimoulis.com');
call insertmaterial(2,'Livro','Artificial Intelligence: Structures and Strategies
for Complex Problem Solving','www.glugger.com');
call insertmaterial(13,'Livro','Vetores e geometria analitica','www.winterle.com');
call insertmaterial(5,'Livro','Sistemas Distribuidos: Principios E Paradigmas','www.tanembaum.com');
call insertmaterial(3,'Livro','Engenharia de software','www.sommervile.com');
call insertmaterial(14,'Estudo Dirigido','A autoviolencia, objeto da sociologia e
problema de saude publica','www.mariaminayo.com');
call insertmaterial(6,'Livro','Dragon Book','www.alfredaho.com');
call insertmaterial(2,'Livro','Prolog Programming for Artificial Intelligence','www.ibratko.com');
call insertmaterial(14,'Livro','Falando da Sociedade','www.howardbecker.com');
call insertmaterial(7,'Video','Redes de Computadores - Aula 01','www.youtube.com/joueyama');
call insertmaterial(14,'Livro','O suicidio','www.durkheim');
call insertmaterial(2,'Video','Prolog para Inteligencia Artificial','www.youtube.com/iaprolog');
call insertmaterial(14,'Livro','Conceitos Sociologicos Fundamentais','www.weber.com');
call insertmaterial(8,'Livro','Sistemas Operacionais Modernos','www.tanembaum.com');
call insertmaterial(7,'Livro','Computer Networking: A Top-Down Approach','www.kurose.com');

INSERT INTO usuarioAreasInt VALUES (1, 'Banco de Dados');
INSERT INTO usuarioAreasInt VALUES (1, 'Calculo');
INSERT INTO usuarioAreasInt VALUES (1, 'Redes de Computadores');
INSERT INTO usuarioAreasInt VALUES (2, 'Compiladores');
INSERT INTO usuarioAreasInt VALUES (2, 'Banco de Dados');
INSERT INTO usuarioAreasInt VALUES (3, 'Calculo');
INSERT INTO usuarioAreasInt VALUES (4, 'Redes de Computadores');
INSERT INTO usuarioAreasInt VALUES (5, 'Banco de Dados');
INSERT INTO usuarioAreasInt VALUES (6, 'Engenharia de Software');
INSERT INTO usuarioAreasInt VALUES (6, 'Compiladores');
INSERT INTO usuarioAreasInt VALUES (10, 'Inteligencia Artificial');
INSERT INTO usuarioAreasInt VALUES (11, 'Inteligencia Artificial');
INSERT INTO usuarioAreasInt VALUES (8, 'Direito');
INSERT INTO usuarioAreasInt VALUES (8, 'Calculo');
INSERT INTO usuarioAreasInt VALUES (19, 'Engenharia de Software');

INSERT INTO curador VALUES (1, 10);
INSERT INTO curador VALUES (4, 5);
INSERT INTO curador VALUES (12, 4);
INSERT INTO curador VALUES (5, 6);
INSERT INTO curador VALUES (20, 2);
INSERT INTO curador VALUES (8, 6);
INSERT INTO curador VALUES (10, 1);
INSERT INTO curador VALUES (2, 2);
INSERT INTO curador VALUES (15, 5);
INSERT INTO curador VALUES (16, 1);
INSERT INTO curador VALUES (9, 4);
INSERT INTO curador VALUES (3, 5);
INSERT INTO curador VALUES (18, 1);
INSERT INTO curador VALUES (17, 7);
INSERT INTO curador VALUES (19, 9);

INSERT INTO materialComentarios VALUES (1, 'Gostei');
INSERT INTO materialComentarios VALUES (1, 'Achei legal');
INSERT INTO materialComentarios VALUES (1, 'Me ajudou na prova');
INSERT INTO materialComentarios VALUES (3, 'Nao passaria sem esse material');
INSERT INTO materialComentarios VALUES (15, 'Tirei 10 por causa desse material');
INSERT INTO materialComentarios VALUES (4, 'Achei top');
INSERT INTO materialComentarios VALUES (10, 'Recomendei a todos os meus amigos');
INSERT INTO materialComentarios VALUES (8, 'Gostei');
INSERT INTO materialComentarios VALUES (7, 'Bacana');
INSERT INTO materialComentarios VALUES (4, 'Nota 10');
INSERT INTO materialComentarios VALUES (3, 'Sensacional');
INSERT INTO materialComentarios VALUES (23, 'Curti demais');
INSERT INTO materialComentarios VALUES (7, 'Adorei o conteudo');
INSERT INTO materialComentarios VALUES (30, 'Muito bacana');
INSERT INTO materialComentarios VALUES (21, 'Praticamente necessario');

INSERT INTO interage VALUES (1, 14);
INSERT INTO interage VALUES (1, 11);
INSERT INTO interage VALUES (3, 15);
INSERT INTO interage VALUES (9, 1);
INSERT INTO interage VALUES (17, 30);
INSERT INTO interage VALUES (11, 25);
INSERT INTO interage VALUES (7, 24);
INSERT INTO interage VALUES (17, 5);
INSERT INTO interage VALUES (16, 16);
INSERT INTO interage VALUES (5, 24);
INSERT INTO interage VALUES (9, 24);
INSERT INTO interage VALUES (11, 11);
INSERT INTO interage VALUES (19, 9);
INSERT INTO interage VALUES (20, 27);
INSERT INTO interage VALUES (13, 3);

INSERT INTO segue VALUES (1, 1);
INSERT INTO segue VALUES (1, 15);
INSERT INTO segue VALUES (3, 11);
INSERT INTO segue VALUES (9, 9);
INSERT INTO segue VALUES (17, 3);
INSERT INTO segue VALUES (11, 5);
INSERT INTO segue VALUES (7, 4);
INSERT INTO segue VALUES (17, 15);
INSERT INTO segue VALUES (16, 6);
INSERT INTO segue VALUES (5, 4);
INSERT INTO segue VALUES (9, 14);
INSERT INTO segue VALUES (11, 1);
INSERT INTO segue VALUES (19, 3);
INSERT INTO segue VALUES (20, 7);
INSERT INTO segue VALUES (13, 13);

INSERT INTO gerencia VALUES (1, 10);
INSERT INTO gerencia VALUES (1, 5);
INSERT INTO gerencia VALUES (12, 4);
INSERT INTO gerencia VALUES (5, 6);
INSERT INTO gerencia VALUES (20, 2);
INSERT INTO gerencia VALUES (8, 6);
INSERT INTO gerencia VALUES (8, 1);
INSERT INTO gerencia VALUES (2, 2);
INSERT INTO gerencia VALUES (1, 6);
INSERT INTO gerencia VALUES (16, 1);
INSERT INTO gerencia VALUES (9, 4);
INSERT INTO gerencia VALUES (3, 5);
INSERT INTO gerencia VALUES (18, 1);
INSERT INTO gerencia VALUES (3, 7);
INSERT INTO gerencia VALUES (19, 9);

-- Consultas (SELECT) frequentemente usadas

-- Consulta 1
SELECT usuarioAreasInt.idUsuario, area.idArea
FROM usuarioAreasInt INNER JOIN area
ON usuarioAreasInt.areasInt = area.nome;

-- Consulta 2
SELECT area.idArea, count(area.idArea)
FROM contem INNER JOIN area
ON contem.idarea = area.idarea
GROUP BY area.idArea;

-- Consulta 3
SELECT area.idArea, count(area.idArea)
FROM segue INNER JOIN area
ON segue.idarea = area.idarea
GROUP BY area.idArea;

/* Analise das consultas (usando EXPLAIN ANALYZE)
	Consulta 1:
		Planning Time: 0.183 ms
		Execution Time: 0.102 ms

	Consulta 2:
		Planning Time: 0.485 ms
		Execution Time: 0.335 ms

	Consulta 3:
		Planning Time: 0.400 ms
		Execution Time: 0.227 ms
*/

-- Indices
CREATE INDEX areaNomeIndex ON area (nome);
CREATE INDEX areaIdIndex ON area (idArea);

/* Analise das consultas apos a criacao dos indices (usando EXPLAIN  ANALYZE)
	Consulta 1:
		Primeira Consulta:
			Planning Time: 1.600 ms
			Execution Time: 0.310 ms

		Segunda Consulta:
			Planning Time: 0.226 ms
			Execution Time: 0.092 ms

	Consulta 2:
		Primeira Consulta:
			Planning Time: 1.738 ms
			Execution Time: 0.302 ms

		Segunda Consulta:
			Planning Time: 0.528 ms
			Execution Time: 0.243 ms

	Consulta 3:
		Primeira Consulta:
			Planning Time: 0.638 ms
			Execution Time: 0.135 ms

		Segunda Consulta:
			Planning Time: 0.243 ms
			Execution Time: 0.199 ms
*/
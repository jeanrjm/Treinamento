CREATE DATABASE IF NOT EXISTS treinamento;

use treinamento;

CREATE TABLE IF NOT EXISTS pessoas (
id int(255) NOT NULL AUTO_INCREMENT, 
nome varchar(255) NOT NULL, 
sobrenome varchar(255) NOT NULL, 
nomecompleto varchar(255) NOT NULL,
UNIQUE (nomecompleto),
PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS salas ( 
id int(255) NOT NULL AUTO_INCREMENT, 
nome varchar(255) NOT NULL, 
lotacao int(8) NOT NULL, 
PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS cafe ( 
id int(255) NOT NULL AUTO_INCREMENT, 
nome varchar(255) NOT NULL, 
lotacao int(8) NOT NULL, 
PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS ensalamento ( 
id int(255) NOT NULL AUTO_INCREMENT, 
pessoa int(255) NOT NULL, 
salaUm int(255) NOT NULL, 
salaDois int(255) NOT NULL, 
cafeUm int(255) NOT NULL, 
cafeDois int(255) NOT NULL, 
PRIMARY KEY (id),
FOREIGN KEY (pessoa) REFERENCES pessoas(id));



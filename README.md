
MONTANDO A BASE DE DADOS:
Instale o sotware XAMPP (disponivel em https://sourceforge.net/projects/xampp/) .
Abra a aplicação XAMPP Control Panel. 
Clique Start para Apache e tambem para MySQL. 
No seu browser acesse http://localhost/phpmyadmin/server_sql.php
Copie os conteudos do arquivo init.sql (disponivel em Treinamento\src) para o campo de texto.
Clique em executar, na parte inferior direita da pagina.
As tabelas da base de dados serão criadas.

COMPILANDO E EXECUTANDO O PROJETO:
O projeto foi desenvolvido utilizando a ferramenta NetBeans IDE 8.2 (disponivel em https://netbeans.org/downloads/old/8.2/).
Ao abrir a aplicação NetBeans, clique em "Arquivo", depois em "Abrir projeto..." e selecione o diretorio Treinamento.
Com o projeto aberto aperte Executar projeto (Botão com simbolo play).

UTILIZAÇÃO:
Para cadastrar uma pessoa:
	Clique na opção "Pessoas" e em seguida em "Cadastrar nova" ou em Ferramentas>Cadastrar>Cadastrar Pessoa.
	Preencha os campos e clique "Cadastrar".
	Ao cadastrar uma pessoa, esta é prontamente alocada em salas e cafés nas duas etapas, se houverem vagas.
	
Para cadastrar uma Sala:
	Clique na opção "Salas" e em seguida em "Cadastrar nova" ou em Ferramentas>Cadastrar>Cadastrar Sala.
	Preencha os campos e clique "Cadastrar".
	Ao cadastrar uma sala, é prontamente feita a redistribuição de todas as pessoas.
	
Para cadastrar um Espaço de Café:
	Clique na opção "Espaços do Café" e em seguida em "Cadastrar novo" ou em Ferramentas>Cadastrar>Cadastrar Espaço do Café.
	Preencha os campos e clique "Cadastrar".
	Ao cadastrar um Espaço do Café, é prontamente feita a redistribuição de todas as pessoas.

Para consultar uma pessoa:
	Clique na opção "Pessoas" e em seguida em "Consultar" ou em Ferramentas>Consultar>Consultar Pessoas.
	Selecione a pessoa.

Para consultar uma Sala:
	Clique na opção "Salas" e em seguida em "Consultar" ou em Ferramentas>Consultar>Consultar Salas.
	Selecione a Sala.
	
Para consultar um Espaço de Café:
	Clique na opção "Espaços do Café" e em seguida em "Consultar" ou em Ferramentas>Consultar>Consultar Espaços do Café.
	Selecione o Espaço do Café.
	
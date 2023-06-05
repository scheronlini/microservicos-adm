CREATE TABLE `fornecedor` (
  `id` INT(10) AUTO_INCREMENT PRIMARY KEY,
  `cnpj` longtext,
  `razao_Social` longtext,
  `nome_Fantasia` longtext,
  `endereco` longtext,
  `telefone` longtext,
  `celular` longtext,
  `site` longtext,
  `email` longtext,
  `contato` longtext,
  `observacao` longtext,
  `inscricao_Estadual` longtext,
  `inscricao_Municipal` longtext,
  `data_Abertura` datetime(6),
  `ramo_Atividade` longtext
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

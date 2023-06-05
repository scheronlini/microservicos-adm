CREATE TABLE `enderecos` (
  `id` INT(10) AUTO_INCREMENT PRIMARY KEY,
  `cep` longtext,
  `logradouro` longtext,
  `bairro` longtext,
  `localidade` longtext,
  `uf` longtext,
  `numero` longtext,
  `ponto_De_Referencia` longtext
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

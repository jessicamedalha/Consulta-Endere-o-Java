Feature: Realizar requisicao POST
  Scenario: CPF valido
    Given acesso a conexao via cep
    And procura frete
    When informo Cep
    Then retorna endereco e frete
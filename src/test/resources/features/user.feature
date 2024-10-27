Feature: Cadastro de usuário

  Scenario: Cadastrar um novo usuário com sucesso
    Given que eu já tenha um usuário admin cadastrado
    And que eu tenha feito login com o usuario admin
    When eu faço uma requisição POST para o cadastro de usuário com nome "Hadryan Silva" e email "hadryan2001@gmail.com"
    Then o status de resposta da requisição de cadastro de usuário deve ser 201
    And a resposta deve conter o nome "Hadryan Silva" e o email "hadryan2001@gmail.com"

  Scenario: Cadastrar um novo usuário sem autorização
    Given que eu não tenha feito login com o usuario admin
    When eu faço uma requisição POST para o cadastro de usuário com nome "Hadryan Silva" e email "hadryan2001@gmail.com"
    Then o status de resposta da requisição de cadastro de usuário deve ser 401

  Scenario: Cadastrar um usuário que já existe
    Given que eu tenha feito login com o usuario admin
    And que o usuário "Hadryan Silva" já exista no banco de dados
    When eu faço uma requisição POST para o cadastro de usuário com nome "Hadryan Silva" e email "hadryan2001@gmail.com"
    Then o status de resposta da requisição de cadastro de usuário deve ser 400
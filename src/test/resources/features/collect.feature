Feature: Agendar Coleta
  Scenario: Agendar uma nova coleta com sucesso
    Given que eu tenha feito o cadastro do usuário "user"
    And que eu tenha feito login com o usuario "user"
    When eu faço uma requisição "POST" para o endpoint de coleta
    Then o status de resposta da requisição de coleta deve ser 201

  Scenario: Agendar uma nova coleta sem autenticar
    Given que o usuário "user" já esteja cadastrado
    And que eu não tenha feito login
    When eu faço uma requisição "POST" para o endpoint de coleta
    Then o status de resposta da requisição de coleta deve ser 401

  Scenario: Cancelar um agendamento de coleta
    Given que o usuário "user" já esteja cadastrado
    And que eu tenha feito login com o usuario "user"
    And que a coleta já tenha sido agendada
    When eu faço uma requisição "DELETE" para o endpoint de coleta
    Then o status de resposta da requisição de coleta deve ser 204
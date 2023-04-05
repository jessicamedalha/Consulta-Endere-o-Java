Feature: Rest API Functionality Scenarios

  Scenarios:  Rest Api POST CEP
    Given Get Call to "<url>"
    Then Response Code "<responseMessage>" is validated

    Examples:
      | url      | responseMessage |
      | /consulta-endereco | 200   |

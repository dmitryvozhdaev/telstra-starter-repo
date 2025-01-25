Feature: Sim card activator
  Sim card activator should success with ICCID="1255789453849037777" and fails with ICCID="8944500102198304826"

  Scenario Outline: Is activation with the <ICCID> successful
    Given I fetch the POST /activate endpoint of the activation service with <ICCID>
    When I fetch the GET /customer endpoint of the activation service with <simCardId>
    Then It should respond with <expectedActive>

    Examples:
      | ICCID               | simCardId | expectedActive |
      | 1255789453849037777 | 1         | "true"         |
      | 8944500102198304826 | 2         | "false"        |

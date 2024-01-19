Feature: Player plays

  Scenario: not enough
    Given A player and his first proposition is 3
    When that's not enough and he replays
    Then he plays 4

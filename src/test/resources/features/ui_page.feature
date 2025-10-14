@UI
Feature: UI smoke check for calculator page
  Scenario: Page loads and shows some content
    Given I open the calculator page in a browser
    Then I should see a page title or visible content

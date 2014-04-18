Scenario: Verify the search function

Given I access Baidu site
When I search text on Baidu
Then I should see the search result
When I nevigate a result link randomly
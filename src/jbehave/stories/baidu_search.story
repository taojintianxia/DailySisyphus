Scenario: Verify the search function

Given I access Baidu site
When I search text on Baidu
Then I should see the search result on baidu
When I nevigate a result link on baidu randomly
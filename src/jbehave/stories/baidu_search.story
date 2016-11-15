Scenario: Verify the search function

Given 我访问了百度的首页
When I search text on Baidu
Then I should see the search result on baidu
When I navigate a result link on baidu randomly
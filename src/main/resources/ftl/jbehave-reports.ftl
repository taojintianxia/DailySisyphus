<#ftl strip_whitespace=true>
<#macro renderStat stats name class="">
	<#assign value = stats.get(name)!0>
	<#if (value != 0)>
		<span class="${class}">${value}</span>
	<#else>
		${value}
	</#if>
</#macro>

<body style="font-family: Courier New, Verdana, Helvetica, sans serif; font-size: 12px; color: black">
	<div style="background: #ffffff; border: dotted 1px #666; margin: 2px; content: 2px; padding: 2px;">
		<h2>JBehave (Selenium) Story Reports</h2>
		<table style="border-collapse: collapse; text-align: center; width: 100%;">
			<colgroup span="2" style="border: 1px solid #000;"></colgroup>
			<colgroup span="5" style="border: 1px solid #000;"></colgroup>
			<colgroup style="border: 1px solid #000;"></colgroup>
			<tr style="height: 37px;">
				<th colspan="2">Stories</th>
				<th colspan="5">Scenarios</th>
				<th></th>
			</tr>
			<tr style="height: 25px;">
				<th style="border: 1px dotted #666; font-size: 11px;">Name</th>
				<th style="border: 1px dotted #666; font-size: 11px;">Excluded</th>
				<th style="border: 1px dotted #666; font-size: 11px;">Total</th>
				<th style="border: 1px dotted #666; font-size: 11px;">Successful</th>
				<th style="border: 1px dotted #666; font-size: 11px;">Pending</th>
				<th style="border: 1px dotted #666; font-size: 11px;">Failed</th>
				<th style="border: 1px dotted #666; font-size: 11px;">Excluded</th>
				<th style="border: 1px dotted #666; font-size: 11px;">Details</th>
			</tr>
			<#assign reportNames = reportsTable.getReportNames()>
			<#assign totalReports = reportNames.size() - 1>
			<tr style="border-top: 1px solid #000; padding-top: 10px; font-weight: bold; height: 25px;">
				<td style="border: 1px dotted #666; font-size: 12px;">${totalReports}</td> 
				<#assign stats = reportsTable.getReport("Totals").getStats()>
				<td style="border: 1px dotted #666; font-size: 12px;"><@renderStat stats "notAllowed" "failed"/></td>
				<td style="border: 1px dotted #666; font-size: 12px;"><@renderStat stats "scenarios"/></td>
				<td style="border: 1px dotted #666; font-size: 12px;"><@renderStat stats "scenariosSuccessful" "successful"/></td>
				<td style="border: 1px dotted #666; font-size: 12px;"><@renderStat stats "scenariosPending" "pending"/></td>
				<td style="border: 1px dotted #666; font-size: 12px;"><@renderStat stats "scenariosFailed" "failed"/></td>
				<td style="border: 1px dotted #666; font-size: 12px;"><@renderStat stats "scenariosNotAllowed" "failed"/></td>
				<td style="border: 1px dotted #666; font-size: 12px;">Totals</td>
			</tr>
			<#list reportNames as name>
				<#assign report = reportsTable.getReport(name)>
				<#if name != "Totals">
				<tr style="height: 23px;">
					<#assign stats = report.getStats()>
					<#assign stepsFailed = stats.get("stepsFailed")!0>
					<#assign scenariosFailed = stats.get("scenariosFailed")!0>
					<#assign pending = stats.get("pending")!0>
					<#assign totalScenarios = stats.get("scenarios")!0>
					<#assign storyClass = "border: 1px dotted #666; font-size: 12px;">
					<#if stepsFailed != 0 || scenariosFailed != 0>
						<#assign storyClass = storyClass + "color: red; font-weight:bold;">
					<#elseif 0 == totalScenarios && name != "AfterStories" && name != "BeforeStories">
						<#assign storyClass = storyClass + "color: #FF9900; font-weight:bold;">
					<#elseif pending != 0>
						<#assign storyClass = storyClass + "color: blue; font-style:italic;">
					<#else>
						<#assign storyClass = storyClass + "color: black;">
					</#if>
					<td style="${storyClass} text-align: left;">${report.name}</td>
					<td style="${storyClass}"><@renderStat stats "notAllowed" "failed"/></td>
					<td style="${storyClass}"><@renderStat stats "scenarios"/></td>
					<td style="${storyClass}"><@renderStat stats "scenariosSuccessful" "successful"/></td>
					<td style="${storyClass}"><@renderStat stats "scenariosPending" "pending"/></td>
					<td style="${storyClass}"><@renderStat stats "scenariosFailed" "failed"/></td>
					<td style="${storyClass}"><@renderStat stats "scenariosNotAllowed" "failed"/></td>
					<td style="${storyClass}">
						<#assign filesByFormat = report.filesByFormat>
						<#list filesByFormat.keySet() as format>
							<#assign file = filesByFormat.get(format)><a href="${file.name}">${format}</a><#if format_has_next>|</#if>
						</#list>
					</td>
				</tr>
				</#if>
			</#list>
			<tr style="border-top: 1px solid #000; padding-top: 10px; font-weight: bold; height: 25px;">
				<td style="border: 1px dotted #666; font-size: 12px;">${totalReports}</td> 
				<#assign stats = reportsTable.getReport("Totals").getStats()>
				<td style="border: 1px dotted #666; font-size: 12px;"><@renderStat stats "notAllowed" "failed"/></td>
				<td style="border: 1px dotted #666; font-size: 12px;"><@renderStat stats "scenarios"/></td>
				<td style="border: 1px dotted #666; font-size: 12px;"><@renderStat stats "scenariosSuccessful" "successful"/></td>
				<td style="border: 1px dotted #666; font-size: 12px;"><@renderStat stats "scenariosPending" "pending"/></td>
				<td style="border: 1px dotted #666; font-size: 12px;"><@renderStat stats "scenariosFailed" "failed"/></td>
				<td style="border: 1px dotted #666; font-size: 12px;"><@renderStat stats "scenariosNotAllowed" "failed"/></td>
				<td style="border: 1px dotted #666; font-size: 12px;">Totals</td>
			</tr>
			<tr style="height: 25px;">
				<th style="border: 1px dotted #666; font-size: 11px;">Name</th>
				<th style="border: 1px dotted #666; font-size: 11px;">Excluded</th>
				<th style="border: 1px dotted #666; font-size: 11px;">Total</th>
				<th style="border: 1px dotted #666; font-size: 11px;">Successful</th>
				<th style="border: 1px dotted #666; font-size: 11px;">Pending</th>
				<th style="border: 1px dotted #666; font-size: 11px;">Failed</th>
				<th style="border: 1px dotted #666; font-size: 11px;">Excluded</th>
				<th style="border: 1px dotted #666; font-size: 11px;">Details</th>
			</tr>
			<tr style="height: 37px;">
				<th colspan="2">Stories</th>
				<th colspan="5">Scenarios</th>
				<th></th>
				<th></th>
			</tr>
		</table>
		<br />
	</div>


</body>

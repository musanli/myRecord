<html>
<head>
  <title>${title}</title>
  
</head>
<body>
  <h1>Just a blank page.</h1>
  <div style="page-break-before:always;">
      <div align="center">
	      <h1>${title}222</h1>
	  </div>
	  
	  <tbody>
		<tr>
		<td>Simple Cell 1.1</td>
		<td>Simple Cell 1.2</td>
		<td>Simple Cell 1.3</td>
		</tr>
		<#list SysmaxnoList as Sysmaxno>
	        <tr>
		        <td><b>${Sysmaxno.nolimite}</b></td>
		        <td><b>${Sysmaxno.notype}</b></td>
		        <td>
		          <i>${Sysmaxno.serno}</i>
		        </td>
	        </tr>
	     </#list>
		</tbody>
		</table>

  </div>
</body>
</html> 
<#macro html title="" desc="" keyword="">
<!DOCTYPE html>
<html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="description" content="${desc}" />
        <meta name="keywords" content="${keyword}" />
        <title>${title}</title>
        </head>
        <body> 
        <#nested/>
        
        </body>
</html> 
</#macro>

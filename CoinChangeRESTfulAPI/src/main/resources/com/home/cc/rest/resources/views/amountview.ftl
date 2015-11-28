<!DOCTYPE html>

<html>
  <head>
    <title>Coin Change Finder</title>
    <style type="text/css">
      .label {text-align: right}
      .error {color: red}
    </style>

  </head>

  <body>
  <#if error?has_content>
       <p><b>${amount} is not a valid input </b></p>
       <p><b>Message from server: ${error}</b></p>
  <#else>
       <h2>Change found for the amount ${amount}</h2>
       <p>
           
       <p><b>${amount}</b> = <b> ${breakDown} </b></p>    
  </#if>
    
    <a href="/">Start Again</a> 
    
  </body>

</html>

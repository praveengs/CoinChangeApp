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
    <h2>Coin Change Generator</h2>

    <form action="/coin-change" method="GET">
      <table>
        <tr>
             <TD class = "select">Select Currency</TD>
             <TD>
             <select name="currency">
                 <#-- Note: x is a loop variable -->
                 <#list currencies as currency>
                    <option value="${currency}">${currency}</option>
                 </#list>
             </select>
             </TD>
        </tr>

        <tr>
          <td class="label">
            Enter the amount
          </td>
          <td>
            <input type="text" name="amount" value="">
          </td>
          <td class="error">
          </td>
        </tr>

      </table>

      <input type="submit">
    </form>
  </body>

</html>

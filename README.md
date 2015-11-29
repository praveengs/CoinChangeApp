# CoinChangeApp

A couple of points about the application.

1. Used java 8 for the program so that I could leverage the use of streams and lambdas to write my code concisely.
2. CoinChangeModel subproject
    - Used spring to define a collection of currency models.
    - Used spring java config to make the loading of currencies a bit more obvious, combined with loading of currency models from a spring xml file(currency-formats.xml).
    - There is one Currency per currency, and each model has its attributes along with the patterns that can be used to match the amount
    - The patterns are regex expressions, which can be adapted/created for further patterns.
    - There is a regex expression to match the currency amount (currency regex) and another regex expression to extract the amount (amount regex) from the currency regex
    - CurrencyBuilder class does the leg work for computing the change using the currency models and patterns. This is where the logic of computing the change persists.
    - CoinChangeGenerator does (i) the initilisation using spring and (ii) does the conversion work of HashMap of results from CurrencyBuilder into String and JSON, as and when needed/called
    - The code itself was initially built from test cases written within the test subfolder.
3. CoinChangeRESTfulAPI subproject
    - Used dropwizard framework for creating RESTful web services to consume requests
       . This helped in reducing a lot of boiler plate code to create a new REST service
       . Easy to run as a standalone program
       . Uses jersey behind the scenes to register the services
       . Provides support for freemarker, which helps in templating web pages to display
    - Used freemarker within dropwizard for templating the display pages
    - CoinChangeController is the entry point for the webservice, which instantiates and registers the available services
    - Two services are registered, under "/" (mapped to FrontPageResource) and under "/coin-change" (mapped to CoinChangeResource)
    - The FrontPageResource is used to display the currency and amount form. 
    	. Uses the PageView class to set up the data structure for the page
    	. Uses frontpage.ftl under resources directory for the template of the page
    	. Gets the currency list from CoinChangeGenerator to display the drop down list
    - The CoinChangeResource is used to retrieve the change for a particular amount and display it in a web page
    	. Retrieves the change from CoinChangeGenerator 
    	. Displays it using amountview.ftl template under resources directory
    	. If an InvalidInput is received, displays it gracefully in the web page.
    
  
##Extensiveness of currency
1. Since it is regex match, newer patterns could be matched using regex without code change
2. Since it is using spring to load the patterns, only file that needs to be edited is the xml file. Again no change in code
3. Also addition of new currency and its patterns are confined in the xml, which makes it flexible for future addition of currencies

##TODOs
1. Possibly separate out UI/template bits out into a separate project
2. The REST services started out as something that would return JSON, the initial test cases were there for that. But then when UI was blended into it, these test cases were no longer valid. So had to comment them out. This can be sorted out once point 1 is done. But this is for future

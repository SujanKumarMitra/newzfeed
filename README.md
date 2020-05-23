# <img src = "src/main/resources/static/images/icon.png" width="150px" alt = "logo" /> Newsfeed App
A <a href ="https://spring.io/projects/spring-boot">Spring Boot</a> Application which displays curated news articles to users. <br/>
Link: [Heroku](https://newzfeed.herokuapp.com) OR https://newzfeed.herokuapp.com/                       

## Features
1. Displays headlines on homepage 
1. Articles are segregated into different categories.
1. Search articles using keywords in search bar.
1. User can also sign up in the appliation.
1. For signed up users:
   1. Users can subscribe to different categories
   1. A new link in which articles of __categories the user is subscribed to__, will be displayed.

## To run
1. Import the project in your IDE.
1. Fill up the appropriate credentials in <a href="src/main/resources/application.properties">application.properties</a>
   1. DataSource Configurations
   1. News API key. (__if not present then generate one for free at <a href ="https://newsapi.org/">News API</a>__)
   1. Facebook and Google OAuth credentials. (__required for OAuth login__)
1. Run the project
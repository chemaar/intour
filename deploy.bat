PATH=C:\Program Files\Git\cmd;C:\Program Files (x86)\Heroku\bin;C:\Program Files (x86)\sbt\\bin;C:\Ruby21-x64\\bin;
heroku login
heroku deploy:war --war trunk\apps\intour-web\target\intour-web-0.1-SNAPSHOT.war --app intour-web

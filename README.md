# IT Revolution Test Web Project

**PROJECT AVILIABLE ON **HEROKU** [Github Issue Browser](https://itrevolution2017.herokuapp.com/)**
### Made by [Nikita Khomenko](https://github.com/KhomenkoCode) and  [Daniil Nikulin](https://github.com/ddci)

----------
**Option 1:** To run this project you need to install Tomcat and deploy given WAR file into it and run Tomcan 
Folder path to put WAR file ***apache-tomcat-8.?.??/webapps/ITRevolution2017***  
Then open [localhost:8080/ITRevolution2017/](http://lcalhost:8080/ITRevolution2017/)


----------

**Option 2:** You can build the project from the given  code and move artifacts (WAR file) into Tomcats folder *webapps/ITRevolution and start the project*.
Or set your IntelliJ IDEA IDE for this project: following this guide: [Intellij IDEA деплой на Tomcat](http://devcolibri.com/4249) providing Tomcat Server is downloaded and unzipped into some folder (you will need to specify it in  IDE)
Your IntelliJ IDEA IDE Run Configuration should look like this:

![alt text](https://raw.githubusercontent.com/ddci/ITRev_Web_Test_Project/master/img/idea_1.JPG "Preview")
![alt text](https://raw.githubusercontent.com/ddci/ITRev_Web_Test_Project/master/img/Idea_2.JPG "Preview")


But before you run use ```mvn clean install```


----------
**Option3:** Almost the same for **Eclipse**:  
**1** Переходим в перспективу "Java EE" или добавляем отображение установленных серверов eclipse 

![alt text](https://raw.githubusercontent.com/KhomenkoCode/ITRevolution2017/master/img/eclipse_1.jpg "Preview") 

**2** Выбираем сервер и нажимает Add and Remove 

**3** Добавляем сервер в "конфигуриуемые"  
![alt text](https://raw.githubusercontent.com/KhomenkoCode/ITRevolution2017/master/img/eclipse_1.jpg "Preview")

 **4**Нажимаем пуск 
 
 ![alt text](https://raw.githubusercontent.com/KhomenkoCode/ITRevolution2017/master/img/eclipse_1.jpg "Preview")  

----------


```
code
```

### Risetek User Management & Identify

======================
### DEBUG and Development
* `mvn gwt:run-codeserver` 进行gwt界面调试
* `mvn jetty:run` 启动本地服务
 
### DOCKER
* docker run --name openauth -v /home/developer/webapps:/var/lib/jetty/webapps -p 10082:8080 jetty
* docker run -d --name openauth -v /home/developer/webapps:/var/lib/jetty/webapps -p 10082:8080 jetty
* docker run -d --name openauth -v /home/developer/webapps:/var/lib/jetty/webapps -v /home/developer/risetekauth:/risetekauth -p 10082:8080 jetty
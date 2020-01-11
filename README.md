### Risetek User Management & Identify

======================
### DEBUG and Development
* `mvn gwt:run-codeserver` 进行gwt界面调试
* `mvn jetty:run` 启动本地服务
 
### DOCKER
* docker run --name openauth -v /home/developer/webapps:/var/lib/jetty/webapps -p 10082:8080 jetty
* docker run -d --name openauth -v /home/developer/webapps:/var/lib/jetty/webapps -p 10082:8080 jetty
* docker run -d --name openauth -v /home/developer/webapps:/var/lib/jetty/webapps -v /home/developer/risetekauth:/risetekauth -p 10082:8080 jetty

### DOCKER IMAGE
* copy risetek.auth-0.0.1-SNAPSHOT.war to docker/src/ROOT.war, build docker image
* [OR] copy risetek.auth-0.0.1-SNAPSHOT.war to local dir as ROOT.war, mount local dir as volumes to /var/lib/jetty/webapps.

### 数据迁移/备份
* 数据迁移或者备份的时候，应该将 db.script整体进行迁移或者备份 
FROM tomcat:9-jre11-slim
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY DrawDocker.war /usr/local/tomcat/webapps/
RUN cd /usr/local/tomcat/webapps/ &&\
        mv DrawDocker.war ROOT.war
RUN /usr/local/tomcat/bin/startup.sh

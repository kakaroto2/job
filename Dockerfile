FROM w1yd/tomcat7:jdk7-newrelic

MAINTAINER zheng guang "zzglfh@gamil.com"

WORKDIR /code

RUN echo "Asia/Shanghai" > /etc/timezone && \
        dpkg-reconfigure -f noninteractive tzdata

RUN rm -r /usr/local/tomcat/webapps/* 

#build 
ADD pom.xml /code/pom.xml 

#TODO  local jar /src/main/webapp/WEB-INF/lib/javapns-jdk16-163.jar  need del
ADD /src/main/webapp/WEB-INF/lib/ /code/src/main/webapp/WEB-INF/lib
RUN mvn dependency:resolve
ADD . /code

RUN mvn install  && cp -r target/ROOT.war /usr/local/tomcat/webapps/ && rm -r /code && rm -r /root/.m2


# Start Tomcat
CMD ["catalina.sh", "run"]

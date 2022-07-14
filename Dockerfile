FROM centos:centos7
USER root
RUN yum -y install musl-dev linux-headers g++ gcc git curl java-1.8.0-openjdk-devel which wget
RUN yum -y clean all
WORKDIR /code
COPY . /code
COPY config.properties /code
RUN chmod +x /code/install.sh
RUN sh /code/install.sh
EXPOSE 8080
CMD ["/code/apache-tomcat-8.5.35/bin/catalina.sh", "run"]

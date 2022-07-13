FROM centos:centos7
USER root
RUN yum -y install musl-dev linux-headers g++ gcc elasticsearch git curl java-1.8.0-openjdk-devel which
RUN yum -y clean all
WORKDIR /code
COPY myproject /code
COPY install.sh /code
COPY start.sh /code
RUN chmod +x /code/install.sh
RUN sh /code/install.sh
RUN chmod +x /code/start.sh
ENTRYPOINT sh start.sh

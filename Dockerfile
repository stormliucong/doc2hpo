FROM centos:centos7
USER root
RUN yum -y install musl-dev linux-headers g++ gcc elasticsearch git curl java-1.8.0-openjdk-devel which
RUN yum -y clean all
WORKDIR /code
COPY public_mm_data_lite_base_2020aa.zip /code
COPY public_mm_lite_3.6.2rc6_binaryonly.zip /code
RUN chmod +x /code/install.sh
COPY config.properties /code
RUN chmod +x /code/start.sh
ENTRYPOINT sh start.sh

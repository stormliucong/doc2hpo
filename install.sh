#wget -O ~/jdk8.rpm -N --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u181-b13/96a7b8442fe848ef90c96a2fad6ed6d1/jdk-8u181-linux-x64.rpm
#yum -y localinstall ~/jdk8.rpm
#update-alternatives --install /usr/bin/java java /usr/java/jdk1.8.0_181-amd64/jre/bin/java 1
#update-alternatives --install /usr/bin/jar jar /usr/java/jdk1.8.0_181-amd64/bin/jar 1
#update-alternatives --install /usr/bin/javac javac /usr/java/jdk1.8.0_181-amd64/bin/javac 1
#update-alternatives --install /usr/bin/javaws javaws /usr/java/jdk1.8.0_181-amd64/jre/bin/javaws 1
wget https://archive.apache.org/dist/tomcat/tomcat-8/v8.5.35/bin/apache-tomcat-8.5.35.tar.gz
wget https://archive.apache.org/dist/maven/maven-3/3.6.0/binaries/apache-maven-3.6.0-bin.tar.gz
tar -xzf apache-tomcat-8.5.35.tar.gz
tar -xzf apache-maven-3.6.0-bin.tar.gz
# doc2hpo stuff
#### 1. copy mmp java api to lib
mkdir ./src/main/webapp/WEB-INF/lib
mkdir ./properties
#### 2. copy mmlite jar to lib
# unzip public_mm_data_lite_base_2020aa.zip
cp ./public_mm_lite/target/metamaplite-3.6.2rc6.jar ./src/main/webapp/WEB-INF/lib
cp ./public_mm_lite/lib/* ./src/main/webapp/WEB-INF/lib
cp ./public_mm_lite/config/* ./properties

### 3. copy local config to container
cp ./config.properties ./src/main/webapp/WEB-INF/
# if necessary export JAVA_HOME=/Users/cl3720/Desktop/doc2hpo_workplace/jdk-18.0.1.1.jdk/Contents/Home
./apache-maven-3.6.0/bin/mvn clean validate install
cp ./target/doc2hpo.war ./apache-tomcat-8.5.35/webapps/
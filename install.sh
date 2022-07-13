#wget -O ~/jdk8.rpm -N --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u181-b13/96a7b8442fe848ef90c96a2fad6ed6d1/jdk-8u181-linux-x64.rpm
#yum -y localinstall ~/jdk8.rpm
#update-alternatives --install /usr/bin/java java /usr/java/jdk1.8.0_181-amd64/jre/bin/java 1
#update-alternatives --install /usr/bin/jar jar /usr/java/jdk1.8.0_181-amd64/bin/jar 1
#update-alternatives --install /usr/bin/javac javac /usr/java/jdk1.8.0_181-amd64/bin/javac 1
#update-alternatives --install /usr/bin/javaws javaws /usr/java/jdk1.8.0_181-amd64/jre/bin/javaws 1
cd public_mm
chmod +x bin/install.sh
./bin/install.sh
cd ..
cd public_mm_lite
chmod +x install.sh
./install.sh
cd ..
# doc2hpo stuff
#### 1. copy mmp java api to lib
mkdir ./doc2hpo/src/main/webapp/WEB-INF/lib
mkdir ./doc2hpo/properties
#### 2. copy mmlite jar to lib
unzip public_mm_data_lite_base_2020aa.zip
cp ./public_mm_lite/target/metamaplite-3.6.2rc6.jar ./doc2hpo/src/main/webapp/WEB-INF/lib
cp ./public_mm_lite/lib/* ./doc2hpo/src/main/webapp/WEB-INF/lib
cp ./public_mm_lite/config/* ./doc2hpo/properties

### 3. copy local config to container
cp ./config.properties ./doc2hpo/src/main/webapp/WEB-INF/
cd doc2hpo
# if necessary export JAVA_HOME=/Users/cl3720/Desktop/doc2hpo_workplace/jdk-18.0.1.1.jdk/Contents/Home
../apache-maven-3.6.0/bin/mvn clean validate install
cp ./target/doc2hpo.war ../apache-tomcat-8.5.35/webapps/
cd ..

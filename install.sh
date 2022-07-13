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
cp ./public_mm/src/javaapi/target/metamap-api-2.0.jar ./doc2hpo/src/main/webapp/WEB-INF/lib
cp ./public_mm/src/javaapi/dist/prologbeans.jar ./doc2hpo/src/main/webapp/WEB-INF/lib
cp ./public_mm/src/javaapi/dist/MetaMapApi.jar ./doc2hpo/src/main/webapp/WEB-INF/lib
#### 2. copy mmlite jar to lib
cp ./public_mm_lite/target/metamaplite-3.6.2rc3.jar ./doc2hpo/src/main/webapp/WEB-INF/lib
cp ./public_mm_lite/lib/* ./doc2hpo/src/main/webapp/WEB-INF/lib
cp ./public_mm_lite/config/* ./doc2hpo/properties
#### 3. change config file (if necessary)
##### Important. Make sure you set everything correctly.
##### Otherwise there is a 404 error.
# change the name of config.properties_bak to config.properties
# myproject/doc2hpo/src/main/webapp/WEB-INF/config.properties_bak:MetamapBinPath=/yourpathto/public_mm/bin
# myproject/doc2hpo/src/main/webapp/WEB-INF/config.properties_bak:metamapliteconfiger=/yourpathto/doc2hpo/properties/metamaplite.properties
# make sure this is actually changed. for real.
mv doc2hpo/src/main/webapp/WEB-INF/config.properties_bak doc2hpo/src/main/webapp/WEB-INF/config.properties
cd doc2hpo
../apache-maven-3.6.3/bin/mvn clean validate install
cp ./target/doc2hpo.war ../apache-tomcat-8.5.35/webapps/
cd ..

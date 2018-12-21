# doc2hpo
doc2hpo is a java spring mvc based webapp to parse clinical note and get the HPO for phenolyzer analysis.
 ## Installation
  ### Step 0 : download everything you need
  ```bash 
  #### 0. install java if you don't have one
  sudo add-apt-repository ppa:webupd8team/java
  sudo apt-get update
  sudo apt-get install oracle-java8-installer
  javac -version
  #### 1. download apache tomcat if you don't have one
  wget http://ftp.naz.com/apache/tomcat/tomcat-8/v8.5.35/bin/apache-tomcat-8.5.35.tar.gz
  #### 2. download apache maven if you don't have one
  wget https://www-us.apache.org/dist/maven/maven-3/3.6.0/binaries/apache-maven-3.6.0-bin.tar.gz
  #### 3. git clone this repository 
  git clone https://github.com/stormliucong/doc2hpo.git
  ```
  #### 4. download MetaMap and MetaMap Java API if you don't have one (Optional, only if you want to use MetaMap parsing engine)
   please visit https://metamap.nlm.nih.gov/ to download _MetaMap 2016v2 Linux Version_ and _MetaMap Java API Release for Linux_
  #### 5. please put everything in one directory (Let's call it `__myproject__` for now)
   you should have __apache-maven-3.6.0-bin.tar.gz__, __public_mm_linux_javaapi_2016v2.tar.bz2__, __apache-tomcat-8.5.35.tar.gz__, __doc2hpo/__ now under __myproject__
 
  ### Step 1: Installation of everything you need
  ```bash
  cd myproject
  #### 1. install tomcat
  tar -xvf apache-tomcat-8.5.35.tar.gz
  #### 2.install maven
  tar -xzvf apache-maven-3.6.0-bin.tar.gz
  #### 3.install MetaMap
  bunzip2 -c public_mm_linux_main_2016v2.tar.bz2 | tar xvf -
  cd public_mm/
  ./bin/install.sh` 
  # prese ender to use default settings #
  #### 4.install MetaMap Java API
  cd ../
  bzip2 -dc public_mm_linux_javaapi_2016v2.tar.bz2 | tar xvf -
  cd public_mm/
  ./bin/install.sh
  # prese ender to use default settings #
  # test java api #
  ./bin/testapi.sh breast cancer
  ```
  ### Step 2: Configuration of Doc2Hpo
  ```bash
  cd myproject
  #### 1. copy mmp java api to lib
  mkdir ./doc2hpo/src/main/webapp/WEB-INF/lib
  cp ./public_mm/src/javaapi/target/metamap-api-2.0.jar ./doc2hpo/src/main/webapp/WEB-INF/lib
  cp ./public_mm/src/javaapi/dist/prologbeans.jar ./doc2hpo/src/main/webapp/WEB-INF/lib
  cp ./public_mm/src/javaapi/dist/MetaMapApi.jar ./doc2hpo/src/main/webapp/WEB-INF/lib
  #### 2. change config file (if necessary)
  cd ./doc2hpo/src/main/webapp/WEB-INF
  vi config.properties
  ```
  ### Step 3: Deploy of Doc2Hpo
  ```bash
  cd myproject
  #### 1. maven compile
  cd doc2hpo/
  ../apache-maven-3.6.0/bin/mvn clean validate install
  cp ./target/doc2hpo.war ./apache-tomcat-8.5.35/webapps/
  #### 2. start MetaMap server (optional)
  cd myproject
  ./public_mm/bin/skrmedpostctl start
  ./public_mm/bin/wsdserverctl start
  nohup ./public_mm/bin/mmserver16 &
  #### 3. start tomcat
  cd myproject
  ./apache-tomcat-8.5.35/bin/startup.sh
  ```
  ### Step 4: visit Doc2Hpo at *localhost:8080/doc2hpo*, and you are all set!


## References
  ### Download link
  - java version 1.8.0_191 (https://www.java.com/en/download/)
  - apache-tomcat version 8.5.35 (https://tomcat.apache.org/download-90.cgi)
  - apache-maven-3.6.0 (https://maven.apache.org/install.html)
  - metamap-api-2.0.jar (https://metamap.nlm.nih.gov/MainDownload.shtml)
  - MetaMap 2016v2 Linux Version (https://metamap.nlm.nih.gov/MainDownload.shtml)
  - ncbo bioportal (https://github.com/stormliucong/docker-compose-bioportal)
  - Api key for ncbo annotator (http://data.bioontology.org/documentation)
  ### Documentation
  - Install metamap and metamap java api (https://metamap.nlm.nih.gov/Installation.shtml and https://metamap.nlm.nih.gov/Docs/README_javaapi.shtml#Downloading,%20Extracting%20and%20Installing%20the%20API%20distribution)
  * You have to get a free UMLS license to install the software
  - Starting supporting servers and running the MetaMap server
  * Follow the instruction here https://metamap.nlm.nih.gov/Docs/README_javaapi.shtml#Using%20the%20MetaMap%20server
  - Install ncbo bioportal docker image (https://github.com/stormliucong/docker-compose-bioportal)
  * add a proxy setup when build the docker container if necessary (https://docs.docker.com/network/proxy/)
  * change docker-compose-bioportal/bioportal-api/Dockerfile `bundle config git.allow_insecure true install` if necessary
  * add proxy for maven if necessart docker-compose-bioportal/bioportal-annotator-proxy/Dockerfile (http://maven.apache.org/guides/mini/guide-proxies.html)
  * change war file to `/annotators/sifr-bioportal-annotator-proxy/target/annotator.war` if necessary in docker-compose-bioportal/bioportal-annotator-proxy/Dockerfile 
  * change tomcat link if necessary (https://archive.apache.org/dist/tomcat/tomcat-6/v6.0.53/bin/apache-tomcat-6.0.53.tar.gz) in docker-compose-bioportal/bioportal-annotator-proxy/Dockerfile
  - Change MetamapBinPath in `doc2hpo/src/main/webapp/WEB-INF/config.properties`
  - Please change Api key for ncbo annotator in `doc2hpo/src/main/webapp/WEB-INF/config.properties`
  * You need to register a free account to get api key https://bioportal.bioontology.org/account
  - Add proxy and port if necessary in `doc2hpo/src/main/webapp/WEB-INF/config.properties`
  * `Proxy=null` and `Port=null` if you don't need proxy.
  - export the `doc2hpo.war` file for the project. You could do it using eclipse or by maven
  * You may find this link helpful https://www.baeldung.com/tomcat-deploy-war
  - deploy the war file under `apache-tomcat-8.5.35/webapps`
  * Make sure privilege is correct.
  - start the tomcat and browse the results.
  * You could check version requirement by calling api at `servername:8080/doc2hpo/version`

## Versioning
0.18.4

## New features under development
  - Test across multiple browser and platforms
  - Add context based annotation in backend
  - Using different color to seperate the category (e.g. family, negation, education) in frontend
  - Add more parsers
  - metamap Lite https://metamap.nlm.nih.gov/MetaMapLite.shtml
  - cTakes http://ctakes.apache.org/
  - ClinPhen http://bejerano.stanford.edu/clinphen/

## Publications
under preparation

## Authors
Cong Liu, Chi Yuan, Kai Wang, Chunhua Weng
stormliucong@gmail.com

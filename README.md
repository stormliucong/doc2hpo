# doc2hpo
doc2hpo is a java spring mvc based webapp to parse clinical note and get the HPO for phenolyzer analysis.
## Demo website
- Primary: https://impact.dbmi.columbia.edu/doc2hpo/ (Currently unavailable)
- Backup: https://doc2hpo.wglab.org/

## RESTful API Example
```
import requests
import json

# for test purpose.
url = "https://impact.dbmi.columbia.edu/doc2hpo/version"
r = requests.post(url)
print(r.json())
# {u'ncbo': None, u'java': u'1.8.0_191', u'tomcat': u'8.5.35', u'doc2hpo': u'1.21.0', u'metamaplite': u'metamaplite-3.6.2rc3.jar', u'metamap': u'2016v2'}

# for string-based match. faster. 
url = "https://impact.dbmi.columbia.edu/doc2hpo/parse/acdat"
json = {
	"note": "He denies synophrys.",
    "negex": True
}
# headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
r = requests.post(url,json = json)
print(r.json())

# with negation detection enabled.
url = "https://impact.dbmi.columbia.edu/doc2hpo/parse/acdat"
json = {
	"note": "He denies synophrys.",
    "negex": True
}
# headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
r = requests.post(url,json = json)
print(r.json())

# using metamap lite. Much faster than Original Metamap.
url = "https://impact.dbmi.columbia.edu/doc2hpo/parse/metamaplite"
json = {
	"note": "He denies synophrys.",
    "negex": True
}
# headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
r = requests.post(url,json = json)
print(r.json())

# using ncbo annotator - recommended if single file is large.
url = "https://impact.dbmi.columbia.edu/doc2hpo/parse/ncbo"
json = {
	"note": "He denies synophrys.",
    "negex": True
}
# headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
r = requests.post(url,json = json)
print(r.json())

```
## Deploy Doc2Hpo with Docker
1. Clone the Doc2Hpo github repository
```
git clone https://github.com/stormliucong/doc2hpo.git
cd doc2hpo
```
2. (Optional) Download MetaMapLite and UMLS if you don't have one. 
Please visit https://lhncbc.nlm.nih.gov/ii/tools/MetaMap/run-locally/MetaMapLite.html for details.
and check the download.sh file for the version requirement
After download, unzip files.
```
unzip public_mm_lite_3.6.2rc6_binaryonly.zip
unzip public_mm_data_lite_base_2020aa.zip
```
3. Change `config.properties`
```
# setup the proxy you want to use. Put null if don't use
Proxy=null
# setup the proxy port you want to use. Put null if don't use
Port=null
# ncbo api url. use public one http://data.bioontology.org by default
NcboUrl=https://data.bioontology.org
# ncbo api key. use Cong's api in the public demo server. Input your own for internal server.
NcboApiKey=put-your-own-api-key-here
# dir for metamaplite setting. (No need to change, if you follow this instruction.)
metamapliteDataRoot=/code/public_mm_lite/data
```
4. Build the COHD docker image
```bash
docker build -t doc2hpo .
```
5. Run the COHD docker container (the second port mapping to 443 is only necessary if enabling HTTPS)
```
docker run -d -p [HOST:PORT]:8080 --name=doc2hpo-production doc2hpo
```




## [DEPRECATED] Instructions for manually deploying Doc2Hpo
  ### Step 0 : download everything you need
  ```bash 
  #### 0. install java if you don't have one
  sudo add-apt-repository ppa:webupd8team/java
  sudo apt-get update
  sudo apt-get install oracle-java8-installer
  javac -version
  #### 1. download apache tomcat if you don't have one
  wget https://archive.apache.org/dist/tomcat/tomcat-8/v8.5.35/bin/apache-tomcat-8.5.35.tar.gz
  #### 2. download apache maven if you don't have one
  wget https://archive.apache.org/dist/maven/maven-3/3.6.0/binaries/apache-maven-3.6.0-bin.tar.gz
  #### 3. git clone this repository 
  git clone https://github.com/stormliucong/doc2hpo.git
  ```
  #### 4. (Optional) download MetaMap and MetaMap Java API if you don't have one 
   please visit https://metamap.nlm.nih.gov/ to download _MetaMap 2016v2 Linux Version_ and _MetaMap Java API Release for Linux_
  #### 5. (Optional) download MetaMapLite if you don't have one
   please visit https://metamap.nlm.nih.gov/MetaMapLite.shtml to download __MetaMapLite 2018 3.6.2rc3 with Category 0+4+9 (USAbase) 2018AA UMLS dataset__
  #### 6. please put everything in one directory (Let's call it `__myproject__` for now)
   you should have __apache-maven-3.6.0-bin.tar.gz__, __public_mm_linux_javaapi_2016v2.tar.bz2__, __public_mm_lite_3.6.2rc3.zip__, __apache-tomcat-8.5.35.tar.gz__, __doc2hpo/__ now under __myproject__
 
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
  ./bin/install.sh
  # press enter to use default settings #
  #### 4.install MetaMap Java API
  cd ../
  bzip2 -dc public_mm_linux_javaapi_2016v2.tar.bz2 | tar xvf -
  cd public_mm/
  ./bin/install.sh
  # prese ender to use default settings #
  # test java api #
  ./bin/testapi.sh breast cancer
  #### 5.install MetaMapLite
  cd ../
  unzip public_mm_lite_3.6.2rc3.zip
  ```
  ### Step 2: Configuration of Doc2Hpo
  ```bash
  cd myproject
  #### 1. copy mmp java api to lib
  mkdir ./doc2hpo/src/main/webapp/WEB-INF/lib
  cp ./public_mm/src/javaapi/target/metamap-api-2.0.jar ./doc2hpo/src/main/webapp/WEB-INF/lib
  cp ./public_mm/src/javaapi/dist/prologbeans.jar ./doc2hpo/src/main/webapp/WEB-INF/lib
  cp ./public_mm/src/javaapi/dist/MetaMapApi.jar ./doc2hpo/src/main/webapp/WEB-INF/lib
  #### 2. copy mmlite jar to lib
  cp ./public_mm_lite/target/metamaplite-3.6.2rc3.jar ./doc2hpo/src/main/webapp/WEB-INF/lib
  cp ./public_mm_lite/lib/* ./doc2hpo/src/main/webapp/WEB-INF/lib
  #### 3. change config file (if necessary)
  ##### Important. Make sure you set everything correctly.
  ##### Otherwise there is a 404 error and some engines in doc2hpo might not work properly
  ```
  example configure file
  ```bash
  # please change the name of this file to config.properties_bak.
  # setup the proxy you want to use. Put null if don't use
  Proxy=null
  # setup the proxy port you want to use. Put null if don't use
  Port=null
  # ncbo api url. use public one http://data.bioontology.org by default
  NcboUrl=https://data.bioontology.org
  # ncbo api key. use Cong's api in the public demo server. Input your own for internal server.
  NcboApiKey=xxxxxxxxxxx 
  # dir for metamaplite setting
  metamapliteDataRoot=/home/cl3720/public_mm_lite/data
  ```

  ```
  cd ./doc2hpo/src/main/webapp/WEB-INF
  # change the name of config.properties_bak to config.properties
  mv config.properties_bak config.properties
  vi config.properties
  cd myproject
  cd ./doc2hpo/properties
  # change metamaplite db path accordingly
  # you don't have to change by default.
  vi metamaplite.properties
  ```
  ### Step 3: Deploy of Doc2Hpo
  ```bash
  cd myproject
  #### 1. maven compile
  cd doc2hpo/
  ../apache-maven-3.6.0/bin/mvn clean validate install
  cp ./target/doc2hpo.war ../apache-tomcat-8.5.35/webapps/
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
  
  ### Step 5: For IMPACT2 server only.
  change the ajax url to /doc2hpo/parse/acdat for Apache deligation (a better solution is required).
  


## References
  ### Download link
  - java version 1.8.0_191 (https://www.java.com/en/download/)
  - apache-tomcat version 8.5.35 (https://tomcat.apache.org/download-90.cgi)
  - apache-maven-3.6.0 (https://maven.apache.org/install.html)
  - metamap-api-2.0.jar (https://metamap.nlm.nih.gov/MainDownload.shtml)
  - MetaMap 2016v2 Linux Version (https://metamap.nlm.nih.gov/MainDownload.shtml)
  - ncbo bioportal (https://github.com/stormliucong/docker-compose-bioportal)
  - Api key for ncbo annotator (http://data.bioontology.org/documentation)
  - MetaMap Lite (https://metamap.nlm.nih.gov/MetaMapLite.shtml)
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
1.21.0



## New features under development
  - Test across multiple browser and platforms
  - Add context based annotation in backend
  *Using different colors to seperate the category (e.g. family, education) in frontend
  - Add more parsers
  *cTakes http://ctakes.apache.org/
  *ClinPhen http://bejerano.stanford.edu/clinphen/
  *clinicalbert based parsing https://github.com/EmilyAlsentzer/clinicalBERT
  - Add FHIR supoort

## Publications
Cong Liu, Fabricio Sampaio Peres Kury, Ziran Li, Casey Ta, Kai Wang, Chunhua Weng, Doc2Hpo: a web application for efficient and accurate HPO concept curation, Nucleic Acids Research, , gkz386, 
https://doi.org/10.1093/nar/gkz386
## Authors
Cong Liu, Chi Yuan, Kai Wang, Chunhua Weng
stormliucong@gmail.com

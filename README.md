# doc2hpo

doc2hpo is a java spring mvc based webapp to parse clinical note and get the HPO for phenolyzer analysis.

## Getting Started
It is not enough to use this git repo to deploy the webapp.

### Prerequisites
- java version 8 (https://www.java.com/en/download/)
- apache-tomcat-9.0.5 (https://tomcat.apache.org/download-90.cgi)
- apache-maven-3.6.0 (https://maven.apache.org/install.html)
- metamap-api-2.0.jar (Optional) (https://metamap.nlm.nih.gov/MainDownload.shtml)
- MetaMap 2016v2 Linux Version (https://metamap.nlm.nih.gov/MainDownload.shtml)
- Api key for ncbo annotator (http://data.bioontology.org/documentation)


### Configure
- Install metamap and metamap java api (https://metamap.nlm.nih.gov/Installation.shtml and https://metamap.nlm.nih.gov/Docs/README_javaapi.shtml#Downloading,%20Extracting%20and%20Installing%20the%20API%20distribution)
* You have to get a free UMLS license to install the software
- Starting supporting servers and running the MetaMap server
- Change MetamapBinPath in `doc2hpo/src/main/webapp/WEB-INF/config.properties`
- Register a free account on ncbo annotator https://bioportal.bioontology.org/account
- Please change Api key for ncbo annotator in `doc2hpo/src/main/webapp/WEB-INF/config.properties`
- 

## Versioning

V_0.1.2
## Authors

Cong Liu, Chi Yuan, Kai Wang, Chunhua Weng

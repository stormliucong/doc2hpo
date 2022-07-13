# need UMLS license for these 4 downloads
wget https://metamap.nlm.nih.gov/download/public_mm_linux_main_2016v2.tar.bz2
wget https://metamap.nlm.nih.gov/download/public_mm_linux_javaapi_2016v2.tar.bz2
wget https://metamap.nlm.nih.gov/download/metamaplite/public_mm_lite_3.6.2rc3_binaryonly.zip
wget https://metamap.nlm.nih.gov/download/metamaplite/public_mm_data_lite_usabase_2018ab_ascii.zip
tar -xjf public_mm_linux_main_2016v2.tar.bz2
tar -xjf public_mm_linux_javaapi_2016v2.tar.bz2
unzip public_mm_lite_3.6.2rc3_binaryonly.zip
unzip public_mm_data_lite_usabase_2018ab_ascii.zip
# apache maven/tomcat
wget https://archive.apache.org/dist/tomcat/tomcat-8/v8.5.35/bin/apache-tomcat-8.5.35.tar.gz
wget https://mirrors.koehn.com/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
tar -xzf apache-tomcat-8.5.35.tar.gz
tar -xzf apache-maven-3.6.3-bin.tar.gz
# doc2hpo
git clone https://github.com/stormliucong/doc2hpo.git

docker build -t doc2hpo .
docker run -d -p 7000:8080 --name=doc2hpo-production doc2hpo

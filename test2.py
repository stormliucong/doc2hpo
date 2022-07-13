import requests
import json

# for string-based match. faster. 
url = "http://localhost:7000/doc2hpo/session/updateTerms"
j = {"start":1214,"length":17,"hpoId":"HP:0005280","hpoName":"FLAT NASAL BRIDGE","negated":True}
# headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
r = requests.post(url,json = j)
print(r.content)

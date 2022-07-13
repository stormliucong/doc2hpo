import requests
import json
# for test purpose.
url = "http://localhost:7000/doc2hpo/version"
r = requests.post(url)
print(r.json())

# for string-based match. faster. 
url = "http://doc2hpo.wglab.org/parse/acdat"
j = {
    "note": "He denies synophrys.",
    "negex": True
}
# headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
r = requests.post(url,json = j)
print(r.content)

# with negation detection enabled.
url = "http://localhost:7000/doc2hpo/parse/acdat"
j = {
    "note": "He denies synophrys.",
    "negex": True
}
# headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
r = requests.post(url,json = j)
print(r.json())

# using metamap lite. Much faster than Original Metamap.
url = "http://157.245.2.226:5004/doc2hpo/parse/metamaplite"
j = {
    "note": "He denies synophrys.",
    "negex": True
}
# headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
r = requests.post(url,json = j)
print(r.json())

# using ncbo annotator - recommended if single file is large.
url = "http://157.245.2.226:5004/doc2hpo/parse/ncbo"
j = {
    "note": "He denies synophrys.",
    "negex": True
}
# headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
r = requests.post(url,json = j)
print(r.json())


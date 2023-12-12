This project is a service that is desgined to be deployed in Google Cloud Functions. The intention is to deploy to GCP via Gcloud commands. 
Once deployed you can hit the endpoint and it will return a long value representing a counter. 
The counter is stored in FireStore also a google cloud service the logic will get the document stored within the datastore and return the value. In this case a long value. 
The solution is designed to allow multiple users to access the endpoint and not receive the same value.

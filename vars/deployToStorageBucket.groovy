#! /usr/bin/env groovy

// @Author: Dinesh Gurram
import hudson.model.*

def call(Closure body) {

    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
           try{
            stage('DeploytoStorageBucket'){


        				//Deploy to GCP
        				sh """
        					#!/bin/bash
        					echo "deploy stage";
        					curl -o /tmp/google-cloud-sdk.tar.gz https://dl.google.com/dl/cloudsdk/channels/rapid/downloads/google-cloud-sdk-225.0.0-linux-x86_64.tar.gz;
        					tar -xvf /tmp/google-cloud-sdk.tar.gz -C /tmp/;
        					/tmp/google-cloud-sdk/install.sh -q;
                            source /tmp/google-cloud-sdk/path.bash.inc;



        					 gcloud config set project ${GOOGLE_PROJECT_ID};
        					 gcloud components install app-engine-java;
        					 gcloud components install app-engine-python;
        					 gcloud auth activate-service-account --key-file ${GOOGLE_SERVICE_ACCOUNT_KEY};

        					 gcloud config list;
        					 ls
        					 gsutil cp -r dist/my-first-app gs://my-qa1/Angular1/
        					 gsutil cp app.yaml gs://my-qa1/Angular1/
                             mkdir angular-gcp-aptu
                             gsutil rsync -r gs://my-qa1/Angular1 ./angular-gcp-aptu
                             cd angular-gcp-aptu
                             ls
                             gcloud app deploy --project=angular-317016
                             echo "Deployed to GCP Successfully"

        				"""
        				cleanWs()
                       echo"Workspace cleaned"

        		}
        	}
        	catch(e){
        	     cleanWs()
                 echo"Workspace cleaned"
        	}


 }

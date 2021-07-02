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
        					 gsutil cp app.yaml ./dist/design1
        					 gsutil cp package.json ./dist/design1
                             cd ./dist/design1
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

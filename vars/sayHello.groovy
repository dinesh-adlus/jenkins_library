#! /usr/bin/env groovy
import hudson.model.*

/**
  @author: Dinesh Gurram
  @description: if you are using a SPA/node/any containerized application create a Jenkins file and pass the
     reference to this libray as
           -->    @Library('place your library name here')_
*/


def call(String name = 'sai') {

pipeline {

 // you can place env variables across the files to use them in multiple instances,
 //  if you do not wish to do so in any case feel free to use the $WORKSPACE variable as well.
  	 environment {
          GOOGLE_PROJECT_ID = 'angular-317016';

  		  GOOGLE_SERVICE_ACCOUNT_KEY = credentials('Angular');
     }

  /**
   @description: agent corresponds to the core "node" where the app is running.
  */
  agent { label 'master' }

  // You can exclude skipDefaultCheckout but you would be seeing redundant
  // stage in the jenkins pipeline staging.
   options {
       skipDefaultCheckout(true)
    }

   //Since you would be using npm to perform all bundling add a plugin and jenkins and provide that value here.
   // ex: nodejs
   tools { nodejs "nodejs" }

  // stages refers to the mainstops in jenkins workflow declaring all individual stages here.
  stages {
    stage('checkout') {
      steps {
            git url: "https://github.com/dinesh-adlus/angular-test-app"
           echo "checkout is successfull"
      }
    }
    stage('build'){
      steps{
        sh (script: "sh build.sh", returnStdout: true)
      }
        /**
         Once build stage is passed you will head straight to deploying to gcp.
         Approach used here is
                   1: GCP App Engine
                   2: deploy to storage buckets
        */

    }
    		stage('Publish'){
    			steps{
    				println('Starting publish..');
    				script{
    					def server = Artifactory.server('adtech-service-artifactory');
    					println("here is the server: "+server);
    					println("here is the server: ${server}");

    					def buildInfo = Artifactory.newBuildInfo();
    					buildInfo.env.capture = true;

    					buildInfo.retention maxBuilds: 10;
    					buildInfo.retention maxDays: 10;

    					println('Before adding upload spec');
    					def uploadSpec = """{
    						"files": [
     									{
    										"pattern": "*.tar.gz",
    										"target": "libs-release-local/XXXX/",
    										"recursive": "true",
    										"flat": "false",
    										"props": "Version=2"
    									},
    									{
    										"pattern": "*.zip",
    										"target": "libs-release-local/XXXX/",
    										"recursive": "true",
    										"flat": "false",
    										"props": "Version=2"
    									}
    								]
    					}
    					""";
    					println('Before calling upload service');
    					server.upload(uploadSpec);


    					//download the uploaded artifact -  this is to download any dependant modules from
    					// the artifcatory if any
    					println('Before adding download spec');
    					def downloadSpec = """{
    						"files": [
     									{
    										"pattern": "**/*.war",
    										"target": "libs-release-local"
    									}
    								]
    					}
    					""";
    					server.download(downloadSpec);


    				}
    				println('Publish Success');


    			}
    		}





        stage('Deploy'){
        			steps{

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
        					 gcloud app deploy --version=v01;
                            			 echo "Deployed to GCP"
        				"""
        				}

        		}

  }
}
 }


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
          env.GOOGLE_PROJECT_ID = 'angular-317016';

  		  env.GOOGLE_SERVICE_ACCOUNT_KEY = credentials('service_account_key':f1c2976575fbbc8ec5da9f7d31d20877bb6dd4da);
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
    }

  /**
   Once build stage is passed you will head straight to deploying to gcp.
   Approach used here is
             1: GCP App Engine
             2: deploy to storage buckets
  */
    deployToGCP(env)
  }
}
 }


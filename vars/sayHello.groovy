#! /usr/bin/env groovy
import hudson.model.*

/**
  @author: Dinesh Gurram
*/


def call(String name = 'sai') {

pipeline {

 // you can place env variables across the files to use them in multiple instances,
 //  if you do not wish to do so in any case feel free to use the $WORKSPACE variable as well.
  	 environment {
          GOOGLE_PROJECT_ID = 'angular-317016';
  		  GOOGLE_SERVICE_ACCOUNT_KEY = credentials('Angular');
  		  PATTERN = '${WORKSPACE/index.html}'
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
        sh "ls"
        sh "rm -rf ./node_modules"
         deployToStorageBucket {

           }
      }


    }

  }
}
 }


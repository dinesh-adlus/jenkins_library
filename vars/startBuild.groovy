#! /usr/bin/env groovy
import hudson.model.*
import com.cloudbees.groovy.cps.NonCPS

/**
  @author: Dinesh Gurram
*/


def call(body) {

  // If you do not have multiple values to pass on to Map, then you can simply exclude the body block
  //   and consume the value directly(as regular argument).
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

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
    stage('configuration') {
          steps {
            script {

               //default: master
               git url: "https://github.com/dinesh-adlus/config-management"
               echo "checkout is successfull"
               echo "Reading the configuration from configuration file."
               def readConfig = readJSON file: "${WORKSPACE}/${config.path}"
               def branch = readConfig.branch
              }
          }
    }
    stage('checkout') {
      steps {
            git branch: '${branch}', url: "${readConfig.gitUrl}"
           echo "checkout is successful"
      }
    }
    stage('build'){
      steps{
        sh (script: "sh build.sh", returnStdout: true)
        sh "ls"
        sh "rm -rf ./node_modules"
        // Using GCP as the standard deploy strategy.
         deployToStorageBucket {

           }
      }


    }

  }
}
 }


#! /usr/bin/env groovy

import hudson.model.*

def call(String name = 'sai') {
pipeline {
  agent { label 'master' }
   options {
       skipDefaultCheckout(true)
    }

  stages {
    stage('init'){
      steps{
         tools { nodejs "nodejs" }
      }
    }

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
  }
}
 }


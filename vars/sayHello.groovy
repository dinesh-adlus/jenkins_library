#! /usr/bin/env groovy

import hudson.model.*

def call(String name = 'sai') {



pipeline {
  agent any

  tools {nodejs "nodejs"}

    node {
      stage("Checkout") {
         git url: "https://github.com/dinesh-adlus/angular-test-app"
      }
      echo "checkout is successfull"

      stage("Build"){
       sh (script: "sh build.sh", returnStdout: true)
      }  
             
    }
   }
}  
  

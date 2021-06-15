#! /usr/bin/env groovy

import hudson.model.*

def call(String name = 'sai') {
    node {
      stage("Checkout") {
         git url: "https://github.com/dinesh-adlus/angular-test-app"
      }
      echo "checkout is successfull"
        
      stage("Build"){
         sh " %WORKSPACE%/build.sh"
      }  
             
    }
}  
  

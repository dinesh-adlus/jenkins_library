#! /usr/bin/env groovy

def call(String name = 'human') {


  echo "Hello, ${name}."

  node ('testNode') {

    stages {

      stage('build'){

        script {
          sh 'npm install'
        }
        
      }
    }
  }
}
#! /usr/bin/env groovy

import hudson.model.*


def call(String name = 'human') {
  echo "Hello, ${name}."

     node {
        stage('build'){

        script {
          bat 'npm --version'
        }
      }
     }
   
}

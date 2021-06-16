#! /usr/bin/env groovy

import hudson.model.*

def call(String name = 'sai') {
pipeline {
  agent { label 'master' }

  tools { nodejs "nodejs" }

  stages {
    stage('Test npm') {
      steps {
        sh """
          npm --version
        """
      }
    }
  }
}
 }


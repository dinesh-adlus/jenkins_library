#! /usr/bin/env groovy

import hudson.model.*

def call(Closure body) {

    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    stage("Deploy To App Engine") {

    }






 }

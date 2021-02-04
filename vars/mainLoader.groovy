#! /usr/bin/env groovy

def call(String name = 'human'){
      node('windows') {
       echo ("Hello ${name}");
    }
}

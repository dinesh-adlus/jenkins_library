#! /usr/bin/env groovy

import hudson.model.*

def call(Closure body) {

    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

        		stage('Publish'){
        			steps{
        				println('Starting publish..');
        				script{
        					def server = Artifactory.server('adtech-service-artifactory');
        					println("here is the server: "+server);
        					println("here is the server: ${server}");

        					def buildInfo = Artifactory.newBuildInfo();
        					buildInfo.env.capture = true;

        					buildInfo.retention maxBuilds: 10;
        					buildInfo.retention maxDays: 10;

        					println('Before adding upload spec');
        					def uploadSpec = """{
        						"files": [
         									{
        										"pattern": "*.tar.gz",
        										"target": "libs-release-local/XXXX/",
        										"recursive": "true",
        										"flat": "false",
        										"props": "Version=2"
        									},
        									{
        										"pattern": "*.zip",
        										"target": "libs-release-local/XXXX/",
        										"recursive": "true",
        										"flat": "false",
        										"props": "Version=2"
        									}
        								]
        					}
        					""";
        					println('Before calling upload service');
        					server.upload(uploadSpec);


        					//download the uploaded artifact -  this is to download any dependant modules from
        					// the artifcatory if any
        					println('Before adding download spec');
        					def downloadSpec = """{
        						"files": [
         									{
        										"pattern": "**/*.war",
        										"target": "libs-release-local"
        									}
        								]
        					}
        					""";
        					server.download(downloadSpec);


        				}
        				println('Publish Success');


        			}
        		}



 }



#! /usr/bin/env groovy

import hudson.model.*


// def call(String name = 'human') {
//   echo "Hello, ${name}."

//      node {
//         stage('build'){

//         script {
//           bat 'npm --version'
//         }
//       }
//     }
// }


def call(String name = 'sai') {
    node {
      stage("Checkout") {
         git url: "https://github.com/dinesh-adlus/angular-test-app"
      }
    //   stage("Install") {
    //         bat "npm install"
    //     }
    var File = build.sh
        if [ -f "$FILE" ]; then
          echo "$FILE exists."
        fi
        stage("build") {
            echo 'build success'
            // bat "sh build.sh"
        }
    }
}  
  

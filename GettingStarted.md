
```
  The Primary prerequisite is be sure to understand and decide the kind of pipeline structure that
  you are intending to use.
         1:Declarative
               syntax:
                  pipeline {
                      agent any
                      stages{
                         stage{
                            steps{
                              ......
                            }              
                         }
                      }
                  }
         2:Scripted.
               syntax:
                  def call(){
                     stage(){
                       ....
                     }
                  }

  More details can be found at https://www.jenkins.io/doc/book/pipeline/syntax/

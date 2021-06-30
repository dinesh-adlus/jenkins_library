##! If you wish to learn something quick with ci/cd with jenkins this is your one stop.

```
  This repo consists of all the base tasks/tools that you need to integrate with your apps--> deployment.

 ```
   Steps To Use this library:
   
   1: create a Jenkinsfile in your application and add the lib to it.
       
        ex: @Library('jenkins_library')_
        
   2: Add the library git url to your jenkins.
        jenkins --> manage-jenkins --> Global-pipeline-libraries -- Add your details
        
        Next you need to specify how you'll pull up the library.
        Go to Retrieval method -->ModernSCM --> provide your repo details.
        
   3: Go to Jenkins --> Manage plugins --> install all the needed ones.
          ex: NodeJS,Maven...
          
   4: You are all set you use it now.Once connection is established and if you see any issue in the 
      pipeline it's all on the library, you can proceed with debugging.
      
    -- Happy Coding 😊  --       

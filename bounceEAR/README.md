# DevOps-Jenkins_Pipeline

Flow:
- Select arguments from Jenkins form
- Build the Job
- Set SWM variables 
- Install package using SWM cli command
- Execute package's scripts pre_proc and post_proc
  pre_proc creates aff links, and logs directory
  post_proc executes python script to connect to WebLogic console URL and bounce the EAR app
-  an email report is created in html format and sent to the user



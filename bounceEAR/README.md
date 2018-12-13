# DevOps-Jenkins_Pipeline

Flow:
1)Select arguments from Jenkins form
2)Build the Job
3)Set SWM variables 
4)Install package using SWM cli command
5)Execute package's scripts pre_proc and post_proc
pre_proc creates aff links, and logs directory
post_proc executes python script to connect to WebLogic console URL and bounce the EAR app
6) an email report is created in html format and sent to the user



pipeline{
	agent any
	environment {
		PHASE='BUILD_DEPLOY'
		TARGET_ENV	 = 'DEV'
		BACKEND_BRANCH = 'feature_Iteration64'
		UI_BRANCH = 'feature_Iteration64'
		ORDERSTATUSJOB = 'com.COMPANY.APP/com.COMPANY.APP.orderstatus/feature%2Fiteration64'
		ORDERSTATUSUIJOB = 'com.COMPANY.APP/com.COMPANY.APP.OrderStatusAdminUi/feature%2Fiteration64'
		EMAIL_SUBJECT = "Order Status installation report from $TARGET_ENV"
		EMAIL_LIST = 'ernestomedina17@gmail.com'
		EMAIL_TEMPLATE = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'></head><body>Hi All,<br><br><strong> <em> <h2> Deployment Report from $TARGET_ENV </strong> </em> </h2><br><table border='1' width='1000' cellspacing='0' cellpadding='0'><b><tr bgcolor='4BACC6'><td width='20%'> Component </td><td width='25%'> Branch </td><td width='10%'> Sanity Status </td></tr><tr bgcolor='#90ee90'><td>BACKEND</td><td>BACKEND_BRANCH</td><td>BACKEND_STATUS</td></tr><tr bgcolor='#90ee90'><td>UI</td><td>UI_BRANCH</td><td>UI_STATUS</td></tr></table><br><br><br><div>Regards,<br>Infra OMX Team<br></div></body></html>"
	}
	stages {
		stage('Install Order Status'){		
			parallel{
				stage('Install Order Status Backend'){	
					steps {
						script{
							sh 'echo $EMAIL_TEMPLATE > email_body.html'
							job = null
							def BACKEND_JOB
							try{					
								BACKEND_JOB = build job: ORDERSTATUSJOB, parameters: [[$class: 'StringParameterValue', name: 'PHASE', value: 'BUILD_DEPLOY'], [$class: 'StringParameterValue', name: 'TARGET_ENV', value: "$TARGET_ENV"]],propagate:true				
								print 'Install Order Status is Complete'
								sh 'sed -i "s/BACKEND_BRANCH/$BACKEND_BRANCH/g" email_body.html'
								sh 'sed -i "s/BACKEND_STATUS/SUCCESS/g" email_body.html'
							}
							catch(e){ print 'Install orderstatus Failed' 
								sh 'sed -i "s/BACKEND_STATUS/FAIL/g" email_body.html'
							}
						}
					}
				}
				stage('Install Order Status UI') { 
					steps {
						script{
							job = null
							def UI_JOB
							try{		
								UI_JOB = build job: ORDERSTATUSUIJOB, parameters:[[$class: 'StringParameterValue', name: 'PHASE', value: 'BUILD_DEPLOY'],[$class: 'StringParameterValue', name: 'TARGET_ENV', value: "$TARGET_ENV"]],propagate:true
								print 'Install orderstatusUI is Complete'
								sh 'sed -i "s/UI_BRANCH/$UI_BRANCH/g" email_body.html'
								sh 'sed -i "s/UI_STATUS/SUCCESS/g" email_body.html'
							}
							catch(e){
								print 'Install orderstatusUI Failed'
								sh 'sed -i "s/UI_STATUS/FAIL/g" email_body.html'
							}
						}
					}		
				}	
			}
		}
		stage('Send Email Report') {
			steps   {
				script  {
					EMAIL_REPORT = readFile(file: "email_body.html")
					emailext attachLog: true, body: "$EMAIL_REPORT", compressLog: true, mimeType: 'text/html', subject: "$EMAIL_SUBJECT", to: "$EMAIL_LIST"
				}
			}
		}
	}
}
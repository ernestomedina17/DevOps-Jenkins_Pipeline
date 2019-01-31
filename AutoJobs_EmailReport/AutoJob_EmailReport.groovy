pipeline{
	agent any
	environment {
		PHASE='BUILD_DEPLOY'
		TARGET_ENV	 = 'DEV'
		BACKEND_BRANCH = 'feature_Iteration64'
		UI_BRANCH = 'feature_Iteration64'
		APP_JOB = 'com.COMPANY.APP/com.COMPANY.APP.AppName/feature%2Fiteration64'
		APPUI_JOB = 'com.COMPANY.APP/com.COMPANY.APP.AppNameAdminUi/feature%2Fiteration64'
		EMAIL_SUBJECT_BE = "EOS BE installation report from $TARGET_ENV Branch: $BACKEND_BRANCH Status: SUCCESS"
		EMAIL_SUBJECT_UI = "EOS UI installation report from $TARGET_ENV Branch: $UI_BRANCH Status: SUCCESS"
		EMAIL_LIST = 'yourEmail@company.com'
		EMAIL_TEMPLATE = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'></head><body>Hi All,<br><br><strong> <em> <h2> Deployment Report from $TARGET_ENV </strong> </em> </h2><br><table border='1' width='1000' cellspacing='0' cellpadding='0'><b><tr bgcolor='4BACC6'><td width='20%'> Component </td><td width='25%'> Branch </td><td width='10%'> Sanity Status </td><td width='10%'> Build Link </td></tr><tr bgcolor='#90ee90'><td>APP_COMPONENT</td><td>BRANCH</td><td>STATUS</td><td><a href='LINK'>Link</a></td></tr></table><br><br><br><div>Note: If the Link takes you to the Parent Job it means the Child Job failed to start</div><br><div>Regards,<br>Infra OMX Team<br></div></body></html>"
	}
	stages { 
		stage('Install APP_NAME'){		
			parallel{
				stage('Install APP_NAME Backend'){	
					steps {
						script{
							sh 'echo $EMAIL_TEMPLATE > email_body_BE.html'
							sh 'echo $EMAIL_SUBJECT_BE > email_status_BE.html'

							job = null
							def BACKEND_JOB
							try{				
								sh 'sed -i "s/BRANCH/$BACKEND_BRANCH/g" email_body_BE.html'
								sh 'sed -i "s/APP_COMPONENT/BackEnd/g" email_body_BE.html'
								BACKEND_JOB = build job: APP_JOB, parameters: [[$class: 'StringParameterValue', name: 'PHASE', value: 'BUILD_DEPLOY'], [$class: 'StringParameterValue', name: 'TARGET_ENV', value: "$TARGET_ENV"],[$class: 'StringParameterValue', name: 'SKIP_TEST_AUTOMATION', value: 'false'],[$class: 'StringParameterValue', name: 'DB_MODE', value: 'incremental']],propagate:true				
								print 'Install APP_NAME is Complete'
								sh 'sed -i "s/STATUS/SUCCESS/g" email_body_BE.html'
								sendEmailReport(BACKEND_JOB, "BE")
							}
							catch(e){ print 'Install AppName Failed' 
								sh 'sed -i "s/STATUS/FAIL/g" email_body_BE.html'
								sh 'sed -i "s/90ee90/ffff99/g" email_body_BE.html'
								sh 'sed -i "s/SUCCESS/FAIL/g" email_status_BE.html'
								sendEmailReport(BACKEND_JOB, "BE")
							}
						}
					}
				}
				stage('Install APP_NAME UI') { 
					steps {
						script{
							sh 'echo $EMAIL_TEMPLATE > email_body_UI.html'
							sh 'echo $EMAIL_SUBJECT_UI > email_status_UI.html'
							sh 'sed -i "s/BRANCH/$UI_BRANCH/g" email_body_UI.html'
							sh 'sed -i "s/APP_COMPONENT/UI/g" email_body_UI.html'
							job = null
							def UI_JOB							
							try{										
								UI_JOB = build job: APPUI_JOB, parameters:[[$class: 'StringParameterValue', name: 'PHASE', value: 'BUILD_DEPLOY'],[$class: 'StringParameterValue', name: 'TARGET_ENV', value: "$TARGET_ENV"],[$class: 'StringParameterValue', name: 'SKIP_TEST_AUTOMATION', value: 'false']],propagate:true
								print 'Install AppNameUI is Complete'																
								sh 'sed -i "s/STATUS/SUCCESS/g" email_body_UI.html'
								sendEmailReport(UI_JOB, "UI")
							}
							catch(e){
								print 'Install AppNameUI Failed'								
								sh 'sed -i "s/STATUS/FAIL/g" email_body_UI.html'
								sh 'sed -i "s/90ee90/ffff99/g" email_body_UI.html'
								sh 'sed -i "s/SUCCESS/FAIL/g" email_status_UI.html'
								sendEmailReport(UI_JOB, "UI")
							}
						}
					}		
				}	
			}
		}
	}
}

def sendEmailReport(UI_JOB, APP_COMPONENT) { 
	env.APP_COMPONENT = APP_COMPONENT
	if (UI_JOB) { 
		env.UI_JOB_URL = UI_JOB.absoluteUrl.replaceAll( "/","\\\\/")
		sh 'sed -i "s/LINK/$UI_JOB_URL/g" email_body_$APP_COMPONENT.html'
	} else {
		env.UI_JOB_URL = BUILD_URL.replaceAll( "/","\\\\/")
		sh 'sed -i "s/LINK/$UI_JOB_URL/g" email_body_$APP_COMPONENT.html'
	}
	EMAIL_REPORT = readFile(file: "email_body_" + APP_COMPONENT + ".html")
	EMAIL_SUBJECT_REPORT = readFile(file: "email_status_" + APP_COMPONENT + ".html")
	emailext attachLog: false, body: "$EMAIL_REPORT", compressLog: true, mimeType: 'text/html', subject: "$EMAIL_SUBJECT_REPORT", to: "$EMAIL_LIST"							
}
							

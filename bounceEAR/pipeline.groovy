pipeline { //Wrote by ernestomedina17
    agent any
    environment {
        String Not_Set = 'Not_set'
        String VTIER = 'Not_Set'
        String TargetWLMServer = 'Not_Set'
        String Node2 = 'Not_Set'
        String URL2 = 'Not_Set'
        }
        
    stages {
        stage('Build') {
            steps {
                script {
                    echo 'Building..'
                    if (App.equals("omx")) {
                        switch (Env2) {
                            case 'Dev2': Node2 = 'newtd2a1-d2nap1a1.xxx.com' ; VTIER = 'd2nap1a1'; break
                            case 'IT1': Node2 = 'newti1a1-i1nap1a1.xxx.com'  ; VTIER = 'i1nap1a1'; break
                            case 'IT2': Node2 = 'newti2a1-i2nap1a1.xxx.com'  ; VTIER = 'i2nap1a1'; break
                            case 'IT3': Node2 = 'newti3a1-i3nap1a1.xxx.com'  ; VTIER = 'i3nap1a1'; break
                            case 'IT4': Node2 = 'newti4a1-i4nap1a1.xxx.com'  ; VTIER = 'i4nap1a1'; break
                            case 'IT5': Node2 = 'newti5a1-i5nap1a1.xxx.com'  ; VTIER = 'i5nap1a1'; break
                            case 'IT6': Node2 = 'newti6a1-i6nap1a1.xxx.com'  ; VTIER = 'i6nap1a1'; break
                            case 'SIT1_E2E': Node2 = 'zltv5699-t1c1m421.vci.att.com' ; VTIER = 't1c1m421'; break
                            case 'ST1': Node2 = 'newtt1a1-t1nap1a1.xxx.com'  ; VTIER = 't1nap1a1'; break
                            case 'ST2': Node2 = 'bportt2a1-t2bpo1m1.xxx.com' ; VTIER = 't2bpo1m1'; break
                            case 'ST3': Node2 = 'newtt3a1-t3nap1a1.xxx.com'  ; VTIER = 't3nap1a1'; break
                            case 'ST4': Node2 = 'newtt4a1-t4nap1a1.xxx.com'  ; VTIER = 't3nap1a1'; break
                            case 'ST5': Node2 = 'newtt5a1-t5nap1a1.xxx.com'  ; VTIER = 't3nap1a1'; break
                            default: Node2 = Not_Set ; VTIER = Not_Set
                        }
                    } else if (App.equals("aff_wf")) {
                        switch (Env2) {
                            case 'Dev2': println 'Ther is No AFF in Dev2' ; currentBuild.result = 'FAILURE' ; break 
                            case 'IT1': Node2 = 'newti1a1-i1nas1a1.xxx.com'; VTIER = 'i1nas1a1'; break
                            case 'IT2': Node2 = 'newti2a1-i2nas1a1.xxx.com'; VTIER = 'i2nas1a1'; break
                            case 'IT3': Node2 = 'newti3a1-i3nas1a1.xxx.com'; VTIER = 'i3nas1a1'; break
                            case 'IT4': Node2 = 'newti4a1-i4nas1a1.xxx.com'; VTIER = 'i4nas1a1'; break
                            case 'IT5': Node2 = 'newti5a1-i5nas1a1.xxx.com'; VTIER = 'i5nas1a1'; break
                            case 'IT6': Node2 = 'newti6a1-i6nas1a1.xxx.com'; VTIER = 'i6nas1a1'; break
                            case 'SIT1_E2E': Node2 = 'zltv5705-t1c1m418.vci.att.com'; VTIER = 't1c1m418'; break
                            case 'ST1': Node2 = 'newtt1a1-t1nas1a1.xxx.com'; VTIER = 't1nas1a1'; break
                            case 'ST2': Node2 = 'newtt2a1-t2nas1a1.xxx.com'; VTIER = 't2nas1a1'; break
                            case 'ST3': Node2 = 'newtt3a1-t3nas1a1.xxx.com'; VTIER = 't3nas1a1'; break
                            case 'ST4': Node2 = 'newtt4a1-t4nas1a1.xxx.com'; VTIER = 't3nas1a1'; break
                            case 'ST5': Node2 = 'newtt5a1-t5nas1a1.xxx.com'; VTIER = 't3nas1a1'; break
                            default: Node2 = Not_Set ; VTIER = Not_Set
                        }
                    } else { // Env Athena
                        VTIER = "athena"
                        switch (Env2) {
                            case 'Dev2': Node2 = 'athd2a1-athena.xxx.com'; break
                            case 'IT1': Node2 = 'athi1a1-athena.xxx.com'; break
                            case 'IT2': Node2 = 'athi2a1-athena.xxx.com'; break
                            case 'IT3': Node2 = 'athi3a1-athena.xxx.com'; break
                            case 'IT4': Node2 = 'athi4a1-athena.xxx.com'; break
                            case 'IT5': Node2 = 'athi5a1-athena.xxx.com'; break
                            case 'IT6': Node2 = 'athi6a1-athena.xxx.com'; break
                            case 'SIT1_E2E': Node2 = 'zltv9699-athena.vci.att.com'; break
                            case 'ST1': Node2 = 'atht1a1-athena.xxx.com'; break
                            case 'ST2': Node2 = 'atht2a1-athena.xxx.com'; break
                            case 'ST3': Node2 = 'atht3a1-athena.xxx.com'; break
                            case 'ST4': Node2 = 'atht4a1-athena.xxx.com'; break
                            case 'ST5': Node2 = 'atht5a1-athena.xxx.com'; break
                            default: Node2 = Not_Set ; VTIER = Not_Set
                        }
                    }
                    // URL Setup
                    def NewtonCommonSS = ['ActivationInterfaceEar','BmpInterfaceEar','BrassInterfaceEar','CamoInterfaceEar',
                                 'ClarifyInterfaceEar','CommentsInterfaceEar','DateCalcInterfaceEar','DesignInterfaceEar',
                                 'EdfInterfaceEar','EFMSInterfaceEar','EnterpriseOrderDataInterfaceEar','EsignInterfaceEar',
                                 'GetOrderDataRouterEar','GpsInterfaceEar','InstarInterfaceEar','IPDBInterfaceEar',
                                 'LeoInterfaceEar','MilestonesInterfaceEar','MRInterfaceEar','OmxCommonStatusInterfaceEar',
                                 'OsmInterfaceEar','OTPInterfaceEAR','SsppInterfaceEar','WmxInterfaceEar']
                    def WorkerMgtCthS1 = ['CthHirInterface','CthToolsInterface','iTracksInterface','iTracksReportClientInterface','KeepAlive4ServiesWar']
                    def WorkerMgtRasS1 = ['jms-notran-adp','jms-xa-adp','RasInterface']
                    
                    if (App.equals("aff_wf") && Env2.equals("SIT1_E2E")) { URL2 = "t3://"+Node2+":10168" ; TargetWLMServer = "omxaff_Server" }
                    else if (App.equals("aff_wf") && Env2.equals("ST2")) { URL2 = "t3://"+Node2+":9009"  ; TargetWLMServer = "affServerA1_t2nas1a1" }
                    else if (App.equals("aff_wf"))                       { URL2 = "t3://"+Node2+":9007"  ; TargetWLMServer = "omxaff_Server" }
                    else if (App.equals("camo"))                         { URL2 = "t3://"+Node2+":9022"  ; TargetWLMServer = "CamoServicesServer1" }
                    else if (App.equals("digitalnotifications"))         { URL2 = "t3://"+Node2+":9016"  ; TargetWLMServer = "DigNotifServicesServer1" }
                    else if (App.equals("ocxinterfacesWLdomains"))       { URL2 = "t3://"+Node2+":8701"  ; TargetWLMServer = "ocxInterfacesServicesServer1"}
                    else if (App.equals("omx"))                          { URL2 = "t3://"+Node2+":9001"
                        if (NewtonCommonSS.contains(EAR))                {                                 TargetWLMServer = 'NewtonCommonServicesServer1' }
                        else if (EAR.equals('ActiveVOS_Server'))         {                                 TargetWLMServer = 'NewtonActivevosServer1' }
                        else                                             {                                 TargetWLMServer = 'NewtonServicesServer1' }
                    }
                    else if (App.equals("reporting"))                    { URL2 = "t3://"+Node2+":9026" ;  TargetWLMServer = "ReportingServicesServer1"}
                    else if (App.equals("tnmgt"))                        { URL2 = "t3://"+Node2+":9012" ;  TargetWLMServer = "TnMgmntServicesServer1"}
                    else if (App.equals("tnplatform"))                   { URL2 = "t3://"+Node2+":9038" ;  TargetWLMServer = "TnPlatformServicesServer1" }
                    else if (App.equals("workermgt"))                    { URL2 = "t3://"+Node2+":9018"
                        if (WorkerMgtCthS1.contains(EAR))           {                                 TargetWLMServer = "WorkerMgtCthServer1" }
                        else if (WorkerMgtRasS1.contains(EAR))      {                                 TargetWLMServer = "WorkerMgtRasServer1" }
                        else (EAR.equals('SchedInterface'))              {                                 TargetWLMServer = "WorkerMgtSchedServer1" }
                    }
                    else { URL2 = Not_Set }
                } // End of script
            } // End of steps
        } 
        stage('Test') {
            steps {
                echo 'Testing..'
                echo Env2.toString()
                echo App.toString()
                echo EAR.toString()
                echo Node2.toString()
                echo URL2.toString()
                echo Email2.toString()
                echo VTIER.toString()
                echo TargetWLMServer.toString()
                script {
                    assert VTIER != Not_Set
                    assert TargetWLMServer != Not_Set
                    assert URL2 != Not_Set
                    assert Node2 != Not_Set
                }
            }
        }    
        stage('Deploy') {
            steps {
                echo 'Deploying....'
                sh "swmcli node varset -n $Node2 -c com.att.athena:infra -v User=$Email2 -v App=$App -v EAR=$EAR -v URL=$URL2 -v TargetWLMServer=$TargetWLMServer -v VTIER=$VTIER"
                sh "swmcli install -n $Node2 -c com.att.athena:infra:1.3.2 -w"
            }
        }
    }
}


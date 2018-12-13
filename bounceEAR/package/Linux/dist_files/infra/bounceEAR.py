import sys
import datetime
# Arg1 URL, Arg2 EAR, Arg3 TargetWLMServer
print '##################################################################'
URL = str(sys.argv[1])
EAR = str(sys.argv[2])
TargetWLMServer = str(sys.argv[3])
App = str(sys.argv[4])
if App == "aff_wf": 
	connect('UserIDA','XXXXXXXXXXXX',URL)
else:
	connect('UserIDB','XXXXXXXXXXXX',URL)
domainRuntime()
cd('AppRuntimeStateRuntime/AppRuntimeStateRuntime')
if EAR == "ActiveVOS_Server":
	EAR = "ActiveVOS Server" 
print '##################################################################'
print 'Current_State: ',cmo.getCurrentState(EAR, TargetWLMServer)
now = datetime.datetime.now()
print "Current_Time: " + now.isoformat()
print '##################################################################'
stopApplication(EAR)
print '##################################################################'
print 'Stop_State: ',cmo.getCurrentState(EAR, TargetWLMServer)
now = datetime.datetime.now()
print "Stop_Time: " + now.isoformat()
print '##################################################################'
startApplication(EAR)
print '##################################################################'
print 'Intended State: ',cmo.getIntendedState(EAR)
print 'Final_State: ',cmo.getCurrentState(EAR, TargetWLMServer)
now = datetime.datetime.now()
print "Final_Time: " + now.isoformat()
print '##################################################################'
if cmo.getIntendedState(EAR) != cmo.getCurrentState(EAR, TargetWLMServer):
        sys.exit("Invalid EAR State, the EAR cannot be started, please open Webtrax Ticket to WL IT OMX Infra or Dev Team")
else:
	print '##################################################################'
	print '######### The EAR Bounce has been completed succesfully ##########'
	print '##################################################################'


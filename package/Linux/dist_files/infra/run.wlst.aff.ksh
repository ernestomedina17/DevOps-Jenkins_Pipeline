#!/bin/ksh

initialize() {
    if [ ${0#/} = ${0} ] ; then
        DOLLAR_ZERO=${PWD}/${0}
    else
        DOLLAR_ZERO=${0}
    fi
    cd $(dirname ${DOLLAR_ZERO})
    export SELF_DIR=${PWD}
    cd ${OLDPWD}
    CONFIG_DIR=/opt/app/$VTIER/appl/AFF/JEE/omxaff_Domain/scripts
}

initialize "$@"

. ${CONFIG_DIR}/setenvomxaff_Server.sh
CLASSPATH=`echo $CLASSPATH | tr ':' '\n' | grep weblogic.jar`
echo "Classpath: $CLASSPATH" >> ~/infra/logs/bounce_End.log
${JAVA_HOME}/bin/java -cp $CLASSPATH -Xmx512m weblogic.WLST $*


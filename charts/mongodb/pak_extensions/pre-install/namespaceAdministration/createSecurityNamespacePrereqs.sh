#!/bin/bash
#
###############################################################################
# Licensed Materials - Property of Gemteks.
# Copyright Gemteks Corporation 2019. All Rights Reserved.
# U.S. Government Users Restricted Rights - Use, duplication or disclosure
# restricted by GSA ADP Schedule Contract with Gemteks Corp.
#
# Contributors:
#  Gemteks Corporation - initial API and implementation
###############################################################################
#
# You need to run this script for each namespace.
#
# This script takes one argument; the namespace where the chart will be installed.
#
# Example:
#     ./createSecurityNamespacePrereqs.sh myNamespace
#

. ../../common/kubhelper.sh


if [ "$#" -lt 1 ]; then
	echo "Usage: createSecurityNamespacePrereqs.sh NAMESPACE"
  exit 1
fi

namespace=$1

kubectl get namespace $namespace &> /dev/null
if [ $? -ne 0 ]; then
  echo "ERROR: Namespace $namespace does not exist."
  exit 1
fi

if supports_scc; then
  echo "Adding all namespace users to SCC..."
  if command -v oc >/dev/null 2>&1 ; then
    oc adm policy add-scc-to-group merc-mongodb-scc system:serviceaccounts:$namespace
  else
    echo "ERROR:  The OpenShift CLI is not available..." 
  fi
fi

if supports_psp; then
  # Replace the NAMESPACE tag with the namespace specified in a temporary yaml file.
  sed 's/{{ NAMESPACE }}/'$namespace'/g' merc-mongodb-rb.yaml > $namespace-merc-mongodb-rb.yaml

  echo "Adding a RoleBinding for all namespace users to the PSP..."
  # Create the role binding for all service accounts in the current namespace
  kubectl create -f $namespace-merc-mongodb-rb.yaml -n $namespace

  # Clean up - delete the temporary yaml file.
  rm $namespace-merc-mongodb-rb.yaml
fi;
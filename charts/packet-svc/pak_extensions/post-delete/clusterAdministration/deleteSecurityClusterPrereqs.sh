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
# This script can be run after all releases are deleted from the cluster.
#

. ../../common/kubhelper.sh

# Delete the PodSecurityPolicy and ClusterRole for all releases of this chart.

if supports_scc; then
  echo "Removing the SCC..."
  kubectl delete -f ../../pre-install/clusterAdministration/merc-packet-svc-scc.yaml
fi

if supports_psp; then
    echo "Removing the PSP and ClusterRole..."
    kubectl delete -f ../../pre-install/clusterAdministration/merc-packet-svc-psp.yaml
    kubectl delete -f ../../pre-install/clusterAdministration/merc-packet-svc-cr.yaml
fi
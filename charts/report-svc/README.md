# report-svc

[report-svc](https://nodered.org/)is a report generation service and provides API to generate report.

Its report format is xls and its has clean setting every 10 mins to clean report folder.

fixed:

2018-08-16:initial UAT env

## TL;DR;

```bash
$ helm install stable/report-svc
```

## Introduction

This chart bootstraps a [report-svc](https://nodered.org/) deployment on a [Kubernetes](http://kubernetes.io) cluster using the [Helm](https://helm.sh) package manager.

## Prerequisites

- Kubernetes 1.4+ with Beta APIs enabled

## Installing the Chart

To install the chart with the release name `my-release`:

```bash
$ helm install --name my-release stable/report-svc
```

The command deploys report-svc  on the Kubernetes cluster in the default configuration. The [configuration](#configuration) section lists the parameters that can be configured during installation.

> **Tip**: List all releases using `helm list`

## Uninstalling the Chart

To uninstall/delete the `my-release` deployment:

```bash
$ helm delete my-release
```

The command removes all the Kubernetes components associated with the chart and deletes the release.
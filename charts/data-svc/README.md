# data-svc

[data-svc](https://nodered.org/)is a data event management service and provides API to access raw data event.

Its database is using mongodb to store event info.

fixed:

2018-08-16:initial UAT env

## TL;DR;

```bash
$ helm install stable/data-svc
```

## Introduction

This chart bootstraps a [data-svc](https://nodered.org/) deployment on a [Kubernetes](http://kubernetes.io) cluster using the [Helm](https://helm.sh) package manager.

## Prerequisites

- Kubernetes 1.4+ with Beta APIs enabled

## Installing the Chart

To install the chart with the release name `my-release`:

```bash
$ helm install --name my-release stable/data-svc
```

The command deploys data-svc  on the Kubernetes cluster in the default configuration. The [configuration](#configuration) section lists the parameters that can be configured during installation.

> **Tip**: List all releases using `helm list`

## Uninstalling the Chart

To uninstall/delete the `my-release` deployment:

```bash
$ helm delete my-release
```

The command removes all the Kubernetes components associated with the chart and deletes the release.

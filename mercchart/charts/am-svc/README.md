# am-svc

[am-svc](https://nodered.org/)is an account management service and provides API about user and admin control.

Its database is using mysql to store account info and system properties.

fixed:

2018-08-16:delete values.yml nodeSelector/change version to 1.1.0

## TL;DR;

```bash
$ helm install stable/am-svc
```

## Introduction

This chart bootstraps a [am-svc](https://nodered.org/) deployment on a [Kubernetes](http://kubernetes.io) cluster using the [Helm](https://helm.sh) package manager.

## Prerequisites

- Kubernetes 1.4+ with Beta APIs enabled

## Installing the Chart

To install the chart with the release name `my-release`:

```bash
$ helm install --name my-release stable/am-svc
```

The command deploys am-svc  on the Kubernetes cluster in the default configuration. The [configuration](#configuration) section lists the parameters that can be configured during installation.

> **Tip**: List all releases using `helm list`

## Uninstalling the Chart

To uninstall/delete the `my-release` deployment:

```bash
$ helm delete my-release
```

The command removes all the Kubernetes components associated with the chart and deletes the release.

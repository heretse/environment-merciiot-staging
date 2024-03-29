# What’s new...

## Latest: Chart Version 1.0.0

1. packet-svc Docker images based on Universal Base Images (UBI) are now publicly available from [Docker hub](https://hub.docker.com/r/merciiot/packet-svc) and the chart uses it as the default image
1. Defined the most restrictive SecurityContextConstraints (SCC) for OpenShift support
1. Updated `kubeVersion` of chart to `>=1.9.0-any` to support various cloud environments. Ensure patch for [CVE-2018-1002105](https://github.com/kubernetes/kubernetes/issues/71411) is installed in your Kubernetes environment
1. Updated default resource limit values for CPU and memory

## Breaking Changes

* None

## Fixes

* None

## Prerequisites

* Tiller v2.12.3
* For all others, refer to prerequisites in [README.md](https://github.com/heretse/environment-merciiot-staging/tree/master/packet-svc/README.md).

## Documentation

Please refer to [README.md](https://github.com/heretse/environment-merciiot-staging/tree/master/packet-svc/README.md).

## Limitations

The chart does not yet provide an out of the box secure configuration for the `/metrics` endpoint.

## Version History

| Chart  | Date          | IBM Cloud Private Supported | Details                      |
| ------ | ------------- | --------------------------- | ---------------------------- |
| 1.0.0  | Jul 10, 2019  | >=3.2.0                     |  Initial release; Supports auto-scaling, ingress routing|

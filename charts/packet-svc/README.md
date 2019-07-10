# packet-svc Helm Chart


[packet-svc] is a packet filter and parsing service.

Its protocal is using mqtt to subscribe topic "GIOT-GW/UL/+" and filter duplicate packet and parsing to event info to store mongodb.


## Introduction

This chart bootstraps a [packet-svc] deployment on a [Kubernetes](http://kubernetes.io) cluster using the [Helm](https://helm.sh) package manager.

## Resources Required

### System resources
- CPU Requested : 500m (500 millicpu)
- Memory Requested : 512Mi (~ 537 MB)
- Kubernetes 1.9+ 

## Accessing packet-svc

From a browser, use http://*external-ip*:*nodeport* to access the application.

## Chart Details

  - Installs one `Deployment` running packet-svc image
  - Installs a `Service` and optionally an `Ingress` to route traffic to packet-svc


## Prerequisites

### packet-svc Docker image requirements

packet-svc Docker images based on Universal Base Images (UBI) are publicly available from [Docker hub](https://cloud.docker.com/u/merciiot/repository/docker/merciiot/packet-svc) and used by chart as default. Ensure your Kubernetes environment has set the image enforcement policy appropriately to allow access to those repositories. See [Enforcing container image security](https://www.ibm.com/support/knowledgecenter/en/SSBS6K_3.2.0/manage_images/image_security.html) for more information.

### PodSecurityPolicy Requirements

This chart requires a PodSecurityPolicy to be bound to the target namespace prior to installation. Choose either a predefined PodSecurityPolicy or have your cluster administrator create a custom PodSecurityPolicy for you:

* Predefined PodSecurityPolicy name: [`ibm-restricted-psp`](https://ibm.biz/cpkspec-psp)
* Custom PodSecurityPolicy definition:

```yaml
apiVersion: extensions/v1beta1
kind: PodSecurityPolicy
metadata:
  name: merc-packet-svc-psp
spec:
  allowPrivilegeEscalation: false
  forbiddenSysctls:
  - '*'
  fsGroup:
    ranges:
    - max: 65535
      min: 1
    rule: MustRunAs
  requiredDropCapabilities:
  - ALL
  runAsUser:
    rule: MustRunAsNonRoot
  seLinux:
    rule: RunAsAny
  supplementalGroups:
    ranges:
    - max: 65535
      min: 1
    rule: MustRunAs
  volumes:
  - configMap
  - emptyDir
  - projected
  - secret
  - downwardAPI
  - persistentVolumeClaim
```

* Custom ClusterRole for the custom PodSecurityPolicy:

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  name: merc-packet-svc-clusterrole
rules:
- apiGroups:
  - extensions
  resourceNames:
  - merc-packet-svc-psp
  resources:
  - podsecuritypolicies
  verbs:
  - use
  ```


#### Configuration scripts can be used to create the required resources

Download the following scripts located at [/pak_extensions/pre-install](https://github.com/heretse/environment-merciiot-staging/tree/master/packet-svc/pak_extensions/pre-install) directory.

* The pre-install instructions are located at `clusterAdministration/createSecurityClusterPrereqs.sh` for cluster admins to create the PodSecurityPolicy and ClusterRole for all releases of this chart.

* The namespace scoped instructions are located at `namespaceAdministration/createSecurityNamespacePrereqs.sh` for team admin/operator to create the RoleBinding for the namespace. This script takes one argument; the name of a pre-existing namespace where the chart will be installed.
  * Example usage: `./createSecurityNamespacePrereqs.sh myNamespace`

#### Configuration scripts can be used to clean up resources created

Download the following scripts located at [/pak_extensions/post-delete](https://github.com/heretse/environment-merciiot-staging/tree/master/packet-svc/pak_extensions/post-delete) directory.

* The post-delete instructions are located at `clusterAdministration/deleteSecurityClusterPrereqs.sh` for cluster admins to delete the PodSecurityPolicy and ClusterRole for all releases of this chart.

* The namespace scoped instructions are located at `namespaceAdministration/deleteSecurityNamespacePrereqs.sh` for team admin/operator to delete the RoleBinding for the namespace. This script takes one argument; the name of the namespace where the chart was installed.
  * Example usage: `./deleteSecurityNamespacePrereqs.sh myNamespace`

#### Creating the required resources

This chart defines a custom `SecurityContextConstraints` which can be used to finely control the permissions/capabilities needed to deploy this chart. You can enable this custom `SecurityContextConstraints` resource using the supplied instructions or scripts in the `pak_extensions/pre-install` directory.

* From the user interface, you can copy and paste the following snippets to enable the custom `SecurityContextConstraints`
  * Custom `SecurityContextConstraints` definition:

  ```yaml
  apiVersion: security.openshift.io/v1
  kind: SecurityContextConstraints
  metadata:
    annotations:
    name: merc-packet-svc-scc
  allowHostDirVolumePlugin: false
  allowHostIPC: false
  allowHostNetwork: false
  allowHostPID: false
  allowHostPorts: false
  allowPrivilegedContainer: false
  allowedCapabilities: []
  allowedFlexVolumes: []
  defaultAddCapabilities: []
  fsGroup:
    type: MustRunAs
    ranges:
    - max: 65535
      min: 1
  readOnlyRootFilesystem: false
  requiredDropCapabilities:
  - ALL
  runAsUser:
    type: MustRunAsNonRoot
  seccompProfiles:
  - docker/default
  seLinuxContext:
    type: RunAsAny
  supplementalGroups:
    type: MustRunAs
    ranges:
    - max: 65535
      min: 1
  volumes:
  - configMap
  - downwardAPI
  - emptyDir
  - persistentVolumeClaim
  - projected
  - secret
  priority: 0
  ```

* From the command line, you can run the setup scripts included under `pak_extensions/pre-install`
  As a cluster admin the pre-install instructions are located at:
  * `pre-install/clusterAdministration/createSecurityClusterPrereqs.sh`

  As team admin the namespace scoped instructions are located at:
  * `pre-install/namespaceAdministration/createSecurityNamespacePrereqs.sh`

### Limitations

See [RELEASENOTES.md](https://github.com/heretse/environment-merciiot-staging/tree/master/packet-svc/RELEASENOTES.md)

### Installing the Chart

The Helm chart has the following values that can be overridden by using `--set name=value`. For example:

*    `helm repo add ibm-charts https://github.com/heretse/environment-merciiot-staging/tree/master/repo/`
*    `helm install --name my-release --set resources.constraints.enabled=true --set autoscaling.enabled=true --set autoscaling.minReplicas=1 merciiot/packet-svc --tls`

Resource constraints can be enabled using `resources.constraints.enabled` parameter. Required resource can be configured using `resources.requests.cpu` and `resources.requests.memory` parameters. Resource limits can be configured using `resources.limits.cpu` and `resources.limits.memory` parameters. Review the default values specified by the chart and adjust according to your needs. It is recommended not to use `Xmx` or `Xms`. Use `XX:MaxRAMPercentage` and `XX:InitialRAMPercentage` for any fine tuning, such as if there are other processes set to run in the same container.

### Verifying the Chart

See the instruction after the helm installation completes for chart verification. The instruction can also be displayed by viewing the installed helm release under Menu -> Workloads -> Helm Releases or by running the command: `helm status my-release --tls`

### Uninstalling the Chart

To uninstall/delete the `my-release` deployment:

```bash
helm delete my-release --purge --tls
```
### Configuration

| Qualifier | Parameter  | Definition | Allowed Value |
|---|---|---|---|
| `image`   | `pullPolicy` | Image Pull Policy | `Always`, `Never`, or `IfNotPresent`. Defaults to `Always` if `:latest` tag is specified, or `IfNotPresent` otherwise. See Kubernetes - [Updating Images](https://kubernetes.io/docs/concepts/containers/images/#updating-images)  |
|           | `repository` | Name of image, including repository prefix (if required). | See Docker - [Extended tag description](https://docs.docker.com/engine/reference/commandline/tag/#parent-command) |
|           | `tag`        | Docker image tag. | See Docker - [Tag](https://docs.docker.com/engine/reference/commandline/tag/) |
|           | `pullSecret`        | Image pull secret, if using a Docker registry that requires credentials. | See Kubernetes - [ImagePullSecrets](https://kubernetes.io/docs/tasks/configure-pod-container/configure-service-account/#add-imagepullsecrets-to-a-service-account) |
|           | `license`    |  The license state of the image being deployed. | `Empty` (default) for development. `accept` if you have previously accepted the production license. |
|           | `readinessProbe`       | Configure when container is ready to start accepting traffic. Use this to override the default readiness probe configuration. See [Configure Liveness and Readiness Probes](#configure-liveness-and-readiness-probes) for more information. | YAML object of readiness probe |
|           | `livenessProbe`        | Configure when to restart container. Use this to override the default liveness probe configuration. See [Configure Liveness and Readiness Probes](#configure-liveness-and-readiness-probes) for more information. | YAML object of liveness probe |
|           | `lifecycle`        | Handlers for the PostStart and PreStop lifecycle events of container. | YAML object of lifecycle handlers |
|           | `security`  | Configure the security attributes of the image | YAML object of security attributes |
| `deployment`     | `annotations` | Additional annotations to be added to Deployment (or StatefulSet if persistence is enabled) | YAML object of annotations |
|                  | `labels`     | Additional labels to be added to Deployment (or StatefulSet if persistence is enabled)  | YAML object of labels |
| `pod`     | `annotations` | Additional annotations to be added to pods | YAML object of annotations |
|           | `labels`     | Additional labels to be added to pods  | YAML object of labels |
|           | `extraInitContainers` | Additional Init Containers which are run before the containers are started | YAML array of `initContainers` definitions |
|           | `extraContainers`     | Additional containers to be added to the server pods | YAML array of `containers` definitions |
|           | `security`  | Configure the security attributes of the pod | YAML object of security attributes |
|           | `extraNodeSelectorRequirements`  | Additional node selector expressions to require before selecting a node for pods. Note that these will need to be satisfied in addition to the defined architecture labels in order to be scheduled. | YAML array of expressions to match. |
|           | `extraNodeSelectorPreferences`  | Additional node selector expressions to prefer, in order of weight, when selecting a node for pods. | YAML array of expressions to match. |
| `service` | `enabled`    | Specifies whether the `HTTP` port service is enabled or not.  |  |
|           | `name`       | The service metadata name and DNS A record.  | |
|           | `port`       | The port that this container exposes.  |   |
|           | `targetPort` | Port that will be exposed externally by the pod. | |
|           | `type`       | Specify type of service. | Valid options are `ClusterIP` and `NodePort`. See [Publishing services - service types](https://kubernetes.io/docs/concepts/services-networking/service/#publishing-services---service-types). This parameter is ignored for headless services and instead, it is forced to `clusterIP` with a value of `None`. This chart makes a service headless when persistence is enabled, which is done by setting either `logs.persistTransactionLogs` or `logs.persistLogs` to `true`. See [Headless services](https://kubernetes.io/docs/concepts/services-networking/service/#headless-services). |
|           | `labels`         | Additional labels to be added to service.                       |  YAML object of labels  |
|           | `annotations`    | Additional annotations to be added to service.                  |  YAML object of annotations  |
|           | `extraPorts`     | List of additional ports that are exposed by this service.      |  YAML list service ports. See [Virtual IPs and service proxies](https://kubernetes.io/docs/concepts/services-networking/service/#virtual-ips-and-service-proxies).    |
|           | `extraSelectors` | List of additional label keys and values. Kubernetes routes service traffic to pods with label keys and values matching selector values.| See [Services](https://kubernetes.io/docs/concepts/services-networking/service/). |
| `iiopService`| `enabled` | Specifies whether the IIOP port service is enabled or not.  |   |
|           | `nonSecurePort`       | The IIOP port that this container exposes.  |   |
|           | `nonSecureTargetPort` | IIOP Port that will be exposed externally by the pod. | |
|           | `securePort`       | The secure IIOP port that this container exposes. Specifying this port is needed if SSL is enabled. |   |
|           | `secureTargetPort` | Secure IIOP Port that will be exposed externally by the pod. Specifying this port is needed if SSL is enabled. | |
|           | `type`       | Specify type of service. | Valid options are `ClusterIP` and `NodePort`. See [Publishing services - service types](https://kubernetes.io/docs/concepts/services-networking/service/#publishing-services---service-types). This parameter is ignored for headless services and instead, it is forced to `clusterIP` with a value of `None`. This chart makes a service headless when persistence is enabled, which is done by setting either `logs.persistTransactionLogs` or `logs.persistLogs` to `true`. See [Headless services](https://kubernetes.io/docs/concepts/services-networking/service/#headless-services).|
| `jmsService`| `enabled`  | Specifies whether the JMS port service is enabled or not.  |   |
|           | `port`      | The JMS port that this container exposes.  |   |
|           | `targetPort` | JMS Port that will be exposed externally by the pod. | |
|           | `type`       | Specify type of service. | Valid options are `ClusterIP` and `NodePort`. See [Publishing services - service types](https://kubernetes.io/docs/concepts/services-networking/service/#publishing-services---service-types). This parameter is ignored for headless services and instead, it is forced to `clusterIP` with a value of `None`. This chart makes a service headless when persistence is enabled, which is done by setting either `logs.persistTransactionLogs` or `logs.persistLogs` to `true`. See [Headless services](https://kubernetes.io/docs/concepts/services-networking/service/#headless-services). |
| `ssl`       | `enabled`                       | Specifies whether SSL is enabled. Set to true only if Liberty server is configured to use SSL in the Docker image. | `true` (default) or `false` |
|           | `useClusterSSLConfiguration`    | Set to true if you want to use the SSL ConfigMap and secrets generated by the createClusterSSLConfiguration option. Set to false if the Docker image already has SSL configured. | `false` (default) or `true` |
|           | `createClusterSSLConfiguration` | Specifies whether to automatically generate SSL ConfigMap and secrets. The generated ConfigMap is: liberty-config.  The generated secrets are: `mb-keystore`, `mb-keystore-password`, `mb-truststore`, and `mb-truststore-password`.  Only generate the SSL configuration one time. If you generate the configuration a second time, errors might occur. | `false` (default) or `true` |
| `ingress` | `enabled`        | Specifies whether to use ingress.        |  `false` (default) or `true`  |
|           | `rewriteTarget`  | Specifies the target URI where the traffic must be redirected. | See [Ingress Configuration](https://github.com/OpenLiberty/ci.docker/docs) for more info on this. |
|           | `path`           | Specifies the path for the Ingress HTTP rule.    | See [Ingress Configuration](https://github.com/OpenLiberty/ci.docker/docs) for more info on this. |
|           | `host`           | Specifies a fully qualified domain names of Ingress, as defined by RFC 3986. | See [Ingress Configuration](https://github.com/OpenLiberty/ci.docker/docs) for more info on this. |
|           | `secretName`     | Specifies the name of the Kubernetes secret that contains Ingress' TLS certificate and key.   | See [Ingress Configuration](https://github.com/OpenLiberty/ci.docker/docs) for more info on this. |
|           | `labels`         | Specifies custom labels.         | YAML object of labels. See [Ingress Configuration](https://github.com/OpenLiberty/ci.docker/docs) for more info on this. |
|           | `annotations`    | Specifies custom annotations.    | YAML object of annotations. See [Ingress Configuration](https://github.com/OpenLiberty/ci.docker/docs) for more info on this.  |
| `persistence` | `name`                   | Descriptive name that will be used as a prefix for the generated persistence volume claim. A volume is only bound if either `logs.persistTransactionLog` or `logs.persistLogs` is set to `true`. | |
|             | `useDynamicProvisioning` | If `true`, the persistent volume claim will use the storageClassName to bind the volume. If `storageClassName` is not set then it will use the default StorageClass setup by kube Administrator.  If `false`, the selector will be used for the binding process. | `true` (default) or `false` |
|             | `fsGroupGid`             | Defines file system group ID for volumes that support ownership management. This value is added to the containers supplemental groups.  | `nil` |
|             | `storageClassName`       | Specifies a StorageClass pre-created by the Kubernetes sysadmin. When set to `""`, then the PVC is bound to the default `storageClass` setup by kube Administrator. | |
|             | `selector.label`         | When matching a PV, the label is used to find a match on the key. | See Kubernetes - [Labels and Selectors](https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/). | |
|             | `selector.value`         | When matching a PV, the value is used to find a match on the values. | See Kubernetes - [Labels and Selectors](https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/). | |
|             | `size`                   | Size of the volume to hold all the persisted data. | Size in Gi (default is `1Gi`) |
| `logs`        | `persistLogs`            | When `true`, the server logs will be persisted to the volume bound according to the persistence parameters. | `false` (default) or `true` |
|             | `persistTransactionLogs` | When `true`, the transaction logs will be persisted to the volume bound according to the persistence parameters. | `false` (default) or `true` |
|             | `consoleFormat`          | _[18.0.0.1+]_ Specifies container log output format | `json` (default) or `basic` |
|             | `consoleLogLevel`        | _[18.0.0.1+]_ Controls the granularity of messages that go to the container log | `info` (default), `audit`, `warning`, `error` or `off` |
|             | `consoleSource`          | _[18.0.0.1+]_ Specifies the sources that are written to the container log. Use a comma separated list for multiple sources. This property only applies when consoleFormat is set to `json`.  | Sources can be one or more of `message`, `trace`, `accessLog`, `ffdc`, `audit`. Default value is `message,trace,accessLog,ffdc`  |
| `microprofile` | `health.enabled` | Specifies whether to use the [MicroProfile Health](https://microprofile.io/project/eclipse/microprofile-health) endpoint (`/health`) for readiness and liveness probes of the container. Requires HTTP service to be enabled and Liberty server must be configured to use MicroProfile Health in the Docker image. | `false` (default) or `true` |
| `monitoring` | `enabled` | _[18.0.0.3+]_ Specifies whether to use Liberty features `monitor-1.0` and `mpMetrics-1.1` to monitor the server runtime environment and application metrics. Requires HTTP service to be enabled and Liberty server must be configured to enable monitoring in the Docker image. See [Monitoring](#monitoring) for more info. | `false` (default) or `true` |
| `replicaCount` |     |  Describes the number of desired replica pods running at the same time. | Default is `1`.  See [Replica Sets](https://kubernetes.io/docs/concepts/workloads/controllers/replicaset) |
| `autoscaling` | `enabled`                        | Specifies whether a horizontal pod autoscaler (HPA) is deployed.  Note that enabling this field disables the `replicaCount` field. | `false` (default) or `true` |
|             | `minReplicas`                    | Lower limit for the number of pods that can be set by the autoscaler.   |  `Positive integer` (default to `1`)  |
|             | `maxReplicas`                    | Upper limit for the number of pods that can be set by the autoscaler.  Cannot be lower than `minReplicas`.   |  `Positive integer` (default to `10`)  |
|             | `targetCPUUtilizationPercentage` | Target average CPU utilization (represented as a percentage of requested CPU) over all the pods.  |  `Integer between `1` and `100` (default to `50`)  |
| `resources` | `constraints.enabled` | Specifies whether the resource constraints specified in this Helm chart are enabled.   | `false` (default) or `true`  |
|           | `limits.cpu`          | Describes the maximum amount of CPU allowed. | Default is `4000m`. See Kubernetes - [meaning of CPU](https://kubernetes.io/docs/concepts/configuration/manage-compute-resources-container/#meaning-of-cpu)  |
|           | `limits.memory`       | Describes the maximum amount of memory allowed. | Default is `2Gi`. See Kubernetes - [meaning of Memory](https://kubernetes.io/docs/concepts/configuration/manage-compute-resources-container/#meaning-of-memory) |
|           | `requests.cpu`        | Describes the minimum amount of CPU required. If not specified, the CPU amount will default to the limit (if specified) or implementation-defined value. | Default is `500m`. See Kubernetes - [meaning of CPU](https://kubernetes.io/docs/concepts/configuration/manage-compute-resources-container/#meaning-of-cpu) |
|           | `requests.memory`     | Describes the minimum amount of memory required. If not specified, the memory amount will default to the limit (if specified) or the implementation-defined value. | Default is `512Mi`. See Kubernetes - [meaning of Memory](https://kubernetes.io/docs/concepts/configuration/manage-compute-resources-container/#meaning-of-memory) |
| `arch`    | `amd64` | Architecture preference for amd64 worker node.   | `0 - Do not use`, `1 - Least preferred`, `2 - No preference` (default) or `3 - Most preferred`  |
|           | `ppc64le`          | Architecture preference for ppc64le worker node. | `0 - Do not use`, `1 - Least preferred`, `2 - No preference` (default) or `3 - Most preferred`  |
|           | `s390x`       | Architecture preference for s390x worker node. | `0 - Do not use`, `1 - Least preferred`, `2 - No preference` (default) or `3 - Most preferred` |
| `env`       | `jvmArgs`             | Specifies the `JVM_ARGS` environmental variable for the Liberty runtime. | |
| `sessioncache` | `hazelcast.enabled` | Enable session caching using Hazelcast | `false` |
| `sessioncache` | `hazelcast.embedded` | Hazelcast Topology. Embedded (true). Client/Server (false). | `false` |
|              | `hazelcast.image.repository` | Name of Hazelcast image, including repository prefix (if required).| `hazelcast/hazelcast` |
|              | `hazelcast.image.tag` | Docker image tag | `3.10.6` |
|              | `hazelcast.image.pullPolicy` | Image Pull Policy | `IfNotPresent` |
| `rbac`      | `install`             | Install RBAC. Set to `true` if using a namespace with RBAC. | `true` |
| `oidcClient`| `enabled`         | Set to `true` to enable security using OpenId Connect. |   `false` (default) or `true`  |
|                | `clientId`     | The client ID that has been obtained from the OpenId Connect Provider. |  a string  |
|                | `clientSecretName` | The Kubernetes secret containing the client secret that has been obtained from the OpenId Connect Provider. The key inside this secret must be named `clientSecret`. |  a string  |
|                | `discoveryURL` | The discovery URL of the OpenId Connect Provider. |  a URL  |
| `app`      | `autoCreate`             | Adds `prism.app.auto-create` annotation for integration with Application Navigator. | `true` |
|            | `version`             | Adds `prism.app.auto-create.version` annotation. | `1.0.0` |

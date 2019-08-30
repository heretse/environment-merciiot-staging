pipeline {
  options {
    disableConcurrentBuilds()
  }
  agent {
      label "jenkins-maven"
  }
  environment {
    DEPLOY_NAMESPACE      = "jx-staging"
    ORG                   = 'merciiot'
    DB_NAME_MONGO         = 'mongodb'
    DB_NAME_MYSQL         = 'mysqldb'
    APP_NAME_ALT          = 'alt-svc'
    APP_NAME_AM           = 'am-svc'
    APP_NAME_DATA         = 'data-svc'
    APP_NAME_DEVICE       = 'device-svc'
    APP_NAME_LAYOUT       = 'layout-svc'
    APP_NAME_LOCATION     = 'location-svc'
    APP_NAME_MQTT         = 'mqtt-svc'
    APP_NAME_NOTIFICATION = 'notification-svc'
    APP_NAME_PACKET       = 'packet-svc'
    APP_NAME_REPORT       = 'report-svc'
    CHARTMUSEUM_CREDS     = credentials('jenkins-x-chartmuseum')
  }
  stages {
    stage('CI Build Release') {
      steps {
        script{properties([disableConcurrentBuilds()])}
        container('maven') {
          // ensure we're not on a detached head
          sh "git checkout develop"
          sh "git config --global credential.helper store"
          sh "jx step validate --min-jx-version 1.1.73"
          sh "jx step git credentials"
          // so we can retrieve the version in later steps
          sh "echo \$(jx-release-version) > VERSION"
        }
        dir ('charts/mongodb') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('charts/mysqldb') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('charts/alt-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('charts/am-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('charts/data-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('charts/device-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('charts/layout-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('charts/location-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('charts/mqtt-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('charts/notification-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('charts/packet-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('charts/report-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        container('maven') {
          sh 'git add --all'
          sh 'git commit -m \"release \$(cat VERSION)\" --allow-empty'
          sh 'git tag -fa v\$(cat VERSION) -m \"Release version \$(cat VERSION)\"'
          sh 'git push origin v\$(cat VERSION)'
        }
        dir ('charts/mongodb') {
          container('maven') {
            sh 'export VERSION=`cat ../../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step validate --min-jx-version 1.2.36"
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$DB_NAME_MONGO:\$(cat ../../VERSION)"
          }
        }
        dir ('charts/mysqldb') {
          container('maven') {
            sh 'export VERSION=`cat ../../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step validate --min-jx-version 1.2.36"
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$DB_NAME_MYSQL:\$(cat ../../VERSION)"
          }
        }
        dir ('charts/am-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step validate --min-jx-version 1.2.36"
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_AM:\$(cat ../../VERSION)"
          }
        }
        dir ('charts/alt-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_ALT:\$(cat ../../VERSION)"
          }
        }
        dir ('charts/data-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_DATA:\$(cat ../../VERSION)"
          }
        }
        dir ('charts/device-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_DEVICE:\$(cat ../../VERSION)"
          }
        }
        dir ('charts/layout-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_LAYOUT:\$(cat ../../VERSION)"
          }
        }
        dir ('charts/location-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_LOCATION:\$(cat ../../VERSION)"
          }
        }
        dir ('charts/mqtt-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_MQTT:\$(cat ../../VERSION)"
          }
        }
        dir ('charts/notification-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_NOTIFICATION:\$(cat ../../VERSION)"
          }
        }
        dir ('charts/packet-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_PACKET:\$(cat ../../VERSION)"
          }
        }
        dir ('charts/report-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_REPORT:\$(cat ../../VERSION)"
          }
        }
      }
    }
    stage('Promote to Environments') {
      steps {
        dir ('charts/mongodb') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('charts/mysqldb') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('charts/alt-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('charts/am-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('charts/data-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('charts/device-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('charts/layout-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('charts/location-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('charts/mqtt-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('charts/notification-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('charts/packet-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('charts/report-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
      }
    }
    stage('Validate Environment') {
      steps {
        container('maven') {
          dir('charts/mongodb') {
            sh 'jx step helm build'
          }
          dir('charts/mysqldb') {
            sh 'jx step helm build'
          }
          dir('charts/alt-svc') {
            sh 'jx step helm build'
          }
          dir('charts/am-svc') {
            sh 'jx step helm build'
          }
          dir('charts/data-svc') {
            sh 'jx step helm build'
          }
          dir('charts/device-svc') {
            sh 'jx step helm build'
          }
          dir('charts/layout-svc') {
            sh 'jx step helm build'
          }
          dir('charts/location-svc') {
            sh 'jx step helm build'
          }
          dir('charts/mqtt-svc') {
            sh 'jx step helm build'
          }
          dir('charts/notification-svc') {
            sh 'jx step helm build'
          }
          dir('charts/packet-svc') {
            sh 'jx step helm build'
          }
          dir('charts/report-svc') {
            sh 'jx step helm build'
          }
        }
      }
    }
    stage('Update Environment') {
      steps {
        container('maven') {
          dir('charts/mongodb') {
            sh 'jx step helm apply'
          }
          dir('charts/mysqldb') {
            sh 'jx step helm apply'
          }
          dir('charts/alt-svc') {
            sh 'jx step helm apply'
          }
          dir('charts/am-svc') {
            sh 'jx step helm apply'
          }
          dir('charts/data-svc') {
            sh 'jx step helm apply'
          }
          dir('charts/device-svc') {
            sh 'jx step helm apply'
          }
          dir('charts/layout-svc') {
            sh 'jx step helm apply'
          }
          dir('charts/location-svc') {
            sh 'jx step helm apply'
          }
          dir('charts/mqtt-svc') {
            sh 'jx step helm apply'
          }
          dir('charts/notification-svc') {
            sh 'jx step helm apply'
          }
          dir('charts/packet-svc') {
            sh 'jx step helm apply'
          }
          dir('charts/report-svc') {
            sh 'jx step helm apply'
          }
        }
      }
    }
  }
  post {
      always {
          cleanWs()
      }
  }
}
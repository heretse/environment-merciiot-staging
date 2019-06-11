pipeline {
  options {
    disableConcurrentBuilds()
  }
  agent {
      label "jenkins-maven"
  }
  environment {
    DEPLOY_NAMESPACE    = "jx-staging"
    ORG                 = 'merciiot'
    APP_NAME_ALT        = 'alt-svc'
    APP_NAME_AM         = 'am-svc'
    APP_NAME_DATA       = 'data-svc'
    APP_NAME_DEVICE     = 'device-svc'
    APP_NAME_LAYOUT     = 'layout-svc'
    APP_NAME_LOCATION   = 'location-svc'
    APP_NAME_PACKET     = 'packet-svc'
    APP_NAME_REPORT     = 'report-svc'
    CHARTMUSEUM_CREDS   = credentials('jenkins-x-chartmuseum')
  }
  stages {
    stage('CI Build Release') {
      steps {
        script{properties([disableConcurrentBuilds()])}
        container('maven') {
          // ensure we're not on a detached head
          sh "git checkout master"
          sh "git config --global credential.helper store"
          sh "jx step validate --min-jx-version 1.1.73"
          sh "jx step git credentials"
          // so we can retrieve the version in later steps
          sh "echo \$(jx-release-version) > VERSION"
        }
        dir ('mercchart/charts/alt-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('mercchart/charts/am-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('mercchart/charts/data-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('mercchart/charts/device-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('mercchart/charts/layout-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('mercchart/charts/location-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('mercchart/charts/packet-svc') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('mercchart/charts/report-svc') {
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
        dir ('am-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step validate --min-jx-version 1.2.36"
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_AM:\$(cat ../VERSION)"
          }
        }
        dir ('alt-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_ALT:\$(cat ../VERSION)"
          }
        }
        dir ('data-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_DATA:\$(cat ../VERSION)"
          }
        }
        dir ('device-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_DEVICE:\$(cat ../VERSION)"
          }
        }
        dir ('layout-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_LAYOUT:\$(cat ../VERSION)"
          }
        }
        dir ('location-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_LOCATION:\$(cat ../VERSION)"
          }
        }
        dir ('packet-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_PACKET:\$(cat ../VERSION)"
          }
        }
        dir ('report-svc') {
          container('maven') {
            sh 'export VERSION=`cat ../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME_REPORT:\$(cat ../VERSION)"
          }
        }
      }
    }
    stage('Promote to Environments') {
      steps {
        dir ('mercchart/charts/alt-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('mercchart/charts/am-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('mercchart/charts/data-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('mercchart/charts/device-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('mercchart/charts/layout-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('mercchart/charts/location-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('mercchart/charts/packet-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
        dir ('mercchart/charts/report-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../../VERSION)'

            // release the helm chart
            sh 'make release'
          }
        }
      }
    }
    stage('Validate Environment') {
      steps {
        container('maven') {
          dir('mercchart/charts/alt-svc') {
            sh 'jx step helm build'
          }
          dir('mercchart/charts/am-svc') {
            sh 'jx step helm build'
          }
          dir('mercchart/charts/data-svc') {
            sh 'jx step helm build'
          }
          dir('mercchart/charts/device-svc') {
            sh 'jx step helm build'
          }
          dir('mercchart/charts/layout-svc') {
            sh 'jx step helm build'
          }
          dir('mercchart/charts/location-svc') {
            sh 'jx step helm build'
          }
          dir('mercchart/charts/packet-svc') {
            sh 'jx step helm build'
          }
          dir('mercchart/charts/report-svc') {
            sh 'jx step helm build'
          }
        }
      }
    }
    stage('Update Environment') {
      steps {
        container('maven') {
          dir('mercchart/charts/alt-svc') {
            sh 'jx step helm apply'
          }
          dir('mercchart/charts/am-svc') {
            sh 'jx step helm apply'
          }
          dir('mercchart/charts/data-svc') {
            sh 'jx step helm apply'
          }
          dir('mercchart/charts/device-svc') {
            sh 'jx step helm apply'
          }
          dir('mercchart/charts/layout-svc') {
            sh 'jx step helm apply'
          }
          dir('mercchart/charts/location-svc') {
            sh 'jx step helm apply'
          }
          dir('mercchart/charts/packet-svc') {
            sh 'jx step helm apply'
          }
          dir('mercchart/charts/report-svc') {
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
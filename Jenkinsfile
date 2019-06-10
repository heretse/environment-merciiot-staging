pipeline {
  options {
    disableConcurrentBuilds()
  }
  agent {
      label "jenkins-maven"
  }
  environment {
    DEPLOY_NAMESPACE  = "jx-staging"
    ORG               = 'merciiot'
    APP_NAME_AM       = 'am-svc'
    APP_NAME_ALT      = 'alt-svc'
    CHARTMUSEUM_CREDS = credentials('jenkins-x-chartmuseum')
  }
  stages {
    stage('CI Build Release') {
      // when {
      //   branch 'master'
      // }
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
        dir ('mercchart/charts/am-svc') {
          container('maven') {
            sh "make tag"
          }
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
      }
    }
    stage('Promote to Environments') {
      // when {
      //   branch 'master'
      // }
      steps {
        dir ('mercchart/charts/am-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../../VERSION)'

            // release the helm chart
            sh 'make release'

            // promote through all 'Auto' promotion Environments
            // sh 'jx promote -b --all-auto --timeout 1h --version \$(cat ../VERSION)'
          }
        }
        dir ('mercchart/charts/alt-svc') {
          container('maven') {
            sh 'jx step changelog --version v\$(cat ../../../VERSION)'

            // release the helm chart
            sh 'make release'

            // promote through all 'Auto' promotion Environments
            // sh 'jx promote -b --all-auto --timeout 1h --version \$(cat ../VERSION)'
          }
        }
      }
    }
    stage('Validate Environment') {
      steps {
        container('maven') {
          dir('mercchart/charts/am-svc') {
            sh 'jx step helm build'
          }
          dir('mercchart/charts/alt-svc') {
            sh 'jx step helm build'
          }
        }
      }
    }
    stage('Update Environment') {
      // when {
      //   branch 'master'
      // }
      steps {
        container('maven') {
          dir('mercchart/charts/am-svc') {
            sh 'jx step helm apply'
          }
          dir('mercchart/charts/alt-svc') {
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
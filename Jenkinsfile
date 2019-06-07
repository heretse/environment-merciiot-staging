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
    APP_NAME          = 'am-svc'
    CHARTMUSEUM_CREDS = credentials('jenkins-x-chartmuseum')
  }
  stages {
    stage('CI Build Release') {
      when {
        branch 'master'
      }
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
        dir ('mercchart') {
          container('maven') {
            sh "make tag"
          }
        }
        dir ('am-svc') {
          container('maven') {
            // sh "npm install"
            // sh "CI=true DISPLAY=:99 npm test"

            sh 'export VERSION=`cat ../VERSION` && skaffold run -f skaffold.yaml'
            sh "jx step validate --min-jx-version 1.2.36"
            sh "jx step post build --image \$JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST:\$JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT/$ORG/$APP_NAME:\$(cat ../VERSION)"
          }
        }
      }
    }
    stage('Promote to Environments') {
      when {
        branch 'develop'
      }
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
      }
    }
    stage('Validate Environment') {
      steps {
        container('maven') {
          dir('mercchart/charts/am-svc') {
            sh 'jx step helm build'
          }
        }
      }
    }
    stage('Update Environment') {
      when {
        branch 'develop'
      }
      steps {
        container('maven') {
          dir('mercchart/charts/am-svc') {
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
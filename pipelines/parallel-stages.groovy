pipeline {
  agent any 
  stages {
    stage('run-parallel-branches') {
      steps {
        parallel(
          ('checkout-demo-apps'): {
            dir('demo-apps'){
              checkout scmGit(
                branches: [[name: 'main']],
                userRemoteConfigs: [[url: 'https://github.com/avikjis27/demo-apps.git']])
            }
          },
          ('checkout-argo-gitops'): {
            dir('argo-gitops'){
              checkout scmGit(
                branches: [[name: 'main']],
                userRemoteConfigs: [[url: 'https://github.com/avikjis27/argo-gitops.git']])
            }
          }
        )
      }
    }
  }
}
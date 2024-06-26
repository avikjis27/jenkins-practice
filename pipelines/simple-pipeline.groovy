pipeline {
    agent any
    parameters {
            string(name: 'BRANCH', defaultValue: 'main', description: 'Branch to checkout')
            booleanParam(name: 'PUBLISH', defaultValue: false, description: 'Do you want to publish docker image?')
            string(name: 'DOCKER_REPO', description: 'Docker repository to publish')
            string(name: 'DOCKER_TAG', defaultValue: 'latest', description: 'Docker tag to publish')
            string(name: 'REPO_USERNAME', description: 'Repository username')
            password(name: 'REPO_PASSWORD', description: 'Repository password')
    }
    stages {
        stage ("Update job description"){
            steps{
                script {
                    currentBuild.displayName = "#${env.BUILD_ID}-${params.BRANCH}"
                }
            }  
        }
        stage ("Verify job parameters"){
            steps{
                script {
                    if (params.PUBLISH){

                    }
                }
            }  
        }
        stage ("Checkout SCM") {
            steps {
                //checkout scm
                dir('demo-apps'){
                    checkout scmGit(
                        branches: [[name: params.BRANCH]],
                        userRemoteConfigs: [[url: 'https://github.com/avikjis27/demo-apps.git']])
                }
            }
        }
        stage ("Docker build") {
            steps{
                sh '''
                    cd ${WORKSPACE}/demo-apps/nodejs-webapp
                    docker build -t nodejs-webapp:latest .
                '''
            }
        }
        stage ("Docker tag") {
            when {
                expression {
                    return params.PUBLISH;
                }
            }
            steps{
                sh "docker tag nodejs-webapp ${params.DOCKER_REPO}:${params.DOCKER_TAG}"
            }
        }
        stage ("Docker publish") {
            when {
                expression {
                    return params.PUBLISH;
                }
            }
            steps {
                //Insecured way to pass password.
                sh "docker login -u ${params.REPO_USERNAME} -p ${params.REPO_PASSWORD}"
                // One should store the password in secret store and use withCredential
                //  withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'REPO_PASSWORD', usernameVariable: 'REPO_USERNAME')]) {
                //      sh "docker login -u ${env.REPO_USERNAME} -p ${env.REPO_PASSWORD}"
                //  }
                sh "docker push ${params.DOCKER_REPO}:${params.DOCKER_TAG}"
            }
            
        }
    }
}
pipeline {
    agent any
    parameters {
            booleanParam(name: 'DEBUG_BUILD', defaultValue: true, description: '')
            choice(name: 'CHOICES', choices: ['one', 'two', 'three'], description: '')
            string(name: 'BRANCH', defaultValue: 'main', description: 'Branch to checkout')
    }
    stages {
        stage ("Checkout SCM") {
            git branch: params.BRANCH,
                credentialsId: 'github-username-password',
                url: 'https://github.com/avikjis27/demo-apps.git'
        }
        
        
        // stage("Checkout") {
        //     checkout scm
        // }
    }
}
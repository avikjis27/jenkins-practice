pipeline {
    agent any
    stages {
        stage ("Print branch name"){
            steps{
                script {
                    echo "Triggering branch ${env.BRANCH_NAME}"
                }
            }  
        }
    }
}
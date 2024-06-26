pipelineJob('simple_pipeline') {
  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('https://github.com/avikjis27/jenkins-practice.git')
          }
          branch('*/main')
        }
        scriptPath('pipelines/simple-pipeline.groovy')
      }
      lightweight()
    }
  }
}
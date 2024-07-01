pipelineJob('parallel_stages_pipeline') {
  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('https://github.com/avikjis27/jenkins-practice.git')
          }
          branch('*/main')
        }
        scriptPath('pipelines/parallel-stages.groovy')
      }
      lightweight()
    }
  }
}
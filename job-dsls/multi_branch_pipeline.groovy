multibranchPipelineJob('multi_branch_pipeline') {
  branchSources {
    git {
      id('01072024') // IMPORTANT: use a constant and unique identifier
      remote('https://github.com/avikjis27/jenkins-practice.git')
      includes('release/*')
    }
  }
  orphanedItemStrategy {
    discardOldItems {
        numToKeep(20)
    }
  }
  factory {
        workflowBranchProjectFactory {
            scriptPath('pipelines/multibranch-pipeline.groovy')
        }
    }
}
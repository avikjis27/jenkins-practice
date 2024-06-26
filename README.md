# Jenkins Playground

This repo can be used to quickly setup jenkins environment in local machine to play, learn, practice Jenkins technology.

The Jenkins is running on docker and using Jenkins Configuration as Code ([JCasC](https://www.jenkins.io/projects/jcasc/)) concept to setup a bare minimum jenkins playgorund. See [jenkins.yaml](./jenkins.yaml) file for details. 

In [jenkins.yaml](./jenkins.yaml) we have a seeder job created. The seeder job will in turn use [Job DSL](https://plugins.jenkins.io/job-dsl/) plugin to prepare other [job](./job-dsls/simple_pipeline.groovy).  
```
jobs:
  - script: |
      freeStyleJob('seeder-job') {
        logRotator(-1, 10)
        scm {
            github(ownerAndProject = 'avikjis27/jenkins-practice', branch= 'main', protocol = 'https')
        }
        steps {
            dsl(['job-dsls/simple_pipeline.groovy'], 'DELETE')
        }
      }
```
The required plugins are mentioned in [plugins.yml](./plugins.yml). This file is getting injected inside the jenkins container by the [bootstrap.sh](./bootstrap.sh) and `jenkins-plugin-cli` will be used to install those in jenkins container.

## Quick launch in local machine
Local machine should have `docker` and `docker compose`.
```
./bootstrap.sh
```
## Quick launch in gitpod

Click the button below to start a new development environment:

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/?editor=code-desktop#https://github.com/avikjis27/jenkins-practice)

## Post launch activities
1. Open http://localhost:8080 in browser.
2. You should see the Jenkins login prompt. Credential is `deployer/deployer`.
3. There is a seeder [job](http://localhost:8080/job/seeder-job/) available. This job will create other pipeline jobs to play with.

## Tear down application
```
./tear-down.sh
```
## Known issues
In MAC machine docker build is not running from the jenkins container as I am not able to configure the docker in docker correctly for MAC may be.




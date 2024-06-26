#!/bin/bash -x

jenkins_controller_container_name=jenkins-controller
docker compose up -d
sleep 30
docker cp plugins.yml ${jenkins_controller_container_name}:/tmp/plugins.yml
docker cp jenkins.yaml ${jenkins_controller_container_name}:/tmp/jenkins.yaml
docker exec -it ${jenkins_controller_container_name} /bin/bash -c "
jenkins-plugin-cli --plugin-file /tmp/plugins.yml
cp -r -p /usr/share/jenkins/ref/plugins/. /var/jenkins_home/plugins/.
cp /tmp/jenkins.yaml /var/jenkins_home/.
"
docker compose restart
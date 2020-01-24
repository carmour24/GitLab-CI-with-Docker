This repository is intended to demonstrate how to set up GitLab CI in a Docker environment. This includes configuring the GitLab Docker runner to provision containers for the build process, the use of Docker in Docker (DinD) to generate a Docker image including build artefacts from a previous build stage and the use of TestContainers running a Cypress container in DinD for testing purposes. The videos and reports of the test runs created by Cypress, are made available as artifacts of the build process also.

We will configure a specific runner for a project we will create in the GitLab git repo.

To get started:

1. Clone the repo to your local machine. 
1. Delete the .git folder as we'll be adding a subdirectory to a local GitLab instance hosted in Docker.
1. Run `docker-compose up -d` to get Gitlab and the Gitlab Docker runner started. Once gitlab is reporting as "Up (healthy)" you can continue. Use `docker-compose ps` to check for service health.
1. Navigate to the gitlab instance at http://localhost:1480 and create a password for root.
1. Log in with root and the password you used in the previous step and create a project.
1. Register the runner with the Gitlab server:
  1. Navigate into the project then into Settings -> CI/CD.
  1. Expand the runners section, find "Set up a specific Runner manually" and copy the runner registration token displayed.
  1. Next, from the terminal, run the following command to register the runner non-interactively. Replace "[runner registration token]" with the token copied in the previous step. This will register the docker runner and allow it to run untagged builds:

	   docker-compose exec gitlab-runner gitlab-runner register \
			-n \
			--docker-image alpine:latest \
			--executor docker \
			-u http://gitlab \
			--docker-privileged \
			--docker-volumes "/certs/client" \
			--docker-network-mode docknet \
			--run-untagged \
			-r [runner registration token]

Now gitlab is configured and the runner is ready to go we can go ahead and add some content to our project. Go to ./repo in your shell and add the contents to the GitLab repo. You'll be prompted to enter a username and password. Use root and the password you specified originally:

1. `git init`
1. `git remote add origin http://localhost:1480/root/demo.git`
1. `git add .`
1. `git commit -m "Initial commit"`
1. `git push -u origin master`

Test docker run -p 8080:8080 demo

TODO: Ensure video artifacts are stored for failing tests.

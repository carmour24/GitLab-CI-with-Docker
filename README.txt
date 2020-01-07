This repository is intended to demonstrate how to set up GitLab CI in a Docker environment. This includes configuring the GitLab Docker runner to provision containers for the build process, the use of Docker in Docker (DinD) to generate a Docker image including build artefacts from a previous build stage and the use of TestContainers running in DinD for testing purposes.

We will configure a specific runner for a project we will create.

To get started:

# Clone the repo to your local machine. 
# Delete the .git folder as we'll be adding a subdirectory to a local GitLab instance hosted in Docker.
# Run `docker-compose up -d` to get Gitlab and the Gitlab Docker runner started. Once gitlab is reporting as "Up (health)" you can continue.
# Navigate to the gitlab instance at http://localhost:1480 and create a password for root.
# Log in with root and the password you used in the previous step and create a project.
# Register the runner with the Gitlab server:
# Navigate into the project then into Settings -> CI/CD.
# Expand the runners section, find the "Set up a specific Runner manually" and copy the runner registration token displayed.
# Next run the following command to register the runner non-interactively. Replace "[runner registration token] with the token from the previous step. This will register the docker runner and allow it to run untagged builds:

      docker-compose exec gitlab-runner gitlab-runner register \
	-n \
	--docker-image alpine:latest \
	--executor docker \
	-u http://gitlab \
	-r [runner registration token] \
	--docker-network-mode gitlab-ci-example_docknet \
	--run-untagged

Now gitlab is configured and the runner is ready to go we can go ahead and add some content to our project. Go to ./repo in your shell and add the contents to the GitLab repo. You'll be prompted to enter a username and password. Use root and the password you specified originally:

# git init
# git remote add origin http://localhost:1480/root/demo.git
# git add .
# git commit -m "Initial commit"
# git push -u origin master


Test docker run -p 8080:8080 demo

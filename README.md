# About
This is the backend of the portal template

# Prerequisites

### Required software
* **JDK 17**
* **Docker Desktop**
* Portal Frontend: TBD
* Nl Portal docker compose: https://github.com/nl-portal/nl-portal-docker-compose

### Required environment variables
For the project to run without errors, you need the .env.properties file in your spring application.

  
# Running, Stopping and Cleaning

### Building and Starting

1. Build the project in gradle(in case this doesn't happen automatically, press the reload button in your gradle tab and wait for it to finish building)
2. You need to have the containers running from the nl-portal-docker-compose
3. Start the project with the gradle task ```bootRun``` or from the commandline with: ```./gradlew bootRun```

The database container is started and the corresponding database is made, if it doesn't exist yet, automatically.

### Stopping
You can stop or restart the process via the respective buttons in the UI of your IDE.

The database container is stopped when the ```bootRun``` task fails executing (also happens when you stop the program from running in the IDE)

# Forking & Merging the template in your own repository
To receive template updates with changes to for example dependency versions, use the following steps:

## Forking
If you want your forked repository to not be public, you need to use github import instead of using a github fork [GitHub fork does not allow changing the repository visibility]
For making an import: [Link](https://docs.github.com/en/migrations/importing-source-code/using-github-importer/importing-a-repository-with-github-importer)

## Merging / updating
First time setup:
```
git clone https://github.com/Gemeente-DenHaag/my-nl-portal-backend.git
cd .\my-nl-portal-backend\
git branch -a
git remote add template https://github.com/nl-portal/nl-portal-backend-template.git
git remote -v
git fetch template
git branch -a
```

To update:
```
git fetch template main
git merge template/main
git push
```

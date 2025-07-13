pipeline {

    agent any

    environment {

        // Maven settings file path

        MAVEN_SETTINGS = "/home/oussama/.m2/settings.xml"

        // Docker Hub username - REPLACE WITH YOUR DOCKER HUB USERNAME

        DOCKER_HUB_USERNAME = "bengagioussama"

        // Docker image name and tag

        DOCKER_IMAGE = "${DOCKER_HUB_USERNAME}/foyer-app:1.0"

        // SonarQube details

        SONAR_PROJECT_KEY = "Foyer"

        SONAR_HOST_URL = "http://localhost:9000"

        SONAR_LOGIN = "admin" // In a real scenario, use Jenkins credentials

        SONAR_PASSWORD = "admin" // In a real scenario, use Jenkins credentials

    }

    stages {

        stage("Checkout" ) {

            steps {

                script {

                    echo "Cloning Git repository..."

                    // Assuming Jenkins is configured to clone the repo, or you can add git clone command here

                    // For a Jenkins pipeline, the SCM checkout step usually handles this automatically.

                    // If you need to explicitly clone, use: git branch: 'main', credentialsId: 'your-git-credentials-id', url: 'https://github.com/bengagioussama/Foyer.git'

                }

            }

        }

        stage("Maven Build" ) {

            steps {

                script {

                    echo "Running Maven clean..."

                    sh "mvn clean"

                    echo "Running Maven compile..."

                    sh "mvn compile"

                    echo "Running Maven test..."

                    sh "mvn test"

                    echo "Running Maven package..."

                    sh "mvn package"

                }

            }

        }

        stage("SonarQube Analysis") {

            steps {

                script {

                    echo "Running SonarQube analysis..."

                    // This step assumes SonarQube is accessible from the Jenkins agent

                    // and the credentials are correct.

                    sh "mvn sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_LOGIN} -Dsonar.password=${SONAR_PASSWORD}"

                }

            }

        }

        stage("Nexus Deploy") {

            steps {

                script {

                    echo "Deploying to Nexus..."

                    // This step assumes Nexus is accessible from the Jenkins agent

                    // and the credentials are configured in Jenkins or ~/.m2/settings.xml on the agent.

                    sh "mvn deploy -s /var/lib/jenkins/.m2/settings.xml"

                }

            }

        }

        stage("Build Docker Image") {

            steps {

                script {

                    echo "Building Docker image..."

                    sh "docker build -t ${DOCKER_IMAGE} ."

                }

            }

        }

        stage("Publish Docker Image") {

            steps {

                script {

                    echo "Logging into Docker Hub..."

                    // In a real Jenkins setup, use Jenkins Credentials for Docker login

                    // withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {

                    //    sh "echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin"

                    // }

                    // For local testing, you might need to manually log in on the Jenkins agent or use environment variables

                    sh "docker login -u ${DOCKER_HUB_USERNAME} -p Oussama22" // REPLACE WITH YOUR DOCKER HUB PASSWORD

                    echo "Pushing Docker image..."

                    sh "docker push ${DOCKER_IMAGE}"

                }

            }

        }

        stage("Deploy with Docker Compose") {

            steps {

                script {

                    echo "Deploying application with Docker Compose..."

                    // Ensure docker-compose.yml is in the workspace

                    sh "docker compose up -d"

                }

            }

        }

    }

    post {

        always {

            echo "Pipeline finished."

            // Add cleanup steps here if needed

            // sh "docker compose down" // To stop the application after testing

        }

        failure {

            echo "Pipeline failed. Check logs for details."

        }

        success {

            echo "Pipeline succeeded!"

        }

    }

}



pipeline {

    agent any

    environment {


        MAVEN_SETTINGS = "/home/oussama/.m2/settings.xml"



        DOCKER_HUB_USERNAME = "bengagioussama"


        DOCKER_IMAGE = "${DOCKER_HUB_USERNAME}/foyer-app:1.0"


        SONAR_PROJECT_KEY = "Foyer"

        SONAR_HOST_URL = "http://localhost:9000"

        SONAR_LOGIN = "admin" 

        SONAR_PASSWORD = "admin" // In a real scenario, use Jenkins credentials

    }

    stages {

        stage("Checkout" ) {

            steps {

                script {

                    echo "Cloning Git repository..."

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

        
                    sh "mvn sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_LOGIN} -Dsonar.password=${SONAR_PASSWORD}"

                }

            }

        }

        stage("Nexus Deploy") {

            steps {

                script {

                    echo "Deploying to Nexus..."


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



                    sh "docker login -u ${DOCKER_HUB_USERNAME} -p Oussama22" 

                    echo "Pushing Docker image..."

                    sh "docker push ${DOCKER_IMAGE}"

                }

            }

        }

        stage("Deploy with Docker Compose") {

            steps {

                script {

                    echo "Deploying application with Docker Compose..."

            

                    sh "docker compose up -d"

                }

            }

        }

    }

    post {

        always {

            echo "Pipeline finished."


        }

        failure {

            echo "Pipeline failed. Check logs for details."

        }

        success {

            echo "Pipeline succeeded!"

        }

    }

}



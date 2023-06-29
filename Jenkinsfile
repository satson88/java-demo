pipeline { 
    agent any

    //environment {
        /*registry = "sen31088"  // Replace with your Docker registry URL
        DOCKERHUB_CREDENTIALS= credentials('docker-sen31088')
        imageName = "webapp"  // Replace with your desired image name
        containerName = "my-webapp-container"  // Replace with your desired container name
        dockerfilePath = "./Dockerfile"  // Replace with the path to your Dockerfile
        dockerArgs = "-p 8080:80"  // Replace with your desired container arguments
        version = sh(script: 'jq \'.version\' version.json', returnStdout: true).trim() */
        //name = sh(script: 'cat pom.xml | grep -A1 java-demo | grep artifactId | awk -F'[><]' '{print $3}'' , returnStdout: true).trim()
        //version=sh(script: 'cat pom.xml | grep -A1 java-demo | grep version | awk -F'[><]' '{print $3}'' , returnStdout: true).trim()
    //}
    
    stages {
        stage('Clean WS') { 
            steps { 
                cleanWs()
            }
        }

        stage('SCM'){
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'sen', url: 'https://github.com/sen31088/java-demo.git']])
            }
        }
        
        stage('Build') {
            steps {
                   sh "/opt/maven/bin/mvn clean install"
                    		 
                }
            }
        

        stage('Unit Test') {
            steps {
                
                sh "/opt/maven/bin/mvn test"
            }
        }
        
        stage('Push to Artifcatory') {
            steps {
                sh "/opt/maven/bin/mvn deploy"
            }
        }

        stage('Code Analysis') {
            steps {
                sh "/opt/maven/bin/mvn clean verify sonar:sonar \
                    -Dsonar.projectKey=java-demo \
                    -Dsonar.host.url=http://sonar.manolabs.co.in:9000 \
                    -Dsonar.login=sqp_73c10b13df89fb590df8a43b7a81107f2fda4814"
            }
        }


        stage('Deploy') {
            steps {
                sh ''' 
                name=`cat pom.xml | grep -A1 java-demo | grep artifactId | awk -F'[><]' '{print $3}'`
                version=`cat pom.xml | grep -A1 java-demo | grep version | awk -F'[><]' '{print $3}'`
                
                echo "------------------------"
                echo $name
                echo $version
                echo "-------------------------"
                ssh root@10.0.1.207 <<  EOF
                cd /java-app
                curl  -o java-app.jar -u admin:pass123 "http://nexus.manolabs.co.in:8081/repository/java-demo/com/sen/$name/$version/$name-$version.jar"
                sh start.sh
                exit
                EOF
                '''
            }
        }
    }
}

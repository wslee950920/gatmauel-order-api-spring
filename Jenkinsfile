node('master'){
    stage('Poll'){
        checkout scm
    }

    stage('Unit test'){
        sh './gradlew clean test --no-daemon'
        junit '**/build/test-results/test/*.xml'
    }

    stage('Build package'){
        sh './gradlew clean build -x test --no-daemon'
        sh 'cp build/libs/order-0.0.1-SNAPSHOT.jar docker/app.jar'
    }

    stage("Build Docker image"){
        app = docker.build("524195111135.dkr.ecr.ap-northeast-2.amazonaws.com/gatmauel-user-api-spring:${env.BUILD_ID}", "./docker")
    }

    stage("Push image"){
        sh 'rm  ~/.dockercfg || true'
        sh 'rm ~/.docker/config.json || true'

        docker.withRegistry('https://524195111135.dkr.ecr.ap-northeast-2.amazonaws.com', 'ecr:ap-northeast-2:aws-wslee950920'){
            app.push()
        }
    }
}
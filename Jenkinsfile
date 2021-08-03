node('master'){
    stage('Poll'){
        checkout scm
    }

    stage('Unit test'){
        sh './gradlew clean test --no-daemon'
        junit '**/build/test-results/test/*.xml'
    }

    stage('Build'){
        sh './gradlew clean build'
    }
}
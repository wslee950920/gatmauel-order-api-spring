node('master'){
    stage('Poll'){
        checkout scm
    }

    stage('Unit test'){
        sh './gradlew clean test'
        junit '**/build/test-results/test/*.xml'
    }

    stage('Build'){
        sh './gradlew clean build'
    }
}
#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    stage('check tools and install') {
        sh "java -version"
        sh "./mvnw com.github.eirslett:frontend-maven-plugin:install-node-and-yarn -DnodeVersion=v6.9.5 -DyarnVersion=v0.19.1"
    }

    stage('yarn install') {
        sh "./mvnw com.github.eirslett:frontend-maven-plugin:yarn"
    }
    stage('backend and frontend tests') {
        try {
            sh "./mvnw test"
            sh "yarn test"
        } catch(err) {
            throw err
        } finally {
            junit '**/target/surefire-reports/TEST-*.xml'
            junit '**/target/test-results/karma/TESTS-*.xml'
        }
    }

    stage('packaging & building docker image') {
        sh "./mvnw clean"
        sh "./mvnw package -Pprod docker:build  -DskipTests"
        archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    }


    stage('push docker image') {
        sh "docker save cof:latest  | ssh -C user@your-server.com docker load"
    }

    stage('deploy app.') {
        sh "ssh user@your-server.com 'docker-compose up -d'"
    }
}

pipeline {
	agent any

    environment {
		USER = 'ubuntu'              // 원격 서버의 SSH 사용자 이름
        HOST = '134.185.97.121'
        DIR = '/home/dev/esg-self-assessment'
    }

    triggers {
		githubPush() // GitHub Webhook 트리거
    }

    stages {
		stage('Check Branch') {
			steps {
				script {
					if (env.GIT_BRANCH != 'origin/main') {
						currentBuild.result = 'SUCCESS'
                        return
                    }
                }
            }
        }
        stage('Git Clone') {
			steps {
				git branch: 'main', url: 'https://github.com/jaewoong-gwon/esg-self-assessment.git'
            }
        }
        stage('Deploy') {
			steps {
				sshagent(credentials: ['dev-server-ssh']) {
					sh """
                        ssh -o StrictHostKeyChecking=no ${USER}@${HOST} << EOF
                        cd ${DIR}
                        docker compose down
                        docker compose up -d --build
                        exit
                        EOF
                    """
                }
            }
        }
    }
}

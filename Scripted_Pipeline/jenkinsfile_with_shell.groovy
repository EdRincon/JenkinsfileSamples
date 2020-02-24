/*
* This groovy download and excecute Shell scripts as a basic example of
* an upgrade from a pipeline view of freestyle jobs to a pipeline job
*/
def gitUser = ''
def repositoryURL ='https://github.com/EdRincon/shellExamples.git'
def branch = "master"
def path = "Shells"

node {master} {
	stage ('Setup') {
		try{
			println "Downloading scripts"
			cloneRepository(branch, repositoryURL, path)
			println "setting up permissions"
			sh(script: "chmod -R 777 ${workspace}/${path}")
		} catch (Exception err){
			error("There was an error while trying to setup shell scripts due to: ${err.message}")
		}
	}

	stage ('Compilation & Unit Testing') {
		try{
			println "Application compilation starting"
			sh(script : "${workspace}/${path}/build.sh")
			println "Application compilation completed"
		} catch (Exception err){
			error("Compilation failed due to: ${err.message}")
		}
	}

	stage ('Test') {
		try{
			println "Application testing starting"
			sh(script : "${workspace}/${path}/test.sh")
			println "Application testing completed"
		} catch (Exception err){
			error("Test failed due to: ${err.message}")
		}
	}

	stage ('Deploy') {
		try{
			println "Application deployment starting"
			sh(script : "${workspace}/${path}/deploy.sh")
			println "Application deployment completed"
		} catch (Exception err){
			error("Deployment failed due to: ${err.message}")
		}
	}
}

def cloneRepository(branch, url, path) {
  checkout([$class: 'GitSCM', branches: [[name: "*/${branch}"]], doGenerateSubmoduleConfigurations: false, extensions:
    [[$class: 'RelativeTargetDirectory', relativeTargetDir: "./${path}"],[$class: 'UserExclusion', excludedUsers: ''],
    [$class: 'MessageExclusion', excludedMessage: '(?s).*JENKINS_IGNORE.*']],
    submoduleCfg: [], userRemoteConfigs: [[url: "${url}"]]
  ])
}
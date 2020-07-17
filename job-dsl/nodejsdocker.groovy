job('NodeJS Docker example') {
    scm {
        git('https://github.com/dkr290/docker-demo.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins@localhost.localdomain')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('danikr238/docker-nodejs')
            tag('${GIT_REVISION,length=9}')
            dockerHostURI('tcp://192.168.0.92:2376')
            registryCredentials('danikr238')
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}

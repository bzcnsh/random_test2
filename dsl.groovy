folder('Demo2')

deliveryPipelineView('Demo2/Pipeline') {

    pipelineInstances(5)
    allowPipelineStart()
    enableManualTriggers()
    showChangeLog()
    pipelines {
        component('Component', 'Demo2/Build')
    }

}

job('Demo2/Build') {
    deliveryPipelineConfiguration("Build", "Build")
    scm {
        git {
            remote {
                url('https://github.com/bzcnsh/random_test.git')
            }
        }
    }
    wrappers {
        deliveryPipelineVersion('1.0.0.\$BUILD_NUMBER', true)
    }
    publishers {
        buildPipelineTrigger('Demo2/DeployCI') {
        }
    }
}

job('Demo2/Sonar') {
    deliveryPipelineConfiguration("Build", "Sonar")
    scm {
        git {
            remote {
                url('https://github.com/bzcnsh/random_test.git')
            }
        }
    }

    wrappers {
        buildName('\$PIPELINE_VERSION')
    }

    steps {
        shell(
                'sleep 10'
        )
    }
}

job('Demo2/DeployCI') {
    deliveryPipelineConfiguration("CI", "Deploy")

    wrappers {
        buildName('\$PIPELINE_VERSION')
    }

    steps {
        shell(
                'sleep 5'
        )
    }

    publishers {
        buildPipelineTrigger('Demo2/TestCI') {
        }
    }
}

job('Demo2/TestCI') {
    deliveryPipelineConfiguration("CI", "Test")

    wrappers {
        buildName('\$PIPELINE_VERSION')
    }

    steps {
        shell(
                'sleep 10'
        )
    }


    publishers {
        buildPipelineTrigger('Demo2/DeployQA') {
        }
    }
}

job('Demo2/DeployQA') {
    deliveryPipelineConfiguration("QA", "Deploy")

    wrappers {
        buildName('\$PIPELINE_VERSION')
    }

    steps {
        shell(
                'sleep 5'
        )
    }

    publishers {
        buildPipelineTrigger('Demo2/TestQA') {
        }
    }
}

job('Demo2/TestQA') {
    deliveryPipelineConfiguration("QA", "Test")

    wrappers {
        buildName('\$PIPELINE_VERSION')
    }

    steps {
        shell(
                'sleep 10'
        )
    }


    publishers {
        buildPipelineTrigger('Demo2/DeployProd') {
        }
    }
}

job('Demo2/DeployProd') {
    deliveryPipelineConfiguration("Prod", "Deploy")

    wrappers {
        buildName('\$PIPELINE_VERSION')
    }

    steps {
        shell(
                'sleep 5'
        )
    }

    publishers {
        buildPipelineTrigger('Demo2/TestProd') {
        }
    }
}

job('Demo2/TestProd') {
    deliveryPipelineConfiguration("Prod", "Test")

    wrappers {
        buildName('\$PIPELINE_VERSION')
    }

    steps {
        shell(
                'sleep 5'
        )
    }

}

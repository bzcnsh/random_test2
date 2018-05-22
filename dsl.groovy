folder('Demo2')

deliveryPipelineView('Demo/Pipeline') {

    pipelineInstances(5)
    allowPipelineStart()
    enableManualTriggers()
    showChangeLog()
    pipelines {
        component('Component', 'Demo/Build')
    }

}

job('Demo/Build') {
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
        buildPipelineTrigger('Demo/DeployCI') {
        }
    }
}

job('Demo/Sonar') {
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

job('Demo/DeployCI') {
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
        buildPipelineTrigger('Demo/TestCI') {
        }
    }
}

job('Demo/TestCI') {
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
        buildPipelineTrigger('Demo/DeployQA') {
        }
    }
}

job('Demo/DeployQA') {
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
        buildPipelineTrigger('Demo/TestQA') {
        }
    }
}

job('Demo/TestQA') {
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
        buildPipelineTrigger('Demo/DeployProd') {
        }
    }
}

job('Demo/DeployProd') {
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
        buildPipelineTrigger('Demo/TestProd') {
        }
    }
}

job('Demo/TestProd') {
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

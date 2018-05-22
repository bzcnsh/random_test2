def gituser = 'bzcnsh'
def project = 'random_test'
def branchApi = new URL("https://api.github.com/repos/${gituser}/${project}/branches")
def branches = new groovy.json.JsonSlurper().parse(branchApi.newReader())

folder(project)
branches.each {
  def branchName = it.name
  def testJobName = "${project}-${branchName}-test".replaceAll('/','-')
  def applyJobName = "${project}-${branchName}-apply".replaceAll('/','-')

  deliveryPipelineView("${project}/${branchName}") {
      pipelineInstances(5)
      allowPipelineStart()
      enableManualTriggers()
      showChangeLog()
      pipelines {
          component('Component', testJobName)
      }
  }

  job(testJobName) {
    deliveryPipelineConfiguration(project, "test")
    scm {
        git("git://github.com/${gituser}/${project}.git", branchName)
    }
    wrappers {
        deliveryPipelineVersion('1.0.0.\$BUILD_NUMBER', true)
    }
    steps {
      shell(
        'echo test'
      )
    }
    publishers {
      buildPipelineTrigger(applyJobName) {
      }
    }
  }

  job(applyJobName) {
    deliveryPipelineConfiguration(project, "apply")
    scm {
        git("git://github.com/${gituser}/${project}.git", branchName)
    }
    steps {
      shell(
        'echo apply'
      )
    }
  }
}

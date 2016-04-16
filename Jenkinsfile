node {
  stage 'checkout'
  checkout scm
  
  def projectHome = pwd()
  stage 'build and unit test'
  sh "chmod +x ${projectHome}/gradlew"
  sh "${projectHome}/gradlew clean test"
}

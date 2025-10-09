# Build: CMD에서 Gradle bootBuildImage
custom_build(
    ref='catalog-service',
    command='set EXPECTED_REF=catalog-service:tilt-build && gradlew.bat bootBuildImage --imageName %EXPECTED_REF%',
    deps=['build.gradle', 'src']
)

# Deploy Kubernetes YAML
k8s_yaml([
    'k8s/deployment.yml',
    'k8s/service.yml'
])

# Manage resource with port-forward
k8s_resource(
    'catalog-service',
    port_forwards=['9001']
)

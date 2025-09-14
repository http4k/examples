

dependencies {
    implementation(libs.pulumi.core)
    implementation(libs.pulumi.aws)
    implementation(libs.pulumi.aws.native)
    testRuntimeOnly(libs.junit.platform.launcher)
}

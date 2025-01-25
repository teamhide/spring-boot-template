rootProject.name = "template"
include("template-domain")
include("template-infra")
include("template-api")
include("template-application")
include("template-core")
include("support")
include("support:logging")
findProject(":support:logging")?.name = "logging"

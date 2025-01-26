rootProject.name = "template"
include("template-domain")
include("template-infra")
include("template-api")
include("template-application")
include("template-core")
include("support")
include("support:logging")
findProject(":support:logging")?.name = "logging"
include("template-infra:infra")
findProject(":template-infra:infra")?.name = "infra"
include("template-infra:persistence")
findProject(":template-infra:persistence")?.name = "persistence"

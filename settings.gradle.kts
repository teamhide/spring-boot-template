rootProject.name = "template"
include("template-domain")
include("template-infra")
include("template-api")
include("template-application")
include("template-core")
include("support")
include("support:logging")
findProject(":support:logging")?.name = "logging"
include("template-infra:persistence")
findProject(":template-infra:persistence")?.name = "persistence"
include("support:migration")
findProject(":support:migration")?.name = "migration"
include("template-infra:pg-client")
findProject(":template-infra:pg-client")?.name = "pg-client"

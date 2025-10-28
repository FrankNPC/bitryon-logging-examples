
terraform {
  required_version = ">= 1.13"
  
  required_providers {
    bitryon = {
      source  = "bitryon-io/bitryon-cloud-provider" 
      version = ">= 1.0.51"
    }
  }
}


provider "bitryon" { 
  api_url         = "http://localhost"    # for your dev/test: https://dev-cloud-api.bitryon.io
                                          # for your      prd: https://prd-cloud-api.bitryon.io
                                          # we may change it more formal in the future
  account_api_key = "" # get it from the account.
}

resource "bitryon_cloud_application" "test" {
  name        = "bitryon-your-service"
  retention   = 12312321             # seconds to keep data alive
  tags        = "xxx,few,wwww,22222" # for search purpose
}

output "bitryon_cloud_application" {
  value = bitryon_cloud_application.test
}
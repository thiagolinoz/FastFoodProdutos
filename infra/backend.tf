terraform {
  backend "s3" {
    bucket = "backend-postech-fiap-fastfood"
    key    = "backend/tfstate/terraform.tfstate"
    region = "us-east-1"
  }
}

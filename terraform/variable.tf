# Variables
variable "region" {
  description = "The AWS region to deploy the resources"
  type        = string
  default     = "us-east-1"  # Adjust to your desired AWS region
}

variable "ssh_key_name" {
  description = "The name of the SSH key pair to use for the EC2 instances."
  type        = string
  default     = "cicd"
}

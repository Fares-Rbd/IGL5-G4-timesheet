# Provider Configuration
provider "aws" {
  region = var.region
}

# Data block to get the current AWS Account ID
data "aws_caller_identity" "current" {}

# Create VPC
resource "aws_vpc" "timesheet_vpc" {
  cidr_block = "10.0.0.0/16"

  tags = {
    Name = "timesheet-vpc"
  }
}

# Create Public Subnets in Different Availability Zones
resource "aws_subnet" "public_subnet_a" {
  vpc_id                  = aws_vpc.timesheet_vpc.id
  cidr_block              = "10.0.2.0/24"
  availability_zone       = "us-east-1a"
  map_public_ip_on_launch = true

  tags = {
    Name = "public-subnet-a"
  }
}

resource "aws_subnet" "public_subnet_b" {
  vpc_id                  = aws_vpc.timesheet_vpc.id
  cidr_block              = "10.0.3.0/24"
  availability_zone       = "us-east-1b"
  map_public_ip_on_launch = true

  tags = {
    Name = "public-subnet-b"
  }
}

# Create Internet Gateway
resource "aws_internet_gateway" "igw" {
  vpc_id = aws_vpc.timesheet_vpc.id

  tags = {
    Name = "timesheet-igw"
  }
}

# Route Table for Public Subnet
resource "aws_route_table" "public_route_table" {
  vpc_id = aws_vpc.timesheet_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw.id
  }

  tags = {
    Name = "public-route-table"
  }
}

# Associate Route Table with Public Subnets
resource "aws_route_table_association" "public_route_table_association_a" {
  subnet_id      = aws_subnet.public_subnet_a.id
  route_table_id = aws_route_table.public_route_table.id
}

resource "aws_route_table_association" "public_route_table_association_b" {
  subnet_id      = aws_subnet.public_subnet_b.id
  route_table_id = aws_route_table.public_route_table.id
}

# Create Security Group for Public Node Group
resource "aws_security_group" "timesheet_public_node_sg" {
  vpc_id = aws_vpc.timesheet_vpc.id

  # Allow NodePort traffic on the range 30000-32767
  ingress {
    from_port   = 30000
    to_port     = 32767
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 30000
    to_port     = 32767
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  # Allow access to the Kubernetes API server on port 6443
  ingress {
    from_port   = 6443
    to_port     = 6443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Allow HTTPS access to the control plane (port 443)
  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Allow access to MySQL database on port 3306
  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]  # Modify this for more restrictive access
  }

  # Allow kubelet communication on port 10250
  ingress {
    from_port   = 10250
    to_port     = 10250
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Allow CoreDNS on port 53 (TCP/UDP)
  ingress {
    from_port   = 53
    to_port     = 53
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 53
    to_port     = 53
    protocol    = "udp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Allow etcd communication (if using etcd for HA clusters)
  ingress {
    from_port   = 2379
    to_port     = 2380
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Allow all outbound traffic for nodes to access external services
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "timesheet-public-node-sg"
  }
}


# Create EKS Cluster
resource "aws_eks_cluster" "timesheet_cluster" {
  name     = "timesheet-cluster"
  role_arn = "arn:aws:iam::${data.aws_caller_identity.current.account_id}:role/LabRole"

  vpc_config {
    subnet_ids         = [
      aws_subnet.public_subnet_a.id,
      aws_subnet.public_subnet_b.id
    ]
    security_group_ids = [aws_security_group.timesheet_public_node_sg.id]
  }

  depends_on = []
}

# Create Launch Template for Node Group
resource "aws_launch_template" "node_group_launch_template" {
  name_prefix   = "node-group-launch-template"
  instance_type = "t3.medium"

  # Attach the security group to the launch template
  network_interfaces {
    security_groups = [aws_security_group.timesheet_public_node_sg.id]
  }

  tags = {
    Name = "timesheet-node-group-template"
  }
}

# Create Node Group using Launch Template
resource "aws_eks_node_group" "timesheet_node_group" {
  cluster_name    = aws_eks_cluster.timesheet_cluster.name
  node_group_name = "timesheet-node-group"
  node_role_arn   = "arn:aws:iam::${data.aws_caller_identity.current.account_id}:role/LabRole"
  subnet_ids      = [
    aws_subnet.public_subnet_a.id,
    aws_subnet.public_subnet_b.id
  ]

  # Reference the launch template here
  launch_template {
    id      = aws_launch_template.node_group_launch_template.id
    version = "$Latest"
  }

  scaling_config {
    desired_size = 2
    max_size     = 3
    min_size     = 1
  }

  depends_on = [aws_eks_cluster.timesheet_cluster]
}

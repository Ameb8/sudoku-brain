# Use Ubuntu as base image
FROM ubuntu:22.04

# Install dev tools and libraries
RUN apt-get update && apt-get install -y \
  gcc \
  gdb \
  make \
  curl \
  libcurl4-openssl-dev \
  libcjson-dev \
  vim \
  build-essential \
  ca-certificates \
  openssh-client


WORKDIR /app

CMD [ "bash" ]


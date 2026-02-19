#!/usr/bin/env bash
set -euo pipefail

echo "Starting deploy script..."

: "${SERVICE_NAME:?SERVICE_NAME must be set}"
: "${IMAGE_REGISTRY:?IMAGE_REGISTRY must be set}"
: "${ECR_REPOSITORY:?ECR_REPOSITORY must be set}"
: "${IMAGE_TAG:?IMAGE_TAG must be set}"

ROOT_DIR=$(pwd)
echo "Repository root: $ROOT_DIR"

cd k8s/customer-service

echo "Applying manifests in k8s/customer-service to namespace $SERVICE_NAME"
kubectl apply -k . -n "$SERVICE_NAME"

IMAGE="${IMAGE_REGISTRY}/${ECR_REPOSITORY}:${IMAGE_TAG}"
echo "Updating deployment image to $IMAGE"
kubectl set image deployment/customer-service-deployment \
  customer-service="$IMAGE" -n "$SERVICE_NAME"

echo "Patching deployment to set imagePullPolicy=Always"
kubectl patch deployment customer-service-deployment -n "$SERVICE_NAME" \
  -p '{"spec":{"template":{"spec":{"containers":[{"name":"customer-service","imagePullPolicy":"Always"}]}}}}'

echo "Deploy script completed."

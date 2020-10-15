# Deploy AWS

Para realizar o deploy para a AWS definir as variáveis:

```
export AWS_REGISTRY_URL = $AWS_REGISTRY_URL
export AWS_ACCESS_KEY_ID = $AWS_ACCESS_KEY_ID
export AWS_SECRET_ACCESS_KEY = $AWS_SECRET_ACCESS_KEY
export AWS_DEFAULT_REGION = sa-east-1
export VERSION_NUMBER = 1.0.0
export DEPLOYMENT_ENV = dev
export BUILD_ID = $BUILD_ID
```

Realizar o Login no ECR:
```
$(aws ecr get-login --region ${AWS_DEFAULT_REGION} --no-include-email)
```

Criar a TAG e subir para o AWS ECR:
```
docker tag renatoalejusto/archstock:latest ${AWS_REGISTRY_URL}:latest
docker push ${AWS_REGISTRY_URL}:latest
```

Parar o serviço:
```
$(aws ecs stop-task --cluster $CLUSTER_NAME --task $(aws ecs list-tasks --cluster $CLUSTER_NAME --service $SERVICE_NAME --output text --query taskArns[0]))
```

Parar o serviço:
```
$(aws ecs update-service --cluster $CLUSTER_NAME --service $SERVICE_NAME --task-definition $TASK_FAMILY:$TASK_VERSION)
```

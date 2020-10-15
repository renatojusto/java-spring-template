# Monitoramento

A aplicação utiliza o Spring Actuator para gerar informações para o Prometheus via micrometer.

Para rodar o monitoramento basta executar:

```
docker-compose -f src/main/docker/monitoring.yml up -d
```

O sistema irá subir:

1. Prometheus na porta http://localhost:9090
2. Grafana na porta http://localhost:3000

A porta default da aplicação será host.docker.internal:8081

## Grafana

O usuário e senha do grafana será `admin`. Ele já virá com um dashboard de JVM pronto.

É possível importar nas configurações de datasource um dashboard do Prometheus. 


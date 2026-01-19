FROM python:3.11-alpine

LABEL author="Angel"
LABEL description="Websocket para Tunel VNC a HMI"

RUN apk update && \
    apk add --no-cache git && \
    rm -rf /var/cache/apk/*

WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir websockify -r requirements.txt

RUN mkdir -p /app/token
# RUN chmod +x ./run

EXPOSE 6080

# - Comando de ejecucion directa ****Sin Token Plugin*****
# CMD ["./run", "6081", "127.0.0.1:5900"]

# - token-plugin: plugin estandar
# - token-source: ruta al archivo token.txt

# CMD ["python3", "./websockify", "--auth-plugin", "auth_api", "--auth-source", "ApiAuth", "6081"]

CMD ["websockify","--token-plugin","TokenFile","--token-source","/app/tokens","6081"]


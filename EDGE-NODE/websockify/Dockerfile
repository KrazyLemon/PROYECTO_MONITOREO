FROM python:3.11-alpine

LABEL author="Angel"
LABEL description="Websocket para Tunel VNC a HMI"

RUN apk update && \
    apk add --no-cache git && \
    rm -rf /var/cache/apk/*

WORKDIR /app
RUN git clone https://github.com/novnc/websockify.git .

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

RUN mkdir -p /app/tokens

EXPOSE 6080

# - Comando de ejecucion directa ****Sin Token Plugin*****
# CMD ["./run", "6081", "127.0.0.1:5900"]

# - token-plugin: plugin estandar
# - token-source: ruta al archivo token.txt
CMD [ "python3","./websockify","--token-plugin","TokenFile","--token-source","/app/token","6081" ]

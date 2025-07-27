FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine
RUN apk add --no-cache gettext
COPY --from=build /app/build /usr/share/nginx/html

# Create startup script
RUN echo '#!/bin/sh' > /start.sh && \
    echo 'cat > /etc/nginx/conf.d/default.conf << EOF' >> /start.sh && \
    echo 'server {' >> /start.sh && \
    echo '    listen $PORT;' >> /start.sh && \
    echo '    server_name _;' >> /start.sh && \
    echo '    root /usr/share/nginx/html;' >> /start.sh && \
    echo '    index index.html;' >> /start.sh && \
    echo '    location / {' >> /start.sh && \
    echo '        try_files $uri $uri/ /index.html;' >> /start.sh && \
    echo '    }' >> /start.sh && \
    echo '}' >> /start.sh && \
    echo 'EOF' >> /start.sh && \
    echo 'nginx -g "daemon off;"' >> /start.sh && \
    chmod +x /start.sh

CMD ["/start.sh"]
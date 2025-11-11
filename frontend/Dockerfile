FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine
RUN apk add --no-cache gettext

COPY --from=build /app/build /usr/share/nginx/html
COPY nginx.conf.template /etc/nginx/conf.d/default.conf.template

RUN rm -f /etc/nginx/conf.d/default.conf

CMD envsubst '$PORT' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf && \
    echo "=== PORT is: $PORT ===" && \
    echo "=== Final nginx config ===" && \
    cat /etc/nginx/conf.d/default.conf && \
    echo "=== Files in html directory ===" && \
    ls -la /usr/share/nginx/html/ && \
    nginx -g 'daemon off;'
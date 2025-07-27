FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine
RUN apk add --no-cache gettext
COPY --from=build /app/build /usr/share/nginx/html

# Simple approach: create config at runtime
CMD sh -c 'echo "server { listen $PORT; location / { root /usr/share/nginx/html; try_files \$uri \$uri/ /index.html; } }" > /etc/nginx/conf.d/default.conf && nginx -g "daemon off;"'
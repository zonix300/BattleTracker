FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
RUN echo "=== BUILD STAGE COMPLETE ===" && ls -la build/

FROM nginx:alpine
RUN apk add --no-cache gettext

COPY --from=build /app/build /usr/share/nginx/html

# Debug: Show what files we actually copied
RUN echo "=== FILES IN NGINX HTML DIR ===" && ls -la /usr/share/nginx/html/

# Create simple nginx config without variables first
RUN echo 'server {
    listen 80;
    server_name _;
    root /usr/share/nginx/html;
    index index.html;
    
    location / {
        try_files $uri $uri/ /index.html;
    }
}' > /etc/nginx/conf.d/default.conf

# Show the nginx config
RUN echo "=== NGINX CONFIG ===" && cat /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
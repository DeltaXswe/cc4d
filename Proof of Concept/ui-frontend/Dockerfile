#stage 1
FROM node:16 as node

WORKDIR /app
COPY package.json package-lock.json ./
RUN npm ci

COPY src src
COPY *.json *.js ./
# RUN npm install
RUN npm run build --prod

#stage 2
FROM nginx:alpine
COPY --from=node /app/dist/produlytics-frontend /usr/share/nginx/html
COPY ./ui-backend-proxy.conf /etc/nginx/conf.d/default.conf

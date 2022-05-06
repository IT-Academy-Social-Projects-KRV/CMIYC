### STAGE 1: Build ###
FROM node:latest AS build
WORKDIR /usr/src/app
COPY ../Frontend/package.json ../Frontend/package-lock.json ./
RUN npm install
COPY ../Frontend .
RUN npm run build

### STAGE 2: Run ###
FROM nginx:latest
COPY ../K8s/nginx.conf /etc/nginx/nginx.conf
COPY --from=build /usr/src/app/dist/Frontend /usr/share/nginx/html
EXPOSE 80

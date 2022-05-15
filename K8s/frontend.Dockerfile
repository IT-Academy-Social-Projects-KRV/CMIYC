FROM node:latest AS build
WORKDIR /usr/src/app
COPY Frontend  ./
RUN npm install
RUN npm run build

FROM nginx:latest
COPY K8s/nginx.conf /etc/nginx/nginx.conf
COPY --from=build /usr/src/app/dist/Frontend /usr/share/nginx/html
CMD sh /usr/share/nginx/html/assets/set-env.sh && nginx -g 'daemon off;'
EXPOSE 80
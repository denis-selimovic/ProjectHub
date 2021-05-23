FROM node:14.15-alpine AS node
COPY . /app
WORKDIR /app
RUN npm install -g @angular/cli@11.2.11
RUN npm install
RUN ng build --prod

FROM nginx:alpine
COPY --from=node /app/dist/frontend /usr/share/nginx/html
EXPOSE 80

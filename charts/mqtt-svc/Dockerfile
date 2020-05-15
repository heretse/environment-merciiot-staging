FROM node:8

ENV NODE_ENV development

RUN mkdir /opt/mqtt-svc

WORKDIR /opt/mqtt-svc

COPY . .
#install node-module
RUN npm install

# port 1883 for mqtt server
EXPOSE 1883

CMD node ./server.js
FROM node:6

ENV NODE_ENV development
COPY ./src /home/node/app
WORKDIR /home/node/app

#install node-module
RUN npm install

# port 1883 for mqtt server
EXPOSE 1883

CMD node ./server.js

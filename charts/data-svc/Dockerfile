FROM nodered/node-red-docker:slim-v8

RUN npm install --save node-red-contrib-crypto-js

RUN npm install --save node-red-contrib-moment

RUN npm install --save node-red-contrib-redis@1.1.7

RUN npm install --save node-red-node-cf-cloudant

RUN npm install --save node-red-node-mongodb

RUN npm install --save node-red-node-mysql

RUN npm install --save node-red-contrib-objectid

WORKDIR /data

COPY ./flows.json flows.json

COPY ./flows_cred.json flows_cred.json

COPY ./package.json package.json

COPY ./settings.js settings.js

WORKDIR /usr/src/node-red
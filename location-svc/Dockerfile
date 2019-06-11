FROM nodered/node-red-docker

RUN npm install --save node-red-contrib-redis

RUN npm install --save node-red-node-cf-cloudant

RUN npm install --save node-red-node-mysql

WORKDIR /data

COPY ./flows.json flows.json

COPY ./flows_cred.json flows_cred.json

COPY ./package.json package.json

COPY ./settings.js settings.js

WORKDIR /usr/src/node-red
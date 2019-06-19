FROM nodered/node-red-docker

RUN npm install --save node-red-contrib-amqp2

RUN npm install --save node-red-contrib-crypto-js

RUN npm install --save node-red-contrib-moment

RUN npm install --save node-red-contrib-objectid

RUN npm install --save node-red-contrib-redis

RUN npm install --save node-red-node-cf-cloudant

RUN npm install --save node-red-node-mongodb

RUN npm install --save node-red-node-mysql

WORKDIR /data

COPY ../../alt-svc/flows.json flows.json

COPY ../../alt-svc/flows_cred.json flows_cred.json

COPY ../../alt-svc/package.json package.json

COPY ../../alt-svc/settings.js settings.js

WORKDIR /usr/src/node-red
FROM nodered/node-red-docker

RUN npm install --save node-red-contrib-moment

RUN npm install --save node-red-contrib-redis

RUN npm install --save node-red-node-cf-cloudant

RUN npm install --save node-red-node-mongodb

RUN npm install --save node-red-node-mysql

WORKDIR /data

COPY ../../packet-svc/flows.json flows.json

COPY ../../packet-svc/flows_cred.json flows_cred.json

COPY ../../packet-svc/package.json package.json

COPY ../../packet-svc/settings.js settings.js

WORKDIR /usr/src/node-red
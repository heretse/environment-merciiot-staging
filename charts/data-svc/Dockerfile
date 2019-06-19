FROM nodered/node-red-docker

RUN npm install --save node-red-contrib-crypto-js

RUN npm install --save node-red-contrib-moment

RUN npm install --save node-red-contrib-redis

RUN npm install --save node-red-node-cf-cloudant

RUN npm install --save node-red-node-mongodb

RUN npm install --save node-red-node-mysql

WORKDIR /data

COPY ../../data-svc/flows.json flows.json

COPY ../../data-svc/flows_cred.json flows_cred.json

COPY ../../data-svc/package.json package.json

COPY ../../data-svc/settings.js settings.js

WORKDIR /usr/src/node-red
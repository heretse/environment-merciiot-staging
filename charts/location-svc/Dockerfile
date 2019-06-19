FROM nodered/node-red-docker

RUN npm install --save node-red-contrib-redis

RUN npm install --save node-red-node-cf-cloudant

RUN npm install --save node-red-node-mysql

WORKDIR /data

COPY ../../location-svc/flows.json flows.json

COPY ../../location-svc/flows_cred.json flows_cred.json

COPY ../../location-svc/package.json package.json

COPY ../../location-svc/settings.js settings.js

WORKDIR /usr/src/node-red
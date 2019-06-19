FROM nodered/node-red-docker

RUN npm install --save node-red-contrib-excel

RUN npm install --save node-red-contrib-moment

RUN npm install --save node-red-contrib-redis

RUN npm install --save node-red-node-cf-cloudant

RUN npm install --save node-red-node-mongodb

RUN npm install --save node-red-node-mysql

WORKDIR /data

COPY ../../report-svc/flows.json flows.json

COPY ../../report-svc/flows_cred.json flows_cred.json

COPY ../../report-svc/package.json package.json

COPY ../../report-svc/settings.js settings.js

WORKDIR /usr/src/node-red
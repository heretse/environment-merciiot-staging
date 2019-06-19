FROM nodered/node-red-docker

RUN npm install --save node-red-contrib-crypto-js

RUN npm install --save node-red-contrib-nr-ldapauth

RUN npm install --save node-red-contrib-redis

RUN npm install --save node-red-node-cf-cloudant

RUN npm install --save node-red-node-mysql

RUN npm install --save node-red-contrib-moment

WORKDIR /data

COPY ../../am-svc/flows.json flows.json

COPY ../../am-svc/flows_cred.json flows_cred.json

COPY ../../am-svc/package.json package.json

COPY ../../am-svc/settings.js settings.js

WORKDIR /usr/src/node-red

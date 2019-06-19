FROM nodered/node-red-docker

RUN npm install --save node-red-bluemix-nodes

RUN npm install --save node-red-contrib-redis

RUN npm install --save node-red-node-cf-cloudant

RUN npm install --save node-red-node-mysql

WORKDIR /data

COPY ../../layout-svc/flows.json flows.json

COPY ../../layout-svc/flows_cred.json flows_cred.json

COPY ../../layout-svc/package.json package.json

COPY ../../layout-svc/settings.js settings.js

WORKDIR /usr/src/node-red
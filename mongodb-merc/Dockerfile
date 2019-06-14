FROM mongo:3.6
ADD db_build.sh /docker-entrypoint-initdb.d/
ADD mongo-default /docker-entrypoint-initdb.d/
ADD addUser.js /docker-entrypoint-initdb.d/
RUN chmod +x /docker-entrypoint-initdb.d/db_build.sh
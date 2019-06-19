FROM mongo:3.6
ADD ../../mongodb-merc/db_build.sh /docker-entrypoint-initdb.d/
ADD ../../mongodb-merc/mongo-default /docker-entrypoint-initdb.d/
ADD ../../mongodb-merc/addUser.js /docker-entrypoint-initdb.d/
RUN chmod +x /docker-entrypoint-initdb.d/db_build.sh
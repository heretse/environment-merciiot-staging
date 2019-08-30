#! /bin/bash
date=$(date '+%Y%m%d')
declare -a backup_folders=("argi_dev")
for backup in "${backup_folders[@]}"
do
   echo "Bulid $backup"
   mongorestore -h 127.0.0.1 --port 27017 -u $MONGO_INITDB_ROOT_USERNAME -p $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase admin -d $backup /docker-entrypoint-initdb.d/$backup
done

declare -a backup_auth_folders=("authdb")
for backup_auth in "${backup_auth_folders[@]}"
do
   echo "Bulid $backup_auth"
   mongorestore -h 127.0.0.1 --port 27017 -u $MONGO_INITDB_ROOT_USERNAME -p $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase admin -d $backup_auth /docker-entrypoint-initdb.d/$backup_auth
done

echo "Add user to access db argi_dev"
mongo "argi_dev" -u $MONGO_INITDB_ROOT_USERNAME -p $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase admin /docker-entrypoint-initdb.d/addUser.js

echo "Add user to access db authdb"
mongo "authdb" -u $MONGO_INITDB_ROOT_USERNAME -p $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase admin /docker-entrypoint-initdb.d/addAuthUser.js